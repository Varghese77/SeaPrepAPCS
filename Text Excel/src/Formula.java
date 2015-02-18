import java.util.ArrayList;

public class Formula {

	// public fields
	public String originalFormula = "no_formula_given";
	
	public ArrayList<String> references = new ArrayList<String>(100);
	
	
	public int previousCellType;
	
	public String previousCellData = "";
	
	public String previousCellAddress = ""; 
	
	public Formula(String[] cellMeta, int displayType) {
		/* sets up original formula and 
		 * saves past data in case formula is invalid
		 * cellMeta[0] = Cell.address
		 * cellMeta[1] = original command or s
		 * cellMeta[2] = Cell.displayInternalContent()
		 */
		
		previousCellAddress = cellMeta[0];
		originalFormula = cellMeta[1];
		previousCellData = cellMeta[2];
		previousCellType = displayType;
		
		int column = previousCellAddress.charAt(0) - 65;
		int row = previousCellAddress.charAt(1) - 49;
		
		//assigns specific cell display content to formula
		CellData.spreadSheet[row][column].displayContent = 4;
		
	}
	
	public String toString() {
		String noParenthesesFormula = originalFormula.substring(1, originalFormula.length()-1);
		references = new ArrayList<String>();
		
		// creates array of expression parts
		String[] parts;
		try {
			parts = noParenthesesFormula.split(" ");
		} catch (StringIndexOutOfBoundsException noInput) {
			// if there are no spaces in user entered formula
			parts = new String[1];
			parts[0] = "";
		}
		
		// Extracts information from referred cells
		convertCellAddress(parts);
		
		//determines if formula follows correct format
		if (!validateContents(parts)) {
			terminateFormula(parts);
		}
		
		// multiplies and divides numbers first
		mult_div(parts);
		
		// adds and subtracts numbers second
		add_sub(parts);
		
		if (parts[0].length() == 0) {
			// if terminateFormula was called
			int column = previousCellAddress.charAt(0) - 65;
			int row = previousCellAddress.charAt(1) - 49;
			
			// restores cell with previous data
			CellData.spreadSheet[row][column].setCell(previousCellData, previousCellType);
			return CellData.spreadSheet[row][column].toString();
			
		} else {
			// final evaluation of formula is returned
			return parts[0];
		}
		
	}
	
	public boolean checkReferences() {
		return check(previousCellAddress);
	}
	
	public boolean check(String address) {
		boolean isValid = true;
		for (int i = 0; i < references.size(); i++) {
			if (references.get(i).equals(address)){
				return false;
			}
		}
		
		for (int i = 0; i < references.size(); i++)  {
			String reference = references.get(i);
			int column = reference.charAt(0) - 65;
			int row = reference.charAt(1) - 49;
			
			int cellDisplayType = CellData.spreadSheet[row][column].displayContent;
			if (cellDisplayType == 4){
				isValid = CellData.spreadSheet[row][column].formula.check(address);
			}
		}
		
		return isValid;
	}
	
	public static boolean validateContents(String[] parts){
		boolean isValid = true;
		
		/* determines if every other part of the 
		 * array is a number thus keeping format
		 */
		for (int i = 0; i < parts.length; i += 2){
			try {
				Double.parseDouble(parts[i]);
			} catch (NumberFormatException isNotNum) {
				isValid = false;
			}
		}
		return isValid;
	}
	
	private void convertCellAddress(String[] parts) {
		for (int i = 0; i < parts.length; i++) {
			/* gets the first character in the string
			 * Also, if the array part is empty, it is 
			 * set to 'a' which will eventually lead to terminateFormula();
			 */
			char firstChar;
			if (parts[i].length() == 0) {
			firstChar = '!';
			} else {
				firstChar = parts[i].charAt(0);
			}
			
			// determines that part isn't a sum or avg equation
			if (parts[i].toUpperCase().indexOf("SUM") == -1 && parts[i].toUpperCase().indexOf("AVG") == -1) {
				
				//determines if part possible represents an address
				if ((firstChar >= 65 && firstChar <= 90) && parts[i].length() == 2) {
					references.add(parts[i]);
					
					//converts address to array indices 
					int column = firstChar - 65;
					int row = parts[i].charAt(1) - 49;
				
					try {
					if (checkReferences()){
						// swaps data from cell to temporary string to formula
						String tempStringHolder = CellData.spreadSheet[row][column].toString();
						parts[i] = tempStringHolder;
					
						//tests if the data swapped is a real number
						Double.parseDouble(parts[i]);
					}else {
						Exception e = new Exception();
						throw e;
					}
					} catch (Exception e) { // ArrayIndexOutOfBoundsException or stackOverflowError
						terminateFormula(parts);
					}
				}
			} else {
				if (parts[i].toUpperCase().indexOf("SUM") != -1) {
					// data sent to sum method and stored in temporary string
					String tempStringHolder = sum(parts[i]);
					
					// swaps data
					parts[i] = tempStringHolder.substring(0, tempStringHolder.indexOf(" "));
				} else {
					parts[i] = avg(parts[i]);
				}
			}
		}
		if (!checkReferences()) {
			terminateFormula(parts);
		}
	}
	
