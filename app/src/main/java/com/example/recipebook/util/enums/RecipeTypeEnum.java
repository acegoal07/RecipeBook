package com.example.recipebook.util.enums;

public enum RecipeTypeEnum {
    DEFAULT,
    VEGETARIAN,
    VEGAN;

    /**
     * Gets the RecipeTypeEnum from a string
     *
     * @param text The string to get the RecipeTypeEnum from
     * @return The RecipeTypeEnum
     */
    public static RecipeTypeEnum fromString(String text) {
        for (RecipeTypeEnum b : RecipeTypeEnum.values()) {
            if (b.toString().equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
}
