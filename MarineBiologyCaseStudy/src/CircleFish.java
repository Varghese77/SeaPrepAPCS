import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public class CircleFish extends Fish{
	
	private boolean turnedLastStep = false;
	private boolean firstStep = true;
	
	public CircleFish(Environment env, Location loc) {
		super(env, loc);
		super.changeColor(Color.BLACK);
	}
	
	public CircleFish(Environment env, Location loc, Direction dir) {
		super(env, loc, dir);
		super.changeColor(Color.BLACK);
	}
	
	protected Location nextLocation()
    {
		if (firstStep)
		{
			Location frontLoc =  super.environment().getNeighbor(location(), direction());
			if (super.environment().isValid(frontLoc) && super.environment().isEmpty(frontLoc)){
				firstStep = false;
				return frontLoc;
			}
			firstStep = false;
			return location();
		}
		
		if (!turnedLastStep){
			Location locFrontRight = super.environment().getNeighbor(location(), direction());
			locFrontRight = super.environment().getNeighbor(locFrontRight, direction().toRight());

			if (super.environment().isValid(locFrontRight) && super.environment().isEmpty(locFrontRight)){
				turnedLastStep = true;
				return locFrontRight;
			}
		
			super.changeDirection(direction().toRight());
			turnedLastStep = true;
			return location();
			
		} else {
			
			Location frontLoc =  super.environment().getNeighbor(location(), direction());
			if (super.environment().isValid(frontLoc) && super.environment().isEmpty(frontLoc)){
				turnedLastStep = false;
				return frontLoc;
			}
			turnedLastStep = false;
			return location();
		}
    }

}
