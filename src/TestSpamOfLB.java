import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by zhanglizhong on 3/25/17.
 */
public class TestSpamOfLB {
    private HashMap<String, ArrayList<Double>> data;
    private ArrayList<Integer> class_name;

    public TestSpamOfLB(File spamFolder) throws Exception {
        String[] spamFiles = spamFolder.list();
        int count = 0;
        this.class_name = new ArrayList<>(Collections.nCopies(spamFiles.length, 0));
        this.data = new HashMap<>();
        for (String file : spamFiles) {
            BufferedReader br = new BufferedReader(new FileReader(spamFolder + "/" + file));
            String eachLine = br.readLine();

            while (eachLine != null) {

                for (String t : eachLine.replaceAll("^a-zA-Z", "").replaceAll("\\p{Punct}+", "").toLowerCase().split(" ")) {
                    if (data.containsKey(t)) {
                        data.get(t).set(count, data.get(t).get(count) + 1);
                    } else {
                        ArrayList<Double> nums_words = new ArrayList<>(Collections.nCopies(spamFiles.length, 0.0));
                        nums_words.set(count, 1.0);
                        data.put(t, nums_words);
                    }
                }
                eachLine = br.readLine();
            }
            class_name.set(count, 1);
            count++;
            br.close();

        }
    }

    public HashMap<String, ArrayList<Double>> getData() {
        return data;
    }

    public ArrayList<Integer> getClass_name() {
        return class_name;
    }
}
