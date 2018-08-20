package HomeWork2;

import weka.classifiers.Classifier;
import weka.core.Capabilities;
import weka.core.Instance;
import weka.core.Instances;

import java.util.*;

class Node {
	Node[] children;
	Node parent;
	int attributeIndex;
	double returnValue;
	Instances Instances;
	boolean isLeaf;
	int height;

	Node(Instances object) {
		this.Instances = object;
		this.children = new Node[0];
		this.returnValue = getMajorityOfNode();
		this.isLeaf = false;
	}
	// http://www.cs.cmu.edu/~aarti/Class/10701_Spring14/slides/decision_trees.pdf
	// Page 26 - "How to assign label to each leaf"
	private double getMajorityOfNode() {
		double one = 0.0, zero = 0.0;
		for (int i = 0; i < this.Instances.size(); i++) {
			if (this.Instances.instance(i).classValue() == 1) {
				one++;
			}
			else zero++;
		}
		return zero > one ? 0.0 : 1.0;
	}
	// Return true only if there is same classValue for every instances
	// This function also check if the Node is a Leaf
	boolean perfectSeparationNode(){
		double classValue = this.Instances.instance(0).classValue();
		for (int i = 1; i < this.Instances.numInstances(); i++) {
			if (this.Instances.instance(i).classValue() != classValue) {
				return false;
			}
		}
		this.isLeaf = true;
		return true;
	}
}

public class DecisionTree implements Classifier {

	private Node rootNode;
	private boolean impurityMeasure; // FALSE is Gini, TRUE is Entropy
	private double p_value = 1;
	private double[][] chiTable = new double[][]{
			// 0.75
			{0.102, 0.575, 1.213, 1.923, 2.675, 3.455, 4.255, 5.071, 5.899, 6.737, 7.584, 8.438, 9.299, 10.165, 11.037},
			// 0.5
			{0.455, 1.386, 2.366, 3.357, 4.351, 5.348, 6.346, 7.344, 8.343, 9.342, 10.341, 11.340, 12.340, 13.339, 14.339},
			// 0.25
			{1.323, 2.773, 4.108, 5.385, 6.626, 7.841, 9.037, 10.219, 11.389, 12.549, 13.701, 14.845, 15.984, 17.117, 18.245},
			// 0.05
			{3.841, 5.991, 7.815, 9.488, 11.070, 12.592, 14.067, 15.507, 16.919, 18.307, 19.675, 21.026, 22.362, 23.685, 24.996},
			// 0.005
			{7.879, 10.597, 12.838, 14.860, 16.750, 18.548, 20.278, 21.955, 23.589, 25.188, 26.757, 28.300, 29.819, 31.319, 32.801}
	};
	private int treeDepth = 0;
	private int sumHeight = 0;

	/**
	 * Return the classification of the instance.
	 * In order to see the Classified Instance, we will want to find the LEAF
	 * Each leaf node assigns a classification
	 * @param instance - Instance object.
	 * @return double number, 0 or 1, represent the classified class.
	 */
	@Override
	public double classifyInstance(Instance instance) {
		int attributeIndex; // is 0
		Node currentNode = rootNode;
		int index;
		// While we didn't arrive to a perfect separation
		while (!currentNode.isLeaf) {
			attributeIndex = currentNode.attributeIndex;
			index = (int) instance.value(attributeIndex);
			if (currentNode.children[index] == null) { // In order to verify if one of the child, without children,
				// return the classification of the instance
				return currentNode.returnValue;
			}
			currentNode = currentNode.children[index];
			sumHeight++;
		}
		if (currentNode.height > treeDepth) {
			treeDepth = currentNode.height;
		}
		return currentNode.returnValue;
	}

	/**
	 * buildClassifier: Builds a decision tree from the training data. buildClassifier is separated from buildTree
	 * in order to allow us to do extra preprocessing before calling buildTree method or post processing after.
	 * @param arg0 - Instances object
	 */
	@Override
	public void buildClassifier(Instances arg0) {
		// Create from rootNode - a new tree
		// FALSE is Gini, TRUE is Entropy
		buildTree(arg0, impurityMeasure);
	}

	/*
	Builds the decision tree on given data set using queue algorithm that we saw in class
	Using a queue
	FALSE is Gini, TRUE is Entropy
	*/
	private void buildTree(Instances object, boolean impurityMeasure) {

		rootNode = new Node(object);
		rootNode.parent = null;
		rootNode.height = 0;
		Node currentNode;
		Queue<Node> treeQueue = new LinkedList<>();
		treeQueue.add(rootNode);
		while (!treeQueue.isEmpty()) {
			currentNode = treeQueue.remove();
			checkAndGenerateNewChildren(object, impurityMeasure, currentNode, treeQueue);
		}
	}

