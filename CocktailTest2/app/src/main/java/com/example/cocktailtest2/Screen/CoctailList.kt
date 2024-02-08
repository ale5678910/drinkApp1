package com.example.cocktailtest2.Screen

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cocktailtest2.Adapter.DrinkAdapter
import com.example.cocktailtest2.Class.Drink
import com.example.cocktailtest2.R
import com.example.cocktailtest2.ViewModel.CocktailListViewModel
import com.example.cocktailtest2.databinding.ActivityCoctailListBinding
import java.util.Locale

class CoctailList : AppCompatActivity() {

    private lateinit var binding : ActivityCoctailListBinding
    private val viewModel : CocktailListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coctail_list)
        binding = ActivityCoctailListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        makeAllInvisible()
        viewModel.getDrink()

        viewModel.error.observe(this){
            if (!it){
                makeAllVisible()
            }else{
                makeAllInvisible()
                binding.progress.visibility = View.INVISIBLE
                visibilityRefreshPage()
                binding.errorMessage.text = viewModel.errorMessage.value
            }
        }
        binding.refreshButton.setOnClickListener {
            viewModel.getDrink()
            makeAllInvisible()
            binding.progress.visibility = View.VISIBLE
        }

        viewModel.drink.observe(this){
            val adapt = DrinkAdapter(it,this)
            binding.recycle.apply {
                adapter = adapt
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
            }
            binding.searchCocktail.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    if (p0 != null) {
                        val filteredList = ArrayList<Drink>()
                        for (i in it) {
                            if (i.name.lowercase(Locale.ROOT).contains(p0)) {
                                filteredList.add(i)
                            }
                        }
                        adapt.setFilteredList(filteredList)
                        if (filteredList.isEmpty()) {
                            binding.errorMessage.visibility = View.VISIBLE
                            binding.errorMessage.text = "No Drink found"
                        } else{
                            binding.errorMessage.visibility = View.INVISIBLE
                        }
                    }
                    return true
                }
            })
        }

        binding.addCoctailButton.setOnClickListener {
            val intent = Intent(this,AddCocktailActivity::class.java)
            startActivity(intent)
        }
    }
    private fun makeAllInvisible(){
        binding.searchCocktail.visibility = View.INVISIBLE
        binding.recycle.visibility = View.INVISIBLE
        binding.addCoctailButton.isClickable = false
        binding.errorMessage.visibility = View.INVISIBLE
        binding.refreshButton.visibility = View.INVISIBLE
    }

    private fun makeAllVisible(){
        binding.searchCocktail.visibility = View.VISIBLE
        binding.recycle.visibility = View.VISIBLE
        binding.addCoctailButton.isClickable = true
        binding.progress.visibility = View.INVISIBLE
        binding.errorMessage.visibility = View.INVISIBLE
        binding.refreshButton.visibility = View.INVISIBLE
    }

    private fun visibilityRefreshPage(){
        binding.errorMessage.visibility = View.VISIBLE
        binding.refreshButton.visibility = View.VISIBLE
    }

}