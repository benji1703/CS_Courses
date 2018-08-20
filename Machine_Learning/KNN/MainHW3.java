package HomeWork3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

import weka.core.Instances;

public class MainHW3 {

    public enum majorityFunction {weighted, uniform}

    public enum DistanceCheck {Regular, Efficient}

    private static void shuffleInstances(Instances data) {
        data.randomize(new Random());
    }

    public static BufferedReader readDataFile(String filename) {
        BufferedReader inputReader = null;

        try {
            inputReader = new BufferedReader(new FileReader(filename));
        } catch (FileNotFoundException ex) {
            System.err.println("File not found: " + filename);
        }

        return inputReader;
    }

    public static Instances loadData(String fileName) throws IOException {
        BufferedReader datafile = readDataFile(fileName);
        Instances data = new Instances(datafile);
        data.setClassIndex(data.numAttributes() - 1);
        return data;
    }

    public static void main(String[] args) throws Exception {

        Instances autoPrice = loadData("auto_price.txt");
        shuffleInstances(autoPrice);
        Instances scaledAutoPrice = FeatureScaler.scaleData(autoPrice);
        shuffleInstances(scaledAutoPrice);
        double[] bestParametersScaled;// 0 = error, 1 = bestK , 2 = BestP , 3 = majorityFunction
        double[] bestParametersOriginal;// 0 = error, 1 = bestK , 2 = BestP , 3 = majorityFunction
        // unscaled
        double[] tempbestParameters;
        int[] number_of_folds = {3, 5, 10, 50, autoPrice.numInstances()};

        System.out.println("----------------------------");
        System.out.println("Results for original dataset: ");
        System.out.println("----------------------------");
        bestParametersOriginal = findBestError(autoPrice, majorityFunction.weighted, true, 10, 0); //zero for unscaled
        tempbestParameters = findBestError(autoPrice, majorityFunction.uniform, false, 10, 0);
        updateParameters(bestParametersOriginal, tempbestParameters);
        printError(bestParametersOriginal);

        System.out.println("----------------------------");
        System.out.println("Results for scaled dataset: ");
        System.out.println("----------------------------");
        bestParametersScaled = findBestError(scaledAutoPrice, majorityFunction.weighted, true, 10,
                1);// one for scaled
        tempbestParameters = findBestError(scaledAutoPrice, majorityFunction.uniform, false, 10, 1);
        updateParameters(bestParametersScaled, tempbestParameters);
        printError(bestParametersScaled);


        // We need here to set the parameters - the best from the previous test
        // And set the knn
        Knn knnForFoldEfficient = createKnn(bestParametersScaled, DistanceCheck.Efficient);
        Knn knnForFoldRegular = createKnn(bestParametersScaled, DistanceCheck.Regular);


        for (int number_of_fold : number_of_folds) {

            System.out.println("----------------------------");
            System.out.println("Results for " + number_of_fold + " folds");
            System.out.println("----------------------------");
            findBestErrorWithFoldValue(scaledAutoPrice, knnForFoldEfficient, number_of_fold, DistanceCheck.Efficient);
            findBestErrorWithFoldValue(scaledAutoPrice, knnForFoldRegular, number_of_fold, DistanceCheck.Regular);

        }

    }


    private static Knn createKnn(double[] finalBestParametersOriginal, DistanceCheck regular) {
        Knn knnForFold = new Knn();
        if (regular == DistanceCheck.Regular) {
            knnForFold.setEfficient(false);
        } else knnForFold.setEfficient(true);
        if ((int) finalBestParametersOriginal[3] == 0) // uniform = false = 0
            knnForFold.setWeighting(true);
        else
            knnForFold.setWeighting(true);// weighting = true = 1
        knnForFold.setK_value((int) finalBestParametersOriginal[1]);
        knnForFold.setP_value((int) finalBestParametersOriginal[2]);
        return knnForFold;
    }

    private static void updateParameters(double[] bestParameters, double[] tempbestParameters) {
        if (tempbestParameters[0] < bestParameters[0]) {
            System.arraycopy(tempbestParameters, 0, bestParameters, 0, tempbestParameters.length);
        }
    }
    private static double[] findBestError(Instances instances, majorityFunction major, boolean
            weighted, int num_of_folds, double info) throws Exception {
        double bestError = Double.MAX_VALUE;
        double[] parameters = new double[4]; // 0 = error, 1 = bestK , 2 = BestP , 3 = majorityFunction
        double bestK = 0;
        double bestP = 0;

        Knn knnToPrint = new Knn();
        knnToPrint.buildClassifier(instances);
        knnToPrint.setWeighting(weighted);

        for (int i = 1; i <= 20; i++) {
            for (int j = 0; j <= 3; j++) {
                knnToPrint.setK_value(i);
                knnToPrint.setP_value(j);
                double tempErr = knnToPrint.crossValidationError(instances, num_of_folds);
                if (tempErr < bestError) {
                    bestK = i;
                    bestP = j;
                    bestError = tempErr;
                }
            }
        }
        parameters[0] = bestError;
        parameters[1] = bestK;
        parameters[2] = bestP;
        if (major.equals(majorityFunction.uniform)) {
            parameters[3] = 0; // uniform
        } else {
            parameters[3] = 1; // weighted
        }
        return parameters;

    }
    private static void printError (double [] paramters)
    {
        if (paramters[3] == 0) {
            System.out.println("Cross validation error with K = " + (int) paramters[1] + ", lp = " + (int) paramters[2] + " majority " +
                    "function = uniform for auto_price data is: " + paramters[0]);
        }
        else {
            System.out.println("Cross validation error with K = " + (int) paramters[1] + ", lp = " + (int) paramters[2] + " majority " +
                    "function = weighted for auto_price data is: " + paramters[0]);
        }
    }


    private static void findBestErrorWithFoldValue(Instances instances, Knn workingKnn, int number_of_fold, DistanceCheck dc) throws Exception {

        double error = workingKnn.crossValidationError(instances, number_of_fold);
        System.out.println("Cross validation error of " + dc + " knn on auto_price dataset is " + error + " and the average elapsed time is: " + workingKnn.getTotalTimeForFolding() / number_of_fold + "\nThe total elapsed time is: " + workingKnn.getTotalTimeForFolding());

    }
}
