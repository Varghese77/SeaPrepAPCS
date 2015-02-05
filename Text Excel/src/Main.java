import java.util.Scanner;


public class Main {
	
	// Error message String to be printed after each cycle
	public static String Error_Message = "";
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		
		// handleDimensions() 
		handleDimensions();
		
		System.out.println("Do you want to autoload commands from a document? Enter yes or no.");
		String auto = scan.nextLine();
		if (auto.toUpperCase().equals("YES")) {
			System.out.print("File Location:");
			String location = scan.nextLine();
			CellCommands.autoLoad(location);
		} else if (auto.toUpperCase().equals("NO")) {
			
		} else {
			System.out.println("Input unreadable, program will continue like normal.");
		}
		//initializes command variable
		PrintCells.printData();
		String command;
		do {
			if (!Error_Message.equals("")) {
				System.out.print("ERROR: ");
				System.out.println(Error_Message);
			}
			Error_Message = "";
			
			System.out.print("Visicalc> ");
			
			//asks for command
			command = scan.nextLine().trim();
			if (!command.equals("quit")) {
				commandProcessing(command);
			}

		} while (!command.equals("quit"));
		scan.close();
		
	}
	
	public static void handleDimensions () {
		try {
		Scanner getDimensions = new Scanner(System.in);
		System.out.println("Enter the dimensions of the spreadsheet separated by \'X\'");
		String dimensions = getDimensions.nextLine().trim();
		spreadSheetMeta(dimensions);
		} catch (Exception e) {
			System.out.println("Dimensions formot not correct, remember to use an uppercase 'X'.\n");
			Main.handleDimensions();
		}
	}
	
	public static void commandProcessing (String command) {
		CellCommands.determineCommand(command);
		PrintCells.printData();
	}
		
	
	public static void spreadSheetMeta(String dimensions) {
		
		//converts user dimensions to spreadsheet dimensions
		PrintCells.rows = Integer.parseInt(dimensions.substring(0,dimensions.indexOf('X')));
		PrintCells.columns = Integer.parseInt(dimensions.substring(dimensions.indexOf('X') + 1));
		
		//creates Array with dimensions
		CellData.spreadSheet = new Cell[PrintCells.rows][PrintCells.columns];
		
		//sets String field in cell array to not be null
		for (int i = 0; i <= PrintCells.rows-1; i++) {
			for (int k = 0; k <= PrintCells.columns-1; k++) {
				CellData.spreadSheet[i][k] = new Cell("       ", 1);
				String addressLetter = (char) (k + 'A') + "";
				int addressNumber = (i + 1);
				//sets cell address field by location
				CellData.spreadSheet[i][k].address = addressLetter + addressNumber;
			}
		}
	}
}

