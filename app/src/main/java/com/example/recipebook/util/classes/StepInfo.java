package com.example.recipebook.util.classes;

public class StepInfo {

    private RecipeStepType StepType;
    private String step;
    public enum RecipeStepType {
        NORMAL,
        COOK;
    }

    /**
     * Constructor for StepInfo
     * @param step The step
     */
    public StepInfo(String step, RecipeStepType type) {
        this.step = step;
        this.StepType = type;
    }
    /**
     * Gets the step
     * @return The step
     */
    public String getStep() {
        return this.step;
    }
    /**
     * Gets the step type
     * @return The step type
     */
    public RecipeStepType getStepType() {
        return this.StepType;
    }
    /**
     * Gets the cook step info
     * @return The cook step info
     */
    public CookStepInfo getCookStepInfo() {
        if (this.StepType != RecipeStepType.COOK) {
            return null;
        }
        return new CookStepInfo(this.step);
    }
    /**
     * Converts a into type to an stepType
     * @param num The int
     * @return The step type
     */
    public static RecipeStepType convertIntToType(int num) {
        if (num == 1) {
            return RecipeStepType.COOK;
        }
        return RecipeStepType.NORMAL;
    }
}