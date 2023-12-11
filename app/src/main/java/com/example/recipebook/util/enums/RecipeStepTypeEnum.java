package com.example.recipebook.util.enums;

public enum RecipeStepTypeEnum {
    NORMAL,
    COOK;

    /**
     * Converts a into type to an stepType
     *
     * @param num The int
     * @return The step type
     */
    public static RecipeStepTypeEnum fromInt(int num) {
        switch (num) {
            case 1:
                return RecipeStepTypeEnum.COOK;
            default:
                return RecipeStepTypeEnum.NORMAL;
        }
    }
}
