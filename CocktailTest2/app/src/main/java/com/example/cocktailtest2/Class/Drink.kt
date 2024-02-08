package com.example.cocktailtest2.Class

import java.io.Serializable


data class Drink(
    var id: Int,
    var name:String,
    var alternate_name: String?,
    var alcoholic: Boolean?,
    var glass: String?,
    var category: String,
    var url_thumb: String,
    var image_attribution: String?,
    var image_source: String?,
    var video: String?,
    var tags: String?,
    var iba: String?,
    var creative_commons: String?,
    var instructions: ArrayList<Instruction>?,
    var ingredients: ArrayList<Ingredient>,
): Serializable
