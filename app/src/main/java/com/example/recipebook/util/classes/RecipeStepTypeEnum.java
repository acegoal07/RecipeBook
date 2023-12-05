package com.example.recipebook.util.classes;

public enum RecipeStepTypeEnum {
    NORMAL,
    COOK;

    /**
     * Converts a into type to an stepType
     *
     * @param num The int
     * @return The step type
     */
    public static RecipeStepTypeEnum convertIntToType(int num) {
        if (num == 1) {
            return RecipeStepTypeEnum.COOK;
        }
        return RecipeStepTypeEnum.NORMAL;
    }
}
