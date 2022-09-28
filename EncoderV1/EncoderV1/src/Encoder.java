/*
 * Program to Encode and Decode a String
 * 
 * 
 * -----------------------------
 * 	Index	char	ASCII-Value
 * -----------------------------
 * 	 0		A		65
 * 	 1		B		66
 * 	 2		C		67
 * 	 3		D		68
 * 	 4		E		69
 * 	 5		F		70
 * 	 6		G		71
 * 	 7		H		72
 * 	 8		I		73
 * 	 9		J		74
 * 	10		K		75
 * 	11		L		76
 * 	12		M		77
 * 	13		N		78
 * 	14		O		79
 * 	15		P		80
 * 	16		Q		81
 * 	17		R		82
 * 	18		S		83
 * 	19		T		84
 * 	20		U		85
 * 	21		V		86
 * 	22		W		87
 * 	23		X		88
 * 	24		Y		89
 * 	25		Z		90
 * -----------------------------
 * 	26		0		48
 * 	27		1		49
 * 	28		2		50
 * 	29		3		51
 * 	30		4		52
 * 	31		5		53
 * 	32		6		54
 * 	33		7		55
 * 	34		8		56
 * 	35		9		57
 * -----------------------------
 * 	36		(		40
 * 	37		)		41
 * 	38		*		42
 * 	39		+		43
 * 	40		,		44
 * 	41		-		45
 * 	42		.		46
 * 	43		/		47
 * -----------------------------
 * 					
 * 					
 */

import java.util.Scanner;
import java.util.Random;

public class Encoder{
	
	/*
	 * Class Variables
	 */
	char selection;
	String 	input,		// Text(String) to be Encoded or Decoded
			output;		// Encoded or Decoded text(String)
	//// End of Class Variables
	
	
	public Encoder() {
		selection = '0';
		input = "";
		output = "";
	}

	public Encoder(char c, String s) {
		selection = c;
		input = s;
		if(selection == '1') {
			output = encode(input);
		}
		if(selection == '2') {
			output = decode(input);
		}
	}

	/*
	 * Class Methods
	 */
	public boolean inTable(char c) {
		/*
		 * Checks if c is in the Reference Table
		 */
		int cAscii = (int)c;
		if( (cAscii >= 40 && cAscii <= 57) || (cAscii >= 65 && cAscii <=90)) {
			return true;
		}
		return false;
	}
	
	public int indexToAscii(int index) {
		/*
		 * Returns the Reference Table character's 
		 * 		ASCII Value 
		 * 			given its Index (0-43 inclusive)
		 */
		if(index <= 25) {		// 0-25 : A-Z
			return index+65;
		}
		if(index <= 35) {		// 26-35 : 0-9
			return index+22;
		}
		if(index <= 43) {		// 36-43: '(', ')', '*', '+', ',' ,'-' ,'.' ,'/'.
			return index+4;
		}
		return -1;				// ERROR!
	}
	
	public int asciiToIndex(int ascii) {
		/*
		 * Returns the Reference Table character's 
		 * 		Index 
		 * 			given its ASCII Value
		 * 
		 * WARNING: ascii must be an ASCCI Value of a char in the Reference Table
		 */
		if(ascii >=40 && ascii <=47) {
			return ascii - 4;
		}
		if(ascii >=48 && ascii <=57) {
			return ascii - 22;
		}
		if(ascii >=65 && ascii <=90) {
			return ascii - 65;
		}
		return -1;		// ERROR
	}
	
	public char offset(int offsetValue, char c) {
		/*
		 * Returns the new offset char
		 * 		given a char (c) & the value to offset it by (offsetValue)
		 */
		if(!inTable(c)) {
			return c;
		}
		else {
			return (char)indexToAscii( (asciiToIndex((int)c)+offsetValue+44)%44 );
		}
	}
	
	public String encode(String plainText) {
		String encodedText = "";
		
		/*
		 * Random offset
		 */
		Random random = new Random();
		int offsetInt = random.nextInt(43);
		encodedText += (char) indexToAscii(offsetInt);
				
		/*
		 * Encode
		 */
		for(int i=0; i<plainText.length(); i++) {
			encodedText += offset(offsetInt, plainText.charAt(i));
		}
		
		return encodedText;
	}
	
	public String decode(String encodedText) {
		int offsetIndex = asciiToIndex( (int) encodedText.charAt(0) );
		String decodedText = "";

		/*
		 * Decode
		 */
		for(int i=1; i<encodedText.length(); i++) {
			decodedText += offset( (-offsetIndex), encodedText.charAt(i));
		}
		
		return decodedText;
	}
	//// End of Class Methods
	
	
	public static void main(String[] args) {
				
		//
		// Select Encoder or Decoder
		//
		
		Scanner in = new Scanner(System.in);	// Input
		boolean validSelection = false;			// to check if input in valid
		
		while(!validSelection){
			System.out.println("Enter '1' to Encode or '2' to Decode: ");
			String selection = in.nextLine();
			if( (selection.length() == 1) && (selection.charAt(0) == '1') ) {			// Encoder selected
				System.out.println("Enter the text that you would like to ENCODE: ");
				Encoder message = new Encoder('1',in.nextLine());
				System.out.println(message.output);
				validSelection = true;
			}
			else if( (selection.length() == 1) && (selection.charAt(0) == '2') ) {			// Decoder selected
				System.out.println("Enter the text that you would like to DECODE: ");
				Encoder message = new Encoder('2',in.nextLine());
				System.out.println(message.output);
				validSelection = true;
			}
		}
		
	}

	
}
