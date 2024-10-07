package com.example.fithealth.Model.BaseDeDatos;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.fithealth.Model.BaseDeDatos.Dao.DaoUsuario;
import com.example.fithealth.Model.BaseDeDatos.Tables.Usuario.DatosUsuario;




@Database(entities = {DatosUsuario.class},version = 2,exportSchema = false)
public abstract class FitHealthDatabase extends RoomDatabase {

    private static volatile FitHealthDatabase INSTANCE;

    public abstract DaoUsuario daoUsuario();

    public static FitHealthDatabase getInstance(Context context) {

        if (INSTANCE == null) {
            synchronized (FitHealthDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),FitHealthDatabase.class,"FitHealthBase").build();
                }
            }
        }
        return INSTANCE;
    }

    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // Elimina la columna 'id' de la tabla 'usuario'
            database.execSQL("ALTER TABLE usuario ADD COLUMN id");

        }
    };

}
