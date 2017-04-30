import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhanglizhong on 3/20/17.
 */
public class NaiveBayes {

    private Map<String, Double[]> prob;
    private Double prior;


    public Double getPrior() {
        return prior;
    }

    public Map<String, Double[]> getProb() {
        return prob;
    }

    public NaiveBayes(ParserOfNB parserOfNB) throws Exception {

        this.prob = new HashMap<>();
        this.prior = parserOfNB.getCountHam()/(parserOfNB.getCountHam() + parserOfNB.getCountSpam());

        Double probOfSpam;
        Double probOfHam;


        for(Map.Entry<String, Double[]> entry : parserOfNB.getFrequency().entrySet()){

            probOfHam = (entry.getValue()[0] + 1)/(parserOfNB.getCountHam() + parserOfNB.getFrequency().size());
            probOfSpam = (entry.getValue()[1] + 1)/(parserOfNB.getCountSpam() + parserOfNB.getFrequency().size());

            Double [] pro = {probOfHam, probOfSpam};
            prob.put(entry.getKey(), pro);
        }

    }
}
