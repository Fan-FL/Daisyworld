package Turtle;

public abstract class Daisy{
    protected float albedo = 0.5f;
    protected int age = 0;
    protected int maxAge = 25;
    protected String name = "";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract void update();

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
