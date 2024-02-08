package com.example.cocktailtest2.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktailtest2.Class.Ingredient
import com.example.cocktailtest2.R

class IngredientDrinkDetailAdapter(private val context: Context, private val ingredient: ArrayList<Ingredient>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val VIEW_TYPE_EMPTY = 0
        const val VIEW_TYPE_ITEM = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_EMPTY) {
            val view = LayoutInflater.from(context).inflate(R.layout.ingredient_detail_drink_exception_view, parent, false)
            EmptyViewHolder(view)
        } else {
            val view = LayoutInflater.from(context).inflate(R.layout.ingredient_deail_drink_view, parent, false)
            ItemViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return if (ingredient.isEmpty()) {
            1
        } else {
            ingredient.size
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == VIEW_TYPE_ITEM) {
            (holder as ItemViewHolder).bind(ingredient[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (ingredient.isEmpty()) {
            VIEW_TYPE_EMPTY
        } else {
            VIEW_TYPE_ITEM
        }
    }

    class EmptyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ingredientName: TextView = itemView.findViewById(R.id.ingredientName)
        private val ingredientMeasure: TextView = itemView.findViewById(R.id.ingredientMeasure)

        fun bind(item: Ingredient) {
            ingredientName.text = item.name
            ingredientMeasure.text = item.measure
        }
    }
}