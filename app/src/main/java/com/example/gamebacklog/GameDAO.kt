package com.example.gamebacklog

import androidx.room.Dao
import androidx.room.*
import androidx.room.OnConflictStrategy
import androidx.lifecycle.LiveData

@Dao
interface GameDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGame(game: Game)

    @Update
    fun updateGame(game: Game)

    @Delete
    fun deleteGame(game: Game)

    @Delete
    fun deleteGame(game: List<Game>)

    @Query("SELECT * FROM Game")
    fun getGames(): LiveData<List<Game>>

}