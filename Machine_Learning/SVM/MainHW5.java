package HomeWork5;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import weka.core.Instances;
import weka.classifiers.functions.supportVector.Kernel;
import weka.classifiers.functions.supportVector.PolyKernel;
import weka.classifiers.functions.supportVector.RBFKernel;

public class MainHW5 {

    private static final double ALPHA = 1.5;
    private static final int[] PolynomialKernel = {2, 3, 4};
    private static final double[] RBFKernel = {0.005, 0.05, 0.5};
    private static final double[] cArray = createCArr();

    private enum ROCdata {
        TPR,
        FPR,
        ALPHATPRMINUSFPR
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

        Instances data = loadData("cancer.txt");

        // We will want to shuffle the data first
        shuffleData(data);

        // Divide the data to training and test set – 80% training and 20% test.
        // https://stackoverflow.com/questions/14682057
        int trainingSize = (int) Math.round(data.numInstances() * 0.8);
        int testSize = data.numInstances() - trainingSize;

        Instances train = new Instances(data, 0, trainingSize);
        Instances test = new Instances(data, trainingSize, testSize);

        // Create temps <ROCdata, Double> to save best TPR FPR
        Map <ROCdata, Double> bestError = null;
        Map <ROCdata, Double> tempCalc;
        Kernel bestKernel = new PolyKernel();

        // Finding the best kernel:

        // Start with the polyKernel
        for (int polynomialKernelValue : PolynomialKernel) {
            tempCalc = calculationOfPolyKernelValue(train, test, polynomialKernelValue);
            // Update bestParam
            if (bestError == null || bestError.get(ROCdata.ALPHATPRMINUSFPR) < tempCalc.get(ROCdata.ALPHATPRMINUSFPR)){
                bestError = tempCalc;
                ((PolyKernel) bestKernel).setExponent(polynomialKernelValue);
            }
        }

        // Continue with the RBF Kernel
        for (double rbfKernelValue : RBFKernel) {
            tempCalc = calculationOfRBFKernel(train, test, rbfKernelValue);
            // Update bestParam
            if (bestError.get(ROCdata.ALPHATPRMINUSFPR) < tempCalc.get(ROCdata.ALPHATPRMINUSFPR)){
                bestError = tempCalc;
                bestKernel = new RBFKernel();
                ((RBFKernel) bestKernel).setGamma(rbfKernelValue);
            }
        }

        // Printing the Best Kernel
        if (bestKernel instanceof PolyKernel) {
            printBestKernelPoly(bestError, (PolyKernel) bestKernel);
        } else {
            printBestKernelRBF(bestError, (RBFKernel) bestKernel);
        }

        // Finding the best C value (the slack regularization):
        for (double c : cArray){
            // For each C value, build the SVM classifier with the selected kernel on the training set using the SMO WEKA class.
            Map<ROCdata, Double> calculation = createAndCalcKernelWithC(bestKernel, c, train, test);
            generalPrint(c, calculation, "For C ");
        }
    }

    private static double[] createCArr () {
        int counter = 0;
        double c[] = new double[18];
        for (int i = 1; i > -5; i--) {
            for (int j = 3; j > 0; j--) {
                c[counter++] = (Math.pow(10, i) * j / 3);
            }
        }
        return c;
    }

    private static void generalPrint(double c, Map<ROCdata, Double> calculation, String s) {
        System.out.println(s + c + " the rates are:" + "\n" + "TPR = " +
                calculation.get(ROCdata.TPR) + "\n" + "FPR = " + calculation.get(ROCdata.FPR) + "\n");
    }

    private static void printBestKernelPoly(Map<ROCdata, Double> bestError, PolyKernel bestKernel) {
        System.out.println("The best kernel is Poly with degree " + bestKernel.getExponent() +
                " and a*TPR-FPR of " + bestError.get(ROCdata.ALPHATPRMINUSFPR) + "\n");
    }

    private static void printBestKernelRBF(Map<ROCdata, Double> bestError, RBFKernel bestKernel) {
        System.out.println("The best kernel is RBF with gamma " + bestKernel.getGamma() +
                " and a*TPR-FPR of " + bestError.get(ROCdata.ALPHATPRMINUSFPR) + "\n");
    }

    private static Map<ROCdata, Double> calculationOfPolyKernelValue(Instances train, Instances test, int polynomialKernelValue) throws Exception {
        PolyKernel poly = new PolyKernel();
        poly.setExponent(polynomialKernelValue);
        Map<ROCdata, Double> calculation = createAndCalcKernel (poly, train, test);
        printPolyKernel(polynomialKernelValue, calculation);
        return calculation;
    }

    private static void printPolyKernel(int polynomialKernelValue, Map<ROCdata, Double> calculation) {
        generalPrint(polynomialKernelValue, calculation, "For PolyKernel with degree ");
    }

    private static Map<ROCdata, Double> calculationOfRBFKernel(Instances train, Instances test, double rbfKernelValue) throws Exception {
        RBFKernel rbf = new RBFKernel();
        rbf.setGamma(rbfKernelValue);
        Map<ROCdata, Double> calculation = createAndCalcKernel (rbf, train, test);
        printRBFKernel(rbfKernelValue, calculation);
        return calculation;
    }

    private static void printRBFKernel(double rbfKernelValue, Map<ROCdata, Double> calculation) {
        generalPrint(rbfKernelValue, calculation, "For RBFKernel with gamma ");
    }


    private static void shuffleData(Instances instances){
        instances.randomize(new Random());
    }

    private static Map <ROCdata, Double> createAndCalcKernel (Kernel kernel, Instances train, Instances
            test) throws Exception {
        SVM svm = new SVM();
        svm.setKernel(kernel);
        svm.buildClassifier(train);
        int[] confusion = svm.calcConfusion(test);
        return calcAllResults(confusion);
    }

    private static Map <ROCdata, Double> createAndCalcKernelWithC (Kernel kernel, double C, Instances train, Instances
            test) throws Exception {
        SVM svm = new SVM();
        svm.setKernel(kernel);
        svm.setC(C);
        svm.buildClassifier(train);
        int[] confusion = svm.calcConfusion(test);
        return calcAllResults(confusion);
    }

    // https://en.wikipedia.org/wiki/Receiver_operating_characteristic
    // TP, FP, TN, FN
    private static Map <ROCdata, Double> calcAllResults (int[] confusion) {
        Map <ROCdata, Double> results = new HashMap<>();
        results.put(ROCdata.TPR, calcFPRorTPR(confusion, 0, 3));
        results.put(ROCdata.FPR, calcFPRorTPR(confusion, 1, 2));
        results.put(ROCdata.ALPHATPRMINUSFPR, calcATPRminusFPR(results.get(ROCdata.TPR), results
                .get(ROCdata.FPR)));
        return results;
    }

    private static double calcFPRorTPR(int[] confusion, int i, int j) {
        return (double) confusion[i] / (confusion[i] + confusion[j]);
    }

    private static double calcATPRminusFPR (double TPR, double FPR){
        return (ALPHA * TPR) - (FPR);
    }
}
