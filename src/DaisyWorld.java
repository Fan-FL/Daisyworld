public class DaisyWorld {
    public static void main(String[] args) {
        Simulation simulation = new Simulation();
        Params.useMaxTick = true;
        Params.maxTick = 800;
//        simulation.tick();
        simulation.setup();
        simulation.go();
    }
}
