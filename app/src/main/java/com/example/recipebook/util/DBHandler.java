package com.example.recipebook.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {
    private static final class DB_INFO {
        private static final String DB_NAME = "recipedb";
        private static final int DB_VERSION = 1;
        private static final String TABLE_NAME = "recipes";
        private static final String KEY_ID = "id";
        private static final String KEY_NAME = "name";
        private static final String KEY_DESCRIPTION = "description";
    }
    public DBHandler(Context context) {
        super(context, DB_INFO.DB_NAME, null, DB_INFO.DB_VERSION);
    }

    /**
     * Creates the database
     * @param db The database
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + DB_INFO.TABLE_NAME + "(" + DB_INFO.KEY_ID + " INTEGER PRIMARY KEY," + DB_INFO.KEY_NAME + " TEXT," + DB_INFO.KEY_DESCRIPTION + " TEXT" + ")");
    }

    /**
     * Adds a new collection to the database
     * @param title The title of the collection
     * @param description The description of the collection
     */
    public void addNewCollection(String title, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DB_INFO.KEY_NAME, title);
        values.put(DB_INFO.KEY_DESCRIPTION, description);
        db.insert(DB_INFO.TABLE_NAME, null, values);
        db.close();
    }

    /**
     * Removes a collection from the database by id
     * @param id The id of the collection to remove
     */
    public void removeCollectionById(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DB_INFO.TABLE_NAME, DB_INFO.KEY_ID + " = ?", new String[] { String.valueOf(id) });
        db.close();
    }

    /**
     * Reads all the collections from the database
     * @return An ArrayList of RecipeInfo objects
     */
    public ArrayList<RecipeInfo> readCollections() {
        ArrayList<RecipeInfo> recipeInfoArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DB_INFO.TABLE_NAME, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            String description = cursor.getString(2);
            RecipeInfo collection = new RecipeInfo(id, title, description);
            recipeInfoArrayList.add(collection);
        }
        cursor.close();
        db.close();
        return recipeInfoArrayList;
    }

    /**
     * Reads a collection from the database by id
     * @param id The id of the collection to read
     * @return The collection
     */
    public String[] readCollection(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DB_INFO.TABLE_NAME + " WHERE " + DB_INFO.KEY_ID + " = ?", new String[] { String.valueOf(id) });
        cursor.moveToFirst();
        String[] recipeInfo = new String[] {
                cursor.getString(1),
                cursor.getString(2)
        };
        cursor.close();
        db.close();
        return recipeInfo;
    }

    /**
     * Updates a collection in the database
     * @param id The id of the collection to update
     * @param title The new title
     * @param description The new description
     */
    public void updateCollection(int id, String title, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DB_INFO.KEY_NAME, title);
        values.put(DB_INFO.KEY_DESCRIPTION, description);
        db.update(DB_INFO.TABLE_NAME, values, DB_INFO.KEY_ID + " = ?", new String[] { String.valueOf(id) });
        db.close();
    }

    /**
     * Called when the database needs to be upgraded.
     * @param db The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DB_INFO.TABLE_NAME);
        onCreate(db);
    }
}
