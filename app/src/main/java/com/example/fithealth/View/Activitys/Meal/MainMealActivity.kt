package com.example.fithealth.View.Activitys.Meal

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.fithealth.Model.DataClass.FoodApiResources.FoodNameSearch.Food
import com.example.fithealth.Model.Database.Tables.Entitys.Meal
import com.example.fithealth.Model.Utils.CalendarHelper
import com.example.fithealth.Model.Utils.ExtensionUtils.getColorStateListFromResource
import com.example.fithealth.Model.Utils.ExtensionUtils.toast
import com.example.fithealth.R
import com.example.fithealth.View.Activitys.MainActivity
import com.example.fithealth.ViewModel.FoodApi.FatSecretViewModel
import com.example.fithealth.ViewModel.FoodApi.FatSecretViewModelBuilder
import com.example.fithealth.ViewModel.Local_Database.Meal.MealEntityViewModel
import com.example.fithealth.ViewModel.Local_Database.Meal.MealEntityViewModelBuilder
import com.example.fithealth.databinding.ActivityMainMealBinding
import com.example.fithealth.mealsHandlerByTypes

//COMPROBAR EL ERROR DE CUANDO CAMBIO LA FECHA DEL CALENDARIO NO SE ME ACTUALIZA EL MENU DE COMIDAS
class MainMealActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainMealBinding

    private val fatSecretViewModel: FatSecretViewModel by viewModels {
        FatSecretViewModelBuilder.getFatSecretViewModelFactory()
    }

    private val mealDB: MealEntityViewModel by viewModels {
        MealEntityViewModelBuilder.getMealEntityViewModelFactory(this)
    }

    private var mealType: String? = null

    companion object {
        private const val MEAL_TYPE_KEY = "mealType"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fragmentNavegation()
        mealType = getIntentMealType()
        setupUI()
    }

    private fun getIntentMealType(): String? = intent.getStringExtra(MEAL_TYPE_KEY)

    private fun setupUI() {
        setupOnClicks()
        setupObservers()
    }

    private fun setupObservers() {
        setupFatSecretObservers()
        setupMealDBObservers()
    }

    private fun setupMealDBObservers() {
        mealDB.apply {
            observeMealStatus(updateMealStatus)
            observeMealStatus(insertMealStatus)


        }
    }

    private fun observeMealStatus(liveData: LiveData<Boolean>) {
        liveData.observe(this@MainMealActivity) { status ->
            if (status) {
                setResult(Activity.RESULT_OK, Intent(this,MainActivity::class.java))
                finish()
            }
        }
    }


    private fun setupFatSecretObservers() {
        fatSecretViewModel.apply {
            foodAgreeList.observe(this@MainMealActivity) { agreeMeals ->
                Log.i("datos_observar", "dato observado")
                if (agreeMeals.isNotEmpty()) changeToUsableBtn(mealsInList = agreeMeals.size)
                else changeToNoFoodsBtn()
            }
        }
    }

    private fun changeToNoFoodsBtn() {
        changeAgreeMealsBtnColor(false)
        binding.btnAgreeMeals.text = "Añadir"
    }

    private fun changeToUsableBtn(mealsInList: Int) {
        changeAgreeMealsBtnColor(true)
        binding.btnAgreeMeals.text = "Añadir (${mealsInList})"
    }

    private fun setupOnClicks() {
        binding.apply {
            btnBack.setOnClickListener { finish() }
            btnAgreeMeals.setOnClickListener { handleFoodListAgreement() }
        }
    }

    private fun handleFoodListAgreement() {
        if (!isBtnAgreeMealsUsable()) toast("Añade alguna comida")
        else handleFoodListAndRedirect()
    }

    private fun handleFoodListAndRedirect() {
        val foodListToAdd = fatSecretViewModel.foodAgreeList.value

        if (foodListToAdd?.isNotEmpty()!!) {
            addFoodListInDB(foodListToAdd)
            fatSecretViewModel.clearAgreedFoodList()
        }
    }

    private fun addFoodListInDB(foodListToAdd: List<Food>) {
        mealDB.getMealByDate(CalendarHelper.selectedDate) { meal ->
            if (meal == null) createMealInDB(foodListToAdd)
            else updateMealWithFoodList(meal, foodListToAdd)
        }
    }

    private fun createMealInDB(foodListToAdd: List<Food>) {
        var mealToAdd = Meal(date = CalendarHelper.selectedDate)
        mealToAdd = mealsHandlerByTypes[mealType]?.changeMealByFoodList(mealToAdd, foodListToAdd)!!
        mealDB.insertMeal(mealToAdd)
    }

    private fun updateMealWithFoodList(meal: Meal, foodListToAdd: List<Food>) {
        val mealTypeHandler = mealsHandlerByTypes[mealType]
        val updatedMeal = mealTypeHandler?.changeMealByFoodList(meal, foodListToAdd)
        updatedMeal?.let {
            mealDB.updateMeal(it)
        }
    }

    private fun isBtnAgreeMealsUsable(): Boolean =
        fatSecretViewModel.foodAgreeList.value?.isNotEmpty() ?: false


    private fun changeAgreeMealsBtnColor(isUsable: Boolean) {
        binding.btnAgreeMeals.backgroundTintList =
            if (isUsable) getColorStateListFromResource(R.color.colorVerde)
            else getColorStateListFromResource(R.color.gris_dia)
    }

    private fun fragmentNavegation() {
        val navHostFragment = getFragmentContainer()
        navHostFragment?.let {
            binding.bottomMealNavigation.setupWithNavController(it.navController)
        } ?: toast("Ha habido un error en la navegacion")
    }

    private fun getFragmentContainer(): NavHostFragment? {
        val fragment = supportFragmentManager.findFragmentById(binding.ContainerView.id)
        return fragment as? NavHostFragment
    }


}