import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;

/**
 * Created by zhanglizhong on 3/22/17.
 */
public class TestSetOfNB {

    private Double resultOfHam;
    private Double resultOfSpam;

    public TestSetOfNB(File hamTest, File spamTest, NaiveBayes naiveBayes) throws Exception {

        this.resultOfHam = null;
        this.resultOfSpam = null;
        String []hamFiles = hamTest.list();
        String []spamFiles = spamTest.list();
        Double pos1 = 0.0;
        Double pos2 = 0.0;

        for(String file: hamFiles) {
            Double probHam = Math.log(naiveBayes.getPrior());
            Double probHamBar = Math.log(1 - naiveBayes.getPrior());
            BufferedReader br = new BufferedReader(new FileReader(hamTest+"/"+file));
            String eachLine = br.readLine();

            while(eachLine != null){
                for (String t : eachLine.replaceAll("^a-zA-Z", "").toLowerCase().split(" ")) {
                    if(naiveBayes.getProb().containsKey(t)){
                        probHam += Math.log(naiveBayes.getProb().get(t)[0]);
                        probHamBar += Math.log(naiveBayes.getProb().get(t)[1]);
                    }
                }
                eachLine = br.readLine();
            }
            br.close();
            if(probHam >= probHamBar)
                pos1++;
        }
        this.resultOfHam = pos1/hamFiles.length;

        for(String file: spamFiles) {
            Double probSpam = Math.log(1 - naiveBayes.getPrior());
            Double probSpamBar = Math.log(naiveBayes.getPrior());
            BufferedReader br = new BufferedReader(new FileReader(spamTest+"/"+file));
            String eachLine = br.readLine();

            while(eachLine != null){
                for (String t : eachLine.replaceAll("^a-zA-Z", "").toLowerCase().split(" ")) {
                    if(naiveBayes.getProb().containsKey(t)) {
                        probSpam += Math.log(naiveBayes.getProb().get(t)[1]);
                        probSpamBar += Math.log(naiveBayes.getProb().get(t)[0]);
                    }
                }
                eachLine = br.readLine();
            }
            br.close();
            if(probSpam >= probSpamBar)
                pos2++;
        }
        this.resultOfSpam = pos2/spamFiles.length;

    }

    public TestSetOfNB(File hamTest, File spamTest, NaiveBayes naiveBayes, File stopword) throws Exception {


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


        this.resultOfHam = null;
        this.resultOfSpam = null;
        String []hamFiles = hamTest.list();
        String []spamFiles = spamTest.list();
        Double pos1 = 0.0;
        Double pos2 = 0.0;

        for(String file: hamFiles) {
            Double probHam = Math.log(naiveBayes.getPrior());
            Double probHamBar = Math.log(1 - naiveBayes.getPrior());
            BufferedReader br = new BufferedReader(new FileReader(hamTest+"/"+file));
            String eachLine = br.readLine();

            while(eachLine != null){
                for (String t : eachLine.replaceAll("^a-zA-Z", "").toLowerCase().split(" ")) {
                    if (stop.containsKey(t))
                        continue;
                    else {
                        if (naiveBayes.getProb().containsKey(t)) {
                            probHam += Math.log(naiveBayes.getProb().get(t)[0]);
                            probHamBar += Math.log(naiveBayes.getProb().get(t)[1]);
                        }
                    }
                }
                eachLine = br.readLine();
            }
            br.close();
            if(probHam >= probHamBar)
                pos1++;
        }
        this.resultOfHam = pos1/hamFiles.length;

        for(String file: spamFiles) {
            Double probSpam = Math.log(1 - naiveBayes.getPrior());
            Double probSpamBar = Math.log(naiveBayes.getPrior());
            BufferedReader br = new BufferedReader(new FileReader(spamTest+"/"+file));
            String eachLine = br.readLine();

            while(eachLine != null){
                for (String t : eachLine.replaceAll("^a-zA-Z", "").toLowerCase().split(" ")) {
                    if (stop.containsKey(t))
                        continue;
                    else {
                        if (naiveBayes.getProb().containsKey(t)) {
                            probSpam += Math.log(naiveBayes.getProb().get(t)[1]);
                            probSpamBar += Math.log(naiveBayes.getProb().get(t)[0]);
                        }
                    }
                }
                eachLine = br.readLine();
            }
            br.close();
            if(probSpam >= probSpamBar)
                pos2++;
        }
        this.resultOfSpam = pos2/spamFiles.length;

    }

    @Override
    public String toString() {
        return "Accuracy of Ham = " + resultOfHam +
                ","+"\n" +"Accuracy of Spam = " + resultOfSpam;
    }

}