	private void mult_div(String[] parts) {
		for (int i = 1; i < parts.length; i++) {
			//determines if any index of the array contains a multiplication or division operator
			if (parts[i].equals("*") || parts[i].equals("/")){
				
				if (parts[i].equals("*")) {
					try {
						// stores product of values in indices before and after i in temporary variable
						Double tempValue = Double.parseDouble(parts[i-1]) * Double.parseDouble(parts[i+1]);
						// swaps data
						parts[i-1] = "" + tempValue;
						
						// shifts data in array over for next cycle
						for (int k = i; k < parts.length - 2; k++) {
							parts[k] = parts[k + 2];
						}
						
						// clears data that was already processed and placed in the back of the array
						parts[parts.length - 1] = "";
						parts[parts.length - 2] = "";
						
						i = 0;
					} catch (NumberFormatException division) {
						// in case one part of the expression was not a number
						terminateFormula(parts);
					}
				} else {
					try {
						// stores quotient of values in indices before and after i in temporary variable
						Double tempValue = Double.parseDouble(parts[i-1]) / Double.parseDouble(parts[i+1]);
						//swaps data
						parts[i-1] = "" + tempValue;
						
						// shifts data in array over for next cycle
						for (int k = i; k < parts.length - 3; k++) {
							parts[k] = parts[k + 2];
						}
						
						// clears data that was already processed and placed in the back of the array
						parts[parts.length - 1] = "";
						parts[parts.length - 2] = "";
						
						i = 0;
					} catch (NumberFormatException division) {
						// in case one part of the expression was not a number
						terminateFormula(parts);
					}
					
				}
				
			}
			
		}
	}
	
	private  void add_sub(String[] parts) {
	
		for (int i = 1; i < parts.length; i++) {
			//determines if any index of the array contains an addition or subtraction operator
			if (parts[i].equals("+") || (parts[i].equals("-") && parts[i].length() == 1)){
				if (parts[i].equals("+")) {
					try {
					// stores sum of values in indices before and after i in temporary variable
					Double tempValue = Double.parseDouble(parts[i-1]) + Double.parseDouble(parts[i+1]);
					// swaps data
					parts[i-1] = "" + tempValue;
					
					// shifts data in array over for next cycle
					for (int k = i; k < parts.length - 2; k++) {
						parts[k] = parts[k + 2];
					}
					
					// clears data that was already processed and placed in the back of the array
					parts[parts.length - 1] = "";
					parts[parts.length - 2] = "";
					
					i = 0;
					} catch (NumberFormatException plus) {
						// in case one part of the expression was not a number
						terminateFormula(parts);
					}
					
				} else {
					try{
						// stores difference of values in indices before and after i in temporary variable
						Double tempValue = Double.parseDouble(parts[i-1]) - Double.parseDouble(parts[i+1]);
						// swaps data
						parts[i-1] = "" + tempValue;
						
						// shifts data in array over for next cycle
						for (int k = i; k < parts.length - 3; k++) {
							parts[k] = parts[k + 2];
					}
						
						// clears data that was already processed and placed in the back of the array
						parts[parts.length - 1] = "";
						parts[parts.length - 2] = "";
						
						i = 0;
					} catch (NumberFormatException minus) {
						// in case one part of the expression was not a number
						terminateFormula(parts);
					}
				}
				
			}
			
		}
	}
	
	private String sum(String sum){
		String range = sum.substring(sum.indexOf("(" ) + 1, sum.lastIndexOf(")"));
		
		// gets dimensions of box to sort from range
		int boxWidth = range.charAt(range.indexOf('-') + 1) - range.charAt(0) + 1;
		int boxHeight = Integer.parseInt(range.substring(range.indexOf('-') + 2))
		- Integer.parseInt(range.substring(1, range.indexOf('-'))) + 1;

		/* Determine where the upper left Cell of box
		 * is relative to A1 
		 */
		int startingHorizontalDisplacement = range.charAt(0) - 65;
		int startingVerticalDisplacement = Integer.parseInt(range.substring(1, range.indexOf('-'))) - 1;
		
		/* adds up all the numbers from 
		 * each cell in box as well as 
		 * counts the number of cells
		 */
		double total = 0;
		int numOfCells = 0;
		for (int i = 0; i < boxHeight; i++) {
			for (int k = 0; k < boxWidth; k++) {
				int row = startingVerticalDisplacement+ i;
				int column = startingHorizontalDisplacement + k;
				total += CellData.spreadSheet[row][column].number;
				numOfCells++;
				
				String Address = (char) (column + 65) + "" + (char) (row + 49);
					references.add(Address);
				}
			}
		if (!checkReferences()) {
			return "";
		}
		// returns data and number of cells to be processed later
		return total + " " + numOfCells;
	}

	private String avg(String avg){
		String toBeSplit = sum(avg);
		if (toBeSplit.length() != 0){
			// uses sum method to get data
			String[] sumData = toBeSplit.split(" ");
		
			/* divides sum of cells by the number 
			 * of cells based on returned data from sum
			 */
			return (Double.parseDouble(sumData[0]) / Double.parseDouble(sumData[1])) + "";
		} else {
			return "";
		}
		
	}
	
	public void terminateFormula(String[] parts) {
		// makes array accomplish nothing and displays error message
		parts[0] = "";
		
		Main.Error_Message = "Formula couldn't be evaluated";
	}
}
