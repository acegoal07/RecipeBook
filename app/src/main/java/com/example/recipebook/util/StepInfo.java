package com.example.recipebook.util;

public class StepInfo {
    private static StepType StepType;
    private String step;
    /**
     * Enum for step type
     */
    public enum StepType {
        NORMAL,
        COOK,
        COOL
    }
    /**
     * Constructor for StepInfo
     * @param step The step
     */
    public StepInfo(String step, StepType type) {
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
    public StepType getStepType() {
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
     * Gets the cook tempreture
     * @return The cook tempreture
     */
    public String getCookTemperature() {
        return this.step.split("%%")[1];
    }

    /**
     * Converts a into type to an stepTyoe
     * @param num The int
     * @return The step type
     */
    public static StepType convertIntToType(int num) {
        switch (num) {
            case 0:
                return StepType.NORMAL;
            case 1:
                return StepType.COOK;
            case 2:
                return StepType.COOL;
            default:
                return StepType.NORMAL;
        }
    }
    /**
     * Converts a step type to an int
     * @param type The step type
     * @return The int
     */
    public static int convertTypeToInt(StepType type) {
        switch (type) {
            case NORMAL:
                return 0;
            case COOK:
                return 1;
            case COOL:
                return 2;
            default:
                return 0;
        }
    }
}