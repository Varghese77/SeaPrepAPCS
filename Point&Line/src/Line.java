public class Line {
	 /* uses standard y=mx+b linear line formula
	  * where m = slope and b =y-intercept
	 */

	public double m;
	public double b;

	// constructor
	public Line(Point p1, Point p2) {
		//assigns slope though slope-formula (∆y/∆x)
		m = (p2.y - p1.y) / (p2.x - p1.x);

		/* finds y-intercept based on location of
		of a point and the line's slope*/
		if (p1.x >= 0) {
			b = p1.y - (m * p1.x);
		} else {
			b = p1.y + (m * p1.x);
		}
	}

	public void printTable(int start, int end, int increment) {
		//prints header
		System.out.println("x    | y");
		System.out.println("---------------");

		//print's each individual row
		for (int i = start; i <= end; i += increment) { 
			System.out.print(i);
			
			//determines spaces to be printed after x-value
			int spaces = String.valueOf(i).length();
			for (int k = 4 - spaces; k >= 0; k--) {
				System.out.print(" ");
			}
			//prints column divider & y-value substituting i for x
			System.out.println("| " + (m * i + b));

		}
		System.out.println();

	}

	public boolean isParallel(Line l) {

		//compares slope since parallel lines have equal slopes
		if (l.m == m) {
			return true;
		} else {
			return false;
		}
	}

	public Point intersect(Line l) {
		/*
		 * x-value of intersection is
		 *  found by setting both y=mx+b 
		 *  equation equal and solving for x. 
		 * Proof: Y=MX+B , y=mx+b, X = x, Y = y 
		 * mx+b = Mx+B
		 * 0 = Mx+B–b mx 
		 * 0 = x(M – m)+(B-b) 
		 * x = (-B + b)/(M-m)
		 */
		double x = (-1 * l.b + b) / (l.m - m);

		// finds common y-value from common x-value
		double y = m * x + b;

		// uses x & y values to create a new point
		Point intersection = new Point(x, y);

		return intersection;
	}
}

