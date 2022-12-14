package com.example.user

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_details")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,
    val name:String,
    val Email:String,
    val Phone:String,
    val bitmap: Bitmap
)
