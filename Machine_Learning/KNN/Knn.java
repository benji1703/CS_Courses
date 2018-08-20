package HomeWork3;

import weka.classifiers.Classifier;
import weka.core.Capabilities;
import weka.core.Instance;
import weka.core.Instances;

import java.util.*;


class DistanceCalculator {


    private boolean efficient; // True is efficient, false is Regular

    public boolean isEfficient() {
        return efficient;
    }

    public void setEfficient(boolean efficient) {
        this.efficient = efficient;
    }



    public double distance(Instance one, Instance two, int p_value, double threshold) {

        double distance;

        if (!efficient) { // Using normal
            if (p_value != 0) { // Not infinity
                distance = lPDistance(one, two, p_value);
            } else { // 0 is infinity
                distance = lInfinityDistance(one, two);
            }
        } else { // Using efficient
            if (p_value != 0) { // Not infinity
                distance = efficientLpDistance(one, two, p_value, threshold);
            } else { // 0 is infinity
                distance = efficientLInfinityDistance(one, two, threshold);
            }
        }

        return distance;
    }

    /**
     * Returns the Lp distance between 2 instances.
     *  @param one
     * @param two
     * @param p_value
     */
    private double lPDistance(Instance one, Instance two, int p_value) {

        double distanceSum = 0;

        for (int i = 0; i < one.numAttributes() - 1; i++) {
            distanceSum += Math.pow(Math.abs(one.value(i) - two.value(i)), p_value);
        }

        distanceSum = Math.pow(distanceSum, 1.0 / p_value); // Root in base P

        return distanceSum;

    }

    /**
     * Returns the L infinity distance between 2 instances.
     *
     * @param one
     * @param two
     * @return
     */
    private double lInfinityDistance(Instance one, Instance two) {

        double attributeDistance;
        double maxDistance = 0;

        for (int i = 0; i < one.numAttributes() - 1; i++) {

            attributeDistance = Math.abs(one.value(i) - two.value(i));

            if (attributeDistance > maxDistance) {
                maxDistance = attributeDistance;
            }
        }

        return maxDistance;

    }

    /**
     * Returns the Lp distance between 2 instances, while using an efficient distance check.
     *
     * @param one
     * @param two
     * @param p_value
     * @param threshold
     * @return
     */
    private double efficientLpDistance(Instance one, Instance two, int p_value, double threshold) {

        double distanceSum = 0;
        double currentThreshold = Math.pow(threshold, p_value);

        for (int i = 0; i < one.numAttributes() - 1; i++) {
            distanceSum += Math.pow(Math.abs(one.value(i) - two.value(i)), p_value);
            if (distanceSum > currentThreshold) {
                return Double.MAX_VALUE;
            }
        }

        distanceSum = Math.pow(distanceSum, 1.0 / p_value); // Root in base P

        return distanceSum;
    }

    /**
     * Returns the Lp distance between 2 instances, while using an efficient distance check.
     *
     * @param one
     * @param two
     * @param threshold
     * @return
     */
    private double efficientLInfinityDistance(Instance one, Instance two, double threshold) {

        double attributeDistance;
        double maxDistance = 0;

        for (int i = 0; i < one.numAttributes() - 1; i++) {
            attributeDistance = Math.abs(one.value(i) - two.value(i));

            if (attributeDistance > maxDistance) {
                maxDistance = attributeDistance;
            }
            if (maxDistance > threshold) {
                return Double.MAX_VALUE;
            }
        }

        return maxDistance;
    }

}


public class Knn implements Classifier {

    private Instances m_trainingInstances;

    private int p_value;
    private int k_value;
    private double m_treshold;
    private boolean weighting;
    private boolean efficient;
    private long totalTimeForFolding;


    @Override
    /**
     * Build the knn classifier. In our case, simply stores the given instances for
     * later use in the prediction.
     * @param instances
     */
    public void buildClassifier(Instances instances) throws Exception {
        this.m_trainingInstances = instances;
    }

