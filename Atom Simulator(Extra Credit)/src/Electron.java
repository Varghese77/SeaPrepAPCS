import java.util.ArrayList;

public class Electron implements Locatable

{

	private static int nextAvailableID = 1; // next avail unique identifier


	public HexEnv theEnv; // environment in which the fish lives

	private int myId; // unique ID for this fish

	private Location myLoc; // fish's location


	// constructors and related helper methods

	public Electron(HexEnv env, Location loc)

	{

		initialize(env, loc);

	}

	public Electron(HexEnv env)

	{
		ArrayList<Location> validLocations = env.validLocations();
		Location loc = new Location(0, 0);

		int validLocationsIdx = (int) (Math.random() * validLocations.size());
		Location nextLocation = validLocations.get(validLocationsIdx);
		

		initialize(env, nextLocation);

	}

	private void initialize(HexEnv env, Location loc)

	{

		theEnv = env;

		myId = nextAvailableID;

		nextAvailableID++;

		myLoc = loc;

		theEnv.Electrons.add(this);
		
		theEnv.placeElectron(loc.x, loc.y);

	}

	// accessor methods

	public int id()

	{

		return myId;

	}

	public Environment environment()

	{

		return theEnv;

	}

	public Location location()

	{

		return myLoc;

	}

	public boolean isInEnv()

	{

		return environment().objectAt(location()) == this;

	}

	public String toString()

	{

		return id() + location().toString();

	}

	public void act()

	{


			move();
			
			


	}

	protected void move()

	{

		// Find a location to move to.

		Debug.print("Fish " + toString() + " attempting to move.  ");

		Location nextLoc = nextLocation();

		if (!nextLoc.equals(location()))

		{

			changeLocation(nextLoc);

			Debug.println(" Moves to " + location());

		}

		else

			Debug.println(" Does not move.");

	}

	protected Location nextLocation()

	{
		ArrayList<Location> validLocations = theEnv.validLocations();
		Location loc = new Location(0, 0);

		if (validLocations.size() != 0) {
			int validLocationsIdx = (int) (Math.random() * validLocations.size());
			Location nextLocation = validLocations.get(validLocationsIdx);
			return nextLocation;
			
		} else {
			return myLoc;	
		}
	}

	protected ArrayList emptyNeighbors()

	{

		// Get all the neighbors of this fish, empty or not.

		ArrayList nbrs = environment().neighborsOf(location());

		// Figure out which neighbors are empty and add those to a new list.

		ArrayList emptyNbrs = new ArrayList();

		for (int index = 0; index < nbrs.size(); index++)

		{

			Location loc = (Location) nbrs.get(index);

			if (environment().isEmpty(loc))

				emptyNbrs.add(loc);

		}

		return emptyNbrs;

	}

	protected void changeLocation(Location newLoc)

	{


		Location oldLoc = location();
		
		theEnv.colorTile(oldLoc);

		myLoc = newLoc;
		
		theEnv.placeElectron(newLoc.x, newLoc.y);

		environment().recordMove(this, oldLoc);


	}

}
