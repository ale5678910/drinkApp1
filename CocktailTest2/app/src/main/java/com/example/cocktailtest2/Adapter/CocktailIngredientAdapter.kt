package com.example.cocktailtest2.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktailtest2.Class.CocktailIngredient
import com.example.cocktailtest2.R

class CocktailIngredientAdapter(private val ingredient: ArrayList<CocktailIngredient>)
    : RecyclerView.Adapter<CocktailIngredientAdapter.CustomViewHolder>()  {

    class CustomViewHolder(val view: ViewGroup)
        : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ingredient_add_cocktail_view, parent, false) as ViewGroup
        return CustomViewHolder(view)
    }

    override fun getItemCount(): Int {
        return  ingredient.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val item = ingredient[position]
        val textName = holder.view.findViewById<TextView>(R.id.ingredientName)
        textName.text = item.name
        val textMeasure = holder.view.findViewById<TextView>(R.id.ingredientMeasure)
        textMeasure.text = item.measure
        val buttonDeleteIngredient = holder.view.findViewById<ImageView>(R.id.buttonDeleteIngredient)
        buttonDeleteIngredient.setOnClickListener {
            listener?.deleteIngredient(position)
        }
        val buttonUpdateIngredient = holder.view.findViewById<ImageView>(R.id.buttonUpdateIngredient)
        buttonUpdateIngredient.setOnClickListener {
            listener?.updateIngredient(position,item)
        }

    }
    interface AdapterCallBack{
        fun deleteIngredient(position: Int)
        fun updateIngredient(position: Int,ingredient: CocktailIngredient)
    }
    private  var listener : AdapterCallBack? = null

    fun setOnCallBack(callBack: AdapterCallBack){
        listener = callBack
    }
}