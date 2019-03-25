package com.example.gamebacklog

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private var respository: GameRepository? = null
    private var games: LiveData<List<Game>>? = null

    init {
        respository = GameRepository(application.applicationContext)
        games = respository?.getAllGames()
    }

    fun getAllGames(): LiveData<List<Game>> {
        return games!!
    }


    fun insert(game: Game) {
       respository?.insert(game)
    }


    fun update(game: Game) {
        respository?.update(game)
    }


    fun delete(game: Game) {
        respository?.delete(game)
    }

    fun deleteAll(games: List<Game>) {
        respository?.deleteAllGames(games)
    }

}