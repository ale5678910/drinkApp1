package com.example.cocktailtest2.Class

import java.io.Serializable


data class Ingredient(
    var name: String,
    var measure: String,
    var id: Int
): Serializable
