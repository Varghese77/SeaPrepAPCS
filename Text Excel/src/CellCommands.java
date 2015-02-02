import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.PrintStream;

public class CellCommands {

	// Sends command to specific method
	public static void determineCommand(String com) {
		
		if (com.toUpperCase().indexOf("CLEAR") != -1) {
			clearCell(com.toUpperCase());
		} else if (com.toUpperCase().indexOf("SORT") != -1) {
			sort(com.toUpperCase());
		} else if (com.toUpperCase().indexOf("IMPORT") != -1) {
			int startidx = com.toUpperCase().indexOf("IMPORT") + 7;
			String location = com.substring(startidx).trim();
			importFile(location);
		} else if (com.toUpperCase().indexOf("EXPORT") != -1) {
			int startidx = com.indexOf("EXPORT") + 7;
			String location = com.substring(startidx).trim();
			exportFile(location);
		} else {
			// must send normal command with unmodified data
			setCellCommand(com);
		}
	}

	public static void clearCell(String com) {
		if (com.indexOf(' ') == -1) {
			// clears entire spreadsheet
			for (int i = 0; i < PrintCells.rows; i++) {
				for (int k = 0; k < PrintCells.columns; k++) {
					CellData.spreadSheet[i][k] = new Cell("       ", 1);
				}
			}
		} else {
			// extracts address from characters after "clear"
			int addressIndex = com.indexOf("CLEAR") + 5;
			String address = com.substring(addressIndex).trim();

			// parses address
			int row = address.charAt(0) - 65;
			int column = address.charAt(1) - 49;
			
			// clears specific cell
			CellData.spreadSheet[column][row] = new Cell("       ", 1);
		}

	}
	
	public static void sort(String com) {
		// determines range of box to sort numbers
		String range = com.substring(com.indexOf("SORT") + 5).trim();
		
		// gets dimensions of box to sort from range
		int rangeWidth = range.charAt(range.indexOf('-') + 1) - range.charAt(0) + 1;
		int rangeHeight = Integer.parseInt(range.substring(range.indexOf('-') + 2))
		- Integer.parseInt(range.substring(1, range.indexOf('-'))) + 1;
		
		//creates a temporary array to hold integers before sorting
		double[] tempValues = new double[rangeWidth * rangeHeight];

		/* Determine where the upper left Cell of box
		* is relative to A1 
		*/
		int startingHorizontalDisplacement = range.charAt(0) - 65;
		int startingVerticalDisplacement = Integer
				.parseInt(range.substring(1, range.indexOf('-'))) - 1;
		
		// adds numbers from Cell box to temporary array
		int tempidx = 0;
		for (int i = 0; i < rangeHeight; i++) {
			for (int k = 0; k < rangeWidth; k++) {
				tempValues[tempidx] = CellData.spreadSheet[startingVerticalDisplacement+ i][startingHorizontalDisplacement + k].number;
				tempidx++;
			}
		}

		// Combines various numbers to recreate data accessing loops
		int[] metaData = {rangeWidth, rangeHeight, startingHorizontalDisplacement, startingVerticalDisplacement};
		
		// Continues only if all Cells in box display a double
		if (areAllDoubles(metaData)) {
			// Determines whether to sort in ascending or descending order
			char order = com.charAt(com.indexOf("SORT") + 4);
			
			// sorts data in temporary array
			if (order == 'A') {
				arrangeAscending(tempValues);
			} else {
				arrangeDescending(tempValues);
			}
			
			// rearranges doubles in Cell box in desired order
			tempidx = 0;
			for (int i = 0; i < rangeHeight; i++) {
				for (int k = 0; k < rangeWidth; k++) {
					CellData.spreadSheet[startingVerticalDisplacement + i][startingHorizontalDisplacement + k].number = tempValues[tempidx];
					tempidx++;
				}
			}
		} else {
			// Prints if some values in box were not doubles
			System.out.println("Not all values in range were doubles");
		}
	}

	public static boolean areAllDoubles (int[] metaData) {
		boolean isTrue = true;
		/* If any Cell in box doesn't display a number
		 * the method will return false
		 */
		 
		for (int i = 0; i < metaData[1]; i++) {
			for (int k = 0; k < metaData[0]; k++) {
				int displayContent = CellData.spreadSheet[metaData[3] + i][metaData[2] + k].displayContent;
				if (displayContent != 2) {
					isTrue = false;
				}
			}
		}
		return isTrue;
	}
	
	public static void arrangeAscending(double[] tempValues) {
		//
		for (int i = 1; i < tempValues.length; i++) {
			int k = i;
			// Compares a value with value of last index
			while (tempValues[k] < tempValues[k - 1]) {
				// puts value in temp placeholder before swapping
				double tempDouble1 = tempValues[k];
				double tempDouble2 = tempValues[k - 1];

				tempValues[k] = tempDouble2;
				tempValues[k - 1] = tempDouble1;

				if (k >= 2)
					k--;
			}
		}
	}
	
