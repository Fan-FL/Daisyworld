/**
 * The class represents patch where daisy lives in 
 */
public class Patch {
    // there is no daisy lives in the patch
    private Daisy daisy = null;
    // the default temperature of each patch
    private float temperature = 0.0f;

    // @return the 
    public Daisy getDaisy() {
        return daisy;
    }
    // mark the daisy in the patch
    public void setDaisy(Daisy daisy) {
        this.daisy = daisy;
    }
    // @return the temperature of the patch
    public float getTemperature() {
        return temperature;
    }
    // mark the temperature of the patch
    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }


    public void setup() {
    }

    // calculating the temperature, the proceduce based on 
    public void calcTemperature() {
        float absorbedLuminosity = 0.0f;
        float localHeating = 0.0f;
        if (this.daisy == null) {
            // the percentage of absorbed energy is calculated (1 - albedo-of-surface) 
            // and then multiplied by the solar-luminosity 
            // to give a scaled absorbed-luminosity.
            absorbedLuminosity = (1 - Params.albedoOfSurface) * Params.solarLuminosity;
        } else {
            // the percentage of absorbed energy is calculated (1 - albedo) 
            // and then multiplied by the solar-luminosity
            //to give a scaled absorbed-luminosity.
            absorbedLuminosity = (1 - this.daisy.getAlbedo()) * Params.solarLuminosity;
        }
        // local-heating is calculated as logarithmic function of solar-luminosity
        // where a absorbed-luminosity of 1 yields a local-heating of 80 degrees C
        // and an absorbed-luminosity of .5 yields a local-heating of approximately 30 C
        // and a absorbed-luminosity of 0.01 yields a local-heating of approximately -273 C
        if (absorbedLuminosity > 0.0f){
            localHeating = 72*(float)Math.log(absorbedLuminosity) + 80;
        }else{
            localHeating = 80.0f;
        }
        // set the temperature at this patch to be the average of 
        // the current temperature and the local-heating effect
        this.temperature = (this.temperature + localHeating) / 2.0f;
    }
}
