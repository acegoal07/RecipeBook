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
        private static final String KEY_RECIPE = "recipe";
    }

///////// MAIN DB HANDLERS //////////
    public DBHandler(Context context) {
        super(context, DB_INFO.DB_NAME, null, DB_INFO.DB_VERSION);
    }
    /**
     * Creates the database
     * @param db The database
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + DB_INFO.TABLE_NAME + "(" + DB_INFO.KEY_ID + " INTEGER PRIMARY KEY," + DB_INFO.KEY_NAME + " TEXT," + DB_INFO.KEY_DESCRIPTION + " TEXT, " + DB_INFO.KEY_RECIPE + " TEXT" + ")");
    }
    /**
     * Called when the database needs to be upgraded.
     * @param db The database
     * @param oldVersion The old database version
     * @param newVersion The new database version
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DB_INFO.TABLE_NAME);
        onCreate(db);
    }

///////// RECIPE INFO DB HANDLERS //////////

    /**
     * Adds a new recipe to the database
     * @param title The title of the recipe
     * @param description The description of the recipe
     */
    public void addNewRecipe(String title, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DB_INFO.KEY_NAME, title);
        values.put(DB_INFO.KEY_DESCRIPTION, description);
        db.insert(DB_INFO.TABLE_NAME, null, values);
        db.close();
    }
    /**
     * Removes a recipe from the database by id
     * @param id The id of the recipe to remove
     */
    public void removeRecipeById(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DB_INFO.TABLE_NAME, DB_INFO.KEY_ID + " = ?", new String[] { String.valueOf(id) });
        db.close();
    }
    /**
     * Reads all the recipes from the database
     * @return An ArrayList of RecipeInfo objects
     */
    public ArrayList<RecipeInfo> getRecipes() {
        ArrayList<RecipeInfo> recipeInfoArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DB_INFO.TABLE_NAME, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            String description = cursor.getString(2);
            String recipe = cursor.getString(3);
            RecipeInfo collection = new RecipeInfo(id, title, description, recipe);
            recipeInfoArrayList.add(collection);
        }
        cursor.close();
        db.close();

        return recipeInfoArrayList;
    }
    /**
     * Reads a recipe from the database by id
     * @param id The id of the recipe to read
     * @return The recipe
     */
    public RecipeInfo getRecipeByID(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DB_INFO.TABLE_NAME + " WHERE " + DB_INFO.KEY_ID + " = ?", new String[] { String.valueOf(id) });
        cursor.moveToFirst();
        if (cursor.getCount() == 0) {
            return null;
        }
        RecipeInfo recipeInfo = new RecipeInfo(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3)
        );
        cursor.close();
        db.close();
        return recipeInfo;
    }
    /**
     * Updates the title of a recipe
     * @param id The id of the recipe to update
     * @param newTitle The new title
     */
    public void updateRecipeTitle(int id, String newTitle) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DB_INFO.KEY_NAME, newTitle);
        db.update(DB_INFO.TABLE_NAME, values, DB_INFO.KEY_ID + " = ?", new String[] { String.valueOf(id) });
        db.close();
    }
    /**
     * Updates the description of a recipe
     * @param id The id of the recipe to update
     * @param newDescription The new description
     */
    public void updateRecipeDescription(int id, String newDescription) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DB_INFO.KEY_DESCRIPTION, newDescription);
        db.update(DB_INFO.TABLE_NAME, values, DB_INFO.KEY_ID + " = ?", new String[] { String.valueOf(id) });
        db.close();
    }

///////// RECIPE DB HANDLERS //////////

    /**
     * Reads all the steps from a recipe
     * @param id The id of the recipe to read the steps from
     */
    public ArrayList<StepInfo> readRecipeStepInfo(int id) {
        ArrayList<StepInfo> stepInfoArrayList = new ArrayList<>();
        RecipeInfo recipeInfo = getRecipeByID(id);
        if (recipeInfo != null) {
            if (recipeInfo.getRecipe() == null) {
                return stepInfoArrayList;
            }
            for (String step : recipeInfo.getRecipe().split("!!")) {
                String[] stepInfoBreakdown = step.split("--");
                StepInfo stepinfo = new StepInfo(stepInfoBreakdown[1], StepInfo.convertIntToType(Integer.parseInt(stepInfoBreakdown[0])));
                stepInfoArrayList.add(stepinfo);
            }
        }
        return stepInfoArrayList;
    }
    /**
     * Adds a new step to a recipe
     * @param id The id of the recipe to add the step to
     * @param step The step to add
     */
    public void addNewRecipeStep(int id, String step) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DB_INFO.KEY_RECIPE, step);
        db.update(DB_INFO.TABLE_NAME, values, DB_INFO.KEY_ID + " = ?", new String[] { String.valueOf(id) });
        db.close();
    }
    /**
     * Remove a step in a recipe
     * @param id The id of the recipe to remove the step from
     * @param stepNum The step number to remove
     */
    public void removeRecipeStep(int id, int stepNum) {
        SQLiteDatabase db = this.getWritableDatabase();
        RecipeInfo recipeInfo = getRecipeByID(id);
        if (recipeInfo != null) {
            String[] steps = recipeInfo.getRecipe().split("!!");
            String newSteps = "";
            for (int i = 0; i < steps.length; i++) {
                if (i != stepNum) {
                    newSteps += steps[i] + "!!";
                }
            }
            ContentValues values = new ContentValues();
            values.put(DB_INFO.KEY_RECIPE, newSteps);
            db.update(DB_INFO.TABLE_NAME, values, DB_INFO.KEY_ID + " = ?", new String[] { String.valueOf(id) });
            db.close();
        }
    }
    /**
     * Updates a step in a recipe
     * @param id The id of the recipe to update the step in
     * @param stepNum The step number to update
     * @param newStep The new step
     */
    public void updateRecipeStep(int id, int stepNum, String newStep) {
        SQLiteDatabase db = this.getWritableDatabase();
        RecipeInfo recipeInfo = getRecipeByID(id);
        if (recipeInfo != null) {
            String[] steps = recipeInfo.getRecipe().split("!!");
            String newSteps = "";
            for (int i = 0; i < steps.length; i++) {
                if (i == stepNum) {
                    newSteps += newStep + "!!";
                } else {
                    newSteps += steps[i] + "!!";
                }
            }
            ContentValues values = new ContentValues();
            values.put(DB_INFO.KEY_RECIPE, newSteps);
            db.update(DB_INFO.TABLE_NAME, values, DB_INFO.KEY_ID + " = ?", new String[] { String.valueOf(id) });
            db.close();
        }
    }
}
