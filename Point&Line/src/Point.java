
public class Point {
	//public fields to be set for coordinate address
	public double x;
	public double y;
	
	//constructor
	public Point(double xintitial, double yinitial) {
		x = xintitial;
		y = yinitial;
	}
	
	//converts point to a readable string format
	public String toString() {
		return "(" + x + "," + y + ")";
	}
}
