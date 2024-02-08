package com.example.cocktailtest2.Class

import java.io.Serializable

data class Instruction(
    var language: String,
    var text: String?,
): Serializable
