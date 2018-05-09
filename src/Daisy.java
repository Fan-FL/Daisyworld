public class Daisy{
    protected float albedo = 0.5f;
    protected int age = 0;
    boolean sprout = true;
    protected Species species = Species.BLACK;

    public boolean isSprout() {
        return sprout;
    }

    public void setSprout(boolean sprout) {
        this.sprout = sprout;
    }

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
