package com.example.recipebook.util;

public class RecipeInfo {
    private int id;
    private String title;
    private String description;

    /**
     * Constructor for RecipeCollection
     * @param id
     * @param title
     * @param description
     */
    public RecipeInfo(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    /**
     * Gets the id of the recipe collection
     * @return
     */
    public int getId() {
        return this.id;
    }

    /**
     * Gets the title of the recipe collection
     * @return
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Gets the description of the recipe collection
     * @return
     */
    public String getDescription() {
        return this.description;
    }

    public String returnAllInfo() {
        return "\nID: " + this.id + "\nTitle: " + this.title + "\nDescription: " + this.description;
    }

    /**
     * Sets the id of the recipe collection
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Sets the title of the recipe collection
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Sets the description of the recipe collection
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
