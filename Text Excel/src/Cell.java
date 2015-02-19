
public class Cell {
	
	//public fields
	public String address = "";
	
	public String text = "       ";
	
	public double number;
	
	public Date date;
	
	public Formula formula; 
	
	/*determines what data to display
	 * 1 = String (default)
	 * 2 = real number
	 * 3 = Date
	 * 4 = Formula
	 */
	
	public int displayContent = 1;
	
	//constructor 
	public Cell(String s, int displayType) { 
		if (displayType == 1) {
			text = s.substring(1, s.length()-1);
			displayContent = 1;
		} else if (displayType == 2) {
			number = Double.parseDouble(s);
			displayContent = 2;
		} else {
			date = new Date(s);
			displayContent = 3;
		}
	}
	
	// allows user to change Cell Data and display type
	public void setCell(String s, int displayType) { 
		if (displayType == 1) {
			text = s.substring(1, s.length()-1);
			displayContent = 1;
		} else if (displayType == 2) {
			number = Double.parseDouble(s);
			displayContent = 2;
		} else if (displayType == 3){
			date = new Date(s);
			displayContent = 3;
		} else {
			// Created bundle of information to be processed in Formula
			String[] cellMeta = {address, s, displayInternalContent()};
			formula = new Formula(cellMeta, displayContent);
			//displayContent gets set in formula object
		}
	}
	
	public String toString() {
		if (displayContent == 1) {
			return text;
		} else if (displayContent == 2) {
			return number + "";
		} else if (displayContent == 3) {
			return date.toString();
		} else {
			return formula.toString();
		}
	}

	// For cells to show original Formulas
	public String displayInternalContent() {
		if (displayContent == 1) {
			return text;
		} else if (displayContent == 2) {
			return number + "";
		} else if (displayContent == 3){
			return date.toString();
		} else {
			return formula.originalFormula;
		}
	}
}
