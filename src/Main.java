import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by zhanglizhong on 3/21/17.
 */
public class Main {
    public static void main(String[] args) throws Exception{


        String train = args[0];
        String test = args[1];
        String stop = args[2];
        int num_iterations = Integer.parseInt(args[3]);
        Double learning_rate = Double.parseDouble(args[4]);
        Double lambda = Double.parseDouble(args[5]);


        File folderHam = new File(train + "/ham");
        File folderSpam = new File(train + "/spam");

        String home = System.getProperty("user.home");
//
//        File folderHam = new File(home + "/6375/Assignment2/train/ham");
//        File folderSpam = new File(home + "/6375/Assignment2/train/spam");

        System.out.println(folderHam);
        ParserOfNB parserOfNB = new ParserOfNB(folderHam, folderSpam);
        NaiveBayes naiveBayes = new NaiveBayes(parserOfNB);
//
//        File testHam = new File(home + "/6375/Assignment2/test/ham");
//        File testSpam = new File(home + "/6375/Assignment2/test/spam");


        File testHam = new File(test + "/ham");
        File testSpam = new File(test + "/spam");
        TestSetOfNB testSetOfNB = new TestSetOfNB(testHam, testSpam, naiveBayes);


        System.out.println("Accuracies including stop word using Naive Bayes:");
        System.out.println(testSetOfNB);

        ParserOfLB parserOfLB = new ParserOfLB(folderHam, folderSpam);


        File stopword = new File(stop);


        ParserOfNB parserOfNBStopWord = new ParserOfNB(folderHam, folderSpam, stopword);
        NaiveBayes naiveBayesStopWord = new NaiveBayes(parserOfNBStopWord);

        TestSetOfNB testSetOfNBStopWord = new TestSetOfNB(testHam, testSpam, naiveBayesStopWord);

        System.out.println("\nAccuracies without stop word using Naive Bayes:");
        System.out.println(testSetOfNBStopWord);


        System.out.print("\nAccuracies including stop word using Logistic Regression:\nCalculating");
        LogisticRegression logisticRegression= new LogisticRegression(parserOfLB, learning_rate, lambda, num_iterations);


        System.out.println( "\nAccuracy of Ham = " + logisticRegression.test(new TestHamOfLB(testHam)));
        System.out.println( "Accuracy of Spam = " + logisticRegression.test2(new TestSpamOfLB(testSpam)));


        ParserOfLB parserOfLBStopWord = new ParserOfLB(folderHam, folderSpam, stopword);
        System.out.print("\nAccuracies without stop word using Logistic Regression:\nCalculating");
        LogisticRegression logisticRegressionStopWord= new LogisticRegression(parserOfLBStopWord, learning_rate, lambda, num_iterations);


        System.out.println( "\nAccuracy of Ham = " + logisticRegressionStopWord.test(new TestHamOfLB(testHam)));
        System.out.println( "Accuracy of Spam = " + logisticRegressionStopWord.test2(new TestSpamOfLB(testSpam)));


    }
}


