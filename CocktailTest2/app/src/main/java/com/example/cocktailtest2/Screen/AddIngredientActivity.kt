package com.example.cocktailtest2.Screen

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cocktailtest2.Class.CocktailIngredient
import com.example.cocktailtest2.R
import com.example.cocktailtest2.databinding.ActivityAddIngredientBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class AddIngredientActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAddIngredientBinding
    private var ingredient = CocktailIngredient("","")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_ingredient)
        binding = ActivityAddIngredientBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val type = intent.getIntExtra("type",2)
        val positionIngredient = intent.getIntExtra("position",0)
        if (type == 1){
            val updateIngredient = intent.getSerializableExtra("ingredient") as CocktailIngredient
            ingredient = updateIngredient
            setIngredient(ingredient)
            binding.titleScreen.text = "Modify the ingredient"
            binding.buttonAddIngredient.text = "Modify"
        }

        val resultIntent = Intent()
        binding.buttonAddIngredient.setOnClickListener {
            if (chackForm()){
                getIngredientFromForm()
                resultIntent.putExtra("key", ingredient)
                resultIntent.putExtra("type",type)
                resultIntent.putExtra("position",positionIngredient)
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
        }
        binding.buttonBack.setOnClickListener {
            this.finish()
        }
    }
    private fun isEmptyTextField(layout: TextInputLayout, imput: TextInputEditText): Boolean {
        return if(imput.text.isNullOrEmpty()){
            layout.error = "this can't be null"
            false
        }else{
            layout.error = null
            true
        }
    }
    private fun chackForm() : Boolean{
        return isEmptyTextField(binding.nameIngredientTextInputLayout,binding.imputIngredientName) &&
                isEmptyTextField(binding.measureIngredientTextInputLayout,binding.imputIngredientMeasure)
    }
    private fun setIngredient(ingredient: CocktailIngredient){
        binding.imputIngredientName.setText(ingredient.name)
        binding.imputIngredientMeasure.setText(ingredient.measure)
    }
    fun getIngredientFromForm(){
        ingredient.measure = binding.imputIngredientMeasure.text.toString()
        ingredient.name = binding.imputIngredientName.text.toString()

    }


}