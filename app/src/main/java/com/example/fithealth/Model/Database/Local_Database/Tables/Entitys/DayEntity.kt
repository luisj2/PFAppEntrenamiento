
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.fithealth.Model.DataClass.Routine
import com.example.fithealth.Model.Database.Tables.Entitys.Meal
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Entity(tableName = "dayEntity")
data class DayEntity(
    val date : Date,
    var routines: List<Routine>,
    var meals: List<Meal>
) {
    @PrimaryKey(autoGenerate = false)
    val dayId: String = generateDayId(date)
    companion object {
        fun generateDayId(date: Date = Date()): String {
            val dateFormat = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
            return dateFormat.format(date)
        }
    }
}
