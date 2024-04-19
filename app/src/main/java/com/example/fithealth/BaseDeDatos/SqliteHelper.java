package com.example.fithealth.BaseDeDatos;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.fithealth.PantallasPrincipales.principales.Social.Contacto;

import java.util.ArrayList;
import java.util.List;

public class SqliteHelper extends SQLiteOpenHelper {

    private static final String NOMBRE_BASEDATOS = "FitHealth";
    private static final int VERSION_BASEDATOS = 2;

    private static final String TABLA_CONTACTOS = "Contactos";

    private static final String TABLA_AMIGOS = "Amigos";

    private static final String CONTACTOS_ID = "id";
    private static final String CONTACTOS_NOMBRE = "nombre";
    private static final String CONTACTOS_RUTAIMAGEN = "ruta_imagen";

    public SqliteHelper(@Nullable Context context) {
        super(context, NOMBRE_BASEDATOS, null, VERSION_BASEDATOS);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Crear la tabla de contactos
        String crearTablaContactos = "CREATE TABLE " + TABLA_CONTACTOS + " (id TEXT PRIMARY KEY, nombre TEXT NOT NULL, ruta_imagen TEXT)";
        db.execSQL(crearTablaContactos);

        // Crear la tabla de amigos
        String crearTablaAmigos = "CREATE TABLE " + TABLA_AMIGOS + " (id TEXT PRIMARY KEY, nombre TEXT NOT NULL, ruta_imagen TEXT)";
        db.execSQL(crearTablaAmigos);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS  "+TABLA_CONTACTOS);
        db.execSQL("DROP TABLE IF EXISTS "+TABLA_AMIGOS);

        onCreate(db);
    }

    @SuppressLint("Range")
    public List<Contacto> obtenerContactos() {
        List<Contacto> contactos = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM Contactos";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {

                String id = cursor.getString(cursor.getColumnIndex("id"));
                String nombre = cursor.getString(cursor.getColumnIndex("nombre"));
                String rutaImagen = cursor.getString(cursor.getColumnIndex("ruta_imagen"));
                Contacto contacto = null;

                if (rutaImagen != null) {
                    contacto = new Contacto(id, nombre, Uri.parse(rutaImagen));
                } else {
                    contacto = new Contacto(id, nombre, null);
                }

                contactos.add(contacto);


            } while (cursor.moveToNext());
        }

        db.close();
        cursor.close();


        return contactos;
    }

    public boolean aniadirContacto(Contacto contacto) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("id", contacto.getId());
        values.put("nombre", contacto.getNombre());

        if (contacto.getRutaImagen() != null) {
            values.put("ruta_imagen", contacto.getRutaImagen().toString());
        } else {
            values.put("ruta_imagen", "");
        }


        long resultado = db.insert(TABLA_CONTACTOS, null, values);

        db.close();


        return resultado != -1;

    }

    public boolean deleteContacto(String id) {

        int filasEliminadas = 0;
        if (existeContacto(id)) {
            Log.i("infoEliminacion", "He entrado");
            SQLiteDatabase db = this.getWritableDatabase();

            String where = CONTACTOS_ID + "= ?";

            String[] whereArgs = {id};


            filasEliminadas = db.delete(TABLA_CONTACTOS, where, whereArgs);

        }

        return filasEliminadas > 0;


    }

    public boolean existeContacto(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columnas = getAllContactsColumns();

        String where = CONTACTOS_ID + "= ?";

        String[] whereArgs = {id};

        Cursor cursor = db.query(TABLA_CONTACTOS, columnas, where, whereArgs, null, null, null);

        return cursor.moveToFirst();
    }

    private String[] getAllContactsColumns() {
        return new String[]{CONTACTOS_ID, CONTACTOS_NOMBRE, CONTACTOS_RUTAIMAGEN};
    }

    public void deleteContactos() {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "DELETE FROM " + TABLA_CONTACTOS;

        db.execSQL(query);

        db.close();
    }

    //Consultas Amigos

    @SuppressLint("Range")
    public List<Contacto> getAmigos() {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Contacto> amigos = new ArrayList<>();

        String query = "SELECT * FROM " + TABLA_AMIGOS;

        Cursor cursor = db.rawQuery(query,null);

        if (cursor.moveToFirst()) {

            do {
                String nombre = cursor.getString(cursor.getColumnIndex(CONTACTOS_NOMBRE));
                String id = cursor.getString(cursor.getColumnIndex(CONTACTOS_ID));
                String rutaImagen = cursor.getString(cursor.getColumnIndex(CONTACTOS_RUTAIMAGEN));

                Contacto contacto = null;

                if (rutaImagen != null && !rutaImagen.isEmpty()) {
                    contacto = new Contacto(id, nombre, Uri.parse(rutaImagen));
                } else {
                    contacto = new Contacto(id, nombre, null);
                }

                amigos.add(contacto);


            } while (cursor.moveToNext());


        }

        db.close();
        cursor.close();
        return amigos;
    }

    public boolean aniadirAmigo(Contacto contacto) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(CONTACTOS_ID, contacto.getId());
        values.put(CONTACTOS_NOMBRE, contacto.getNombre());

        if (contacto.getRutaImagen() != null) {
            values.put(CONTACTOS_RUTAIMAGEN, contacto.getRutaImagen().toString());
        } else {
            values.put(CONTACTOS_RUTAIMAGEN, "");
        }

        long filasAfectadas = db.insert(TABLA_AMIGOS, null, values);

        db.close();
        return filasAfectadas > 0;
    }

    public boolean eliminarAmigo(String id) {
        SQLiteDatabase db = this.getWritableDatabase();

        String where = CONTACTOS_ID + " = ?";

        String[] whereArgs = {id};

        int filasEliminadas = db.delete(TABLA_AMIGOS, where, whereArgs);

        db.close();
        return filasEliminadas > 0;
    }
}
