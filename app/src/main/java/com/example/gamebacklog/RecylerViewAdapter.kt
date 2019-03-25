package com.example.gamebacklog

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

class RecyclerAdapter(var items : List<Game>, val context: Context, val listener: (Game) -> Unit) : RecyclerView.Adapter<GameViewHolder>()  {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int ): GameViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val cellForRow = layoutInflater.inflate(R.layout.game_view_cell, p0, false)

        return GameViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int ) {
        holder.bind(items[position], position, listener = listener)
    }

    fun swapedList(newList: List<Game>) {
        items = newList
        if (newList != null) {
            this.notifyDataSetChanged()
        }

    }


}