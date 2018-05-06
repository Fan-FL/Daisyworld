package Turtle;

public class Daisy{
    protected float albedo = 0.5f;
    protected int age = 0;
    protected int maxAge = 25;
    public enum Species {
        BLACK, WHITE
    }
    protected Species species;

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

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }
}
