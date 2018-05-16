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
    // the initial number of grey daisy
    private int numGreys = 0;
    // the initial global temperature
    private float globalTemperature = 0.0f;
    private float maxTemperature = -9999.0f;
    private float minTemperature = 9999.0f;
    private double totalTemperature = 0.0f;
    // The name of the input parameters cvs file
    private String parametersCvsFile = "Parameters.csv";
    // write result to a csv file
    private String csvFile = "Daisyworld.csv";
    // create a new writer
    private FileWriter writer = null;

    //
    public Simulation() {
        //read parameters
        CSVHandler.readCVS(parametersCvsFile);
        // cvs writer
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
     * randomly generate white daisies, black and grey daisies
     * calculating temperature or each patch and global temperature
     * writing the output to csv file
     */
    public void setup(){
        this.maxTemperature = -9999.0f;
        this.minTemperature = 9999.0f;
        this.totalTemperature = 0.0f;
        if (Params.scenario != Scenario.MAINTAIN){
            // get solar luminosity from its cooresponding scenario
            Params.solarLuminosity = Params.scenario.getSolarLuminosity();
        }
        // randomly get the number of black daisy and of white daisy
        Random random = new Random();
        numBlacks = Math.round(this.height*this.width*Params.startPctBlacks);
        numWhites = Math.round(this.height*this.width*Params.startPctWhites);
        numGreys = Math.round(this.height*this.width*Params.startPctGreys);

//        System.out.println(numBlacks);
//        System.out.println(numWhites);
//        System.out.println(numGreys);

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

        //randomly generate grey daisies
        for (int i=0; i<this.numGreys; i++){
            int index = Util.getRandom(0, this.height*this.width, set);
            Daisy daisy = new Daisy();
            daisy.setSpecies(Daisy.Species.GREY);
            daisy.setAlbedo(Params.albedoOfGreys);
            daisy.setAge(random.nextInt(Params.maxAge));
            daisy.setSprout(false);
            patches[index/this.width][index%this.width].setDaisy(daisy);
        }

        this.printWorldMap();

        this.calcTemperature();
        this.calcGlobalTemperature();
        this.countTemperature();

        this.outputSetup();

    }

    // write the output to csv file
    private void outputSetup() {
        try {
            CSVHandler.writeLine(writer, Arrays.asList("startPctWhites", String.valueOf(Params.startPctWhites)));
            CSVHandler.writeLine(writer, Arrays.asList("albedoOfWhites", String.valueOf(Params.albedoOfWhites)));
            CSVHandler.writeLine(writer, Arrays.asList("startPctBlacks", String.valueOf(Params.startPctBlacks)));
            CSVHandler.writeLine(writer, Arrays.asList("albedoOfBlacks", String.valueOf(Params.albedoOfBlacks)));
            CSVHandler.writeLine(writer, Arrays.asList("startPctGreys", String.valueOf(Params.startPctGreys)));
            CSVHandler.writeLine(writer, Arrays.asList("albedoOfGreys", String.valueOf(Params.albedoOfGreys)));
            CSVHandler.writeLine(writer, Arrays.asList("solarLuminosity", String.valueOf(Params.solarLuminosity)));
            CSVHandler.writeLine(writer, Arrays.asList("albedoOfSurface", String.valueOf(Params.albedoOfSurface)));
            CSVHandler.writeLine(writer, Arrays.asList("diffusePct", String.valueOf(Params.diffusePct)));
            CSVHandler.writeLine(writer, Arrays.asList("maxAge", String.valueOf(Params.maxAge)));
            CSVHandler.writeLine(writer, Arrays.asList("numWhites", String.valueOf(this.numWhites)));
            CSVHandler.writeLine(writer, Arrays.asList("numBlacks", String.valueOf(this.numBlacks)));
            CSVHandler.writeLine(writer, Arrays.asList("numGreys", String.valueOf(this.numGreys)));
            CSVHandler.writeLine(writer, Arrays.asList("start globalTemperature", String.valueOf(this.globalTemperature)));
            CSVHandler.writeLine(writer, Arrays.asList("tick", "numWhites", "numBlacks", "numGreys", "globalTemperature"));
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

        try {
            CSVHandler.writeLine(writer, Arrays.asList(
                    "avgTemperature", String.valueOf(this.totalTemperature/this.currentTick),
                    "minTemperature", String.valueOf(this.minTemperature),
                    "maxTemperature", String.valueOf(this.maxTemperature)));
            writer.flush();
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        // print the world map
        this.printWorldMap();
    }

    public void tick() {
        this.calcTemperature();
        this.diffuse();
        this.checkSurvivability();
        this.calcGlobalTemperature();

        this.currentTick++;

        try {
            CSVHandler.writeLine(writer, Arrays.asList(String.valueOf(this.currentTick), String.valueOf(this.numWhites),
                    String.valueOf(this.numBlacks), String.valueOf(this.numGreys), String.valueOf(this.globalTemperature)));
            writer.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        if (Params.scenario == Scenario.RAMP){
            if (this.currentTick > 200 && this.currentTick <= 400){
                Params.solarLuminosity = Params.solarLuminosity + 0.005f;
            }else if(this.currentTick > 600 && this.currentTick <= 850){
                Params.solarLuminosity = Params.solarLuminosity - 0.0025f;
            }
        }

        this.countTemperature();
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
        HashSet<Integer> worldSet = new HashSet<>();

        while(worldSet.size() < this.height*this.width){
            int patchIndex = Util.getRandom(0,this.height*this.width, worldSet);
            int i = patchIndex/this.width;
            int j = patchIndex%this.width;
            Patch patch = patches[i][j];

            // when there is daisy in the patch
            if (patch.getDaisy() != null){
                Daisy daisy = patch.getDaisy();
                // check whether the daisy is a new sprout one,
                // if so it will not sprout daisy at this tick
                if (daisy.isSprout()){
                    continue;
                }
                // increase the daisy's age
                daisy.setAge(daisy.getAge() + 1);
                // when the daisy's age exceeds the max age
                if (daisy.getAge() >= Params.maxAge){
                    if (daisy.getSpecies() == Daisy.Species.BLACK){
                        this.numBlacks--;
                    }else if (daisy.getSpecies() == Daisy.Species.WHITE){
                        this.numWhites--;
                    }else if (daisy.getSpecies() == Daisy.Species.GREY){
                        this.numGreys--;
                    }
                    // delete the object
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
                            }else if (daisy.getSpecies() == Daisy.Species.WHITE){
                                Daisy newDaisy = new Daisy();
                                newDaisy.setSpecies(Daisy.Species.WHITE);
                                newDaisy.setAlbedo(Params.albedoOfWhites);
                                patches[r][c].setDaisy(newDaisy);
                                this.numWhites++;
                            }else if (daisy.getSpecies() == Daisy.Species.GREY){
                                Daisy newDaisy = new Daisy();
                                newDaisy.setSpecies(Daisy.Species.GREY);
                                newDaisy.setAlbedo(Params.albedoOfGreys);
                                patches[r][c].setDaisy(newDaisy);
                                this.numGreys++;
                            }
                            seed = true;
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
                // when the patch has grey daisy, print 3;
                // otherwise, print 0
                if (patch.getDaisy() != null){
                    if(patch.getDaisy().getSpecies() == Daisy.Species.BLACK){
                        System.out.print(String.format("%1$3d", 2));
                    }else if(patch.getDaisy().getSpecies() == Daisy.Species.WHITE){
                        System.out.print(String.format("%1$3d", 1));
                    }else if(patch.getDaisy().getSpecies() == Daisy.Species.GREY){
                        System.out.print(String.format("%1$3d", 3));
                    }

                }else {
                    System.out.print(String.format("%1$3d", 0));
                }
            }
            System.out.println("");
        }
        System.out.println("");
    }

    // count the max, min and total temperate
    private void countTemperature(){
        if (this.minTemperature > this.globalTemperature){
            this.minTemperature = this.globalTemperature;
        }

        if (this.maxTemperature < this.globalTemperature){
            this.maxTemperature = this.globalTemperature;
        }

        this.totalTemperature += this.globalTemperature;
    }
}
