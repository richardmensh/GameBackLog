package com.example.gamebacklog

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.game_view_cell.view.*


class GameViewHolder (val view: View) : RecyclerView.ViewHolder(view) {

    fun bind(game: Game, pos: Int, listener: (Game) -> Unit) = with(itemView) {

        gameTitle.text = game.title
        gamePlatform.text = game.platform
        val stringArray = resources.getStringArray(R.array.status_entries)
        arrayOf(stringArray)
        println(stringArray.size)
        gameStatus.text = stringArray[game.status].toString()

        gameDate.text = game.date
        view.setOnClickListener {
            listener(game)
        }

    }

}