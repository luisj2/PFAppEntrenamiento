package com.example.fithealth.Model.Database


import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.fithealth.Model.Database.Dao.ContactsDao
import com.example.fithealth.Model.Database.Dao.MealDao
import com.example.fithealth.Model.Database.Dao.MessageDao
import com.example.fithealth.Model.Database.Dao.UserDao
import com.example.fithealth.Model.Database.Tables.Entitys.Contact
import com.example.fithealth.Model.Database.Tables.Entitys.Meal
import com.example.fithealth.Model.Database.Tables.Entitys.Message
import com.example.fithealth.Model.Database.Tables.Entitys.User

@Database(
    entities = [Contact::class, User::class, Message::class, Meal::class],
    version = 4
)
@TypeConverters(ConvertersDatabase::class)
abstract class FitHealthDatabase : RoomDatabase() {
    abstract fun getContactsDao(): ContactsDao


    abstract fun getUserDao(): UserDao

    abstract fun getMessageDao(): MessageDao

    abstract fun getMealDao(): MealDao


}