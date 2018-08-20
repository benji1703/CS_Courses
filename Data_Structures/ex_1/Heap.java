public class Heap {

	private Tank[] data;
	private int size;

	/**
	 * Creates an empty list.
	 */
	public Heap() {
		this.data = new Tank[10001];
		this.size = 0;
		data[0] = new Tank("XXX");
	}

	/**
	 * Returns the size of the heap.
	 * 
	 * @return the size of the heap
	 */
	public int size() {
		return this.size;
	}

	/**
	 * Inserts a given tank into the heap. Should run in time O(log(n)).
	 * 
	 * @param t
	 *            - the tank to be inserted.
	 */
	public void insert(Tank t) {
		data[++size] = t;
		percolateUp(size);
	}

	/**
	 * Returns the tank with the highest serial number in the heap. Should run
	 * in time O(1).
	 * 
	 * @return the tank with the highest serial number in the heap.
	 */
	public Tank findMax() {
		Tank max = data[1];
		return max;
	}

	/**
	 * Removes the tank with the highest serial number from the heap. Should run
	 * in time O(log(n)).
	 * 
	 */
	public void extractMax() {
		data[1] = data[size];
		data[size] = null;
		this.size--;
		maxHeapify(1);
	}

	/**
	 * Returns the tank with the k highest serial number in the heap. Should run
	 * in time O(klog(n)).
	 * 
	 * @param k
	 * @return the tank with the k highest serial number in the heap.
	 */
	public Tank findKbiggest(int k) {
		Tank[] kBig = new Tank[k+1];
		kBig[1] = findMax();

		if (k == 1) {
			return kBig[k];
		}

		for (int i = 1; i <= k; i++) {
			kBig[i] = findMax();
			extractMax();
		}
		for (int j = 1; j <= k; j++) {
			insert(kBig[j]);
		}
		return kBig[k];
	}

	/**
	 * Removes the tank with the k highest serial number from the heap. Should
	 * run in time O(klog(n)).
	 * 
	 * @param k
	 */
	public void removeKbiggest(int k) {
		for (int i = 0; i < k; i++) {
			extractMax();
		}
	}
	

	/**
	 * Checks if a given tank is a part of the heap.
	 * 
	 * @param t
	 *            - the tank to be checked
	 * @return true if and only if the tank is in the heap.
	 */
	public boolean contains(Tank t) {
		for (int i = 1; i <= size; i++) {
			if (data[i].compareTo(t) == 0)
				return true;
		}
		return false;
	}

	private int parent(int pos) {
		return pos / 2;
	}

	private int leftChild(int pos) {
		return (2 * pos);
	}

	private int rightChild(int pos) {
		return (2 * pos) + 1;
	}

	private boolean isLeaf(int pos) {
		if (pos >= (size / 2) && pos <= size) {
			return true;
		}
		return false;
	}

	private void maxHeapify(int pos) {
		while ((!isLeaf(pos)) && (data[pos]
				.serialNumber() < data[(leftChild(pos))].serialNumber()
				|| data[pos].serialNumber() < data[(rightChild(pos))]
						.serialNumber())) {
			if (data[leftChild(pos)].serialNumber() > data[rightChild(pos)]
					.serialNumber()) {
				swap(leftChild(pos), pos);
				pos = leftChild(pos);
			} else {
				swap(rightChild(pos), pos);
				pos = rightChild(pos);
			}
		}

	}

	private void percolateUp(int pos) {
		int current = pos;
		while (data[current].serialNumber() > data[parent(current)]
				.serialNumber()) {
			swap(current, parent(current));
			current = parent(current);
		}
	}

	private void swap(int fpos, int spos) {
		Tank tmp;
		tmp = data[fpos];
		data[fpos] = data[spos];
		data[spos] = tmp;
	}

}
