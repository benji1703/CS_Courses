public class TankEstimator {

	private Heap h;

	/**
	 * Creates a new estimator with an empty heap.
	 */
	public TankEstimator() {
		h = new Heap();
	}

	/**
	 * Adds the data of a new captured tank
	 * 
	 * @param t
	 *            - the captured tank.
	 */
	public void captureTank(Tank t) {
		h.insert(t);
	}

	/**
	 * Estimates the total number of produced tanks, based on the information of
	 * captured tanks. Estimation is done according to the formula presented in
	 * the assignment's document.
	 * 
	 * @return an estimation to the total number of produced tanks
	 */
	public int estimateProduction() {
		int k = h.size();
		int m = h.findMax().serialNumber();
		int result = m + (m/k) - 1;
		return result;
	}
}
