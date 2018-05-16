/**
 * The class representing the initial parameters in the simulation
 */
public class Params {
    // Whether the simulation will stop when
    // it reaches the max tick number
    public static boolean useMaxTick = true;
    // The max tick number of simulation
    public static int maxTick = 800;
    // The default percentage of white daisy
//    public static float startPctWhites = 0.2f;
    public static float startPctWhites = 0.15f;
    // The default percentage of black daisy
//    public static float startPctBlacks = 0.2f;
    public static float startPctBlacks = 0.15f;
    // The default percentage of grey daisy
    public static float startPctGreys = 0.1f;
//    public static float startPctGreys = 0.0f;
    // The default albedo of white daisy
    public static float albedoOfWhites = 0.75f;
    // The default albedo of black daisy
    public static float albedoOfBlacks = 0.25f;
    // The default albedo of grey daisy
    public static float albedoOfGreys = 0.5f;
    // The default albedo of surface
    public static float albedoOfSurface = 0.4f;
    // The default value of solar luminosity
    public static float solarLuminosity = 1.0f;
    // The percentage of diffuse
    public static float diffusePct = 0.5f;
    // The max age of daisy
    public static int maxAge = 25;
    // the default scenario
    public static Scenario scenario = Scenario.MAINTAIN;
}
