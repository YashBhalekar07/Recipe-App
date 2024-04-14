package com.example.recipeapp

import androidx.room.Dao
import androidx.room.Query

@Dao
interface Dao { // query and fun define karte hai

    @Query("SELECT * FROM recipe")
    fun getAll():List<Recipe?>?

}