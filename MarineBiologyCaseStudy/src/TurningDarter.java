import java.awt.Color;
import java.util.Random;


public class TurningDarter extends DarterFish{

	public TurningDarter(Environment env, Location loc) {
		super(env,loc);
	}
	
	public TurningDarter(Environment env, Location loc, Direction dir) {
		super(env,loc, dir);
	}
	
	public TurningDarter(Environment env, Location loc, Direction dir, Color col) {
		super(env,loc, dir, col);
	}
	
	protected Location nextLocation()
    {
		Random rand = new Random();
		double probOfTurning = rand.nextDouble();
		if (probOfTurning > 0.1){
			Environment env = environment();
        	Location oneInFront = env.getNeighbor(location(), direction());
        	Location twoInFront = env.getNeighbor(oneInFront, direction());
        	Debug.println("  Location in front is empty? " + 
                        	env.isEmpty(oneInFront));
        	Debug.println("  Location in front of that is empty? " + 
                        	env.isEmpty(twoInFront));
        	if ( env.isEmpty(oneInFront) )
        	{
            	if ( env.isEmpty(twoInFront) )
            		return twoInFront;
            	else
            		return oneInFront;
        	}
		} else {
			Random randDir = new Random();
			Direction turn;
			if (randDir.nextDouble() >= 0.5){
				turn = direction().toLeft();
			} else {
				turn = direction().toLeft();
			}
			
			Environment env = environment();
        	Location oneInFront = env.getNeighbor(location(), turn);
        	Location twoInFront = env.getNeighbor(oneInFront, turn);
        	Debug.println("  Locations to " + turn + "are empty? " + 
                        	env.isEmpty(oneInFront));
        	Debug.println("  Locations to " + turn + "are empty? " + 
                        	env.isEmpty(twoInFront));
        	if ( env.isEmpty(oneInFront) )
        	{
            	if ( env.isEmpty(twoInFront) )
            		return twoInFront;
            	else
            		return oneInFront;
        	}
			
		}
        // Only get here if there isn't a valid location to move to.
        Debug.println("  Darter is blocked.");
        return location();
    }
	
}
