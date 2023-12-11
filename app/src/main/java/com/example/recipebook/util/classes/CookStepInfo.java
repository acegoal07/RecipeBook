package com.example.recipebook.util.classes;

public class CookStepInfo {
    private final String hour;
    private final String minute;
    private final String temperature;
    private final String temperatureUnit;

    /**
     * Constructor for CookStepInfo
     *
     * @param step The step
     */
    public CookStepInfo(String step) {
        String[] splitStep = step.split("%%");
        this.hour = splitStep[0];
        this.minute = splitStep[1];
        this.temperature = splitStep[2];
        this.temperatureUnit = splitStep[3];
    }

    /**
     * Gets the hour
     *
     * @return The hour
     */
    public String getHour() {
        return this.hour;
    }

    /**
     * Gets the minute
     *
     * @return The minute
     */
    public String getMinute() {
        return this.minute;
    }

    /**
     * Gets the temperature
     *
     * @return The temperature
     */
    public String getTemperature() {
        return this.temperature;
    }

    /**
     * Gets the temperature unit
     *
     * @return The temperature unit
     */
    public String getTemperatureUnit() {
        return this.temperatureUnit;
    }

    /**
     * Gets the display time
     *
     * @return The display time
     */
    public String getDisplayTime() {
        if (this.hour.equals("0")) {
            return this.minute + " Mins";
        } else if (this.minute.equals("0")) {
            return this.hour + " Hrs";
        } else {
            return this.hour + " Hrs : " + this.minute + " Mins";
        }
    }

    /**
     * Gets the display temperature
     *
     * @return The display temperature
     */
    public String getDisplayTemperature() {
        return this.temperature + this.temperatureUnit;
    }

    /**
     * Gets the cook temperature symbol position
     *
     * @return The cook temperature symbol position
     */
    public String getCookTemperatureSymbolPosition() {
        if (getTemperatureUnit().equals("°F")) {
            return "1";
        } else {
            return "0";
        }
    }

    /**
     * Gets the full display
     *
     * @return The full display
     */
    public String getFullDisplay() {
        return "Cook for " + getDisplayTime() + " at " + getDisplayTemperature();
    }
}
