package com.example.gamebacklog

import androidx.room.Entity
import androidx.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import java.util.*

@Entity
class Game(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    var title: String,
    var platform: String,
    var status: Int,
    var date: String) : Parcelable {

    private constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(title)
        parcel.writeString(platform)
        parcel.writeInt(status)
        parcel.writeString(date)

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Game> {
        override fun createFromParcel(parcel: Parcel): Game {
            return Game(parcel)
        }

        override fun newArray(size: Int): Array<Game?> {
            return arrayOfNulls(size)
        }
    }
}

// A game consists of a title, platform, date and a status.