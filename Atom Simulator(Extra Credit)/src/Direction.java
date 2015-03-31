
public class Direction {
	
	public static Location North(Location l1){
			l1.y -= 1;
			return l1;
	}
	
	public static Location NorthEast(Location l1){
		if (l1.x % 2 == 0) {
			l1.x += 1;
			l1.y -= 1;
			return l1;
		} else {
			l1.x += 1;
			return l1;
		}
	}
	
	
	public static Location SouthEast(Location l1){
		
		if (l1.x % 2 == 0) {
			l1.x += 1;
			return l1;
		} else {
			l1.x += 1;
			l1.y += 1;
			return l1;
		}	
	}
	
	public static Location South(Location l1){
		l1.y += 1;
		return l1;
		
	}
	
	public static Location SouthWest(Location l1){
		if (l1.x % 2 == 0) {
			l1.x -= 1;
			return l1;
		} else {
			l1.x -= 1;
			l1.y += 1;
			return l1;
		}
	}
	
	public static Location NorthWest(Location l1){
		if (l1.x % 2 == 0){
			l1.x -= 1;
			l1.y -= 1;
			return l1;
		} else {
			l1.x -= 1;
			return l1;
		}
	}
	
}
