import java.io.*;
import java.util.List;

public class CSVHandler {

    private static final char DEFAULT_SEPARATOR = ',';

    /*
        Format special characters
     */
    private static String format(String value) {
        String result = value;
        if (result.contains("\"")) {
            result = result.replace("\"", "\"\"");
        }
        return result;

    }

    /*
        Write to a csv file.
        Add a comma(,) between two elements in values
     */
    public static void writeLine(Writer w, List<String> values) throws IOException {

        boolean first = true;

        StringBuilder sb = new StringBuilder();
        for (String value : values) {
            if (!first) {
                sb.append(DEFAULT_SEPARATOR);
            }
            sb.append(format(value));
            first = false;
        }
        sb.append("\n");
        w.append(sb.toString());
    }

    /*
        Read parameters into Params class from a csv file
     */
    public static void readCVS(String filename){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while((line=reader.readLine())!=null){
                String item[] = line.split(",");
                String name = item[0];
                String value = item[1];
                switch (name){
                    case "useMaxTick":{
                        if(value.toLowerCase().equals("true")){
                            Params.useMaxTick = true;
                        }else{
                            Params.useMaxTick = false;
                        }
                        break;
                    }
                    case "maxTick":{
                        Params.maxTick = Integer.parseInt(value);
                        break;
                    }
                    case "startPctWhites":{
                        Params.startPctWhites = Float.parseFloat(value);
                        break;
                    }
                    case "startPctBlacks":{
                        Params.startPctBlacks = Float.parseFloat(value);
                        break;
                    }
                    case "startPctGreys":{
                        Params.startPctGreys = Float.parseFloat(value);
                        break;
                    }
                    case "albedoOfWhites":{
                        Params.albedoOfWhites = Float.parseFloat(value);
                        break;
                    }
                    case "albedoOfBlacks":{
                        Params.albedoOfBlacks = Float.parseFloat(value);
                        break;
                    }
                    case "albedoOfGreys":{
                        Params.albedoOfGreys = Float.parseFloat(value);
                        break;
                    }
                    case "solarLuminosity":{
                        Params.solarLuminosity = Float.parseFloat(value);
                        break;
                    }
                    case "albedoOfSurface":{
                        Params.albedoOfSurface = Float.parseFloat(value);
                        break;
                    }
                    case "diffusePct":{
                        Params.diffusePct = Float.parseFloat(value);
                        break;
                    }
                    case "maxAge":{
                        Params.maxAge = Integer.parseInt(value);
                        break;
                    }
                    case "scenario":{
                        switch (Integer.parseInt(value)){
                            case 0: {
                                Params.scenario = Scenario.RAMP;
                                break;
                            }
                            case 1: {
                                Params.scenario = Scenario.LOW;
                                break;
                            }
                            case 2: {
                                Params.scenario = Scenario.OUR;
                                break;
                            }
                            case 3: {
                                Params.scenario = Scenario.HIGH;
                                break;
                            }
                            case 4: {
                                Params.scenario = Scenario.MAINTAIN;
                                break;
                            }
                            default:
                                break;
                        }
                    }
                    default:
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}