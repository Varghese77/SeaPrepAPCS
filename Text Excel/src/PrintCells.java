
public class PrintCells {

	// spreadsheet dimensions
	public static int rows;
	public static int columns; 
	
	// shortcut method
	public static void print(String s) {
		System.out.print(s);
	}
	
	// shortcut method
	public static void println(String s) {
		System.out.println(s);
	}
	
	// Prints entire table
	public static void printData() {
		printRowDivide();
		
		// prints letter markers
		print("|       ");
		for (int i = 1; i <= columns; i++) {
			print("|   " + (char) (i + 64) + "   ");
		}
		println("|");
		printRowDivide();
	
		//actual data
		for (int i = 1; i <= rows; i++) {
			// evenly aligns and puts together cells
			if (i < 10) {
				print("||  " + i + "  |");
				for (int k = 1; k <= columns; k++) {
					print(constructCell(i-1,k-1));
				}
			} else if (i < 100){
				print("|| " + i + "  |");
				for (int k = 1; k <= columns; k++) {
					print(constructCell(i-1,k-1));
				}
			} else {
				print("|| " + i + " |");
				for (int k = 1; k <= columns; k++) {
					print(constructCell(i-1,k-1));
				}
			}
			println("|");
			printRowDivide();
		}
	
	}
	
	//prints horizontal line separating rows of cells
	public static void printRowDivide() {
		for (int i = 1; i <= 8 * (columns + 1); i++) {
			print("-");
		}
		println("-");
	}
	
	public static String constructCell(int rowNum, int columnNum) {
		// converts data from cell to string
		String cellString = CellData.spreadSheet[rowNum][columnNum].toString();
		int stringLength = cellString.length();
		
		/* cuts off string if over 7 characters 
		 * and/or fills in spaces to make cell have 
		 * 7 characters total 
		 */
		if (stringLength == 7) {
			return "|" + cellString;
		} else if (stringLength < 7) {
			String cell = "|";
			for (int i = 1; i <= 7 - stringLength; i++) {
				cell += " ";
			}
			cell += cellString;
			return cell;
		} else {
			String cell = "|" + cellString.substring(0,6) + ">";
			return cell;
		}
	}
	
}
