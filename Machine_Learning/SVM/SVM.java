package HomeWork5;

import weka.classifiers.functions.SMO;
import weka.classifiers.functions.supportVector.Kernel;
import weka.core.Instance;
import weka.core.Instances;

public class SVM {
	public SMO m_smo;

	public SVM() {
		this.m_smo = new SMO();
	}

	public void buildClassifier(Instances instances) throws Exception {
		m_smo.buildClassifier(instances);
	}

	// Using
	// http://weka.sourceforge.net/doc.dev/weka/classifiers/functions/SMO.html

	public void setKernel(Kernel k) {
		m_smo.setKernel(k);
	}

	public void setC(double C) {
		m_smo.setC(C);
	}

	public double getC() {
		return m_smo.getC();
	}

	// https://www.researchgate.net/post/How_can_one_extract_values_of_TN_FP_TP_and_FN_from_SVM_to_plot_roc

	public int[] calcConfusion(Instances instances) throws Exception {

		int TP = 0;
		int FP = 0;
		int TN = 0;
		int FN = 0;

		for (Instance instance : instances) {

			double actualClass = instance.classValue();
			double predictedClassWithSMO = m_smo.classifyInstance(instance);

			if (actualClass == 1.0) {
				if (predictedClassWithSMO == 1.0) {
					TP++;
				} else if (predictedClassWithSMO == 0.0) {
					FN++;
				}
			} else if (actualClass == 0.0) {
				if (predictedClassWithSMO == 0.0) {
					TN++;
				} else if (predictedClassWithSMO == 1.0) {
					FP++;
				}
			}
		}

		return new int[]{TP, FP, TN, FN};

	}
}
