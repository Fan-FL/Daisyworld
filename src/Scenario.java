public enum Scenario {
    RAMP("ramp-up-ramp-down", 0.8f), LOW("low solar luminosity", 0.6f),
    OUR("our solar luminosity", 1.0f), HIGH("high solar luminosity", 1.4f), MAINTAIN("maintain", 0.0f);
    private String name;
    private float albedo;
    private Scenario(String name, float albedo) {
        this.name = name;
        this.albedo = albedo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getAlbedo() {
        return albedo;
    }

    public void setAlbedo(float albedo) {
        this.albedo = albedo;
    }
}