import java.util.ArrayList;


public class Main{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BoundedEnv env = new BoundedEnv(20, 20);

		Location loc1 = new Location(7,3);
		Location loc2 = new Location(7,4);
		Direction dir1 = env.getDirection(loc1, loc2);
		Direction dir2 = dir1.toRight();
		Direction dir3 = dir2.reverse();
		Location loc3 = env.getNeighbor(loc1, dir3);
		Location loc4 = env.getNeighbor(new Location(5,2), dir1);
		
		
		System.out.println(env.numObjects());
		System.out.println(loc2);
		System.out.println(dir1);
		System.out.println(dir2);
		System.out.println(dir3);
		System.out.println(loc3);
		System.out.println(loc4 + "\n\n");
		
		ArrayList list = env.neighborsOf(loc1);
		
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
		
		System.out.println("\n");
		System.out.println(Direction.NORTH.inDegrees());
		System.out.println(Direction.EAST.inDegrees());
		System.out.println(Direction.SOUTH.inDegrees());
		System.out.println(Direction.WEST.inDegrees());

	}


}
