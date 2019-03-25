package com.example.gamebacklog

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;

import kotlinx.android.synthetic.main.activity_update_game.*
import kotlinx.android.synthetic.main.create_gamebacklog.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.logging.SimpleFormatter

class UpdateGame : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_game)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (intent.hasExtra("UPDATE_GAME")) {
            setValue()
        }
            fab.setOnClickListener { view ->

                if(gameTitleView.text.isEmpty()) {
                    Snackbar.make(view, "Fill in all the textfields", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()
                } else {

                    val title = gameTitleView.text.toString()
                    val platform = gamePlatformView.text.toString()
                    val status = gameStatusView.selectedItemPosition
                    val id = Date().getTime()

                    if(intent.hasExtra("UPDATE_GAME")) {
                        val game: Game = intent.getParcelableExtra("UPDATE_GAME")
                        game.title = title
                        game.platform = platform
                        game.status = status
                        intent.putExtra("GAME_UPDATED", game)
                        setResult(Activity.RESULT_FIRST_USER, intent)
                        this.finish()
                    } else  if(intent.hasExtra("NEW_GAME")){
                        val date = SimpleDateFormat("dd/MM/yyyy").format(Date())
                        val game = Game(id, title, platform, status, date.toString())
                        val intent = Intent(this, MainActivity::class.java)
                        intent.putExtra("NEW_GAME_ADDED", game)
                        setResult(Activity.RESULT_OK, intent)
                        this.finish()
                    }

                }

        }


    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setValue() {
        val game: Game = intent.getParcelableExtra("UPDATE_GAME")
        gameTitleView.setText(game.title)
        gamePlatformView.setText(game.platform)
        gameStatusView.setSelection(game.status)
    }

}
