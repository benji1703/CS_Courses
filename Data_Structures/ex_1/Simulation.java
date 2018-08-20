public class Simulation {

	public static void main(String[] args) {
		
		int N = Integer.parseInt(args[0]);
		int k = Integer.parseInt(args[1]);
		int T = Integer.parseInt(args[2]);

		int sum = 0;
		int average = 0;

		// Create all the German thanks
		Tank[] gTanks = createGermanTanks(N);

		// Starting the simulation
		for (int i = 0; i < T; i++) {
			// Shuffling the k first tanks to get them random
			TankArrayMove(gTanks, k);
			TankEstimator instance = new TankEstimator();
				for (int j=0; j < k; j++) {
					// Add the "captured" shuffled tanks to the Estimator
					instance.captureTank(gTanks[j]);
				}
				sum += instance.estimateProduction();
		}
		
		average = (sum / T);
		
		System.out.println("The average is: " + average);
	}

	

	// Base 10 to base 26 conversion
	private static String intToLetter(int serialNumber) {

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
	
	// Shuffling Tanks position
	private static void TankArrayMove(Tank[] gTanks, int capturedTanks) {
		
		Tank temp;
		int swap;
		for (int i = 0; i < capturedTanks; i++) {
			swap = (int) (Math.random() * gTanks.length);
			temp = gTanks[i];
			gTanks[i] = gTanks[swap];
			gTanks[swap] = temp;
		}
	}

	//Create the German Tanks
	private static Tank[] createGermanTanks(int numberOf) {
	
		Tank[] gTanks = new Tank[numberOf];

		for (int i = 0; i < numberOf; i++) {
			gTanks[i] = new Tank(intToLetter(i));
		}
		return gTanks;

	}

}
