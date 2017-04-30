import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhanglizhong on 3/25/17.
 */
public class LogisticRegression {
    private HashMap<String, Double> weight;
    Double w0 = 0.0;

    public LogisticRegression(ParserOfLB parserOfLB, Double learning_rate, Double lambda, int num_iterations) throws Exception{
        this.weight = new HashMap<>();


        for(Map.Entry<String, ArrayList<Double>> entry :parserOfLB.getData().entrySet()){
            weight.put(entry.getKey(), 0.0);
        }

        for(int i = 1; i <= num_iterations; i++){
            ArrayList<Double> exp = new ArrayList<>(Collections.nCopies(parserOfLB.getClass_name().size(), 0.0));
            for(int j = 0; j < parserOfLB.getClass_name().size(); j++){

                Double temp1 = 0.0;
                for(Map.Entry<String, Double> words: weight.entrySet()){

                    temp1 += weight.get(words.getKey()) *  parserOfLB.getData().get(words.getKey()).get(j);

                }
                exp.set(j,w0 + temp1);
            }

            Double temp0 = 0.0;
            for(int j = 0; j < parserOfLB.getClass_name().size(); j++) {
                temp0 += (parserOfLB.getClass_name().get(j) - Sigmod(exp.get(j)));
            }
            w0 = w0 + learning_rate * temp0 - learning_rate * lambda * w0;

            for(Map.Entry<String, Double> entry: weight.entrySet()){

                Double temp = 0.0;
                for(int j = 0; j < parserOfLB.getClass_name().size(); j++) {
                    temp += (parserOfLB.getClass_name().get(j) - Sigmod(exp.get(j))) * parserOfLB.getData().get(entry.getKey()).get(j);
                }
                Double temp3 = weight.get(entry.getKey()) + learning_rate * temp - learning_rate * lambda * weight.get(entry.getKey());
                weight.put(entry.getKey(), temp3);
            }
            System.out.print(".");
        }

    }

    private Double Sigmod(double weightSum){
        return 1.0 / (1.0 + Math.exp(-weightSum));
    }

    @Override
    public String toString() {
        return "LogisticRegression{" +
                "weight=" + weight +
                '}';
    }

    public Double test(TestHamOfLB testSetOfHam) {
        Double test_sum = 0.0;
        for(int j = 0; j < testSetOfHam.getClass_name().size(); j++) {
            Double result = w0;

            for(Map.Entry<String, ArrayList<Double>> entry :testSetOfHam.getData().entrySet()){
                if (weight.containsKey(entry.getKey()))
                    result = result + weight.get(entry.getKey()) * entry.getValue().get(j);
            }
            if(result <= 0){
                test_sum++;
            }
        }
        return test_sum/testSetOfHam.getClass_name().size();
    }

    public Double test2(TestSpamOfLB testSetOfSpam) {
        Double test_sum = 0.0;
        for(int j = 0; j < testSetOfSpam.getClass_name().size(); j++) {
            Double result = w0;
            for(Map.Entry<String, ArrayList<Double>> entry :testSetOfSpam.getData().entrySet()){
                if (weight.containsKey(entry.getKey()))
                    result += weight.get(entry.getKey()) * entry.getValue().get(j);
            }
            if(result > 0){
                test_sum++;
            }
        }
        return test_sum/testSetOfSpam.getClass_name().size();
    }

}
