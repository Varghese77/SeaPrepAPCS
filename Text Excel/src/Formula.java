
public class Formula {

	public String originalFormula = "no_formula_given";
	
	public Formula(String s) {
		originalFormula = s;
	}
	
	public String toString() {
		// creates array of expression parts
		String[] parts;
		try {
			parts = originalFormula.split(" ");
		} catch (StringIndexOutOfBoundsException noInput) {
			parts = new String[1];
			parts[0] = "";
		}
		
		convertCellAddress(parts);
		
		mult_div(parts);
		
		add_sub(parts);
		
		return parts[0];
		
	}
	
	private static void convertCellAddress(String[] parts) {
		for (int i = 0; i < parts.length; i++) {
			char firstChar;
			if (parts[i].length() == 0) {
			firstChar = 'a';
			} else {
				firstChar = parts[i].charAt(0);
			}
			if (parts[i].toUpperCase().indexOf("SUM") == -1 && parts[i].toUpperCase().indexOf("AVG") == -1) {
				if ((firstChar >= 65 && firstChar <= 90) && parts[i].length() == 2) {
					int column = firstChar - 65;
					int row = parts[i].charAt(1) - 49;
				
					try {
					String tempStringHolder = CellData.spreadSheet[row][column].toString();
					parts[i] = tempStringHolder;
					Double.parseDouble(parts[i]);
					} catch (Exception e) { // ArrayIndexOutOfBoundsException or stackOverflowError
					terminateFormula(parts);
					}
				}
			} else {
				if (parts[i].toUpperCase().indexOf("SUM") != -1) {
					String tempStringHolder = sum(parts[i]);
					parts[i] = tempStringHolder.substring(0, tempStringHolder.indexOf(" "));
				} else {
					parts[i] = avg(parts[i]);
				}
			}
		}
	}
	
	private static void mult_div(String[] parts) {
		for (int i = 1; i < parts.length; i++) {
			if (parts[i].equals("*") || parts[i].equals("/")){
				if (parts[i].equals("*")) {
					try {
						Double tempValue = Double.parseDouble(parts[i-1]) * Double.parseDouble(parts[i+1]);
						parts[i-1] = "" + tempValue;
						for (int k = i; k < parts.length - 3; k++) {
							parts[k] = parts[k + 2];
						}
						parts[parts.length - 1] = "";
						parts[parts.length - 2] = "";
					} catch (NumberFormatException division) {
						terminateFormula(parts);
					}
				} else {
					try {
						Double tempValue = Double.parseDouble(parts[i-1]) / Double.parseDouble(parts[i+1]);
						parts[i-1] = "" + tempValue;
						for (int k = i; k < parts.length - 3; k++) {
							parts[k] = parts[k + 2];
						}
						parts[parts.length - 1] = "";
						parts[parts.length - 2] = "";
					} catch (NumberFormatException division) {
						terminateFormula(parts);
					}
					
				}
				
			}
			
		}
	}
	
	private static void add_sub(String[] parts) {
	
		for (int i = 1; i < parts.length; i++) {
			if (parts[i].equals("+") || (parts[i].equals("-") && parts[i].length() == 1)){
				if (parts[i].equals("+")) {
					try {
					Double tempValue = Double.parseDouble(parts[i-1]) + Double.parseDouble(parts[i+1]);
					parts[i-1] = "" + tempValue;
					
					for (int k = i; k < parts.length - 3; k++) {
						parts[k] = parts[k + 2];
					}
					parts[parts.length - 1] = "";
					parts[parts.length - 2] = "";
					} catch (NumberFormatException plus) {
						terminateFormula(parts);
					}
					
				} else {
					try{
						Double tempValue = Double.parseDouble(parts[i-1]) - Double.parseDouble(parts[i+1]);
						parts[i-1] = "" + tempValue;
						for (int k = i; k < parts.length - 3; k++) {
							parts[k] = parts[k + 2];
					}
						parts[parts.length - 1] = "";
						parts[parts.length - 2] = "";
					} catch (NumberFormatException minus) {
						terminateFormula(parts);
					}
				}
				
			}
			
		}
	}
	
	private static String sum(String sum){
		String range = sum.substring(sum.indexOf("(" ) + 1, sum.lastIndexOf(")"));
		
		// gets dimensions of box to sort from range
		// FIX THIS FOR DOUBLE DIGITS
		int rangeWidth = range.charAt(range.indexOf('-') + 1) - range.charAt(0) + 1;
		int rangeHeight = Integer.parseInt(range.substring(range.indexOf('-') + 2))
		- Integer.parseInt(range.substring(1, range.indexOf('-'))) + 1;

		/* Determine where the upper left Cell of box
		 * is relative to A1 
		 */
		int startingHorizontalDisplacement = range.charAt(0) - 65;
		int startingVerticalDisplacement = Integer.parseInt(range.substring(1, range.indexOf('-'))) - 1;
		
		double total = 0;
		int numOfCells = 0;
		for (int i = 0; i < rangeHeight; i++) {
			for (int k = 0; k < rangeWidth; k++) {
				total += CellData.spreadSheet[startingVerticalDisplacement+ i][startingHorizontalDisplacement + k].number;
				numOfCells++;
				}
			}
		return total + " " + numOfCells;
	}

	private static String avg(String avg){
		String[] sumData = sum(avg).split(" ");
		return (Double.parseDouble(sumData[0]) / Double.parseDouble(sumData[1])) + "";
		
	}
	
	public static void terminateFormula(String[] parts) {
		Main.Error_Message = "Error: One part(s) of the formula isn't a number";
		parts[0] = "";
	}
}
