package HomeWork2;

import 	java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import weka.core.Instances;

public class MainHW2 {

    public static BufferedReader readDataFile(String filename) {
        BufferedReader inputReader = null;

        try {
            inputReader = new BufferedReader(new FileReader(filename));
        } catch (FileNotFoundException ex) {
            System.err.println("File not found: " + filename);
        }

        return inputReader;
    }

    /**
     * Sets the class index as the last attribute.
     * @param fileName
     * @return Instances data
     * @throws IOException
     */
    public static Instances loadData(String fileName) throws IOException{
        BufferedReader datafile = readDataFile(fileName);

        Instances data = new Instances(datafile);
        data.setClassIndex(data.numAttributes() - 1);
        return data;
    }

    private static void printBest(Instances trainingCancer, Instances testingCancer, DecisionTree dtGini, double p_value) {
        dtGini.setP_value(p_value);
        dtGini.buildClassifier(trainingCancer);
        System.out.println("Best validation error at p_value: "+ p_value);
        System.out.println("Test error with best tree: " + dtGini.calcAvgError(testingCancer));
    }

    private static DecisionTree trainWithGini(Instances trainingCancer, Instances validationCancer, boolean Gini) {
        DecisionTree dtGini = new DecisionTree();
        dtGini.setImpurityMeasure(Gini);
        dtGini.buildClassifier(trainingCancer);
        System.out.println("Validation error using Gini: " + dtGini.calcAvgError(validationCancer));
        return dtGini;
    }

    private static void trainWithEntropy(Instances trainingCancer, Instances validationCancer) {
        DecisionTree dtEntropy = new DecisionTree();
        dtEntropy.setImpurityMeasure(true);
        dtEntropy.buildClassifier(trainingCancer);
        System.out.println("Validation error using Entropy: " + dtEntropy.calcAvgError(validationCancer));
    }

    private static void printInfo(Instances trainingCancer, Instances validationCancer, DecisionTree dtGini, double p_values) {
        dtGini.setP_value(p_values);
        dtGini.buildClassifier(trainingCancer);
        System.out.println("Decision tree with p_value of: " + p_values );
        System.out.println("The train error of the decision tree is: " + dtGini.calcAvgError(trainingCancer));
        System.out.println("Max height on validation data: " + dtGini.getMaxHeightOfTree(validationCancer));
        System.out.println("Average height on validation data: " + dtGini.getAvgHeight(validationCancer));
        System.out.println("The validation error of the decision tree is: " + dtGini.calcAvgError(validationCancer));
        System.out.println("-----------------------------------------------------------");
    }

    public static void main(String[] args) throws Exception {
        Instances trainingCancer = loadData("cancer_train.txt");
        Instances testingCancer = loadData("cancer_test.txt");
        Instances validationCancer = loadData("cancer_validation.txt");

        // We checked both of the ImpurityMeasure and gini is better according to this data
        // We save only the tree with Gini
        trainWithEntropy(trainingCancer, validationCancer);
        DecisionTree dtGini = trainWithGini(trainingCancer, validationCancer, false);// Gini is false
        System.out.println("-----------------------------------------------------------");

        // Continue with Gini Tree
        printInfo(trainingCancer, validationCancer, dtGini, 1.0 );
        printInfo(trainingCancer, validationCancer, dtGini, 0.75);
        printInfo(trainingCancer, validationCancer, dtGini, 0.5);
        printInfo(trainingCancer, validationCancer, dtGini, 0.25);
        printInfo(trainingCancer, validationCancer, dtGini, 0.05);
        printInfo(trainingCancer, validationCancer, dtGini, 0.005);

        // again we checked all the errors and came to the conclusion that 0.25 is the best
        printBest(trainingCancer, testingCancer, dtGini, 0.25);

        dtGini.printTree();

    }

}