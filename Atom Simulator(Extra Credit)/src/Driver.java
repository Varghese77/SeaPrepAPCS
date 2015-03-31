import java.util.Scanner;


public class Driver {

	public static void main(String[] args) {

			Scanner scan = new Scanner(System.in);
			int radius = 4;
			int size = 2 * radius + 1;
			HexEnv f = new HexEnv(radius,80);
			String command = "";
				Electron e1 = new Electron(f);
				//Electron e2 = new Electron(f);
			//	Electron e3 = new Electron(f);
			while(command != "quit"){

				f.step();
				
			}
	}

}
