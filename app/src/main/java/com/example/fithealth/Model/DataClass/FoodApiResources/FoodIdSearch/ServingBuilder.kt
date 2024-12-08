import com.example.fithealth.Model.DataClass.FoodApiResources.FoodIdSearch.Serving

class ServingBuilder {
    private var calories: String = ""
    private var protein: String = ""
    private var sugar: String = ""
    private var fat: String = ""
    private var fiber: String = ""
    private var iron: String = ""
    private var carbohydrate: String = ""
    private var metric_serving_amount: String = ""
    private var amountUnit : String = ""

    // Métodos para establecer valores numéricos con tipo Double, que luego se convertirán a String
    fun setCalories(calories: Double) = apply { this.calories = calories.toString() }
    fun setProtein(protein: Double) = apply { this.protein = protein.toString() }
    fun setSugar(sugar: Double) = apply { this.sugar = sugar.toString() }
    fun setFat(fat: Double) = apply { this.fat = fat.toString() }
    fun setFiber(fiber: Double) = apply { this.fiber = fiber.toString() }
    fun setIron(iron: Double) = apply { this.iron = iron.toString() }
    fun setCarbohydrate(carbohydrate: Double) = apply { this.carbohydrate = carbohydrate.toString() }

    // Método para establecer el valor de gramos con un Double que se convierte a String
    fun setGramsAmount(amount: Int) = apply { this.metric_serving_amount = amount.toString() }

    fun setAmountUnit(unit : String) = apply { this.amountUnit = unit }

    // Método para construir el objeto Serving con los valores establecidos
    fun build(): Serving {
        return Serving(
            calories = this.calories,
            protein = this.protein,
            sugar = this.sugar,
            fat = this.fat,
            fiber = this.fiber,
            iron = this.iron,
            carbohydrate = this.carbohydrate,
            metric_serving_amount = metric_serving_amount,
            metric_serving_unit = this.amountUnit
        )
    }
}
