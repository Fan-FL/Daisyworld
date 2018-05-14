/**
 * The class representing a daisy's state
 */
public class Daisy{
    // this daisy's origin albedo
    protected float albedo = 0.5f;
    // this daisy's initial age
    protected int age = 0;
    // true if the daisy could sprout
    boolean sprout = true;
    // the default species is black daisy
    protected Species species = Species.BLACK;
    
    // @return whether the daisy could sprout
    public boolean isSprout() {
        return sprout;
    }
    // mark whether the daisy could sprout
    public void setSprout(boolean sprout) {
        this.sprout = sprout;
    }
    // there are two types of daisy, black daisy and white daisy
    public enum Species {
        BLACK, WHITE, GREY
    }
    // @return the species of this daisy
    public Species getSpecies() {
        return species;
    }
    // mark this daisy as black or white
    public void setSpecies(Species species) {
        this.species = species;
    }
    // @return the albedo of this daisy
    public float getAlbedo() {
        return albedo;
    }
    // mark this daisy's albedo 
    public void setAlbedo(float albedo) {
        this.albedo = albedo;
    }
    // @return the age of this daisy
    public int getAge() {
        return age;
    }
    // mark this daisy's age 
    public void setAge(int age) {
        this.age = age;
    }
}
