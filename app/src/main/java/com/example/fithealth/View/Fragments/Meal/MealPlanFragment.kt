package com.example.fithealth.View.Fragments.Meal

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fithealth.Model.DataClass.FoodApiResources.FoodNameSearch.Food
import com.example.fithealth.Model.Database.Tables.Entitys.Meal
import com.example.fithealth.Model.Utils.CalendarHelper
import com.example.fithealth.Model.Utils.ExtensionUtils.moveToActivityWithResult
import com.example.fithealth.Model.Utils.ExtensionUtils.toast
import com.example.fithealth.View.Activitys.Meal.MainMealActivity
import com.example.fithealth.View.ReyclerAdapters.Meal.FoodTypes.FoodTypesAdapter
import com.example.fithealth.ViewModel.Local_Database.Meal.MealEntityViewModel
import com.example.fithealth.ViewModel.Local_Database.Meal.MealEntityViewModelBuilder
import com.example.fithealth.databinding.FragmentDietaBinding
import com.example.fithealth.mealTypes
import java.time.LocalDate

class MealPlanFragment : Fragment() {

    private val activityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) updateLists()
        }

    private var _binding: FragmentDietaBinding? = null
    private val binding get() = _binding!!

    private lateinit var foodRecyclerList: List<RecyclerView>


    companion object {
        private const val MEAL_TYPE_KEY = "mealType"
        private const val MEAL_LIST_UPDATE = "mealListUpdate"
    }


    private val mealDB: MealEntityViewModel by viewModels {
        MealEntityViewModelBuilder.getMealEntityViewModelFactory(requireContext())
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDietaBinding.inflate(layoutInflater, container, false)
        inicializeComponents()
        setupUI()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
    }


    private fun setupObservers() {
        mealDBObservers()
    }

    private fun updateDate() {
        binding.tvDayMonth.text = CalendarHelper.getSelectedDateToDayMonthYear()
        updateLists()
    }

    private fun inicializeComponents() {
        binding.apply {
            foodRecyclerList = listOf(rvMealsBreakFast, rvMealsLunch, rvMealsSnack, rvMealsDinner)
        }
    }

    private fun setupUI() {
        setupRecyclers()
        setupOnClicks()
        updateDate()
    }

    private fun setupRecyclers() {
        binding.apply {
            val mealTypeRecyclerViewPairs = listOf(
                Pair(rvMealsBreakFast, mealTypes[0]),
                Pair(rvMealsLunch, mealTypes[1]),
                Pair(rvMealsSnack, mealTypes[2]),
                Pair(rvMealsDinner, mealTypes[3])
            )

            mealTypeRecyclerViewPairs.forEach { (recyclerView, mealType) ->
                configureRecyclerView(recyclerView, mealType)
            }
        }
    }


    private fun setupOnClicks() {
        binding.apply {
            tvDayMonth.setOnClickListener {
                updateLists()
            }
            configureMealAddButtons(arrayOf(btnMealAdd1, btnMealAdd2, btnMealAdd3, btnMealAdd4))

            btnPreviousMonthButton.setOnClickListener { goToPreviousMonth() }

            btnForwardMonthButton.setOnClickListener { goToNextMonth() }
        }
    }

    private fun configureRecyclerView(recyclerView: RecyclerView, mealType: String) {
        val removeElementInDB: (food: Food) -> Unit = { food ->
            handleRemoveElement(mealType,food)
        }
        val newAdapter = FoodTypesAdapter(mutableListOf(), removeElementInDB)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = newAdapter
        }
    }

    private fun handleRemoveElement(mealType: String, food: Food) {
        removeFoodInDB(food, mealType)
        updateLists()
    }

    private fun removeFoodInDB(food: Food, mealType: String) {
        val date: LocalDate = CalendarHelper.selectedDate
        mealDB.removeFoodFromMeal(date, mealType, food)
    }


    private fun mealDBObservers() {
        mealDB.apply {
            mealByUserIdAndDate.observe(viewLifecycleOwner) { meal ->
                if (meal != null) updateFoodTypesListWithMeal(meal)
                else updateFoodTypesWithEmptyLists()
            }
        }
    }

    private fun updateFoodTypesWithEmptyLists() {
        Log.i("MealState","Meal null")
        foodRecyclerList.forEach { recyclerView ->
            updateRecyclerViewWithList(recyclerView, emptyList())
        }
    }

    private fun updateFoodTypesListWithMeal(meal: Meal) {
        Log.i("MealState","Meal no null")
        binding.apply {
            val recyclerViewAdapters = listOf(
                Pair(foodRecyclerList[0], meal.breakfastMealList),
                Pair(foodRecyclerList[1], meal.lunchMealList),
                Pair(foodRecyclerList[2], meal.snackMealList),
                Pair(foodRecyclerList[3], meal.dinnerMealList)
            )

            recyclerViewAdapters.forEach { (recyclerView, foodList) ->
                updateRecyclerViewWithList(recyclerView, foodList)
            }
        }
    }

    private fun updateRecyclerViewWithList(recyclerView: RecyclerView, foodList: List<Food>) {
        val adapter = recyclerView.adapter as FoodTypesAdapter
        adapter.updateList(foodList)
    }


    private fun updateLists() {
        val date = CalendarHelper.selectedDate
        mealDB.getMealByDate(date)
    }

    private fun updateListForMealType(
        recyclerView: RecyclerView,
        foodList: List<Food>
    ) {
        val adapter = recyclerView.adapter as? FoodTypesAdapter
        adapter?.updateList(foodList)
    }

    private fun configureMealAddButtons(btnArray: Array<Button>) {
        btnArray.forEachIndexed { index, button ->
            val mealType = mealTypes.getOrNull(index)
            button.setOnClickListener {
                if (mealType != null) {
                    moveToActivityWithResult(
                        MainMealActivity::class.java,
                        activityResultLauncher = activityResultLauncher,
                        bundleOf(MEAL_TYPE_KEY to mealType)
                    )
                } else toast("Ha ocurrido un error")
            }

        }
    }

    private fun goToNextMonth() {
        CalendarHelper.plusDaySelectedDate()
        updateDate()
    }

    private fun goToPreviousMonth() {
        CalendarHelper.minusDaySelectedDate()
        updateDate()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}