/* Assignment number : 2.1
File Name : ClockAdvance.java
Name (First Last) : Benjamin Arbibe
Student ID : 323795633
Email : benjamin.arbibe@post.idc.ac.il */

public class ClockAdvance {
  
	// Instructor comment: Bad indentation
	// Grade: 0.0
	
  public static void main(String[] args) {

	String hour = args[0]; 
	int addmin = Integer.parseInt(args[1]) ;	
    
    String sHour = "" + (hour.charAt(0)) + (hour.charAt(1)); 		//Creating the HH
	String sMinute = "" + (hour.charAt(3)) + (hour.charAt(4));		//Creating the MM

	int hourint = Integer.parseInt(sHour);							//Convert the string to INT
	int minuteint = Integer.parseInt(sMinute);


	if ((addmin + minuteint) >= 60)	{								//Check if this is more than one hour (60 minutes)
	
			int addhour = (addmin + minuteint) / 60 ;				//Check how many hour to add
			hourint = hourint + addhour;							//Counting how many hour
			int addminu = (addmin + minuteint) % 60;				//Check how many minutes left
			
		if (hourint >= 24){											//Check if this is more than 24h
			hourint = hourint - 24;
			}
			
		if (addminu < 10) {
			System.out.println (hourint + ":0" + addminu);  		//Check to see if this is no "13:1"
			}
		else {
			System.out.println (hourint + ":" + addminu);}			
			}
			
	else {
			minuteint = minuteint + addmin;
			
			if (minuteint < 10) {
				System.out.println (hourint + ":0" + minuteint);   //Printing the result
				}
			else {
				System.out.println (hourint + ":" + minuteint);
				}
			}
			        
  }
  
}		

