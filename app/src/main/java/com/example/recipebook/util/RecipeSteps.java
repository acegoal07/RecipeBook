package com.example.recipebook.util;

import java.util.ArrayList;

public class RecipeSteps {
    private String steps;

    public RecipeSteps(String steps) {
        this.steps = steps;
    }

    /**
     * Gets the steps
     * @return The steps
     */
    public ArrayList<StepInfo> getSteps() {
        if (steps == null) {
            return null;
        }
        ArrayList<StepInfo> returnSteps = new ArrayList<>();
        for (String step : steps.split("!!")) {
            String[] stepInfo = step.split("::");
            returnSteps.add(new StepInfo(stepInfo[1], StepInfo.convertIntToType(Integer.parseInt(stepInfo[0]))));
        }
        return returnSteps;
    }

    /**
     * Gets the raw steps
     * @return The raw string steps
     */
    public String[] getRawSteps() {
        return steps.split("!!");
    }
}
