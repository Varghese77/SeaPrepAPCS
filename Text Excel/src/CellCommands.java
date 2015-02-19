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
		} else  if (com.indexOf("=") != -1){
			// Sends normal command with unmodified data
			setCellCommand(com, true);
		} else if (com.toUpperCase().indexOf("CELL ") != -1){
			com = com.substring(com.toUpperCase().indexOf("CELL ") + 4).trim();
			showCell(com);
		} else if (com.toUpperCase().indexOf("NEW DIMENSIONS") != -1) {
			com = com.substring(com.toUpperCase().indexOf("NEW DIMENSIONS") + 14).trim();
			createNewArray(com);
		} else {	
			Main.Error_Message = "Unable to determine command";
		}
	}

	public static boolean checkRange(String upperLeft, String lowerRight) {
		int tableLength = CellData.spreadSheet.length;
		int tableWidth = CellData.spreadSheet[0].length;
		
		try {
			int upperLeftColumn = upperLeft.charAt(0) - 64;
			int upperLeftRow = Integer.parseInt(upperLeft.substring(1));
		
			if (upperLeftColumn > tableLength || upperLeftRow > tableWidth){
				return false;
			}
		
			int lowerRightColumn = lowerRight.charAt(0) - 64;
			int lowerRightRow = Integer.parseInt(lowerRight.substring(1));
		
			if (lowerRightColumn > tableLength || lowerRightRow > tableWidth){
				return false;
			}
		
			if (upperLeftRow > lowerRightRow || upperLeftColumn > lowerRightColumn){
				return false;
			}
			return true;
		} catch (NumberFormatException inValidRange) {
			return false;
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
			try {
				// extracts address from characters after "clear"
				int addressIndex = com.indexOf("CLEAR") + 5;
				String address = com.substring(addressIndex).trim();

				// parses address
				int row = address.charAt(0) - 65;
				int column = address.charAt(1) - 49;
			
				// terminates program if address is invalid
				if (!checkRange("A1", address)){
					StringIndexOutOfBoundsException e = new StringIndexOutOfBoundsException();
					throw e;
				}
				
				//test to see if any formula is dependent on this cell
				int tableColumn = CellData.spreadSheet.length;
				int tableRow = CellData.spreadSheet[0].length;
				
				boolean refered = true;
				
				for (int i = 0; i < tableColumn; i++){
					for (int k = 0; k < tableRow; k++){
						if (CellData.spreadSheet[i][k].displayContent == 4 ){
							if (!CellData.spreadSheet[i][k].formula.check(address)) {
								refered = false;
							}
						}
					}
				}
				
				if (!refered){
					CellData.spreadSheet[column][row] = new Cell("0.0", 2);
					Main.Error_Message = "Cell Defaulted to 0 because \nit is used in a formula";
					return;
				}
				
				// clears specific cell
				CellData.spreadSheet[column][row] = new Cell("       ", 1);
			} catch (StringIndexOutOfBoundsException e) {
				Main.Error_Message = "Unable to determine command";
			}
		}

	}
	
	public static void sort(String com) {
		try {
		// determines range of box to sort numbers
		String range = com.substring(com.indexOf("SORT") + 5).trim();
		
		String upperLeft = range.substring(0, range.indexOf('-'));
		String lowerRight = range.substring(range.indexOf('-') + 1);
		
		// checks range to see it method needs to terminate
		if (!checkRange(upperLeft, lowerRight)){
			Main.Error_Message = "Range is invalid!";
			return;
		}
		
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
			} else if (order == 'D') {
				arrangeDescending(tempValues);
			} else {
				Exception e = new Exception();
				throw e;
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
		} catch (Exception e) {
			Main.Error_Message = "sort command does not compute \nrefere to the README.txt";
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

	public static void importFile(String location) {
		File f = new File(location);
		Scanner s;
		Scanner s2;
		try {

			s = new Scanner(f);
			boolean isValid = checkImportedFile(s);

			s2 = new Scanner(f);
			if (isValid) {
				// Sends dimensions to create new Cell Array
				String dimensions = s2.nextLine();
				Main.spreadSheetMeta(dimensions);

				while (s2.hasNextLine()) {
					String lineConversion = s2.nextLine();

					int column = lineConversion.charAt(0) - 65;
					int row = Integer.parseInt(lineConversion.substring(1,
							lineConversion.indexOf(':'))) - 1;

					// creates variables of data to be sent to setCell method in
					// Cell object later
					String data = lineConversion.substring(
							lineConversion.indexOf(':') + 1,
							lineConversion.indexOf(','));

					int dataType = Integer.parseInt(lineConversion.substring(
							lineConversion.lastIndexOf(',') + 1).trim());
					CellData.spreadSheet[row][column].setCell(data, dataType);

				}
			} else {
				Main.Error_Message = "Data is Corrupt, Can't Import";
			}
		} catch (FileNotFoundException e) {
			// if file can't be found
			Main.Error_Message = "file not found";
		}
	}

	public static boolean checkImportedFile(Scanner s) {
		boolean isValid = true;
		int range[] = new int[2];
		try {
			String dimensions = s.nextLine();

			int row = Integer.parseInt(dimensions.substring(0, dimensions.indexOf('X')));
			int column = Integer.parseInt(dimensions.substring(dimensions.indexOf('X') + 1));
			
			int numTest = row * column;
			if (numTest <= 0) {
				Exception exception = new Exception(); 
				throw exception;
			}
			range[0] = row;
			range[1] = column;
			

		} catch (Exception e) {
			isValid = false;
		}

		try {
			// Converts each line into specified data in each Cell of
			// spreadSheet
			while (s.hasNextLine()) {
				String lineToCheck = s.nextLine();
				// determines address of Cell
				int dataType = Integer.parseInt(lineToCheck.substring(
						lineToCheck.lastIndexOf(',') + 1).trim());
				int column = lineToCheck.charAt(0) - 65;
				int row = Integer.parseInt(lineToCheck.substring(1,
						lineToCheck.indexOf(':'))) - 1;

				if (lineToCheck.length() < 5) {
					isValid = false;
				} else if (lineToCheck.indexOf(':') != 2
						&& lineToCheck.indexOf(':') != 3) {
					isValid = false;
				} else if (dataType < 1 || dataType > 4) {
					isValid = false;
				} else if (!testAddress(row, column, range)) {
					isValid = false;
				}
			}

		} catch (Exception e) {
			isValid = false;
		}

		return isValid;

	}

	public static boolean testAddress(int row, int column, int[] range) {
		// tests if an address in a range is true
		boolean isTrue = true;
		if (row > range[0] || column > range[1]) {
			isTrue = false;
		}
		return isTrue;

	}
	
	public static void autoLoad(String location) {
		File f = new File(location);
		Scanner s;
		try {
			s = new Scanner(f);
			while (s.hasNextLine()) {
				String Command = s.nextLine().trim();
				
				for (int i = 0; i < Command.length(); i++){
					int idx = Command.charAt(i);
					if (idx == 8220 || idx == 8221) {
						String tempString1 = Command.substring(0, i);
						String tempString2 = Command.substring(i + 1);
						Command = tempString1 + "\"" + tempString2;
					}
				}
				
				System.out.println("Command <" + Command + ">");
				Main.commandProcessing(Command);
				Main.printErrorMessage();
				System.out.println();
			}
			
			System.out.println("Current Table!");
		} catch (FileNotFoundException e) {
			// if file can't be found
			Main.Error_Message = "file not found";
		}
	}
	
	public static void setCellCommand(String com, boolean central) {
		try {
			// Determines address of Cell
			int row = com.charAt(0) - 65;
			int column = com.charAt(1) - 49;

			// Determines data to send to specific Cell
			String data = com.substring(com.indexOf("=") + 1).trim();
			int displayType = inputDataType(data);
		
			// Assigns data and displayType
			if (displayType != 0) {
				if (central){
					CellData.spreadSheet[column][row].setCell(data, displayType);
				} else {
					CellData.spreadSheetCopy[column][row].setCell(data, displayType);
				}
			} else {
				throw new Exception();
			}	
		} catch (Exception invalidCommand){
			Main.Error_Message = "The set cell input format was wrong, \nrefer to the README.txt";
		}
	}

	public static void showCell(String Address) {
		try {
			int column = Address.charAt(0) - 65;
			int row = Address.charAt(1) - 49;
		
			System.out.println("Cell " + Address + ": " + CellData.spreadSheet[row][column].displayInternalContent());
		} catch (ArrayIndexOutOfBoundsException e){
			Main.Error_Message = "Can't determine which cell(s) to show";
		}
	}
	
	public static int inputDataType(String data) {
		if (isString(data)) {
			return 1;
		} else if (isDouble(data)) {
			return 2;
		} else if (isDate(data)) {
			return 3;
		} else if (isFormula(data)){
			return 4;
		} else {
			return 0;
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
			
			if (partLength > 2) {
				return false;
			}
			
			for (int k = 0; k <= partLength - 1; k++) {
				if (part.charAt(k) < '0' || part.charAt(k) > '9') {
					return false;
				}
			}
		}
		return true;
	}

	public static boolean isFormula(String s){
		if (s.length() < 2) {
			return false;
		}

		if (s.charAt(0) == '(' && s.charAt(s.length() - 1) == ')') {
			return true;
		} else {
			return false;
		}
	}

	public static void createNewArray(String s){
		int row = 0;
		int column = 0;
		try {
			column = Integer.parseInt(s.substring(0, s.indexOf('X')));
			row = Integer.parseInt(s.substring(s.indexOf('X') + 1));
		} catch (Exception e){
			Main.Error_Message = "Could not determine dimensions of new table!";
			return;
		}
		
		CellData.spreadSheetCopy = new Cell[row][column];
		
		
		// sets String field in cell array to not be null
		for (int i = 0; i <= row - 1; i++) {
			for (int k = 0; k <= column - 1; k++) {
				CellData.spreadSheetCopy[i][k] = new Cell("       ", 1);
				String addressLetter = (char) (k + 'A') + "";
				int addressNumber = (i + 1);
				
				// sets cell address field by location
				CellData.spreadSheetCopy[i][k].address = addressLetter
						+ addressNumber;
				
					}
				}
		
		int tempRow;
		if (row > CellData.spreadSheet.length) {
			tempRow = CellData.spreadSheet.length;
		} else {
			tempRow = row;
		}

		int tempColumn;
		if (column > CellData.spreadSheet[0].length) {
			tempColumn = CellData.spreadSheet[0].length;
		} else {
			tempColumn = column;
		}

		for (int i = 0; i <= tempRow - 1; i++) {
			for (int k = 0; k <= tempColumn - 1; k++) {
				char letter = (char) (k + 65);
				char num = (char) (i + 49);
				String holder = letter + "" + num + " = ";
				
				if (CellData.spreadSheet[i][k].displayContent == 1) {
					holder += "\"" +  CellData.spreadSheet[i][k].text + "\"";
				} else {
					holder += CellData.spreadSheet[i][k].toString();
				}
				setCellCommand(holder, false);
			}
		}
		
		CellData.spreadSheet = CellData.spreadSheetCopy;
		PrintCells.rows = row;
		PrintCells.columns = column;
	}
}