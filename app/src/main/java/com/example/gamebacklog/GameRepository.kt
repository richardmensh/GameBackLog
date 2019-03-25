package com.example.gamebacklog

import android.content.Context
import java.util.concurrent.Executors
import androidx.lifecycle.LiveData

class GameRepository {
    private var db: AppDatabase? = null
    private var gameDAO: GameDAO? = null
    private var games: LiveData<List<Game>>? = null
    private var executor = Executors.newSingleThreadExecutor()

    constructor(context: Context) {
        db = AppDatabase.getAppDataBase(context)
        gameDAO = db?.gameDAO()
        games = gameDAO?.getGames()

    }

    fun getAllGames(): LiveData<List<Game>> {
        return games!!
    }


    fun insert(game: Game) {
        executor.execute {
            gameDAO?.insertGame(game)
        }
    }


    fun update(game: Game) {
        executor.execute {
            gameDAO?.updateGame(game)
        }
    }


    fun delete(game: Game) {
        executor.execute {
            gameDAO?.deleteGame(game)
        }
    }

    fun deleteAllGames(games: List<Game>) {
        executor.execute {
            gameDAO?.deleteGame(games)
        }
    }

}