    /**
     * Returns the knn prediction on the given instance.
     *
     * @param instance
     * @return The instance predicted value.
     */
    public double regressionPrediction(Instance instance) {

        Instance[] instanceNeighbor;
        // Find the nearest neighbor
        if (!efficient)
            instanceNeighbor = findNearestNeighbors(instance);
        else
            instanceNeighbor = findEfficientNearestNeighbors(instance);

        if (!weighting) {
            return regressionPredictionNormal(instanceNeighbor);
        } else {
            return regressionPredictionWeighted(instance, instanceNeighbor);
        }
    }

    public double regressionPredictionNormal(Instance[] instanceNeighbor) {

        if (instanceNeighbor.length == 0) return 0.0;

        double sum = 0.0;

        return getAverageValue(instanceNeighbor, sum);
    }

    public double getAverageValue(Instance[] instanceNeighbor, double sum) {
        for (int i = 0; i < instanceNeighbor.length; i++) {
            sum += instanceNeighbor[i].classValue();
        }

        return sum / instanceNeighbor.length;
    }

    private double regressionPredictionWeighted(Instance instance, Instance[] instanceNeighbor) {

        if (instanceNeighbor.length == 0) return 0.0;

        double sumNeighbor = 0;
        double sumWi = 0;

        return getWeightedAverageValue(instance, instanceNeighbor, sumNeighbor, sumWi);
    }

    public double getWeightedAverageValue(Instance instance, Instance[] instanceNeighbor, double sumNeighbor, double sumWi) {

        double wi;
        DistanceCalculator dc = new DistanceCalculator();

        for (int i = 0; i < instanceNeighbor.length; i++) {
            wi = calcWi(instance, instanceNeighbor[i], dc);
            sumNeighbor += instanceNeighbor[i].classValue() * wi;
            sumWi += wi;
        }

        if (sumWi == 0) // If sumWi is 0 - they have all the same values
            return instanceNeighbor[0].classValue();
        return sumNeighbor / sumWi;

    }

    private double calcWi(Instance instance, Instance neighbour, DistanceCalculator dc) {

        double wi = 0;

        double dcDis = dc.distance(instance, neighbour, p_value, m_treshold);
        if (dcDis != 0 && dcDis != Double.MAX_VALUE)
            wi = 1 / Math.pow(dcDis, 2);

        return wi;

    }


    /**
     * Caclcualtes the average error on a give set of instances.
     * The average error is the average absolute error between the target value and the predicted
     * value across all insatnces.
     *
     * @param insatnces
     * @return
     */
    public double calcAvgError(Instances insatnces) {

        // Using the same code from DecisionTree.java

        double sumError = 0;
        for (int i = 0; i < insatnces.numInstances(); i++) {
            Instance currentInstance = insatnces.instance(i);
            sumError += Math.abs(regressionPrediction(currentInstance) - currentInstance.classValue());
        }
        return sumError / (double) insatnces.numInstances();
    }

    /**
     * Calculates the cross validation error, the average error on all folds.
     *
     * @param insances     Insances used for the cross validation
     * @param num_of_folds The number of folds to use.
     * @return The cross validation error.
     */
    public double crossValidationError(Instances insances, int num_of_folds) throws Exception {
        // Remember: before splitting the dataset for the cross validation, you need to shuffle the data.
        long startTimer = System.nanoTime();
        double crossValidationError = 0;
        crossValidationError = calcErrorWithTrainValid(insances, num_of_folds, crossValidationError);
        totalTimeForFolding = System.nanoTime() - startTimer;
        return crossValidationError / (double) num_of_folds;
    }

    private double calcErrorWithTrainValid(Instances insances, int num_of_folds, double crossValidationError) throws Exception {
        Instances validData;
        Instances trainData;
        for (int i = 0; i < num_of_folds; i++) {
            validData = new Instances(insances, insances.numInstances());
            trainData = new Instances(insances, insances.numInstances());
            createFolds(insances, num_of_folds, validData, trainData, i);
            buildClassifier(trainData);
            crossValidationError += calcAvgError(validData);
        }
        return crossValidationError;
    }