	private void checkAndGenerateNewChildren(Instances object, boolean impurityMeasure, Node currentNode, Queue<Node> treeQueue) {
		if (!currentNode.perfectSeparationNode() && currentNode.Instances.numInstances() > 0) {
			// FALSE is Gini, TRUE is Entropy
			// A <- the “best” decision attribute for the set in n
			currentNode.attributeIndex = findBestAttribute(currentNode.Instances, impurityMeasure);
			double chiSquare = 0;
			int dF = 0;
			if (p_value != 1 && currentNode.attributeIndex != Integer.MIN_VALUE){ // if Pruning?
				chiSquare = calcChiSquare(currentNode.Instances, currentNode.attributeIndex);
				dF = calculateDF(currentNode);
			}
			// Based on 02 - Decision Tree Page 28
			// If we found a Best Attribute && ChiSquareTest allowed us to create the children
			if (currentNode.attributeIndex != Integer.MIN_VALUE && validChiSquareTest(chiSquare, dF)) {
				// Assign A as the decision attribute for n
				createNewDescendant(currentNode, object.attribute(currentNode.attributeIndex).numValues());
				insertDescendantNodesToQueue(currentNode, treeQueue);
			}
			else currentNode.isLeaf = true;
		}
	}

	private void createNewDescendant(Node currentNode, int numberOfValues) {
		currentNode.children = new Node[numberOfValues];
		Instances tempInstances;
		// AttributeIndex is the best attribute for us
		Instances[] splitedInstances = splitByAtt(currentNode.Instances, currentNode.attributeIndex);
		for (int i = 0; i < numberOfValues; i++) {
			tempInstances = splitedInstances[i];
			if (tempInstances.numInstances() > 0){
				currentNode.children[i] = new Node(tempInstances);
				currentNode.children[i].height = currentNode.height + 1;
				currentNode.children[i].parent = currentNode;
			}
		}
	}

	private void insertDescendantNodesToQueue(Node currentNode, Queue<Node> treeQueue){
		for (int i = 0; i < currentNode.children.length; i++) {
			if (currentNode.children[i] != null && currentNode.children[i].Instances.numInstances() > 0) {
				treeQueue.add(currentNode.children[i]);
			}
		}
	}

	// Returns the number of distinct values of a given attribute.
	private int calculateDF(Node currentNode) {
		return currentNode.Instances.numDistinctValues(currentNode.attributeIndex) - 2; // - 2 => Fix Index + remove one value;
	}

	private boolean validChiSquareTest(double chiSquare, int dF) {
		if (p_value == 1) return true;
		int line = returnLineInChiTable(p_value);
		if (line >= 0 && line <=4)
			return chiSquare >= chiTable[line][dF];
		return false;
	}

	private int returnLineInChiTable(double p_value){
		if (p_value == 0.75) return 0;
		else if (p_value == 0.5) return 1;
		else if (p_value == 0.25) return 2;
		else if (p_value == 0.05) return 3;
		else if (p_value == 0.005) return 4;
		else return Integer.MAX_VALUE;
	}

	// FALSE is Gini, TRUE is Entropy
	private int findBestAttribute(Instances instances, boolean impurityMeasure) {
		int tempBestAttribute = Integer.MIN_VALUE;
		double tempBestGain = Double.MIN_VALUE;
		tempBestAttribute = tempBestAttribute(instances, impurityMeasure, tempBestAttribute, tempBestGain);
		return tempBestAttribute;
	}

	private int tempBestAttribute(Instances instances, boolean impurityMeasure, int tempBestAttribute, double tempBestGain) {
		double currentGain;
		for (int i = 0; i < instances.numAttributes() - 1; i++) {
			currentGain = calcGain(instances, i, impurityMeasure);
			if (currentGain > tempBestGain) {
				tempBestGain = currentGain;
				tempBestAttribute = i;
			}
		}
		return tempBestAttribute;
	}

	public void printTree() {
		printTreeNode(rootNode,0);// for the root
		printTreeHelper(rootNode);
	}

	private void printTreeHelper (Node node) {
		int i = 0 ;
		for (Node nodeChildren : node.children) {
			if (nodeChildren != null) {
				printTreeNode(nodeChildren,i);// for the root
				printTreeHelper(nodeChildren);
			}
			i++;
		}
	}

