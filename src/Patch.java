import Turtle.Daisy;

public class Patch {
    private Daisy daisy = null;
    private float temperature;

    public Daisy getDaisy() {
        return daisy;
    }

    public void setDaisy(Daisy daisy) {
        this.daisy = daisy;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }


    public void setup() {
    }

    public void calcTemperature() {
        float absorbedLuminosity = 0.0f;
        float localHeating = 0.0f;
        if (this.daisy == null) {
            absorbedLuminosity = (1 - Params.albedoOfSurface) * Params.solarLuminosity;
        } else {
            absorbedLuminosity = (1 - this.daisy.getAlbedo()) * Params.solarLuminosity;
        }

        if (absorbedLuminosity > 0.0f){
            localHeating = 72*(float)Math.log(absorbedLuminosity) + 80;
        }else{
            localHeating = 80.0f;
        }

        this.temperature = (this.temperature + localHeating) / 2.0f;
    }
}
