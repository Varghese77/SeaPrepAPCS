// This locatable is from Roy Mathew and Chris Jellen
// It will utilise a new hex-grid environment that will be created later
// and it also invokes some methods from that environment. 
// *locatables use axial coordinates rather than cartesian coordinates. 
import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public class Electron implements Locatable
{
    // Class Variable: Shared among ALL fish
    private static int nextAvailableID = 1;   // next avail unique identifier

    // Instance Variables: Encapsulated data for EACH fish
    private Environment theEnv;        // environment in which the fish lives
    private int myId;                  // unique ID for this fish
    private Location myLoc;            // fish's location
    final private Color MY_COLOR = Color.gray;             // fish's color

  // constructors and related helper methods

    public Electron(Environment env, Location loc)
    {
        initialize(env, loc);
    }

    public Electron(Environment env)
    {
    	Location loc = env.getRandomLocation;
    	/* gets total number of tiles in environment, then uses a 
    	 * random number generator to to select the tile in which the
    	 * electron could be created. 
    	 */
    	int totalTiles = env.getAllNeighbors.size();
    	/*
    	 * environment will he a hex-grid with single point radius,
    	 * total tiles will be a summation of 6X where x starts at 1 
    	 * and increments to the value of the radius. 
    	 */
    	
    	for (int i = 0; i < totalTiles; i++)
    	while (!env.isEmpty(loc)) {
    		Location loc = env.getRandomLocation;
    	}
    	
        initialize(env, loc);
    }
    
    private void initialize(Environment env, Location loc)
    {
        theEnv = env;
        myId = nextAvailableID;
        nextAvailableID++;
        myLoc = loc;
        theEnv.add(this);
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

    public Color color()
    {
        return Color.gray;
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
    	if (isInEnv()) {
    		move();
    	}
    }

    protected void move()
    {
        // Find a location to move to.
        Debug.print("Fish " + toString() + " attempting to move.  ");
        Location nextLoc = nextLocation();
        
        /* nextLocation will be redefined. First, an Arraylist 
         * of all locations will created. Then the method will find out the locations
         * that have electrons in it and remove them from the ArrayList. Next, we will find the neighbors
         * of each electron and remove those also from the array list. Then if any locations are left
         * one will be chosen randomly, if there are no possible locations then a return statement 
         * will be used to break the method. 
         * 
         */

        // If the next location is different, move there.
        if (!nextLoc.equals(location()) )
        {
            changeLocation(nextLoc);
            
            Debug.println("  Moves to " + location());
        }
        else
            Debug.println("  Does not move.");
    }


    protected Location nextLocation()
    {
        // Get list of neighboring empty locations.
        ArrayList<Location> allNbrs = theEnv.getAllNeighbors;

        // If there are no valid empty neighboring locations, then we're done.
        if ( allNbrs.size() == 0 )
            return location();
        
        //removes electron locations and neighbors
        Locatable[] allE = theEnv.allObjects();
        for (int i = 0; i < allE.length; i++) {
        	Location tempLoc = allE[i].location();
        	allNbrs.remove(tempLoc);
        	ArrayList<Location> eNbrs = theEnv.neighborsOf(tempLoc);
        	for (int k = 0; k < eNbrs.size(); k++) {
        		allNbrs.remove(eNbrs.get(k));
        	}
        }
        
        if (allNbrs.size() == 0) {
        	return location();
        } else {
        	int randIdx = (int) (Math.random() * allNbrs.size());
        	return allNbrs.get(randIdx);
        }
    }

    protected ArrayList emptyNeighbors()
    {
        // Get all the neighbors of this fish, empty or not.
        ArrayList nbrs = environment().neighborsOf(location());

        // Figure out which neighbors are empty and add those to a new list.
        ArrayList emptyNbrs = new ArrayList();
        for ( int index = 0; index < nbrs.size(); index++ )
        {
            Location loc = (Location) nbrs.get(index);
            if ( environment().isEmpty(loc) )
                emptyNbrs.add(loc);
        }

        return emptyNbrs;
    }
    
    

    protected void changeLocation(Location newLoc)
    {
        // Change location and notify the environment.
        Location oldLoc = location();
        myLoc = newLoc;
        environment().recordMove(this, oldLoc);

        // object is again at location myLoc in environment
    }


}