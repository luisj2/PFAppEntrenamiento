package com.example.fithealth.View.ReyclerAdapters.Meal.FoodTypes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.fithealth.Model.DataClass.FoodApiResources.FoodNameSearch.Food
import com.example.fithealth.View.ReyclerAdapters.DiffUtils.FoodDiffCallback
import com.example.fithealth.databinding.ItemFoodTypeBinding

class FoodTypesAdapter(
    private var foodList: MutableList<Food>,
    private var removeElementInDB: (food: Food) -> Unit
) :
    RecyclerView.Adapter<FoodTypesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodTypesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemFoodTypeBinding.inflate(layoutInflater, parent, false)
        return FoodTypesViewHolder(binding)
    }

    override fun getItemCount(): Int = foodList.size

    override fun onBindViewHolder(holder: FoodTypesViewHolder, position: Int) {
        val itemFood = foodList[position]
        holder.bind(itemFood)
        val removeElement : (food : Food) -> Unit ={food->
            removeItem(food)
        }
        holder.setupRemoveElementOnClick(itemFood, removeElementInDB,removeElement)
    }

    private fun removeItem(food: Food) {
        val position = foodList.indexOf(food)
        if(position != -1){
            foodList.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position,foodList.size)
        }
    }

    fun updateList(newList: List<Food>) {
        val diffCallback = FoodDiffCallback(foodList, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        foodList = newList.toMutableList()

        diffResult.dispatchUpdatesTo(this)
    }
    fun updateRemoveElementCallback(newCallback: (food: Food) -> Unit) {
        removeElementInDB = newCallback
    }
}