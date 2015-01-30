import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter the dimensions of the spreadsheet separated by \'X\'");
		String dimensions = scan.nextLine().trim();
		spreadSheetMeta(dimensions);
		
		//initializes command variable
		String command;
		do {
			//prints table
			PrintCells.printData();
			System.out.print("Visicalc> ");
			
			//asks for command
			command = scan.nextLine().trim();
			if (!command.equals("quit")) {
				CellCommands.determineCommand(command);
			}
			
		} while (!command.equals("quit"));
		scan.close();
		
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
				String addressNumber = (char) (i + '1') + "";
				//sets cell address field by location
				CellData.spreadSheet[i][k].address = addressLetter + addressNumber;
			}
		}
	}
}

