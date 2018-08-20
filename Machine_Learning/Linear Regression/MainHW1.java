package HomeWork1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

import weka.core.Instances;

public class MainHW1 {

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

	public static void main(String[] args) throws Exception {

		//load data
		Instances trainData = loadData("wind_training.txt");
		Instances testData = loadData("wind_testing.txt");

		//find best alpha and build classifier with all attributes (using trainData)
		LinearRegression windTesting = new LinearRegression();
		windTesting.buildClassifier(trainData);

		System.out.println("The chosen alpha is: " + windTesting.getM_alpha());

		System.out.println("Training error with all features is: " + windTesting.calculateMSE(trainData));
		System.out.println("Test error with all features is: " + windTesting.calculateMSE(testData));

		int[] bestAttributes = new int [4];
		int[] attributesToSave = new int [4];
		int[] attributeToPrint;
		int[] bestAttributeToPrint;

		double bestPredictionSoFar = Double.MAX_VALUE;

		System.out.println("List of all combination of 3 features and the training error");
		//build classifiers with all 3 attributes combinations
		for (int i = 0; i < trainData.numAttributes() - 1; i++){
			for (int j = i + 1; j < trainData.numAttributes() - 1; j++){
				for (int k = j + 1; k < trainData.numAttributes() - 1; k++){

					attributesToSave[0] = i;
					attributesToSave[1] = j;
					attributesToSave[2] = k;
					attributesToSave[3] = trainData.classIndex();

					// Using Filter
					// https://weka.wikispaces.com/Remove+Attributes?responseToken=00f6b46e3cd75c61e15eea9db64045bc5
					Remove remove = new Remove();
					remove.setAttributeIndicesArray(attributesToSave);
					remove.setInvertSelection(true);
					remove.setInputFormat(trainData); // init filter
					Instances filtered = Filter.useFilter(trainData, remove); // apply filter

					//find best alpha and build classifier with all attributes (using filtered)
					windTesting.buildClassifierFor3(filtered);

					double currentPrediction = windTesting.calculateMSE(filtered);

					// Don't print the ClassIndex
					attributeToPrint = Arrays.copyOfRange(attributesToSave,0 , 3);
					System.out.print(Arrays.toString(attributeToPrint) + ": ");
					System.out.println(currentPrediction);

					// Update prediction - to get the Best so far, and remember the attributes
					if (bestPredictionSoFar > currentPrediction){
						bestPredictionSoFar = currentPrediction;
						System.arraycopy(attributesToSave, 0, bestAttributes, 0, 4);
					}
				}
			}
		}

		bestAttributeToPrint = Arrays.copyOfRange(bestAttributes,0 , 3);
		System.out.println("Training error the features " + Arrays.toString(bestAttributeToPrint)
				+ ": "+ bestPredictionSoFar);

		// Now that we found the "best attribute" use them on the test
		Remove remove = new Remove();
		remove.setAttributeIndicesArray(bestAttributes);
		remove.setInvertSelection(true);
		remove.setInputFormat(testData); // init filter
		Instances filtered = Filter.useFilter(testData, remove); // apply filter

		//find best alpha and build classifier with all attributes (using filtered)
		windTesting.buildClassifierFor3(filtered);
		double testDataPredictionWithBestAttributes = windTesting.calculateMSE(filtered);

		System.out.println("Test  error the features " + Arrays.toString(bestAttributeToPrint)
				+ ": "+ testDataPredictionWithBestAttributes);
	}

}