    private void createFolds(Instances insances, int num_of_folds, Instances validData, Instances trainData, int i) {
        for (int j = 0; j < insances.numInstances(); j++) {
            if (j % num_of_folds == i) {
                validData.add(insances.instance(j));
            } else {
                trainData.add(insances.instance(j));
            }
        }
    }


    /**
     * Finds the k nearest neighbors.
     *
     * @param instance
     */
    public Instance[] findNearestNeighbors(Instance instance) {

        Instance[] neighbors = new Instance[k_value];
        Instance[] data = new Instance[m_trainingInstances.numInstances()];
        DistanceCalculator dc = new DistanceCalculator();
        dc.setEfficient(efficient);

        // Copying
        for (int i = 0; i < m_trainingInstances.numInstances(); i++) {
            data[i] = m_trainingInstances.instance(i);
        }

        // If not enough
        if (k_value > data.length)
            return data;

        //Create array for Distances
        double[] distance = new double[data.length];
        for (int i = 0; i < data.length; i++) {
            distance[i] = dc.distance(instance, m_trainingInstances.instance(i), p_value, -1);
        }
        sortWIth2Var(data, distance);
        for (int i = 0; i < neighbors.length; i++) {
            neighbors[i] = data[i];
        }
        return neighbors;
    }

    public Instance[] findEfficientNearestNeighbors(Instance instance) {

        Instance[] neighbors = new Instance[k_value];
        Instance[] data = new Instance[m_trainingInstances.numInstances()];
        DistanceCalculator dc = new DistanceCalculator();
        dc.setEfficient(!efficient);

        // Copying
        for (int i = 0; i < m_trainingInstances.numInstances(); i++) {
            data[i] = m_trainingInstances.instance(i);
        }

        // If not enough
        if (k_value > data.length)
            return data;
        double[] distance = new double[k_value];

        //Create array for Distances
        for (int i = 0; i < k_value; i++) {
            neighbors[i] = m_trainingInstances.instance(i);
            distance[i] = dc.distance(instance, m_trainingInstances.instance(i),p_value, -1);
        }

        dc.setEfficient(efficient);

        sortWIth2Var(neighbors, distance);

        for (int i = k_value; i < data.length; i++) {
            double currentDistance = dc.distance(instance, m_trainingInstances.instance(i), p_value, distance[k_value - 1]);
            if (distance[k_value - 1] > currentDistance) {
                neighbors[k_value - 1] = m_trainingInstances.instance(i);
                distance[k_value - 1] = currentDistance;
                sortWIth2Var(neighbors, distance);
                m_treshold = distance[k_value - 1];
            }
        }
        return neighbors;
    }


    // https://stackoverflow.com/questions/12824423/sort-array-and-reflect-the-changes-in-another-array?utm_medium
    // =organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa
    private void sortWIth2Var(Instance[] data, double[] distance) {

        Map<Double, Instance> myLocalMap = new HashMap<>();

        for (int i = 0; i < data.length; i++) {
            myLocalMap.put(distance[i], data[i]);
        }

        Arrays.sort(distance);

        for (int i = 0; i < data.length; i++) {
            data[i] = myLocalMap.get(distance[i]);
        }

    }

    public boolean isWeighting() {
        return weighting;
    }

    public void setWeighting(boolean weighting) {
        this.weighting = weighting;
    }

    public int getP_value() {
        return p_value;
    }

    public void setP_value(int p_value) {
        this.p_value = p_value;
    }

    public int getK_value() {
        return k_value;
    }

    public void setK_value(int k_value) {
        this.k_value = k_value;
    }

    public boolean isEfficient() {
        return efficient;
    }

    public void setEfficient(boolean efficient) {
        this.efficient = efficient;
    }

    public long getTotalTimeForFolding() {
        return totalTimeForFolding;
    }


    @Override
    public double[] distributionForInstance(Instance arg0) throws Exception {
        return null;
    }

    @Override
    public Capabilities getCapabilities() {
        return null;
    }

    @Override
    public double classifyInstance(Instance instance) {
        return 0.0;
    }
}
