package com.example.cocktailtest2.ViewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cocktailtest2.Class.Drink
import com.example.cocktailtest2.Request.ApiInterface
import com.example.cocktailtest2.Request.RetrofitClient
import kotlinx.coroutines.launch

class CocktailListViewModel : ViewModel() {
    var error = MutableLiveData<Boolean>()
    var errorMessage = MutableLiveData<String>()
    var drink = MutableLiveData<ArrayList<Drink>>()

    fun getDrink() {
        val retrofit = RetrofitClient.getDrink()
        val apiInterface = retrofit.create(ApiInterface::class.java)
        viewModelScope.launch {
            try {
                val response = apiInterface.getDrink()
                if (response.isSuccessful) {
                    val body = response.body()
                    Log.d("testt", body.toString())
                    if (body != null){
                        drink.value = body!!
                    }
                }
                error.value = false
            }catch (e:Exception){
                errorMessage.value = e.message
                e.cause?.let { Log.d("Error", it.toString()) }
                e.localizedMessage?.let { Log.e("Error", it) }
                error.value = true
            }
        }
    }
}