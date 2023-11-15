package com.example.recipebook.util;

public class StepInfo {
    private static StepType StepType = null;
    String step;
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
}
