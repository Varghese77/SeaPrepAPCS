
public class Formula {

	public String originalFormula = "no_formula_given";
	
	public Formula(String s) {
		originalFormula = s;
	}
	
	public String toString() {
		String[] parts;
		try {
			parts = originalFormula.split(" ");
		} catch (StringIndexOutOfBoundsException noInput) {
			parts = new String[1];
			parts[0] = "";
		}
		
		for (int i = 0; i < parts.length; i++) {
			char firstChar;
			if (parts[i].length() == 0) {
			firstChar = 'a';
			} else {
				firstChar = parts[i].charAt(0);
			}
			if ((firstChar >= 65 && firstChar <= 90) && parts[i].length() > 1) {
				int column = firstChar - 65;
				int row = parts[i].charAt(1) - 49;
				
				try {
				String tempStringHolder = CellData.spreadSheet[row][column].toString();
				parts[i] = tempStringHolder;
				} catch (ArrayIndexOutOfBoundsException e) {
					terminateFormula(parts);
				}
			}
			
			try {
				Double.parseDouble(parts[i]);
			} catch (NumberFormatException e) {
				terminateFormula(parts);
			}
			
		}
		
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
		return parts[0];
		
}
	public static void terminateFormula(String[] parts) {
		Main.Error_Message = "Error: One part(s) of the formula isn't a number";
		parts[0] = "";
	}
}
