/*
    Five scenario with corresponding solar luminosity.
    Solar luminosity in MAINTAIN scenario will be set to the value in Params.solarLuminosity.
    Solar luminosity in RAMP will changed in certain periods.
 */
public enum Scenario {
    RAMP("ramp-up-ramp-down", 0.8f), LOW("low solar luminosity", 0.6f),
    OUR("our solar luminosity", 1.0f), HIGH("high solar luminosity", 1.4f),
    MAINTAIN("maintain", 0.0f);
    private String name;
    private float solarLuminosity;
    private Scenario(String name, float albedo) {
        this.name = name;
        this.solarLuminosity = albedo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getSolarLuminosity() {
        return solarLuminosity;
    }

    public void setSolarLuminosity(float albedo) {
        this.solarLuminosity = albedo;
    }
}