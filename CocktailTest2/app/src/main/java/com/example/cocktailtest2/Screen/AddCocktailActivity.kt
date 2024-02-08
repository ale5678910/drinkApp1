package com.example.cocktailtest2.Screen

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cocktailtest2.Adapter.CocktailIngredientAdapter
import com.example.cocktailtest2.Class.CocktailIngredient
import com.example.cocktailtest2.R
import com.example.cocktailtest2.databinding.ActivityAddCocktailBinding
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class AddCocktailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAddCocktailBinding
    private  var ingredient = ArrayList<CocktailIngredient>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_cocktail)
        binding = ActivityAddCocktailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val items = arrayOf("English", "Italian", "French", "German")
        (binding.dropdownMenu2 as MaterialAutoCompleteTextView).setSimpleItems(items)
        binding.dropdownMenu2.setDropDownBackgroundDrawable(
            ColorDrawable(ContextCompat.getColor(this,R.color.white))
        )

        val adapt = CocktailIngredientAdapter(ingredient)
        val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val ingredientRecived = data?.getSerializableExtra("key") as CocktailIngredient
                val typeOfOperation = data.getIntExtra("type",2)
                if (typeOfOperation == 0){
                    ingredient.add(ingredientRecived)
                }
                if (typeOfOperation == 1){
                    val position = data.getIntExtra("position",0)
                    ingredient[position] = ingredientRecived
                }
                adapt.notifyDataSetChanged()
            }
        }
        adapt.setOnCallBack(object : CocktailIngredientAdapter.AdapterCallBack{
            override fun deleteIngredient(position: Int) {
                val builder = AlertDialog.Builder(this@AddCocktailActivity)
                builder.setTitle("ATENSION ATENSION")
                builder.setMessage("Sei sicuro di voler eliminare il dato?")
                builder.setPositiveButton("Yes") { _, _ ->
                    ingredient.removeAt(position)
                    adapt.notifyDataSetChanged()
                }
                builder.setNegativeButton("No") { _, _ ->
                }
                builder.show()
            }
            override fun updateIngredient(position: Int, ingredient: CocktailIngredient) {
                val intent = Intent(this@AddCocktailActivity,AddIngredientActivity::class.java)
                intent.putExtra("type",1)
                intent.putExtra("position",position)
                intent.putExtra("ingredient",ingredient)
                resultLauncher.launch(intent)
            }
        })
        binding.listIngredient.apply {
            adapter = adapt
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        }

        binding.buttonAddIngredient.setOnClickListener {
            val intent = Intent(this,AddIngredientActivity::class.java)
            intent.putExtra("type",0)
            resultLauncher.launch(intent)

        }
        binding.buttonBack.setOnClickListener {
            this.finish()
        }
        binding.buttonCreateCocktail.setOnClickListener {
            if(!binding.dropdownMenu2.text.isNullOrEmpty()){
                if(ingredient.size > 1){
                    if (checkForm()){
                        Log.d("testt",binding.imputCoctailName.text.toString())
                        Log.d("testt",binding.imputCoctailCategory.text.toString())
                        Log.d("testt",binding.imputCoctailInstruction.text.toString())
                        for (element in ingredient){
                            Log.d("testt", element.name +" "+ element.measure)
                        }
                        Log.d("testt",binding.dropdownMenu2.text.toString())
                        if (binding.checkboxAlcoholic.isChecked){
                            Log.d("testt","Alcoholic")
                        }else{
                            Log.d("testt","Not Alcoholic")
                        }
                    }
                }else{
                    val builder = AlertDialog.Builder(this@AddCocktailActivity)
                    builder.setTitle("ATENSION ATENSION")
                    builder.setMessage("Non hai abbastanza ingredienti")
                    builder.setPositiveButton("Ok") { _, _ ->
                    }
                    builder.show()
                }
            }else{
                val builder = AlertDialog.Builder(this@AddCocktailActivity)
                builder.setTitle("ATENSION ATENSION")
                builder.setMessage("Non hai selezionato la lingua")
                builder.setPositiveButton("Ok") { _, _ ->
                }
                builder.show()
            }
        }
    }
    private fun isEmptyTextField(layout: TextInputLayout,imput: TextInputEditText): Boolean {
        return if(imput.text.isNullOrEmpty()){
            layout.error = "this can't be null"
            false
        }else{
            layout.error = null
            true
        }
    }
    private fun checkForm() : Boolean{
        return isEmptyTextField(binding.titleTextInputLayout,binding.imputCoctailName) &&
            isEmptyTextField(binding.categoryTextInputLayout,binding.imputCoctailCategory) &&
            isEmptyTextField(binding.instructionTextInputLayout,binding.imputCoctailInstruction)
    }
}