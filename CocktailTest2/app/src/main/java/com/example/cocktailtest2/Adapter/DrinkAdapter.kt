package com.example.cocktailtest2.Adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.cocktailtest2.Class.Drink
import com.example.cocktailtest2.R
import com.example.cocktailtest2.Screen.DrinkDetailActivity

class DrinkAdapter (private var drink: ArrayList<Drink>, private val context: Activity)
    : RecyclerView.Adapter<DrinkAdapter.CustomViewHolder>() {

    class CustomViewHolder(val view: ViewGroup)
        : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.post_adapter_view, parent, false) as ViewGroup
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val item = drink[position]
        val title = holder.view.findViewById<TextView>(R.id.titlePost)
        title.text = item.name
        val image = holder.view.findViewById<ImageView>(R.id.coctailImage)
        val category = holder.view.findViewById<TextView>(R.id.coctailCategory)
        category.text = item.category
        val cocktailCard = holder.view.findViewById<ConstraintLayout>(R.id.cocktailCard)

        val options = RequestOptions()
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)

        Glide.with(context)
            .load(item.url_thumb)
            .apply(options)
            .into(image)
        cocktailCard.setOnClickListener {
            val intent = Intent(context, DrinkDetailActivity::class.java)
            intent.putExtra("drink",item)
            context.startActivity(intent)
        }
    }
    fun setFilteredList(drink: ArrayList<Drink>){
        this.drink = drink
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return drink.size
    }
}