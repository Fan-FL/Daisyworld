import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

/**
 * The top-level component of the Daisy World simulator.
 * 
 * It is responsible for:
 *  - creating all the components of the system; 
 *  - running the simulation; 
 *  - calculating temperature of each patch
 *  - calculating average global temperature
 *  - checking the survivability of each daisy
 *  - printing the daisy world map
 *  - writing the output to csv file
 */
public class Simulation {
    // the initial tick
    private int currentTick = 0;
    // the default state of the simulation
    private boolean stop = false;
    // the default width of the map 
    private int width = 29;
    // the default height of the map
    private int height = 29;
    // create a new patch
    private Patch[][] patches;
    // the initial number of black daisy 
    private int numBlacks = 0;
    // the initial number of white daisy 
    private int numWhites = 0;
    // the default scenario
    Scenario scenario = Scenario.OUR;
    // the initial global temperature
    private float globalTemperature = 0.0f;
//    private String csvFile = "Daisyworld-" + String.valueOf(System.currentTimeMillis()) + ".csv";
    // write result to a csv file
    private String csvFile = "Daisyworld.csv";
    // create a new writer
    FileWriter writer = null;

    //
    public Simulation() {
        // writing to csv file 
        try {
            this.writer = new FileWriter(csvFile);
        }catch (IOException e) {
            e.printStackTrace();
        }
        //
        this.patches = new Patch[this.height][this.width];
        for(int i=0; i<this.height; i++){
            for(int j=0; j<this.width; j++){
                patches[i][j] = new Patch();
            }
        }
    }
    
    // @return the state of the simulation 
    public boolean isStop() {
        return stop;
    }
    
    // mark the state of the simulation 
    public void setStop(boolean stop) {
        this.stop = stop;
    }
    
    /*
     * setting up the simulation
     * getting the solar luminosity based on the scenario
     * randomly generate white daisies and black daisies
     * calculating temperature or each patch and global temperature
     * writing the output to csv file
     */
    public void setup(){
        if (scenario != Scenario.MAINTAIN){
            // get solar luminosity from its cooresponding scenario
            Params.solarLuminosity = scenario.getSolarLuminosity();
        }
        // randomly get the number of black daisy and of white daisy
        Random random = new Random();
        numBlacks = Math.round(this.height*this.width*Params.startPctBlacks);
        numWhites = Math.round(this.height*this.width*Params.startPctWhites);

//        System.out.println(numBlacks);
//        System.out.println(numWhites);

        //index of occupied patches
        HashSet<Integer> set = new HashSet<>();
        //randomly generate black daisies
        for (int i=0; i<this.numBlacks; i++){
            int index = Util.getRandom(0, this.height*this.width, set);
            Daisy daisy = new Daisy();
            daisy.setSpecies(Daisy.Species.BLACK);
            daisy.setAlbedo(Params.albedoOfBlacks);
            daisy.setAge(random.nextInt(Params.maxAge));
            daisy.setSprout(false);
            patches[index/this.width][index%this.width].setDaisy(daisy);
        }

        //randomly generate white daisies
        for (int i=0; i<this.numWhites; i++){
            int index = Util.getRandom(0, this.height*this.width, set);
            Daisy daisy = new Daisy();
            daisy.setSpecies(Daisy.Species.WHITE);
            daisy.setAlbedo(Params.albedoOfWhites);
            daisy.setAge(random.nextInt(Params.maxAge));
            daisy.setSprout(false);
            patches[index/this.width][index%this.width].setDaisy(daisy);
        }

        this.printWorldMap();

        this.calcTemperature();
        this.calcGlobalTemperature();

        this.outputSetup();
    }

