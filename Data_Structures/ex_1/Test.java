public class Test {
	
	public static void main(String[] args) {
/*
	Tank A = new Tank("a");
	Tank B = new Tank("b");
	Tank C = new Tank("c");
	Tank D = new Tank("d");
	Tank E = new Tank("e");
	Tank F = new Tank("f");
	Tank G = new Tank("g");
	Tank H = new Tank("h");
	Tank I = new Tank("i");
	Tank J = new Tank("j");
	Tank K = new Tank("k");
	Tank L = new Tank("l");
	Tank M = new Tank("m");
	Tank N = new Tank("n");
	Tank O = new Tank("o");
	System.out.println(M.serialNumber());
	// System.out.println(A.compareTo(B));
	Heap test = new Heap();
	test.insert(A);
	test.insert(B);
	test.insert(C);
	test.insert(D);
	test.insert(E);
	test.insert(F);
	test.insert(G);
	test.insert(H);
	test.insert(I);
	test.insert(J);
	test.insert(K);
	test.insert(L);
	test.insert(M);
	test.insert(N);
	test.insert(O);
	test.print();
	System.out.println(test.findMax().serialNumber());
	System.out.println(test.size());
	test.extractMax();
	test.print();
	System.out.println(test.findMax().serialNumber());
	System.out.println(test.size());
	
	System.out.println(test.findKbiggest(1));
	System.out.println(test.findKbiggest(2));
	System.out.println(test.findKbiggest(3));
	System.out.println(test.findKbiggest(4));
	
	test.removeKbiggest(2);
	test.print();
	
	System.out.println(test.contains(A));
	System.out.println(test.contains(M));
*/
	System.out.println(intToLetter(10001));
	}
	
	
	public static String intToLetter (int serialNumber) {
		
		StringBuilder serialS = new StringBuilder();
		
		if (serialNumber == 0) {
			serialS.insert(0, ((char) (serialNumber + 65)));
		}
		
		while (serialNumber > 0) {
			serialS.insert(0, ((char) ((serialNumber % 26) + 65)));
			serialNumber /= 26;
		}
		
		return (serialS.toString());
		
	}
}
