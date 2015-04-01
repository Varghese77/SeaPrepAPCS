import java.util.ArrayList;


public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Location l1 = new Location(1,1);
		Location l2 = new Location(1,6);
		ArrayList<Location> A = new ArrayList();
		A.add(l1);
		A.add(l2);
		removeLocationFromLocationArrayList(A, l2);
		System.out.println(A.size());
	}

	public static void removeLocationFromLocationArrayList(ArrayList<Location> validLocations, Location loc){
		for (int i = 0; i < validLocations.size(); i++){
			Location compLoc = validLocations.get(i);
			if (compLoc.equals(loc)){
				validLocations.remove(i);
			}
		}
	}
}
