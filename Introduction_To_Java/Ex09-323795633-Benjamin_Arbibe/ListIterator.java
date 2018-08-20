/* Assignment number : 9.3
File Name : ListIterator.java
Name (First Last) : Benjamin Arbibe
Student ID : 323795633
Email : benjamin.arbibe@post.idc.ac.il */

package mms;

/**
 * Represents an iterator of a linked list.
 */
public class ListIterator {

	// current position in the list (cursor)
	Node current;

	/**
	 * Constructs a list iterator, starting at the given node
	 */
	public ListIterator(Node node) {
		current = node;
	}

	/**
	 * Checks if this iterator has more nodes to process
	 */
	public boolean hasNext() {
		MemoryBlock dummyTail = new MemoryBlock(-1, -1); // Dummy for tail
		boolean hasNext = (current.next.block.equals(dummyTail));
		return (!hasNext);
	}

	/**
	 * Returns the current element in the list and advances the cursor
	 */
	public MemoryBlock next() {
		MemoryBlock mBlockT = current.block;
		current = current.next;
		return mBlockT;
	}
}