	//	https://stackoverflow.com/questions/28507978/recursion-depth-tabs-dents-in-java?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa
	private void printTreeNode (Node node , int valueChildren) {
		StringBuilder tab = new StringBuilder();
		for (int i = 0; i < node.height ; i++){
			tab.append("\t");
		}

		if (node == rootNode) System.out.println("Root \n"+"Returning value: " + node.returnValue);
		else {
			System.out.println(tab +  "If attribute " + node.parent.attributeIndex + " = " + valueChildren);
			if (node.isLeaf) System.out.println(tab + "\tLeaf. Returning value: " + node.returnValue);
			else System.out.println(tab + "Returning value: " + node.returnValue);
		}
	}

	/**
	 * Calculate the average error on a given instances set (could be the training, test or validation set).
	 *		The average error is the total number of classification mistakes on the input instances set divided by the number of
	 * 		instances in the input set.
	 * 	Input: Instances object.
	 * @param data Instances object
	 * @return Average error (double).
	 */
	public double calcAvgError(Instances data) {
		double counter = 0;
		for (int i = 0; i < data.numInstances(); i++) {
			Instance currentInstance = data.instance(i);
			if (classifyInstance(currentInstance) != currentInstance.classValue())
				counter++;
		}
		return counter / (double) data.numInstances();
	}

	// 5.	calcGain: calculates the gain (giniGain or informationGain depending on the impurity measure) of splitting the
	// 		input data according to the attribute.
	//			a.	Input: Instances object (a subset of the training data), attribute index (int).
	//			b.	Output: The gain (double).
	private double calcGain(Instances data, int attributeIndex, boolean impurityMeasure) {
		// impurityMeasure - FALSE is Gini, TRUE is Entropy
		return impurityMeasure ? informationGain(data, attributeIndex) : giniGain(data, attributeIndex);

	}

	private double giniGain(Instances data, int attributeIndex) {
		double gini = calcGini(propHelper(data));
		Instances[] splitted = splitByAtt(data, attributeIndex);
		double sum = 0;

		for (Instances current : splitted) {
			if (current.numInstances() != 0)
				sum += (((double) current.numInstances() / data.numInstances()) * calcGini(propHelper(current)));
		}
		return gini - sum;
	}

	private double informationGain(Instances data, int attributeIndex) {
		double entropy = calcEntropy(propHelper(data));
		Instances[] splitted = splitByAtt(data, attributeIndex);
		double sum = 0;

		for (Instances current : splitted) {
			if (current.numInstances() != 0)
				sum += (((double) current.numInstances() / data.numInstances()) * calcEntropy(propHelper(current)));
		}
		return entropy - sum;
	}

	// 6.	calcEntropy: Calculates the Entropy of a random variable.
	//			a.	Input: A set of probabilities (the fraction of each possible value in the tested set).
	//			b.	Output: The Entropy (double).
	//
	private static double calcEntropy(List<Double> prop) {
		double entropy = 0;
		for (Double props : prop) {
			if (props == 0.0) {
				return 0.0;
			}
			entropy += props * (Math.log(props) / Math.log(2)); // Using log 2 and not default java log 10
		}
		return (-entropy);
	}

	// 7.	calcGini: Calculates the Gini of a random variable.
	//			a.	Input: A set of probabilities (the fraction of each possible value in the tested set).
	//			b.	Output: The Gini (double).
	//
	private static double calcGini(List<Double> prop) {
		double gini = 0;
		for (Double props : prop) {
			if (props == 0.0) {
				return 0.0;
			}
			gini += Math.pow(props,2);
		}
		return (1 - gini);
	}

	// Using idea from the Tirgul - since it was said that there is not much importance to effectiveness
	private Instances[] splitByAtt(Instances instances, int attributeIndex) {
		int numOfValues = instances.attribute(attributeIndex).numValues();
		String[] attributeValues = new String[numOfValues]; // Possible values according to the best attribute
		Instances[] instancesAfterSplit = new Instances[numOfValues]; // Size of the children to create
		initializeArrayAndCreateObject(instances, numOfValues, instancesAfterSplit);
		getPossibleActualValues(instances, attributeIndex, numOfValues, attributeValues);
		splitTheInputInstances(instances, attributeIndex, numOfValues, attributeValues, instancesAfterSplit);
		return instancesAfterSplit;
	}

