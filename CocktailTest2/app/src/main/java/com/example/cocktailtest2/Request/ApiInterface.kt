package com.example.cocktailtest2.Request

import com.example.cocktailtest2.Class.Drink
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {

    @GET("/api/drink/getall")
    suspend fun getDrink(): Response<ArrayList<Drink>>
}