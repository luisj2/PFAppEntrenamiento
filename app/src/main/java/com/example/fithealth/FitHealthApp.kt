package com.example.fithealth

import android.app.Application
import com.example.fithealth.Model.Strategy.MealHandler.BreakfastHandler
import com.example.fithealth.Model.Strategy.MealHandler.DinnerHandler
import com.example.fithealth.Model.Strategy.MealHandler.LunchHandler
import com.example.fithealth.Model.Strategy.MealHandler.MealHandler
import com.example.fithealth.Model.Strategy.MealHandler.SnackHandler
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

// Lista de nombres de fragmentos
val fragmentsNameMainActivity: Array<String> =
    arrayOf("homeFragment", "mealFragment", "trainingFragment", "socialFragment")


// Crear el mapa correctamente con los nombres de fragmento como claves y sus IDs como valores
val fragmentsIdByNameMap: HashMap<String, Int> = hashMapOf(
    fragmentsNameMainActivity[0] to R.id.homeFragment,
    fragmentsNameMainActivity[1] to R.id.mealPlanFragment,
    fragmentsNameMainActivity[2] to R.id.trainingFragment,
    fragmentsNameMainActivity[3] to R.id.socialFragment
)

//tipos de comidas
val mealTypes: Array<String> = arrayOf("breakfast", "lunch", "snack", "dinner")

//Lista de handler en funcion de la comida que sea
val mealsHandlerByTypes: HashMap<String, MealHandler> = hashMapOf(
    mealTypes[0] to BreakfastHandler,
    mealTypes[1] to LunchHandler,
    mealTypes[2] to SnackHandler,
    mealTypes[3] to DinnerHandler
)

//Id del usuario loggeado
var userId: String? = null

class FitHealthApp : Application() {


    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this@FitHealthApp)
        userId = FirebaseAuth.getInstance().currentUser?.uid
    }
}