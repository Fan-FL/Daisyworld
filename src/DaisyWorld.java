/**
 * The class is used to run the Daisy World simulator.
*/
public class DaisyWorld {
    public static void main(String[] args) {
        Simulation simulation = new Simulation();
        simulation.setup();
        simulation.go();
    }
}
