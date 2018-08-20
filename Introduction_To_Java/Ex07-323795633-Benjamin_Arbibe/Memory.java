/* Assignment number : 7.1
File Name : Memory.java
Name (First Last) : Benjamin Arbibe
Student ID : 323795633
Email : benjamin.arbibe@post.idc.ac.il */

/**
 * Represents a random access memory (RAM) unit. A Memory is an indexed sequence
 * of registers. The Memory enables reading from, or writing to, any individual
 * register according to a given index. The index is typically called "address".
 * The addresses run from 0 to the memory's size, minus 1.
 */

public class Memory {

	private Register[] m; // an array of Register objects

	/**
	 * Constructs a memory of size registers, and sets all the register values
	 * to 0.
	 */
	public Memory(int size) {
		this.m = new Register[size];
		for (int i = 0; i < this.m.length; i++) {
			Register r = new Register();
			this.m[i] = r;
		}
	}

	/** Sets the values of all the registers in this memory to 0. */
	public void reset() {
		for (int i = 0; i < this.m.length; i++) {
			this.m[i].setValue(0);
			;
		}
	}

	/** Returns the value of the register whose address is the given address. */
	public int getValue(int address) {
		return this.m[address].getValue();
	}

	/** Sets the register in the given address to the given value. */
	public void setValue(int address, int value) {
		this.m[address].setValue(value);
	}

	/**
	 * Returns a subset of the memory's contents, as a formated string.
	 * Specifically: Returns the first 10 registers (where the top of the
	 * program normally resides) and the bottom 10 registers (where the
	 * variables normally reside). For each register, returns the register's
	 * address and value.
	 */
	public String toString() {
		// Print the first 10
		StringBuilder sPrint = new StringBuilder();
		for (int i = 0; i < 10; i++) {
			sPrint.append(
					String.format("%2s %4s", i, this.m[i].getValue()) + "\n");
		}

		sPrint.append(String.format("%2s", "..") + "\n");

		// Print the last 10
		for (int j = (this.m.length) - 10; j < this.m.length; j++) {
			sPrint.append(
					String.format("%2s %4s", j, this.m[j].getValue()) + "\n");
		}

		return sPrint.toString();
	}
}
