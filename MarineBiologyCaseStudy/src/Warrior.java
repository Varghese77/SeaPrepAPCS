import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public class Warrior implements Locatable
{
    // Class Variable: Shared among ALL fish
    private static int WarriorID = 1;   // next avail unique identifier

    // Instance Variables: Encapsulated data for EACH fish
    private Environment theEnv;        // environment in which the fish lives
    private int myId;                  // unique ID for this fish
    private Location myLoc;            // fish's location
    private Direction myDir;           // fish's direction



  // constructors and related helper methods

    public Warrior(Environment env, Location loc)
    {
        initialize(env, loc, env.randomDirection(), randomColor());
    }

    public Warrior(Environment env, Location loc, Direction dir)
    {
        initialize(env, loc, dir, randomColor());
    }

    public Warrior(Environment env, Location loc, Direction dir, Color col)
    {
        initialize(env, loc, dir, col);
    }

    private void initialize(Environment env, Location loc, Direction dir,
                            Color col)
    {
        theEnv = env;
        myId = WarriorID;
        WarriorID++;
        myLoc = loc;
        myDir = dir;
        theEnv.add(this);

        // object is at location myLoc in environment
    }

    /** Generates a random color.
     *  @return       the new random color
     **/
    protected Color randomColor()
    {
        // There are 256 possibilities for the red, green, and blue attributes
        // of a color.  Generate random values for each color attribute.
        Random randNumGen = RandNumGenerator.getInstance();
        return new Color(randNumGen.nextInt(256),    // amount of red
                         randNumGen.nextInt(256),    // amount of green
                         randNumGen.nextInt(256));   // amount of blue
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

    public Direction direction()
    {
        return myDir;
    }

    public boolean isInEnv()
    {
        return environment().objectAt(location()) == this;
    }

    public String toString()
    {
        return id() + location().toString() + direction().toString();
    }


  // modifier method

    public void act()
    {
        // Make sure fish is alive and well in the environment -- fish
        // that have been removed from the environment shouldn't act.
        if ( isInEnv() ) 
            move();
    }


  // internal helper methods


    protected void move()
    {
        // Find a location to move to.
        Debug.print("Fish " + toString() + " attempting to move.  ");
        Location nextLoc = nextLocation();

        // If the next location is different, move there.
        if ( ! nextLoc.equals(location()) )
        {
            // Move to new location.
            Location oldLoc = location();
            changeLocation(nextLoc);

            // Update direction in case fish had to turn to move.
            Direction newDir = environment().getDirection(oldLoc, nextLoc);
            changeDirection(newDir);
            Debug.println("  Moves to " + location() + direction());
        }
        else
            Debug.println("  Does not move.");
    }

    protected Location nextLocation()
    {
        // Get list of neighboring empty locations.
        ArrayList emptyNbrs = emptyNeighbors();

        // Remove the location behind, since fish do not move backwards.
        Direction oppositeDir = direction().reverse();
        Location locationBehind = environment().getNeighbor(location(),
                                                            oppositeDir);
        emptyNbrs.remove(locationBehind);
        Debug.print("Possible new locations are: " + emptyNbrs.toString());

        // If there are no valid empty neighboring locations, then we're done.
        if ( emptyNbrs.size() == 0 )
            return location();

        // Return a randomly chosen neighboring empty location.
        Random randNumGen = RandNumGenerator.getInstance();
        int randNum = randNumGen.nextInt(emptyNbrs.size());
	    return (Location) emptyNbrs.get(randNum);
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

    /** Modifies this fish's location and notifies the environment.
     *  @param  newLoc    new location value
     **/
    protected void changeLocation(Location newLoc)
    {
        // Change location and notify the environment.
        Location oldLoc = location();
        myLoc = newLoc;
        environment().recordMove(this, oldLoc);

        // object is again at location myLoc in environment
    }

    /** Modifies this fish's direction.
     *  @param  newDir    new direction value
     **/
    protected void changeDirection(Direction newDir)
    {
        // Change direction.
        myDir = newDir;
    }

}