	public static void arrangeDescending(double[] tempValues) {
		for (int i = 1; i < tempValues.length; i++) {
			int k = i;
			// Compares a value with value of last index
			while (tempValues[k] > tempValues[k - 1]) {
				// puts value in temp placeholder before swapping
				double tempDouble1 = tempValues[k];
				double tempDouble2 = tempValues[k - 1];

				tempValues[k] = tempDouble2;
				tempValues[k - 1] = tempDouble1;

				if (k >= 2)
					k--;
			}
		}
	}
	
	public static void exportFile(String location){
		// creates file in user specified location
		File f = new File(location);
		PrintStream p = null;
		try {
			p = new PrintStream(f);
			// Prints dimensions of Cell array
			p.println(PrintCells.rows + "X" + PrintCells.columns);
			
			//Prints Cell address, data, and display type to be retrieved upon import
			for (int i = 0; i < PrintCells.rows; i++) {
				for (int k = 0; k < PrintCells.columns; k++) {
					p.print(CellData.spreadSheet[i][k].address + ":"); // + CellData.spreadSheet[i][k].toString());
					if (CellData.spreadSheet[i][k].displayContent == 4) {
						p.print(CellData.spreadSheet[i][k].formula.originalFormula);
					} else {
						p.print(CellData.spreadSheet[i][k].toString());
					}
					p.println("," + CellData.spreadSheet[i][k].displayContent);
				}
			}
			p.close();
		} catch (Exception e) {
			// If location is invalid
			Main.Error_Message = "Can't export file, location is invalid";
		} 

	}

	public static void importFile(String location){
		File f = new File(location);
		Scanner s;
		try {
			s = new Scanner(f);
			String dimensions = s.nextLine();
			// Sends dimensions to create new Cell Array
			Main.spreadSheetMeta(dimensions);
			
			// Converts each line into specified data in each Cell of spreadSheet
			while (s.hasNextLine()) {
				String lineConversion = s.nextLine();
				// determines address of Cell
				boolean isValid = true;
				
				if (lineConversion.length() < 5) {
					isValid = false;
				} else if (lineConversion.indexOf(':') != 2) {
					isValid = false;
				}
				
				if (isValid) {
					int column = lineConversion.charAt(0) - 65;
					int row = Integer.parseInt(lineConversion.substring(1, lineConversion.indexOf(':')))-1;
				
					// creates variables of data to be sent to setCell method in Cell object later
					String data = lineConversion.substring(lineConversion.indexOf(':') + 1, lineConversion.indexOf(','));
				
					int dataType = Integer.parseInt(lineConversion.substring(lineConversion.lastIndexOf(',') + 1).trim());
					if (dataType >=1 && dataType <= 4) {
						CellData.spreadSheet[row][column].setCell(data, dataType);
					} else {
						System.out.println("ERROR: Cell is corrupted, Program will malfunction");
					}
				} else {
					System.out.println("ERROR:Data is Corrupt, Program will malfunction");
				}
			}
		} catch (FileNotFoundException e) {
			// if file can't be found
			Main.Error_Message = "file not found";
		}
	}
	
	public static void autoLoad(String location) {
		File f = new File(location);
		Scanner s;
		try {
			s = new Scanner(f);
			while (s.hasNextLine()) {
				String Command = s.nextLine();
				System.out.println("Command:" + Command);
				Main.commandProcessing(Command);
				System.out.println();
			}
		} catch (FileNotFoundException e) {
			// if file can't be found
			Main.Error_Message = "file not found";
		}
	}
	
	public static void setCellCommand(String com) {
		try {
			// Determines address of Cell
			int row = com.charAt(0) - 65;
			int column = com.charAt(1) - 49;

			// Determines data to send to specific Cell
			String data = com.substring(com.indexOf("=") + 1).trim();
			int displayType = inputDataType(data);
		
			// Asigns data and displayType
			CellData.spreadSheet[column][row].setCell(data, displayType);
		} catch (Exception e){
			Main.Error_Message = "The set cell input format was wrong, \nrefer to the README.txt";
		}
	}

	public static int inputDataType(String data) {
		if (isString(data)) {
			return 1;
		} else if (isDouble(data)) {
			return 2;
		} else if (isDate(data)) {
			return 3;
		} else {
			return 4;
		}
	}

	public static boolean isString(String s) {
		if (s.length() < 2) {
			return false;
		}

		if (s.charAt(0) == '"' && s.charAt(s.length() - 1) == '"') {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isDouble(String s) {
		try {
			Double.parseDouble(s);
		} catch (NumberFormatException e) {
			return false;
		}

		return true;
	}

	public static boolean isDate(String s) {

		if (s.indexOf('/') == -1) {
			return false;
		}
		String[] parts = s.split("/");

		if (parts.length != 3) {
			return false;
		}
		for (int i = 0; i <= 2; i++) {
			String part = parts[i];
			int partLength = part.length();
			for (int k = 0; k <= partLength - 1; k++) {
				if (part.charAt(k) < '1' && part.charAt(k) > '0') {
					return false;
				}
			}
		}
		return true;
	}
}