    // write the output to csv file
    private void outputSetup() {
        try {
            CSVWriter.writeLine(writer, Arrays.asList("startPctWhites", String.valueOf(Params.startPctWhites)));
            CSVWriter.writeLine(writer, Arrays.asList("albedoOfWhites", String.valueOf(Params.albedoOfWhites)));
            CSVWriter.writeLine(writer, Arrays.asList("startPctBlacks", String.valueOf(Params.startPctBlacks)));
            CSVWriter.writeLine(writer, Arrays.asList("albedoOfBlacks", String.valueOf(Params.albedoOfBlacks)));
            CSVWriter.writeLine(writer, Arrays.asList("solarLuminosity", String.valueOf(Params.solarLuminosity)));
            CSVWriter.writeLine(writer, Arrays.asList("albedoOfSurface", String.valueOf(Params.albedoOfSurface)));
            CSVWriter.writeLine(writer, Arrays.asList("diffusePct", String.valueOf(Params.diffusePct)));
            CSVWriter.writeLine(writer, Arrays.asList("maxAge", String.valueOf(Params.maxAge)));
            CSVWriter.writeLine(writer, Arrays.asList("numBlacks", String.valueOf(this.numBlacks)));
            CSVWriter.writeLine(writer, Arrays.asList("numWhites", String.valueOf(this.numWhites)));
            CSVWriter.writeLine(writer, Arrays.asList("globalTemperature", String.valueOf(this.globalTemperature)));
            CSVWriter.writeLine(writer, Arrays.asList("tick", "numWhites", "numBlacks", "globalTemperature"));
            writer.flush();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // run the simulation
    public void go(){
        // when the max tick
        if (Params.useMaxTick){
            while (!stop && currentTick < Params.maxTick){
                tick();
            }
        }else{
            // start to run
            while (!stop){
                tick();
            }
        }
        // print the world map
        this.printWorldMap();
        System.out.println("globalTemperature = "+ String.valueOf(this.globalTemperature));

        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void tick() {
        this.calcTemperature();
        this.diffuse();
        this.checkSurvivability();
        this.calcGlobalTemperature();

        this.currentTick++;

        try {
            CSVWriter.writeLine(writer, Arrays.asList(String.valueOf(this.currentTick), String.valueOf(this.numWhites),
                    String.valueOf(this.numBlacks), String.valueOf(this.globalTemperature)));
            writer.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        if (scenario == Scenario.RAMP){
            if (this.currentTick > 200 && this.currentTick <= 400){
                Params.solarLuminosity = Params.solarLuminosity + 0.005f;
            }else if(this.currentTick > 600 && this.currentTick <= 850){
                Params.solarLuminosity = Params.solarLuminosity - 0.0025f;
            }
        }
    }

    // calculating temperature for each patch
    private void calcTemperature(){
        for(int i=0; i<this.height; i++){
            for(int j=0; j<this.width; j++){
                Patch patch = patches[i][j];
                patch.calcTemperature();
            }
        }
    }
    
    // calculating average global temperature
    private void calcGlobalTemperature(){
        float sum = 0.0f;
        for(int i=0; i<this.height; i++){
            for(int j=0; j<this.width; j++){
                Patch patch = patches[i][j];
                sum = sum + patch.getTemperature();
            }
        }
        this.globalTemperature = sum / (this.height*this.width);
    }

    private void diffuse(){
        for(int i=0; i<this.height; i++){
            for(int j=0; j<this.width; j++){
                Patch patch = patches[i][j];
                float diffuseTemp = patch.getTemperature() * Params.diffusePct;
                patch.setTemperature(patch.getTemperature() * (1-Params.diffusePct));
                for (int dr = -1; dr <= 1; dr++) {
                    for (int dc = -1; dc <= 1; dc++) {
                        if (dr == 0 && dc == 0) continue;
                        int r = i + dr;
                        int c = j + dc;
                        if ((r >= 0) && (r < this.height) && (c >= 0) && (c < this.width)) {
                            patches[r][c].setTemperature(patches[r][c].getTemperature() + diffuseTemp/8.0f);
                        }else {
                            patch.setTemperature(patch.getTemperature() + diffuseTemp/8.0f);
                        }
                    }
                }
            }
        }
    }
    
    // checking the survivability of each daisy
    private void checkSurvivability(){
        float seedThreshold = 0.0f;
        for(int i=0; i<this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                Patch patch = patches[i][j];
                // when there is daisy in the patch
                if (patch.getDaisy() != null){
                    Daisy daisy = patch.getDaisy();
                    // check whether the daisy could sprout
                    if (daisy.isSprout()){
                        continue;
                    }
                    // mark the daisy's age 
                    daisy.setAge(daisy.getAge() + 1);
                    // when the daisy's age exceeds the max age 
                    if (daisy.getAge() >= Params.maxAge){
                        if (daisy.getSpecies() == Daisy.Species.BLACK){
                            this.numBlacks--;
                        }else{
                            this.numWhites--;
                        }
                        // there is no daisy in the patch
                        patch.setDaisy(null);
                        continue;
                    }

                    seedThreshold = 0.1457f * patch.getTemperature()
                            - 0.0032f * patch.getTemperature()*patch.getTemperature()
                            - 0.6443f;

                    if (Math.random() < seedThreshold){
                        boolean seed = false;
                        HashSet<Integer> set = new HashSet<>();
                        while(!seed && set.size() < 9){
                            int index = Util.getRandom(0,9, set);
                            int dr = index/3 - 1 ;
                            int dc = index%3 - 1 ;
                            int r = i + dr;
                            int c = j + dc;
                            if ((r >= 0) && (r < this.height) && (c >= 0) && (c < this.width)
                                    && patches[r][c].getDaisy() == null) {
                                if (daisy.getSpecies() == Daisy.Species.BLACK){
                                    Daisy newDaisy = new Daisy();
                                    newDaisy.setSpecies(Daisy.Species.BLACK);
                                    newDaisy.setAlbedo(Params.albedoOfBlacks);
                                    patches[r][c].setDaisy(newDaisy);
                                    this.numBlacks++;
                                }else {
                                    Daisy newDaisy = new Daisy();
                                    newDaisy.setSpecies(Daisy.Species.WHITE);
                                    newDaisy.setAlbedo(Params.albedoOfWhites);
                                    patches[r][c].setDaisy(newDaisy);
                                    this.numWhites++;
                                }
                                seed = true;
                            }
                        }
                    }
                }
            }
        }
        // update the state of patches
        for(int i=0; i<this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                Patch patch = patches[i][j];
                if (patch.getDaisy() != null) {
                    patch.getDaisy().setSprout(false);
                }
            }
        }
    }
   
    // print daisies on the map
    private void printWorldMap(){
        System.out.print(String.format("%1$3s", "idx"));
        for(int j=0; j<this.width; j++){
            System.out.print(String.format("%1$3d", j));
        }
        System.out.println("");
        for(int i=0; i<this.height; i++){
            System.out.print(String.format("%1$3d", i));
            for(int j=0; j<this.width; j++){
                Patch patch = patches[i][j];
                // when the patch has black daisy, print 2;
                // when the patch has white daisy, print 1;
                // otherwise, print 0
                if (patch.getDaisy() != null){
                    if(patch.getDaisy().getSpecies() == Daisy.Species.BLACK){
                        System.out.print(String.format("%1$3d", 2));
                    }else {
                        System.out.print(String.format("%1$3d", 1));
                    }

                }else {
                    System.out.print(String.format("%1$3d", 0));
                }
            }
            System.out.println("");
        }
        System.out.println("");
    }
}
