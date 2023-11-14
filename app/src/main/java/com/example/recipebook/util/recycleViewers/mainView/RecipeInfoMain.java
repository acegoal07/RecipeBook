package com.example.recipebook.util.recycleViewers.mainView;

public class RecipeInfoMain {
    private int id;
    private String title;
    private String description;

    /**
     * Constructor for RecipeCollection
     * @param id The id of the recipe collection
     * @param title The title of the recipe collection
     * @param description The description of the recipe collection
     */
    public RecipeInfoMain(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    /**
     * Gets the id of the recipe collection
     * @return The id
     */
    public int getId() {
        return this.id;
    }

    /**
     * Gets the title of the recipe collection
     * @return The title
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Gets the description of the recipe collection
     * @return The description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Returns all the info of the recipe collection
     * @return A string containing all the info
     */
    public String returnAllInfo() {
        return "\nID: " + this.id + "\nTitle: " + this.title + "\nDescription: " + this.description;
    }
}
