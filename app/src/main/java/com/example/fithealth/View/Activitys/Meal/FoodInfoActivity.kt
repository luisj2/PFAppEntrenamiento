package com.example.fithealth.View.Activitys.Meal

import ServingBuilder
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.fithealth.Model.DataClass.FoodApiResources.FoodIdSearch.Food
import com.example.fithealth.Model.DataClass.FoodApiResources.FoodIdSearch.Serving
import com.example.fithealth.Model.Utils.ExtensionUtils.dissmissLoadingScreen
import com.example.fithealth.Model.Utils.ExtensionUtils.showLoadingScreen
import com.example.fithealth.ViewModel.FoodApi.FatSecretViewModel
import com.example.fithealth.ViewModel.FoodApi.FatSecretViewModelBuilder
import com.example.fithealth.databinding.ActivityFoodInfoBinding
import java.text.DecimalFormat

class FoodInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFoodInfoBinding
    private var foodIdByIntent: String? = null

    private val fatSecretViewModel: FatSecretViewModel by viewModels {
        FatSecretViewModelBuilder.getFatSecretViewModelFactory()
    }

    private lateinit var macros: Serving

    companion object {
        private const val FOOD_ID = "food_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        callSearchFoodById()
        setupUI()
    }

    private fun callSearchFoodById() {
        foodIdByIntent = getFoodIdByIntent()
        if (foodIdByIntent != null) fatSecretViewModel.searchFoodById(foodIdByIntent!!)
    }

    private fun getFoodIdByIntent(): String? = intent.getStringExtra(FOOD_ID)

    private fun setupUI() {
        setupOnClicks()
        setupObservers()
    }

    private fun setupOnClicks() {
        binding.apply {
            btnClose.setOnClickListener {
                finish()
            }

            btnCalculate.setOnClickListener {
                updateMacrosWithInput()
            }
        }
    }

    private fun updateMacrosWithInput() {
        try {
            val newQuantity = binding.etGramsAmount.text.toString().toInt()
            macros = transformMacrosQuantity(macros, newQuantity)
            Log.i("macros_impresion", macros.toString())
            completeMacrosFields()
        } catch (e: NumberFormatException) {
            Log.e("error_numeros", "Error: ${e.message}", e)
        }
    }

    private fun setupObservers() {
        setupFatSecretObservers()
    }

    private fun setupFatSecretObservers() {
        fatSecretViewModel.apply {
            loading.observe(this@FoodInfoActivity) { loading ->
                if (loading) showLoadingScreen()
                else dissmissLoadingScreen()
            }

            foodResult.observe(this@FoodInfoActivity) { foodById ->
                if (foodById != null) setupUIWithFoodInfo(foodById)
                else setupUIWithNullInfo()
            }
        }
    }

    private fun setupUIWithNullInfo() {
        binding.apply {
            tvMealName.text = "Desconocido"
        }
    }

    private fun setupUIWithFoodInfo(food: Food) {
        binding.apply {
            tvMealName.text = food.food_name
            val initialMacros = food.servings.serving[0]
            macros = transformMacrosQuantity(initialMacros)
            completeMacrosFields()
        }
    }

    private fun transformMacrosQuantity(macros: Serving, quantity: Int = 100): Serving {
        val initialQuantity = macros.metric_serving_amount
        Log.i("info_macros", macros.toString())
        return ServingBuilder()
            .setGramsAmount(quantity)
            .setAmountUnit("g")
            .setCalories(calculateMacro(macros.calories, initialQuantity, quantity))
            .setCarbohydrate(calculateMacro(macros.carbohydrate, initialQuantity, quantity))
            .setFat(calculateMacro(macros.fat, initialQuantity, quantity))
            .setFiber(calculateMacro(macros.fiber, initialQuantity, quantity))
            .setIron(calculateMacro(macros.iron, initialQuantity, quantity))
            .setProtein(calculateMacro(macros.protein, initialQuantity, quantity))
            .setSugar(calculateMacro(macros.sugar, initialQuantity, quantity))
            .build()
    }


    //Regla de 3 para tranformar gramos de un alimento por ejemplo trabformar 100g a 40g
    private fun calculateMacro(
        initialMacros: String,
        initialQuantity: String,
        finalQuantity: Int
    ): Double {
        return try {
            // Convertir las entradas a Double, con manejo de valores nulos
            val initialMacrosValue = initialMacros.toDoubleOrNull()
                ?: throw NumberFormatException("Invalid initialMacros: $initialMacros")
            val initialQuantityValue = initialQuantity.toDoubleOrNull()
                ?: throw NumberFormatException("Invalid initialQuantity: $initialQuantity")

            // Verificar división por cero
            if (initialQuantityValue == 0.0) {
                throw ArithmeticException("Division by zero: initialQuantity is 0.0")
            }

            // Calcular el resultado y redondearlo al entero más cercano
            val result = (initialMacrosValue / initialQuantityValue) * finalQuantity
            convertTo2Decimals(result)
        } catch (e: NumberFormatException) {
            Log.e("Error_calculos", "Error: Número inválido en entrada. ${e.message}", e)
            0.00
        } catch (e: ArithmeticException) {
            Log.e(
                "Error_calculos",
                "Error: Operación inválida (posible división por cero). ${e.message}",
                e
            )
            0.00
        }
    }

    private fun convertTo2Decimals(number: Double): Double {
        val decimalFormat = DecimalFormat("#.##")
        return decimalFormat.format(number).toDouble()
    }


    private fun completeMacrosFields() {
        binding.apply {
            tvCalories.text = macros.calories
            tvFat.text = macros.fat
            tvFiber.text = macros.fiber
            tvIron.text = macros.iron
            tvCarbohydrate.text = macros.carbohydrate
            tvProtein.text = macros.protein
            tvSugar.text = macros.sugar
        }
    }


}