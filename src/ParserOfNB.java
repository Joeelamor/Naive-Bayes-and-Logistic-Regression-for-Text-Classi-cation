import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by zhanglizhong on 3/20/17.
 */
public class ParserOfNB {

    private Map<String, Double[]> frequency;
    private Double countSpam;
    private Double countHam;

    public ParserOfNB(File hamFolder, File spamFolder) throws Exception {

        String []hamFiles = hamFolder.list();
        String []spamFiles = spamFolder.list();
        this.frequency = new HashMap<>();
        countSpam = 0.0;
        countHam = 0.0;

        for(String file: hamFiles) {
            BufferedReader br = new BufferedReader(new FileReader(hamFolder+"/"+file));
            String eachLine = br.readLine();

            while(eachLine != null){

                for (String t : eachLine.replaceAll("^a-zA-Z", "").toLowerCase().split(" ")) {

                    countHam += 1;
                    if(frequency.containsKey(t)){
                        frequency.get(t)[0]++;
                    }
                    else {
                        Double[] val = {1.0, 0.0};
                        frequency.put(t, val);
                    }
                }
                eachLine = br.readLine();
            }
            br.close();
        }
        for(String file: spamFiles) {
            BufferedReader br = new BufferedReader(new FileReader(spamFolder+"/"+file));
            String eachLine = br.readLine();

            while(eachLine != null){

                for (String t : eachLine.replaceAll("^a-zA-Z", "").toLowerCase().split(" ")) {

                    countSpam += 1;
                    if(frequency.containsKey(t)){
                        frequency.get(t)[1]++;
                    }
                    else {
                        Double[] val = {0.0, 1.0};
                        frequency.put(t, val);
                    }
                }
                eachLine = br.readLine();
            }
            br.close();
        }


    }


    public ParserOfNB(File hamFolder, File spamFolder, File stopword) throws Exception {

        String []hamFiles = hamFolder.list();
        String []spamFiles = spamFolder.list();
        this.frequency = new HashMap<>();
        countSpam = 0.0;
        countHam = 0.0;


        HashMap<String, Integer> stop = new HashMap<>();

        BufferedReader stopbr = new BufferedReader(new FileReader(stopword));
        String stopeachLine = stopbr.readLine();

        while(stopeachLine != null){

            for (String t : stopeachLine.replaceAll("^a-zA-Z", "").replaceAll("\\p{Punct}+", "").toLowerCase().split(" ")) {
                if(!stop.containsKey(t)){
                    stop.put(t, 1);
                }
            }
            stopeachLine = stopbr.readLine();
        }
        stopbr.close();


        for(String file: hamFiles) {
            BufferedReader br = new BufferedReader(new FileReader(hamFolder+"/"+file));
            String eachLine = br.readLine();

            while(eachLine != null){

                for (String t : eachLine.replaceAll("^a-zA-Z", "").toLowerCase().split(" ")) {
                    if (stop.containsKey(t))
                        continue;
                    else {

                        countHam += 1;
                        if (frequency.containsKey(t)) {
                            frequency.get(t)[0]++;
                        } else {
                            Double[] val = {1.0, 0.0};
                            frequency.put(t, val);
                        }
                    }
                }
                eachLine = br.readLine();
            }
            br.close();
        }
        for(String file: spamFiles) {
            BufferedReader br = new BufferedReader(new FileReader(spamFolder+"/"+file));
            String eachLine = br.readLine();

            while(eachLine != null){

                for (String t : eachLine.replaceAll("^a-zA-Z", "").toLowerCase().split(" ")) {

                    if (stop.containsKey(t))
                        continue;
                    else {
                        countSpam += 1;
                        if (frequency.containsKey(t)) {
                            frequency.get(t)[1]++;
                        } else {
                            Double[] val = {0.0, 1.0};
                            frequency.put(t, val);
                        }
                    }
                }
                eachLine = br.readLine();
            }
            br.close();
        }


    }

    public Map<String, Double[]> getFrequency() {
        return frequency;
    }

    public Double getCountSpam() {

        return countSpam;
    }

    public Double getCountHam() {
        return countHam;
    }
}


