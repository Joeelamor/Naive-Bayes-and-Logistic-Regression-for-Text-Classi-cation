import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by zhanglizhong on 3/25/17.
 */
public class TestHamOfLB {
    private HashMap<String, ArrayList<Double>> data;
    private ArrayList<Integer> class_name;

    public TestHamOfLB(File hamFolder) throws Exception {
        String[] hamFiles = hamFolder.list();
        int count = 0;
        this.class_name = new ArrayList<>(Collections.nCopies(hamFiles.length, 0));
        this.data = new HashMap<>();
        for (String file : hamFiles) {
            BufferedReader br = new BufferedReader(new FileReader(hamFolder + "/" + file));
            String eachLine = br.readLine();

            while (eachLine != null) {

                for (String t : eachLine.replaceAll("^a-zA-Z", "").replaceAll("\\p{Punct}+", "").toLowerCase().split(" ")) {
                    if (data.containsKey(t)) {
                        data.get(t).set(count, data.get(t).get(count) + 1);
                    } else {
                        ArrayList<Double> nums_words = new ArrayList<>(Collections.nCopies(hamFiles.length, 0.0));
                        nums_words.set(count, 1.0);
                        data.put(t, nums_words);
                    }
                }
                eachLine = br.readLine();
            }
            class_name.set(count, 0);
            count++;
            br.close();
        }
    }

    public TestHamOfLB(File hamFolder, File stopword) throws Exception {


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




        String[] hamFiles = hamFolder.list();
        int count = 0;
        this.class_name = new ArrayList<>(Collections.nCopies(hamFiles.length, 0));
        this.data = new HashMap<>();
        for (String file : hamFiles) {
            BufferedReader br = new BufferedReader(new FileReader(hamFolder + "/" + file));
            String eachLine = br.readLine();

            while (eachLine != null) {

                for (String t : eachLine.replaceAll("^a-zA-Z", "").replaceAll("\\p{Punct}+", "").toLowerCase().split(" ")) {
                    if (stop.containsKey(t))
                        continue;
                    else {
                        if (data.containsKey(t)) {
                            data.get(t).set(count, data.get(t).get(count) + 1);
                        } else {
                            ArrayList<Double> nums_words = new ArrayList<>(Collections.nCopies(hamFiles.length, 0.0));
                            nums_words.set(count, 1.0);
                            data.put(t, nums_words);
                        }
                    }
                }
                eachLine = br.readLine();
            }
            class_name.set(count, 0);
            count++;
            br.close();
        }
    }

    public ArrayList<Integer> getClass_name() {
        return class_name;
    }

    public HashMap<String, ArrayList<Double>> getData() {

        return data;
    }
}
