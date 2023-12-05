package com.example.recipebook.util.classes;

public class StepInfo {

    private RecipeStepTypeEnum StepType;
    private String step;

    /**
     * Constructor for StepInfo
     *
     * @param step The step
     */
    public StepInfo(String step, RecipeStepTypeEnum type) {
        this.step = step;
        this.StepType = type;
    }

    /**
     * Gets the step
     *
     * @return The step
     */
    public String getStep() {
        return this.step;
    }

    /**
     * Gets the step type
     *
     * @return The step type
     */
    public RecipeStepTypeEnum getStepType() {
        return this.StepType;
    }

    /**
     * Gets the cook step info
     *
     * @return The cook step info
     */
    public CookStepInfo getCookStepInfo() {
        if (this.StepType != RecipeStepTypeEnum.COOK) {
            return null;
        }
        return new CookStepInfo(this.step);
    }
}