/* Assignment number : 9.2
File Name : LinkedList.java
Name (First Last) : Benjamin Arbibe
Student ID : 323795633
Email : benjamin.arbibe@post.idc.ac.il */

package mms;

/**
 * Represents a list of Nodes. The list has a "first" pointer, which points to
 * the first node in the list, a "last" pointer, which points to the last node
 * in the list, and a size, which is the number of nodes in the list.
 */
public class LinkedList {

	// Three package-private fields come here.
	Node head;
	Node tail;
	int size;

	/**
	 * Constructs a new doubly-connected linked list.
	 */
	public LinkedList() {
		head = new Node(new MemoryBlock(-1, -1)); // 'Dummy' for head
		tail = new Node(new MemoryBlock(-1, -1)); // And tail
		head.next = tail;		// Making the connection - easier
		tail.previous = head; 	// to debug than "null"
		this.size = 0;
	}

	/**
	 * Gets the node located at the given index in this list.
	 * 
	 * @param index
	 *            the index of the node to retrieve, between 0 and size
	 * @throws IllegalArgumentException
	 *             if index is negative or greater than the list's size
	 * @return the node at the given index
	 */
	public Node getNode(int index) {
		//throw IllegalArgumentException for a non valid input
		if (index < 0 || index > this.size) {
			throw new IllegalArgumentException("Illegal Index " + index);
		}
		Node current = this.head.next;	//Starting from the first 'real' node
		for (int i = 0; i < index; i++) {
			current = current.next; 	//Going to the right index
		}
		return current;
	}

	/**
	 * Creates a new Node object that points to the given memory block, and
	 * inserts the node to this list immediately prior to the given index
	 * (position in this list).
	 * <p>
	 * If the given index is 0, the new node becomes the first node in this
	 * list.
	 * <p>
	 * If the given index equals the size of this list, the new node becomes the
	 * last node in this list.
	 * 
	 * @param block
	 *            the memory block to be inserted into the list
	 * @param index
	 *            the index before which the memory block should be inserted
	 * @throws IllegalArgumentException
	 *             if index is negative or greater than the list's size
	 */
	public void add(int index, MemoryBlock block) {
		if (index < 0 || index > this.size) {
			throw new IllegalArgumentException("Illegal Index " + index);
		}
		// If add to first = then use addFirst
		if (index == 0) {
			addFirst(block);
			return;
		}
		// If add to last = then use addLast
		if (index == size) {
			addLast(block);
			return;
		}
		Node insert = new Node(block);
		Node temp = getNode(index);
		// Create the doubly linked add
		insert.next = temp;
		insert.previous = temp.previous;
		temp.previous.next = insert;
		temp.previous = insert;
		this.size++;
	}

	/**
	 * Creates a new node with a reference to the given memory block, and
	 * appends it to the end of this list (the node will become the list's last
	 * node).
	 * 
	 * @param block
	 *            the given memory block
	 */
	public void addLast(MemoryBlock block) {

		// Adding to the prev of tail and updating the linking
		Node node = new Node(block);
		node.next = tail;
		node.previous = tail.previous;
		tail.previous.next = node;
		tail.previous = node;
		this.size++;

	}

	/**
	 * Creates a new node with a reference to the given memory block, and
	 * inserts it at the beginning of this list (the node will become the list's
	 * first node).
	 * 
	 * @param block
	 *            the given memory block
	 */
	public void addFirst(MemoryBlock block) {

		// Adding to the next of head and updating the linking
		Node node = new Node(block);
		node.previous = head;
		node.next = head.next;
		head.next.previous = node;
		head.next = node;
		this.size++;

	}

	/**
	 * Gets the memory block located at the given index in this list.
	 * 
	 * @param index
	 *            the index of the retrieved memory block
	 * @return the memory block at the given index
	 * @throws IllegalArgumentException
	 *             if index is negative or greater than or equal to size
	 */
	public MemoryBlock getBlock(int index) {
		if (index < 0 || index >= this.size) {
			throw new IllegalArgumentException("Illegal Index " + index);
		}
		// Going to the index and "getting" the right block in this index
		Node current = this.head.next;
		for (int i = 0; i < index; i++) {
			current = current.next;
		}
		return current.block;
	}

	/**
	 * Gets the index of the node pointing to the given memory block.
	 * 
	 * @param block
	 *            the given memory block
	 * @return the index of the block, or -1 if the block is not in this list
	 */
	public int indexOf(MemoryBlock block) {
		Node current = head.next;
		int index = 0;
		for (int i = 0; i < size; i++) {
			// Using overide - cleaner code
			// Check the block in this place
			if (current.block == block) {
				return index;
			}
			index++;
			// Continue the check
			current = current.next;
		}
		// If not found then:
		return -1;
	}

	/**
	 * Removes the given node from this list.
	 * 
	 * @param node
	 *            the node that will be removed from this list
	 */
	public void remove(Node node) {
		if (this.indexOf(node.block) == -1)
			return;
		// using "if"s for a cleaner code and debugging possibilities
		// If node to be deleted is the first node
		if (head.next.block == node.block) {
			head.next = node.next;
		}
		// Change next only if node to be deleted is NOT the last node
		if (!(node.next.block == tail.block)) {
			node.next.previous = node.previous;
		}
		// Change prev only if node to be deleted is NOT the first node
		if (!(node.previous.block == head.block)) {
			node.previous.next = node.next;
		}

		// Finally, downsize the list size
		size--;
	}

	/**
	 * Removes from this list the node which is located at the given index.
	 * 
	 * @param index
	 *            the location of the node that has to be removed.
	 * @throws IllegalArgumentException
	 *             if index is negative or greater than or equal to size
	 */
	public void remove(int index) {
		if (index < 0 || index >= this.size) {
			throw new IllegalArgumentException("Illegal Index " + index);
		}
		//Using the getNode method
		Node removeNode = getNode(index);
		//Using the removeNode method
		remove(removeNode);
		return;
	}

	/**
	 * Removes from this list the node pointing to the given memory block.
	 * 
	 * @param block
	 *            the memory block that should be removed from the list
	 * @throws IllegalArgumentException
	 *             if the given memory block is not in this list
	 */
	public void remove(MemoryBlock block) {
		if (indexOf(block) == -1) {
			throw new IllegalArgumentException("Illegal Memory Block " + block);
		}
		int index = indexOf(block);
		//Using the removeNode method
		remove(index);
		return;

	}

	/**
	 * Returns an iterator over this list, starting with the first element.
	 */
	public ListIterator iterator() {
		return new ListIterator(head.next);
	}

	/**
	 * A textual representation of this list, useful for debugging.
	 */
	public String toString() {
		StringBuilder sPrint = new StringBuilder();
		if (size == 0) {
			return " ";
		}
		if (size == 1) {
			return head.next.toString();
		}
		Node currentNode = head.next;
		while (currentNode.next.next != null) {
			sPrint.append(currentNode).append(" ");
			currentNode = currentNode.next;
		}
		return sPrint.append(currentNode).toString();
	}
}