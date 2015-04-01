import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;


public class BreedingAndDieingFish extends Fish{
	
    private double probOfBreeding;     
    private double probOfDieing;        
	 
	 public BreedingAndDieingFish(Environment env, Location loc, double death, double birth)
	    {
	        super(env, loc);
	        probOfBreeding = birth;
	        probOfDieing = death;
	    }
	    public BreedingAndDieingFish(Environment env, Location loc, Direction dir, double death, double birth)
	    {
	        super(env, loc, dir);
	        probOfBreeding = birth;
	        probOfDieing = death;
	    }

	    public BreedingAndDieingFish(Environment env, Location loc, Direction dir, Color col, double death, double birth)
	    {
	        super(env, loc, dir, col);
	        probOfBreeding = birth;
	        probOfDieing = death;
	    }

	    public void act()
	    {
	    	// Ch.3 Ex. set 1 Q. 8 update
	    	probOfDieing += 0.1;
	    	
	        // Make sure fish is alive and well in the environment -- fish
	        // that have been removed from the environment shouldn't act.
	        if ( ! isInEnv() )
	            return;

	        // Try to breed.
	        if ( ! breed() )
	            // Did not breed, so try to move.
	            move();

	        // Determine whether this fish will die in this timestep.
	        Random randNumGen = RandNumGenerator.getInstance();
	        if ( randNumGen.nextDouble() < probOfDieing )
	            die();
	    }


	    protected boolean breed()
	    {

	        Random randNumGen = RandNumGenerator.getInstance();
	        if ( randNumGen.nextDouble() >= probOfBreeding )
	            return false;

	        // Get list of neighboring empty locations.
	        ArrayList emptyNbrs = emptyNeighbors();
	        Debug.print("Fish " + toString() + " attempting to breed.  ");
	        Debug.println("Has neighboring locations: " + emptyNbrs.toString());

	        // If there is nowhere to breed, then we're done.
	        if ( emptyNbrs.size() == 0 )
	        {
	            Debug.println("  Did not breed.");
	            return false;
	        }
	     // Breed to all of the empty neighboring locations.
	        for ( int index = 0; index < emptyNbrs.size(); index++ )
	        {
	            Location loc = (Location) emptyNbrs.get(index);
	            generateChild(loc);
	        }
	        

	        return true;
	    }
	    
	    protected void generateChild(Location loc)
	    {
	        // Create new fish, which adds itself to the environment.
	    	
	    	//Exercise set 1 Question 3
	    	int red = (int) (Math.random() * 256);
	    	int green = (int) (Math.random() * 256);
	    	int blue = (int) (Math.random() * 256);
	    	
	    	Color randColor = new Color(red, green, blue);
	    	
	        Fish child = new Fish(environment(), loc,
	                              environment().randomDirection(), randColor);
	        Debug.println("  New Fish created: " + child.toString());
	    }
	    
	    protected void die()
	    {
	        environment().remove(this);
	    }
}
