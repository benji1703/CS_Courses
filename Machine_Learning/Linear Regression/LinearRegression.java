package HomeWork1;

import weka.classifiers.Classifier;
import weka.core.Capabilities;
import weka.core.Instance;
import weka.core.Instances;

public class LinearRegression implements Classifier {

	private int m_ClassIndex;
	private int m_truNumAttributes;
	private double[] m_coefficients;
	private double m_alpha;
	private static final int ITERATIONS = 20000;
	private static final double EPSILON = 0.003;

	//the method which runs to train the linear regression predictor, i.e.
	//finds its weights.
	@Override
	public void buildClassifier(Instances trainingData) throws Exception {
		trainingData = new Instances(trainingData);
		// Using classIndex from the training data info (from main)
		m_ClassIndex = trainingData.classIndex();
		// Using the attribute numbers from the training set
		m_truNumAttributes = trainingData.numAttributes() - 1;
		// Everything in a Java program not explicitly set to something by the programmer, is initialized to a zero value.
		// https://stackoverflow.com/questions/3426843/what-is-the-default-initialization-of-an-array-in-java
		m_coefficients = new double[m_truNumAttributes + 1];
		// Finding the alpha using the training data
		findAlpha(trainingData);
		m_coefficients = gradientDescent(trainingData);
	}

	//the method which runs to train the linear regression predictor, i.e.
	//finds its weights.
	public void buildClassifierFor3(Instances trainingData) throws Exception {
		trainingData = new Instances(trainingData);
		// Using classIndex from the training data info (from main)
		m_ClassIndex = trainingData.classIndex();
		// Using the attribute numbers from the training set
		m_truNumAttributes = trainingData.numAttributes() - 1;
		// Everything in a Java program not explicitly set to something by the programmer, is initialized to a zero value.
		// https://stackoverflow.com/questions/3426843/what-is-the-default-initialization-of-an-array-in-java
		m_coefficients = new double[m_truNumAttributes + 1];
		m_coefficients = gradientDescent(trainingData);
	}

	// Choosing alpha:
	// Create a for loop with a variable i which goes from -17 up to 0.
	// Set alpha equal to 3^i and run gradient descent with this value for 20,000 iterations.
	// Every 100 iterations calculate the new error and compare it to the previous error (100 iterations ago).
	// If the current error is bigger than the previous error stop the iterations and return the previous error.
	// If you finish the 20,000 iteration the reported error will be the last one.
	// For each alpha you got the best error from your gradient descent algorithm.
	// The alpha which gave you the lowest error, will be the chosen alpha.
	private void findAlpha(Instances data) throws Exception {

		double lastError;
		double currentError;
		double minError = Double.MAX_VALUE;
		double bestAlpha = 0;
		double[] tempTheta;
		// Create a for loop with a variable i which goes from -17 up to 0.
		for (int i = -17; i < 0; i++){
			// Re-init the lastError every new alpha
			lastError = Double.MAX_VALUE;
			tempTheta = new double[m_truNumAttributes + 1];
			m_coefficients = new double[m_truNumAttributes + 1]; // Update to 0
			// Set alpha equal to 3^i and run gradient descent with this value for 20,000 iterations.
			m_alpha = Math.pow(3, i);
			for (int j = 1; j <= ITERATIONS; j++){
				gradientDescentHelper(data, tempTheta);
                // After calculation - copy to m_coefficients (array of double) for calculateMSE
				System.arraycopy(tempTheta, 0, m_coefficients, 0, m_truNumAttributes + 1);
				// Every 100 iterations calculate the new error and compare it to the previous error (100 iterations ago).
				if (j % 100 == 0) {
					// Use the function to calculate the error
					currentError = calculateMSE(data);
                    if (currentError > lastError) {
						m_alpha = bestAlpha;
						break;
					}
					// Update error
					lastError = currentError;
					if (lastError < minError) {
						bestAlpha = m_alpha;
						minError = lastError;
					}
				}
			}
		}
		m_alpha = bestAlpha;
    }

	// A private method to help calculating the Gradient Descent.
	private void gradientDescentHelper(Instances data, double[] tempTheta) throws Exception {
		double partialDerivative;
		double attributeValue;
		for (int l = 0; l < m_truNumAttributes + 1; l++) {
			partialDerivative = 0; // Reset derivative every attribute
			for (int m = 0; m < data.numInstances(); m++) {
				if (l == 0) {
					attributeValue = 1;
				}
				else {
					attributeValue = data.instance(m).value(l - 1);
				}
				partialDerivative += (regressionPrediction(data.instance(m)) - data.instance(m).value(m_ClassIndex))
						* attributeValue;
			}
			partialDerivative /= data.numInstances();
			tempTheta[l] = m_coefficients[l] - (m_alpha * partialDerivative);
		}
	}


	/**
	 * An implementation of the gradient descent algorithm which should
	 * return the weights of a linear regression predictor which minimizes
	 * the average squared error.
	 *
	 * @param trainingData
	 * @throws Exception
	 */
	private double[] gradientDescent(Instances trainingData) throws Exception {

		double lastError = Double.MAX_VALUE;
		double currentError;
		double[] tempTheta = new double [m_truNumAttributes + 1];
        m_coefficients = new double[m_truNumAttributes + 1]; // Update to 0

        currentError = calculateMSE(trainingData);
		int i = 0;

		while (Math.abs(currentError - lastError) > EPSILON){
			gradientDescentHelper(trainingData, tempTheta);
			// After calculation - copy to m_coefficients (array of double)
			System.arraycopy(tempTheta, 0, m_coefficients, 0, m_truNumAttributes + 1);

			// Every 100 iterations calculate the new error and compare it to the previous error (100 iterations ago).
			if (i % 100 == 0){
				lastError = currentError;
				currentError = calculateMSE(trainingData);
			}

			i++;
		}

		return m_coefficients;

	}

	/**
	 * Returns the prediction of a linear regression predictor with weights
	 * given by m_coefficients on a single instance.
	 *
	 * @param instance
	 * @return resultOfInnerProduct
	 * @throws Exception
	 */
	public double regressionPrediction(Instance instance) throws Exception {

		double resultOfInnerProduct = m_coefficients[0];
		for (int i = 1 ; i <= m_truNumAttributes; i++) {
			resultOfInnerProduct += instance.value(i - 1) * m_coefficients[i];
		}
		return resultOfInnerProduct;
	}

	/**
	 * Calculates the total squared error over the data on a linear regression
	 * predictor with weights given by m_coefficients.
	 *
	 * @param testData
	 * @return resultMSE
	 * @throws Exception
	 */
	public double calculateMSE(Instances data) throws Exception {

		double resultMSE = 0.0;
		for (int i = 0; i < data.numInstances(); i++) {
			resultMSE += Math.pow(regressionPrediction(data.instance(i)) - data.instance(i).value(m_ClassIndex), 2);
		}
		resultMSE = resultMSE / (2 * data.numInstances());
		return resultMSE;

	}

	/**
	 * Getter for m_alpha
	 *
	 * @return m_alpha
	 */
	public double getM_alpha() {
		return m_alpha;
	}

	@Override
	public double classifyInstance(Instance arg0) throws Exception {
		// Don't change
		return 0;
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
