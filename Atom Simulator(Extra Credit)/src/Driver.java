import java.util.Scanner;
import java.util.concurrent.TimeUnit;


public class Driver {

	public static void main(String[] args) {

			Scanner scan = new Scanner(System.in);
			int radius = 2;
			HexEnv f = new HexEnv(radius,80);
			String command = "";
				Electron e1 = new Electron(f);
				Electron e2 = new Electron(f);
				Electron e3 = new Electron(f);
				//f.add(new Electron(f));
				//f.remove(e1);
			while(command != "quit"){
				try {
					TimeUnit.MILLISECONDS.sleep(500);
				} catch (InterruptedException e) {

				}
				
				e1.act();
				e2.act();
				e3.act();
			}
	}

}
