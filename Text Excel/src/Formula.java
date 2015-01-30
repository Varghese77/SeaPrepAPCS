
public class Formula {

	public String originalFormula = "no_formula_given";
	
	public Formula(String s) {
		originalFormula = s;
	}
	
	public String toString() {
		
		String[] parts = originalFormula.split(" ");
		
		for (int i = 0; i < parts.length; i++) {
			
			char firstChar = parts[i].charAt(0);
			
			if (firstChar >= 65 && firstChar <= 90) {
				int column = firstChar - 65;
				int row = parts[i].charAt(1) - 49;
				
				String tempStringHolder = CellData.spreadSheet[row][column].toString();
				
				parts[i] = tempStringHolder;
			}
			
		}
		
		for (int i = 1; i < parts.length; i++) {
			if (parts[i].equals("*") || parts[i].equals("/")){
				if (parts[i].equals("*")) {
					
					Double tempValue = Double.parseDouble(parts[i-1]) * Double.parseDouble(parts[i+1]);
					parts[i-1] = "" + tempValue;
					for (int k = i; k < parts.length - 3; k++) {
						parts[k] = parts[k + 2];
					}
					parts[parts.length - 1] = "";
					parts[parts.length - 2] = "";
					
				} else {
					
					Double tempValue = Double.parseDouble(parts[i-1]) / Double.parseDouble(parts[i+1]);
					parts[i-1] = "" + tempValue;
					for (int k = i; k < parts.length - 3; k++) {
						parts[k] = parts[k + 2];
					}
					parts[parts.length - 1] = "";
					parts[parts.length - 2] = "";
				}
				
			}
			
		}
		
		for (int i = 1; i < parts.length; i++) {
			if (parts[i].equals("+") || (parts[i].equals("-") && parts[i].length() == 1)){
				if (parts[i].equals("+")) {
					
					Double tempValue = Double.parseDouble(parts[i-1]) + Double.parseDouble(parts[i+1]);
					parts[i-1] = "" + tempValue;
					
					for (int k = i; k < parts.length - 3; k++) {
						parts[k] = parts[k + 2];
					}
					parts[parts.length - 1] = "";
					parts[parts.length - 2] = "";
					
				} else {
					//System.out.println(parts[i-1]);
				//	System.out.println(parts[i+1]);
					Double tempValue = Double.parseDouble(parts[i-1]) - Double.parseDouble(parts[i+1]);
					parts[i-1] = "" + tempValue;
					for (int k = i; k < parts.length - 3; k++) {
						parts[k] = parts[k + 2];
					}
					parts[parts.length - 1] = "";
					parts[parts.length - 2] = "";
				}
				
			}
			
		}
		
		return parts[0];
		
}
}
