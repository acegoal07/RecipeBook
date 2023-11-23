package com.example.recipebook.util;

public class StepInfo {
    private RecipeStepType StepType;
    private String step;
    public enum RecipeStepType {
        NORMAL,
        COOK,
        COOL;
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
     * Gets the time
     * @return The time
     */
    public String getTime() {
        return this.step.split("%%")[0];
    }
    /**
     * Gets the cook temperature
     * @return The cook temperature
     */
    public String getCookTemperature() {
        return this.step.split("%%")[1];
    }
    /**
     * Converts a into type to an stepTyoe
     * @param num The int
     * @return The step type
     */
    public static RecipeStepType convertIntToType(int num) {
        switch (num) {
            case 1:
                return RecipeStepType.COOK;
            case 2:
                return RecipeStepType.COOL;
            default:
                return RecipeStepType.NORMAL;
        }
    }
}