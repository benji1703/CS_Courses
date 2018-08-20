package HomeWork3;

import weka.core.Instances;
import weka.filters.unsupervised.attribute.Standardize;
import weka.filters.Filter;

public class FeatureScaler {
	/**
	 * Returns a scaled version (using standarized normalization) of the given dataset.
	 * @param instances The original dataset.
	 * @return A scaled instances object.
	 */
	//	https://stackoverflow.com/questions/18468680/scaling-training-data-in-a-weka-classifier?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa
	public static Instances scaleData(Instances instances) throws Exception {

		// http://weka.sourceforge.net/doc.stable/weka/filters/unsupervised/attribute/Standardize.html
		Standardize standardize = new Standardize();
		standardize.setInputFormat(instances);

		return Filter.useFilter(instances, standardize);

	}
}