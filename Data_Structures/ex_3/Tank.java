package HW5;

public class Tank implements Comparable<Tank>{
	
	String serialNumber;
	
	/**
	 * A standard constructor for the tank class.
	 * 
	 * @param serialNumber - the serial number of the tank
	 */
	public Tank(String serialNumber){
		this.serialNumber = serialNumber.toLowerCase();
	}
	
	/**
	 * Compares this tank with another tank for order. 
	 * Returns a negative integer, zero, or a positive integer as the serial number of this tank is
	 * is less than, equal to, or greater than the serial number of the other tank.
	 * 
	 * @param other - the tank to be compared
	 * @return a negative integer, zero, or a positive integer as the serial number of this tank is 
	 * 		   less than, equal to, or greater than the serial number of the other tank.
	 */
	public int compareTo(Tank other) {
		return this.serialNumber() - other.serialNumber();
	}
	
	/**
	 * Convert the string serial number into a positive integer as explained in the assignment's
	 * document.
	 * 
	 * @return the integer represented by the serial number.
	 */
	public int serialNumber(){
		int ans = 0;
		int power = serialNumber.length() - 1;
		for (int i = 0; i < serialNumber.length() ; i++){
			ans += (serialNumber.charAt(i) - 'a') * Math.pow(26,power);
			power--;
		}
		return ans;
	}

	
}
