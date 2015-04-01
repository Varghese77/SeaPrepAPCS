import java.util.Scanner;
import java.util.concurrent.TimeUnit;


public class Driver {

	public static void main(String[] args) {

			Scanner scan = new Scanner(System.in);
			int radius = 8;
			HexEnv f = new HexEnv(radius,40);
			String command = "";
				Electron e1 = new Electron(f);
				Electron e2 = new Electron(f);
				Electron e3 = new Electron(f);
				Electron e4 = new Electron(f);
				Electron e5 = new Electron(f);
				Electron e6 = new Electron(f);
				Electron e7 = new Electron(f);
				Electron e8 = new Electron(f);

			while(command != "quit"){
				try {
					TimeUnit.MILLISECONDS.sleep(500);
				} catch (InterruptedException e) {

				}
				
				e1.act();
				e2.act();
				e3.act();
				e4.act();
				e5.act();
				e6.act();
				e7.act();
				e8.act();
				
			}
	}

}
