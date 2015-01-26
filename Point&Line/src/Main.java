
public class Main {

	public static void main(String[] args) {
		Point p1 = new Point(0, 0);
		Point p2 = new Point(1, 4);
		Point p3 = new Point(3, -4);
		Point p4 = new Point(0, 8);

		// define lines
		Line l1 = new Line(p1, p2);
		Line l2 = new Line(p3, p4);

		// print lines
		l1.printTable(0, 10, 1);
		l2.printTable(0, 10, 1);

		// check if lines are parallel
		if (l1.isParallel(l2)) {
			System.out.println("lines are parallel");
		} else {
			System.out.println("lines are not parallel");
		}

		// find intersection
		Point p5 = l1.intersect(l2);
		String s = p5.toString();
		System.out.println("intersection: " + s);
	}

}


