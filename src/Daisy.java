/**
 * The class representing a daisy's state
 */
public class Daisy{
    // this daisy's albedo
    protected float albedo = 0.5f;
    // this daisy's age
    protected int age = 0;
    // true if the daisy is newly sprouted and cannot reproduce
    boolean sprout = true;
    // the kind of daisy
    protected Species species = Species.BLACK;
    
    public boolean isSprout() {
        return sprout;
    }

    public void setSprout(boolean sprout) {
        this.sprout = sprout;
    }

    // there are three types of daisy, black daisy, white daisy and grey daisy
    public enum Species {
        BLACK, WHITE, GREY
    }

    public Species getSpecies() {
        return species;
    }

    public void setSpecies(Species species) {
        this.species = species;
    }

    public float getAlbedo() {
        return albedo;
    }

    public void setAlbedo(float albedo) {
        this.albedo = albedo;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
