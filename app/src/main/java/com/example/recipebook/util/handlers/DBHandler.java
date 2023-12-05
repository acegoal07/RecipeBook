package com.example.recipebook.util.handlers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.recipebook.util.classes.RecipeDetails;
import com.example.recipebook.util.classes.RecipeSteps;
import com.example.recipebook.util.classes.RecipeTypeEnum;
import com.example.recipebook.util.classes.StepInfo;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {
    private static final class DB_INFO {
        private static final String DB_NAME = "recipeDB";
        private static final int DB_VERSION = 1;
        private static final String TABLE_NAME = "recipes";
        private static final String KEY_ID = "id";
        private static final String KEY_NAME = "name";
        private static final String KEY_RECIPE_TYPE = "recipe_type";
        private static final String KEY_DESCRIPTION = "description";
        private static final String KEY_RECIPE = "recipe";
    }

///////// MAIN DB HANDLERS //////////

    public DBHandler(Context context) {
        super(context, DB_INFO.DB_NAME, null, DB_INFO.DB_VERSION);
    }

    /**
     * Creates the database
     *
     * @param db The database
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + DB_INFO.TABLE_NAME + "(" + DB_INFO.KEY_ID + " INTEGER PRIMARY KEY," + DB_INFO.KEY_NAME + " TEXT," + DB_INFO.KEY_RECIPE_TYPE + " TEXT," + DB_INFO.KEY_DESCRIPTION + " TEXT, " + DB_INFO.KEY_RECIPE + " TEXT" + ")");
    }

    /**
     * Called when the database needs to be upgraded.
     *
     * @param db         The database
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
     *
     * @param title       The title of the recipe
     * @param description The description of the recipe
     */
    public void addNewRecipe(String title, String recipeType, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DB_INFO.KEY_NAME, title);
        values.put(DB_INFO.KEY_RECIPE_TYPE, recipeType);
        values.put(DB_INFO.KEY_DESCRIPTION, description);
        db.insert(DB_INFO.TABLE_NAME, null, values);
        db.close();
    }

    /**
     * Removes a recipe from the database by id
     *
     * @param id The id of the recipe to remove
     */
    public void removeRecipeById(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DB_INFO.TABLE_NAME, DB_INFO.KEY_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    /**
     * Reads all the recipes from the database
     *
     * @return An ArrayList of RecipeInfo objects
     */
    public ArrayList<RecipeDetails> getRecipes() {
        ArrayList<RecipeDetails> recipeDetailsArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DB_INFO.TABLE_NAME, null);
        while (cursor.moveToNext()) {
            recipeDetailsArrayList.add(
                new RecipeDetails(
                        cursor.getInt(0),
                        cursor.getString(1),
                        RecipeTypeEnum.fromString(cursor.getString(2)),
                        cursor.getString(3),
                        new RecipeSteps(cursor.getString(4))
                )
            );
        }
        cursor.close();
        db.close();
        return recipeDetailsArrayList;
    }

    /**
     * Reads a recipe from the database by id
     *
     * @param id The id of the recipe to read
     * @return The recipe
     */
    public RecipeDetails getRecipeByID(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DB_INFO.TABLE_NAME + " WHERE " + DB_INFO.KEY_ID + " = ?", new String[]{String.valueOf(id)});
        if (cursor.getCount() == 0) {
            return null;
        }
        cursor.moveToFirst();
        RecipeDetails recipeDetails = new RecipeDetails(
            cursor.getInt(0),
            cursor.getString(1),
            RecipeTypeEnum.fromString(cursor.getString(2)),
            cursor.getString(3),
            new RecipeSteps(cursor.getString(4))
        );
        cursor.close();
        db.close();
        return recipeDetails;
    }

    /**
     * Updates the title of a recipe
     *
     * @param id       The id of the recipe to update
     * @param newTitle The new title
     */
    public void updateRecipeTitle(int id, String newTitle) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DB_INFO.KEY_NAME, newTitle);
        db.update(DB_INFO.TABLE_NAME, values, DB_INFO.KEY_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    /**
     * Updates the type of a recipe
     *
     * @param id      The id of the recipe to update
     * @param newType The new type
     */
    public void updateRecipeType(int id, String newType) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DB_INFO.KEY_RECIPE_TYPE, newType);
        db.update(DB_INFO.TABLE_NAME, values, DB_INFO.KEY_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    /**
     * Updates the description of a recipe
     *
     * @param id             The id of the recipe to update
     * @param newDescription The new description
     */
    public void updateRecipeDescription(int id, String newDescription) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DB_INFO.KEY_DESCRIPTION, newDescription);
        db.update(DB_INFO.TABLE_NAME, values, DB_INFO.KEY_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

///////// RECIPE DB HANDLERS //////////

    /**
     * Reads all the steps from a recipe
     *
     * @param id The id of the recipe to read the steps from
     */
    public ArrayList<StepInfo> readRecipeStepInfo(int id) {
        return getRecipeByID(id).getRecipe().getSteps();
    }

    /**
     * Adds a new step to a recipe
     *
     * @param id   The id of the recipe to add the step to
     * @param step The step to add
     */
    public void addNewRecipeStep(int id, String step) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DB_INFO.KEY_RECIPE, step);
        db.update(DB_INFO.TABLE_NAME, values, DB_INFO.KEY_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    /**
     * Remove a step in a recipe
     *
     * @param id      The id of the recipe to remove the step from
     * @param stepNum The step number to remove
     */
    public void removeRecipeStep(int id, int stepNum) {
        RecipeDetails recipeDetails = getRecipeByID(id);
        if (recipeDetails != null) {
            String[] steps = recipeDetails.getRecipe().getRawSteps();
            StringBuilder newSteps = new StringBuilder();
            for (int i = 0; i < steps.length; i++) {
                if (i != stepNum) {
                    if (i != 0 && newSteps.length() != 0) {
                        newSteps.append("!!");
                    }
                    newSteps.append(steps[i]);
                }
            }
            ContentValues values = new ContentValues();
            values.put(DB_INFO.KEY_RECIPE, newSteps.toString());
            SQLiteDatabase db = this.getWritableDatabase();
            db.update(DB_INFO.TABLE_NAME, values, DB_INFO.KEY_ID + " = ?", new String[]{String.valueOf(id)});
            db.close();
        }
    }

    /**
     * Updates a step in a recipe
     *
     * @param id      The id of the recipe to update the step in
     * @param stepNum The step number to update
     * @param newStep The new step
     */
    public void updateRecipeStep(int id, int stepNum, String newStep) {
        RecipeDetails recipeDetails = getRecipeByID(id);
        if (recipeDetails != null) {
            String[] steps = recipeDetails.getRecipe().getRawSteps();
            StringBuilder newSteps = new StringBuilder();
            for (int i = 0; i < steps.length; i++) {
                if (i != 0) {
                    newSteps.append("!!");
                }
                if (i == stepNum) {
                    newSteps.append(newStep);
                } else {
                    newSteps.append(steps[i]);
                }
            }
            ContentValues values = new ContentValues();
            values.put(DB_INFO.KEY_RECIPE, newSteps.toString());
            SQLiteDatabase db = this.getWritableDatabase();
            db.update(DB_INFO.TABLE_NAME, values, DB_INFO.KEY_ID + " = ?", new String[]{String.valueOf(id)});
            db.close();
        }
    }

    /**
     * Moves a step in a recipe to a new position
     *
     * @param id         The id of the recipe to move the step in
     * @param oldStepNum The step number to move
     * @param newStepNum The new position of the step
     */
    public void moveRecipeStep(int id, int oldStepNum, int newStepNum) {
        RecipeDetails recipeDetails = getRecipeByID(id);
        if (recipeDetails != null) {
            String[] steps = recipeDetails.getRecipe().getRawSteps();
            String temp = steps[oldStepNum];
            StringBuilder newSteps = new StringBuilder();
            for (int i = 0; i < steps.length; i++) {
                if (i != oldStepNum) {
                    if (i != 0 && !newSteps.toString().isEmpty()) {
                        newSteps.append("!!");
                    }
                    if (i == newStepNum && oldStepNum > newStepNum) {
                        newSteps.append(temp).append("!!");
                    }
                    newSteps.append(steps[i]);
                    if (i == newStepNum && oldStepNum < newStepNum) {
                        newSteps.append("!!").append(temp);
                    }
                }
            }
            ContentValues values = new ContentValues();
            values.put(DB_INFO.KEY_RECIPE, newSteps.toString());
            SQLiteDatabase db = this.getWritableDatabase();
            db.update(DB_INFO.TABLE_NAME, values, DB_INFO.KEY_ID + " = ?", new String[]{String.valueOf(id)});
            db.close();
        }
    }
}
