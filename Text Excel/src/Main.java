import java.util.Scanner;

public class Main {

	// Error message String to be printed after each cycle
	public static String Error_Message = "";

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);

		// Creates Cell array based on user input
		handleDimensions();

		// Determines if program should auto-load commands
		// from an imported document.
		CellData.spreadSheetCopy = CellData.spreadSheet.clone();
		System.out
				.println("Do you want to autoload commands from a document? Enter yes or no.");
		String auto = scan.nextLine();
		if (auto.toUpperCase().equals("YES")) {
			System.out.print("File Location:");
			String location = scan.nextLine();
			CellCommands.autoLoad(location);
		} else if (auto.toUpperCase().equals("NO")) {
			// if user enters "No" then code just moves on
		} else {
			// if input is neither "Yes" or "No", code moves on
			System.out.println("Input unreadable, program will continue like normal.");
		}

		// prints table
		PrintCells.printData();

		// initializes command variable for user commands
		String command;

		// runs main loop of program until user enters quit
		do {

			printErrorMessage();
			
			// Command prompt for user input
			System.out.print("Visicalc> ");

			// Retrieves command
			command = scan.nextLine().trim();
			System.out.println();
			if (!command.equals("quit")) {
				
				//sends command to be processed
				commandProcessing(command);
			}

		} while (!command.toLowerCase().equals("quit"));
		System.out.println("Thank you for using Text Excel!");
		scan.close();

	}

	public static void handleDimensions() {
		Scanner getDimensions = new Scanner(System.in);
		
		try {
			System.out.println("Enter the dimensions of the spreadsheet separated by \'X\'");
			
			/* sends user inputed dimensions to method
			to create and process Cell object */
			String dimensions = getDimensions.nextLine().trim();
			spreadSheetMeta(dimensions);
		} catch (Exception e) {
			// Method recalls itself if user doesn't follow dimensions format
			System.out.println("Dimensions format not correct, remember to use an uppercase 'X'.\n");
			Main.handleDimensions();
		}
	}

	public static void commandProcessing(String command) {
		
		/* This is its own method so that other methods
		 *than main can process central commands (like autoload)
		 */
		
		CellCommands.determineCommand(command);
		PrintCells.printData();
	}

	public static void spreadSheetMeta(String dimensions) {

		// converts user dimensions to spreadsheet dimensions
		PrintCells.rows = Integer.parseInt(dimensions.substring(0,dimensions.indexOf('X')));
		PrintCells.columns = Integer.parseInt(dimensions.substring(dimensions.indexOf('X') + 1));

		// creates Array with dimensions
		CellData.spreadSheet = new Cell[PrintCells.rows][PrintCells.columns];

		// sets String field in cell array to not be null
		for (int i = 0; i <= PrintCells.rows - 1; i++) {
			for (int k = 0; k <= PrintCells.columns - 1; k++) {
				CellData.spreadSheet[i][k] = new Cell("       ", 1);
				String addressLetter = (char) (k + 'A') + "";
				int addressNumber = (i + 1);
				// sets cell address field by location
				CellData.spreadSheet[i][k].address = addressLetter
						+ addressNumber;
			}
		}
	}
	
	public static void printErrorMessage() {
		// prints error message if there is one
		if (!Error_Message.equals("")) {
			System.out.print("ERROR: ");
			System.out.println(Error_Message);
		}
		// makes error message blank for next cycle
		Error_Message = "";
	}
}
