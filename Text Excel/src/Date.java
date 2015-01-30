
public class Date {

	//public parts of date
	public int month;
	
	public int day;
	
	public int year;
	
	//constructor
	public Date(String s) {
		String[] dateParts = s.split("/");
		month = Integer.parseInt(dateParts[0]);
		day = Integer.parseInt(dateParts[1]);
		year = Integer.parseInt(dateParts[1]);
	}
	
	public String toString() {
		return month + "/" + day + "/" + year;
	}
}
