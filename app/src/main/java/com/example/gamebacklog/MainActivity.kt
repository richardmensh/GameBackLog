package com.example.gamebacklog

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    private var games: List<Game> = ArrayList()
    private var adapter: RecyclerAdapter? = null
    private var mainViewModel: MainViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        gamesRecylcerView.layoutManager = LinearLayoutManager(this)
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        mainViewModel?.getAllGames()?.observe(this, Observer { games ->
            // Update the cached copy of the words in the adapter.
            this.games = games
            updateUI()
        })

        fab.setOnClickListener { view ->
            val intent = Intent(this, UpdateGame::class.java)
            intent.putExtra("NEW_GAME", "")
            this.startActivityForResult(intent, 5 )
        }

        initSwipe()
    }

    private fun updateUI() {
        if (adapter == null) {
            adapter = RecyclerAdapter(games, this, listener = {
                val intent = Intent(this, UpdateGame::class.java)
                intent.putExtra("UPDATE_GAME", it)
                this.startActivityForResult(intent, 5 )
            })
            gamesRecylcerView.adapter = adapter
        } else {
            adapter?.swapedList(games)
        }

    }

    private fun initSwipe() {
        val simpleItemTouchCallback =
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

                override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition
                    mainViewModel?.delete(games[position])
                }

            }

        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(gamesRecylcerView)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 5) {
            if (resultCode ==  Activity.RESULT_OK) {
                val game: Game = data!!.getParcelableExtra("NEW_GAME_ADDED")
                mainViewModel?.insert(game)
            } else if(resultCode ==  Activity.RESULT_FIRST_USER) {
                val game: Game = data!!.getParcelableExtra("GAME_UPDATED")
                mainViewModel?.update(game)
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        if (id == R.id.action_delete_item) {
            mainViewModel?.deleteAll(games)
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}
