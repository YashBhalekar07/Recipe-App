package com.example.recipeapp

import androidx.room.Query

interface Dao { // query and fun define karte hai

    @get:Query("SELECT * FROM recipe")
    var all:List<Recipe?>?

}