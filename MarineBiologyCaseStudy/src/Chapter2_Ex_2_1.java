public class Chapter2_Ex_2_1 {

	public static void main(String[] args) {
		
		BoundedEnv env = new BoundedEnv(20,20);
		
		Location loc1 = new Location(7,2);
		Location loc2 = new Location(2,6);
		Location loc3 = new Location(4,8);
		
		Fish f1 = new Fish(env, loc1);
		Fish f2 = new Fish(env, loc2);
		
		println("Set 3 question 1: " + env.numObjects());
		nextQ();
		
		Locatable[] arr = env.allObjects();
		
		println("Set 3 question 2: ");
		for (int i = 0; i < arr.length; i++)
			System.out.println(arr[i]);
		
		nextQ();
		
		println("Set 3 question 3: " + env.isEmpty(loc1));
		nextQ();
		
		println("Set 3 question 4: " + env.isEmpty(loc3));
		nextQ();
		
		println("Set 3 question 5: " + env.objectAt(loc2));
		nextQ();
		
		println("Set 3 question 6: " + env.objectAt(loc3));
		nextQ();
		
		// Part two adding fish
		Fish f3 = new Fish(env, new Location(0,0));
		Fish f4 = new Fish(env, new Location(3,1));
		Fish f5 = new Fish(env, new Location(4,15));
		
		println("Number of fish after adding 3 fish: " + env.numObjects());
		nextQ();
		
		println("With added 3 fish:");
		arr = env.allObjects();
		
		for (int i = 0; i < arr.length; i++)
			println(arr[i]);
		println("Is (4, 15) empty: " + env.isEmpty(new Location(4, 15)));
		nextQ();
		
		// Removing fish
		env.remove(f5);
		env.remove(f4);
		
		//f4.act();
		
		
		println("Number of fish after removing 2 fish: " + env.numObjects());
		nextQ();
		
		arr = env.allObjects();
		
		for (int i = 0; i < arr.length; i++)
			println(arr[i]);
		nextQ();
		
		println("Is (4, 15) empty: " + env.isEmpty(new Location(4, 15)));
		nextQ();
		
		// part 3
		
		//env.add(new Fish(env, new Location(1,6)));
		
		// part 4
		
		println("Exercise Set 3:");
		println(env.neighborsOf(new Location(0,0)));
		println(env.neighborsOf(new Location(1,1)));
		println(env.neighborsOf(new Location(0,1)));
	}
	
	public static void println(Object s){
		System.out.println(s);
	}
	
	public static void nextQ(){
		System.out.println("\n");
	}
}