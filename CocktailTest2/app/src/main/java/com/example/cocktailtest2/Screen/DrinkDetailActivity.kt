package com.example.cocktailtest2.Screen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.cocktailtest2.Adapter.IngredientDrinkDetailAdapter
import com.example.cocktailtest2.Class.Drink
import com.example.cocktailtest2.R
import com.example.cocktailtest2.databinding.ActivityDrinkDetailBinding

class DrinkDetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDrinkDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drink_detail)
        binding = ActivityDrinkDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val drink: Drink = intent.getSerializableExtra("drink") as Drink
        binding.coctailName.text = drink.name

        val options = RequestOptions()
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)

        Glide.with(this)
            .load(drink.url_thumb)
            .apply(options)
            .into(binding.coctailImage)
        if (!drink.instructions.isNullOrEmpty()){
            binding.drinkInstruction.text = drink.instructions!![0].text
        }else{
            binding.drinkInstruction.text = "not found"
        }

        val adapt = IngredientDrinkDetailAdapter(this,drink.ingredients)
        binding.ingredientList.apply {
            adapter = adapt
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        }

        binding.buttonBack.setOnClickListener {
            this.finish()
        }
    }
}