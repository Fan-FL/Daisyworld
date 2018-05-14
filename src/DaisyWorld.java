/**
 * The class is used to run the Daisy World simulator.
*/
public class DaisyWorld {
    public static void main(String[] args) {
        Simulation simulation = new Simulation();
        Params.useMaxTick = true;
        // the simulation will stop when the tick equals 800
        Params.maxTick = 800;
//        simulation.tick();
        simulation.setup();
        simulation.go();
    }
}
