package com.example.recipebook.util.classes;

import java.util.ArrayList;

public class RecipeSteps {

    private String steps;

    public RecipeSteps(String steps) {
        this.steps = steps;
    }

    /**
     * Gets the steps
     *
     * @return The steps
     */
    public ArrayList<StepInfo> getSteps() {
        if (steps == null || steps.isEmpty()) {
            return null;
        }
        ArrayList<StepInfo> returnSteps = new ArrayList<>();
        for (String step : getRawSteps()) {
            String[] stepInfo = step.split("::");
            returnSteps.add(new StepInfo(stepInfo[1], StepInfo.convertIntToType(Integer.parseInt(stepInfo[0]))));
        }
        return returnSteps;
    }

    /**
     * Gets the raw steps
     *
     * @return The raw string steps
     */
    public String[] getRawSteps() {
        if (steps == null || steps.isEmpty()) {
            return null;
        }
        return steps.split("!!");
    }

    /**
     * Gets the raw steps string
     *
     * @return The raw string steps
     */
    public String getRawStepsString() {
        return steps;
    }

    /**
     * Gets the step count
     *
     * @return The step count
     */
    public int getStepCount() {
        if (steps == null || steps.isEmpty()) {
            return 0;
        }
        return getRawSteps().length;
    }
}
