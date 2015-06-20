import java.util.Random;
import java.util.Scanner;

public class Bruteforce {
	
	public static String password  = ""; //The correct password
	public static long attempts = 10000000; //Value of attempts to be made
	public static int threads = 0; //Value of threads TODO implement threads
	public static int percentage = 10; //How often should the statistical information be shown
	public static String allChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ[]!§$%&*#~+-€"; //All possible characters
	public static String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"; //The Upper & Lowercase alphabet
	public static String numericAlphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"; //Alphabet + Numeric chars
	public static String lowerChars = "abcdefghijklmnopqrstuvwxyz"; //Lower alphabet
	public static String upperChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; //Upper alphabet
	public static String otherChars = "[]!§$%&*#~+-€"; //Special characters
	public static int minLength = 0; //Starting length of the brute force
	public static int maxLength = 0; //Maximal length of the brute force
	public static boolean visual = false; //Show every tested password (Will drastically slow down the program)
	public static Random rnd = new Random(); //Random Seed
	public static long starttime = 0; //When the program started forcing
	
	
	public static void main(String args[]){
		start();
	}
	
	public static void start() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Password to Brute");
		password = sc.nextLine(); //right password to test
		double possiblePasswords = Math.pow(62, password.length()); //Would be the count of possible passwords (Lowercase, upercase & numeric chars only)
		System.out.printf(("If these password cointaint Capital Letters, Lower Letters and Numbers there would be %.0f possible passwords\n"),possiblePasswords);
		System.out.println(("Attempts (enter '0' for standard option ("+attempts+" Attempts)"));
		long attemptsx = sc.nextLong();
		if(attemptsx != 0){ //If there was entered a valid number of attempts (else the standard 'attempts' is used)
			attempts = attemptsx;
		}
		System.out.println("Threads (Max: 5)");
		threads = sc.nextInt(); //Number of threads
		if(threads > 5){ //Most systems will not be faster with more than 5 threads at the same time
			threads = 5;
		}else if(threads <= 0){
			threads = 1;
		}
		System.out.println("Minimum Length");
		minLength = sc.nextInt();
		if(minLength < 1){
			minLength = 1;
		}
		System.out.println("Maximum Length");
		maxLength = sc.nextInt();
		
		if(minLength > maxLength){
			int maxLengthx = maxLength;
			maxLength = minLength;
			minLength = maxLengthx;
		}
		
		System.out.println("Console Output (Slows down drastically) (y/n)"); //If every String will be printed, the programm would slow down (So this is an debuging option)
		String graphical = sc.next();
		if(graphical.equalsIgnoreCase("Y")){
			visual = true;
		}
		System.out.println("Percentage Info (e.g. '10' for 10%,20%)"); //1->1%,2%  50->50%,100%
		percentage = sc.nextInt();
		if (percentage <= 0 || percentage > 100 ){
			percentage = 100;
		}
		
		
		sc.close();
		
		String forced = brute();
		System.out.println("Password: "+forced);
		
		
	}
	
	public static String brute(){
	starttime = System.nanoTime(); // Time when the forcing started
	long lastendtime = starttime; //Saved for later
	//Threading should be started here
	for(int k = 1; k<=(100/percentage);k++){ //the statistical information is shown after every k-loop
	for (int i = 0; i<(attempts/(100/percentage));i++){
		String forced = next(); //Generate new String
		if(visual){ //If every String should be printed
		System.out.println(forced);
		}
		if(forced.equals(password)){ //If the correct password is found
			long endtime = System.nanoTime();
			System.out.printf("%.0f Attempts needed\n",((attempts/(100/percentage))*k-i));
			double time = (endtime - starttime) / 1000000000.0; //Get the difference of the start and endtime
			System.out.println(("Took "+time+" seconds")); //Print how long it took
			double spa = time/((attempts/(100/percentage)*k-i)); //How long took 1 Attempt
			System.out.printf("~ %.10f Seconds per attempt\n",spa);
			double aps = (1/(time/((attempts/(100/percentage)*k-i))));//How many Attempts in 1 second
			System.out.printf("%.0f Attempts per Second\n",aps);
			
			return forced; //Return the correct password
			
		}
	} //Threading should close here
	
	//status
	long endtime = System.nanoTime();
	double time = (endtime - starttime) / 1000000000.0; //How long is the forcing running
	double thistime = ((endtime-lastendtime)) / 1000000000.0; //How long took this part (between this and the last statistical information)
	System.out.printf("%3d%%, %" + Long.toString(attempts).length() +  "d attempts, This %6.3f, Total %8.3f Seconds, %.10f Seconds/Attempt, %.0f Attempts/Second\n",(percentage*(k)),( ((attempts * percentage*k)/100  ) ),thistime,time,((thistime)/attempts),(1/(thistime/attempts)));
	lastendtime = endtime; //Set the new Endtime
	}
		return "Not Found"; //All attempts run. Correct password not found
	}
	
	public static String next(){ //Generate new String
		int randomNum = rnd.nextInt((maxLength - minLength) + 1) + minLength; //Random Length between maxLength and minLength
		String nextString = "";
		for(int i=0;i<randomNum;i++){ //As long as the randomNum said
			nextString += lowerChars.charAt(rnd.nextInt(lowerChars.length())); //Next string will be taken out of a char list. TODO (Create input -> What list should be used)
		}
		
		return nextString;
	}

}
