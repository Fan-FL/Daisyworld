/**
 * The main class.
*/
public class DaisyWorld {
    public static void main(String[] args) {
        Simulation simulation = new Simulation();
        simulation.setup();
        simulation.go();
    }
}
