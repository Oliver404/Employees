package com.oliverbotello.employeslocation.entities

import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName
import kotlin.random.Random


class Location (
    @ColumnInfo(name = "latitude")
    @SerializedName(value = "latitude", alternate = ["lat"])
    var latitude: String = "",
    @ColumnInfo(name = "longitude")
    @SerializedName(value = "longitude", alternate = ["log"])
    var longitude: String = ""
) {
    companion object {
        fun generateRandomLocation(): Location = Location(
            "%.6f".format(Random.nextDouble(14.53588, 32.62781)),
            "%.6f".format(Random.nextDouble(-117.06583, -86.73105))
        )
    }

    override fun toString(): String {
        return "${this.latitude}, ${this.longitude}"
    }
}