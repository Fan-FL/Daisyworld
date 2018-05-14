/* The daisy represents 
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
    
    public boolean isSprout() {
        return sprout;
    }

    public void setSprout(boolean sprout) {
        this.sprout = sprout;
    }
    // there are two types of daisy, black daisy and white daisy
    public enum Species {
        BLACK, WHITE
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
