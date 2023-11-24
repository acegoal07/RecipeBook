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
     * Gets the display time
     * @return The display time
     */
    public String getDisplayTime() {
        return getCookStepInfo()[0] + " Hrs : " + getCookStepInfo()[1]+" Mins";
    }
    /**
     * Gets the display temperature
     * @return The display temperature
     */
    public String getDisplayTemperature() {
        return getCookStepInfo()[2] + getCookStepInfo()[3];
    }
    /**
     * Gets the cook hour
     * @return The cook hour
     */
    public String getCookHour() {
        return getCookStepInfo()[0];
    }
    /**
     * Gets the cook minute
     * @return The cook minute
     */
    public String getCookMinute() {
        return getCookStepInfo()[1];
    }
    /**
     * Gets the cook temperature
     * @return The cook temperature
     */
    public String getCookTemperature() {
        return getCookStepInfo()[2];
    }
    /**
     * Gets the cook temperature symbol position
     * @return The cook temperature symbol position
     */
    public String getCookTemperatureSymbolPosition() {
        switch (getCookStepInfo()[3]) {
            case "°C":
                return "0";
            case "°F":
                return "1";
            default:
                return "0";
        }
    }
    /**
     * Gets the cook step info
     * @return The cook step info
     */
    public String[] getCookStepInfo() {
        return this.step.split("%%");
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