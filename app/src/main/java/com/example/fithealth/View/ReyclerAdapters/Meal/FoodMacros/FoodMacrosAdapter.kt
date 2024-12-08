package com.example.fithealth.View.ReyclerAdapters.Meal.FoodMacros

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fithealth.Model.DataClass.FoodApiResources.FoodIdSearch.Serving
import com.example.fithealth.databinding.ItemMealMacrosBinding

class FoodMacrosAdapter(private val macrosList: List<Serving>) :
    RecyclerView.Adapter<FoodMacrosViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodMacrosViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemMealMacrosBinding.inflate(layoutInflater, parent, false)
        return FoodMacrosViewHolder(binding)
    }

    override fun getItemCount(): Int = macrosList.size

    override fun onBindViewHolder(holder: FoodMacrosViewHolder, position: Int) {
        holder.bind(macrosList[position])
    }
}