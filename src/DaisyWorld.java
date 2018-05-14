/* The top-level component of the Daisy World simulator.
 * It is responsible for:
 *  - creating all the components of the system; 
 *  - starting the simulation; 
 *  - stop the simulation. 
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
