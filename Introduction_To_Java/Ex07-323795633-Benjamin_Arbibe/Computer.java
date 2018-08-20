/* Assignment number : 7.0
File Name : Computer.java
Name (First Last) : Benjamin Arbibe
Student ID : 323795633
Email : benjamin.arbibe@post.idc.ac.il */

/**
 * Represents a Vic computer. It is assumed that users of this class are
 * familiar with the Vic computer, described in www.idc.ac.il/vic. The
 * Computer's hardware consists of the following components:
 * 
 * Data register: a register. Program counter: a register. Input unit: a stream
 * of numbers. In this implementation, the input unit is simulated by a text
 * file. When the computer is instructed to execute a READ command, it reads the
 * next number from this file and puts it in the data register. Output unit: a
 * stream of numbers. In this implementation, the output unit is simulated by
 * standard output (by default, the console). When the computer is instructed to
 * execute a WRITE command, it writes the current value of the data register to
 * the standard output. Processor: in this implementation, the processor is
 * emulated by the run method of this class.
 * 
 * The Computer's software is a program written in the numeric Vic machine
 * language. Such a program normally resides in a text file that can be loaded
 * into the computer's memory. This is done by the loadProgram method of this
 * class.
 */

public class Computer {

	/**
	 * This constant represents the size of the memory unit of this Computer
	 * (number of registers). The default value is 100.
	 */
	public final static int MEM_SIZE = 100;

	/**
	 * This constant represents the memory address at which the constant 0 is
	 * stored. The default value is MEM_SIZE - 2.
	 */
	public final static int LOCATION_OF_ZERO = MEM_SIZE - 2;

	/**
	 * This constant represents the memory address at which the number 1 is
	 * stored. The default value is MEM_SIZE - 1.
	 */
	public final static int LOCATION_OF_ONE = MEM_SIZE - 1;

	// Op-code definitions:
	private final static int ADD = 1;

	private final static int SUB = 2;

	private final static int LOAD = 3;

	private final static int STORE = 4;

	private final static int GOTO = 5;

	private final static int GOTOZ = 6;

	private final static int GOTOP = 7;

	private final static int READ = 8;

	private final static int WRITE = 9;

	private final static int STOP = 0;

	private Memory M;

	private Register D;

	private Register PC;

	/**
	 * Constructs a Vic computer. Specifically: constructs a memory that has
	 * MEM_SIZE registers, a data register, and a program counter. Next, resets
	 * the computer (see the reset method API). Note: the initialization of the
	 * input unit and the loading of a program into memory are not done by the
	 * constructor. This is done by the public methods loadInput and
	 * loadProgram, respectively.
	 */
	public Computer() {
		M = new Memory(MEM_SIZE);
		D = new Register();
		PC = new Register();
		reset(); // Reset the Vic Computer for the 1 and 0.
	}

	/**
	 * Resets the computer. Specifically: Resets the memory, sets the memory
	 * registers at addresses LOCATION_OF_ZERO and LOCATION_OF_ONE to 0 and to
	 * 1, respectively, sets the data register and the program counter to 0.
	 */
	public void reset() {
		M.reset(); // Reset the memory
		M.setValue(LOCATION_OF_ONE, 1);
		M.setValue(LOCATION_OF_ZERO, 0);
		D.setValue(0);
		PC.setValue(0);
	}

	/**
	 * Executes the program currently stored in memory. This is done by
	 * affecting the following fetch-execute cycle: Fetches from memory the
	 * current word (3-digit number), i.e. the contents of the memory register
	 * whose address is the current value of the program counter. Extracts from
	 * this word the op-code (left-most digit) and the address (next 2 digits).
	 * Next, executes the command mandated by the op-code, using the address if
	 * necessary. As a side-effect of executing this command, modifies the
	 * program counter. Next, loops to fetch the next word, and so on.
	 */
	public void run() {
		boolean cont = true; // Create the boolean for the loop break
		while (cont) {

			int temp = M.getValue(PC.getValue());
			int oper = 0;
			int adr = 0;

			oper = temp / 100;
			adr = temp % 100;

			switch (oper) {

			case ADD:
				execAdd(adr);
				break;
			case SUB:
				execSub(adr);
				break;
			case LOAD:
				execLoad(adr);
				break;
			case STORE:
				execStore(adr);
				break;
			case GOTO:
				execGoto(adr);
				break;
			case GOTOZ:
				execGotoz(adr);
				break;
			case GOTOP:
				execGotop(adr);
				break;
			case READ:
				execRead();
				break;
			case WRITE:
				execWrite();
				break;
			case STOP:
				System.out.println("Run terminated normally");
				cont = false;
				break;
			}
			if (!cont) {
				break; // If the computer get a Stop, break the loop.
			}
		}
	}

	// Private execution routines, one for each Vic command
	private void execAdd(int addr) {
		D.setValue(D.getValue() + M.getValue(addr));
		PC.addOne();
	}

	private void execSub(int addr) {
		D.setValue(D.getValue() - M.getValue(addr));
		PC.addOne();
	}

	private void execLoad(int addr) {
		D.setValue(M.getValue(addr));
		PC.addOne();
	}

	private void execStore(int addr) {
		M.setValue(addr, D.getValue());
		PC.addOne();
	}

	private void execGoto(int comm) {
		PC.setValue(comm);
	}

	private void execGotoz(int comm) {
		if (D.getValue() == 0) {
			PC.setValue(comm);
		} else {
			PC.addOne();
		}
	}

	private void execGotop(int comm) {
		if (D.getValue() > 0) {
			PC.setValue(comm);
		} else {
			PC.addOne();
		}
	}

	private void execRead() {
		D.setValue(StdIn.readInt());
		PC.addOne();
	}

	private void execWrite() {
		System.out.println(D);
		PC.addOne();
	}

	/**
	 * Loads a program into memory, starting at address 0, using the standard
	 * input. The program is stored in a text file whose name is the given
	 * fileName. It is assumed that the file contains a stream of valid commands
	 * written in the numeric Vic machine language (described in
	 * www.idc.ac.il/vic). The program is stored in the memory, one command per
	 * memory register, starting at address 0.
	 */
	public void loadProgram(String fileName) {
		StdIn.setInput(fileName);
		int place = 0;
		while (StdIn.hasNextLine()) {
			M.setValue(place, StdIn.readInt());
			place++;
		}
	}

	/**
	 * Initializes the input unit from a given text file using the standard
	 * input. It is assumed that the file contains a stream of valid data
	 * values, each being an integer in the range -999 to 999. Each time the
	 * computer is instructed to execute a READ command, the next line from this
	 * file is read and placed in the data register (this READ logic is part of
	 * the run method implementation). Thus, the role of this method is to
	 * initialize the file in order to enable the execution of subsequent READ
	 * commands.
	 */
	public void loadInput(String fileName) {
		StdIn.setInput(fileName);
	}

	/**
	 * This method is used for debugging purposes. It displays the current
	 * contents of the data register, the program counter, and the memory (first
	 * and last 10 memory cells).
	 */
	public String toString() {
		String pString = "D register  = " + D + "\n" + "PC register = " + PC
				+ "\n" + "Memory state: \n" + M;

		return pString;
	}
}