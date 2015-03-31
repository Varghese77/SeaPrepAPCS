
public class Location {
	public int x;
	
	public int y;
	
	Location(int x1, int y1){
		x = x1;
		y = y1;
	}
	
	public String toString() {
		return "(" + x + "," + y + ")";
	}
	public boolean equals(Location loc){
		int locX = loc.x;
		int locY = loc.y;
		
		if (x != locX || y != locY){
			return false;
		}
		
		return true;
	}
}
