/* Assignment number : 9.0
File Name : Node.java
Name (First Last) : Benjamin Arbibe
Student ID : 323795633
Email : benjamin.arbibe@post.idc.ac.il */

package mms;

/**
 * Represents a node in a doubly linked list. Each node points to a MemoryBlock
 * object.
 */
public class Node {

	// Three package-private fields should appear here.
	Node next;
	Node previous;
	MemoryBlock block;

	/**
	 * Constructs a new node pointing to the given memory block
	 * 
	 * @param block
	 *            the given memory block
	 */
	public Node(MemoryBlock block) {
		this.block = block;
	}

	/**
	 * A textual representation of this node, useful for debugging. (See the
	 * test output for examples).
	 */

	public String toString() {
		MemoryBlock block = this.block;
		String sPrint = ("" + block);
		return sPrint;
	}
}