	// Split the input Instances into the match Instances
	private void splitTheInputInstances(Instances instances, int attributeIndex, int numOfValues, String[] attributeValues, Instances[] instancesAfterSplit) {
		for (int i = 0; i < instances.numInstances(); i++) {
			String tempValue = instances.instance(i).stringValue(attributeIndex);
			for (int j = 0; j < numOfValues; j++) {
				if (tempValue.equals(attributeValues[j]))
					instancesAfterSplit[j].add(instances.instance(i));
			}
		}
	}

	// Getting the possible actual values
	private void getPossibleActualValues(Instances instances, int attributeIndex, int numOfValues, String[] attributeValues) {
		for (int i = 0; i < numOfValues; i++) {
			attributeValues[i] = instances.attribute(attributeIndex).value(i);
		}
	}

	// Initialize the array and creating object - with no element inside
	private void initializeArrayAndCreateObject(Instances instances, int numOfValues, Instances[] instancesAfterSplit) {
		for (int i = 0; i < numOfValues; i++) {
			instancesAfterSplit[i] = new Instances(instances, 0);
		}
	}

	// First are Positive, second Negative
	private List<Double> propHelper(Instances instances) {
		List<Double> prop = new ArrayList<>();
		double positive = 0;
		double negative = 0;
		for (int j=0; j < instances.numInstances(); j++) {
			if (instances.get(j).classValue() == 1.0)
				negative ++;
			else
				positive ++;
		}
		prop.add((negative / instances.numInstances())); // First is Negative
		prop.add((positive / instances.numInstances()));
		return prop;
	}


	// 8.	calcChiSquare: Calculates the chi square statistic of splitting the data according to the splitting
	// 		attribute as learned in class.
	//			a.	Input: Instances object (a subset of the training data), attribute index (int).
	//			b.	Output: The chi square score (double).
	//
	private double calcChiSquare(Instances data, int attributeIndex) {

		Instances[] splitted = splitByAtt(data, attributeIndex);
		int lengthOfSplittedData = splitted.length;
		double chiSquareScore = 0;
		double P0 = propHelper(data).get(1); // Positives
		double P1 = propHelper(data).get(0); // Negatives
		double [] Df = new double[lengthOfSplittedData];
		double [] Pf = new double[lengthOfSplittedData];
		double [] Nf = new double[lengthOfSplittedData];
		double [] E0 = new double[lengthOfSplittedData];
		double [] E1 = new double[lengthOfSplittedData];

		for (int i = 0; i < lengthOfSplittedData ; i++) {
			if (splitted[i].numInstances() != 0) {
				Df[i] = calcDF(splitted[i]);
				Pf[i] = calcPF(propHelper(splitted[i]).get(1), Df[i]); // Place 1 is positive
				Nf[i] = calcNF(propHelper(splitted[i]).get(0), Df[i]); // Place 0 is negative
				E0[i] = calcE0(P0, Df[i]);
				E1[i] = calcE1(P1, Df[i]);

				if (E0[i] != 0 && E1[i] != 0) { // You dont want to divide by zero
					chiSquareScore += (Math.pow(E0[i] - Pf[i], 2) / E0[i]) + (Math.pow(E1[i] - Nf[i], 2) / E1[i]);
				}
			}
		}
		return chiSquareScore;

	}

	private double calcDF(Instances data) {
		return data.size();
	}

	private double calcPF(double P0, double DFI) {
		return P0 * DFI;
	}

	private double calcNF(double P1, double DFI) {
		return P1 * DFI;
	}

	private double calcE0(double P0, double DFI) {
		return P0 * DFI;
	}

	private double calcE1(double P1, double DFI) {
		return P1 * DFI;
	}

	public void setImpurityMeasure(boolean impurityMeasure) {
		this.impurityMeasure = impurityMeasure;
	}

	public void setP_value(double p_value) {
		this.p_value = p_value;
	}


	public double getAvgHeight(Instances instances) {
		sumHeight = 0;
		double numInstances = instances.numInstances();
		for (Instance instance : instances) {
			classifyInstance(instance);
		}
		return (double) sumHeight / numInstances;
	}

	public int getMaxHeightOfTree(Instances instances) {
		treeDepth = 0;
		for (Instance instance : instances) {
			classifyInstance(instance);
		}
		return treeDepth;
	}

	@Override
	public double[] distributionForInstance(Instance arg0) throws Exception {
		// Don't change
		return null;
	}

	@Override
	public Capabilities getCapabilities() {
		// Don't change
		return null;
	}

}