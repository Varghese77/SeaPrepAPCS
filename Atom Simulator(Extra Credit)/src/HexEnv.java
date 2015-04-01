import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

public class HexEnv extends JFrame implements Environment {
	
	private static final long serialVersionUID = 1L;
	private JLayeredPane[][] grid;
	public ArrayList<Location> allLocations = new ArrayList<Location>();
	
	public ArrayList<Electron> Electrons = new ArrayList<Electron>();
	private int border = 1;
	
	// Individual hexagon measurments
	final private int HEX_WIDTH;
	final private int HEX_HEIGHT;
	
	public HexEnv(int radius, int width){   
		setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		
		// Square grid with length and width of Atom's diameter
		grid = new JLayeredPane[ 2 * radius + 1][ 2 * radius + 1];
		
		
		HEX_WIDTH = width;
		HEX_HEIGHT = (int) (((Math.sqrt(3.0) / 2.0) * HEX_WIDTH) + 0.5);

		setSize(2 * radius * HEX_WIDTH, 2 * radius * HEX_HEIGHT + 2 * HEX_HEIGHT); 
		createBoundedEnv();
		createElectronShell(radius);
		createNucleus();
		updateGrid();
		setLayout(null); 
		setVisible(true); 
	} 
	
	public ArrayList<Location> validLocations() {
		ArrayList<Location> validLocations = new ArrayList<Location>();
		
		validLocations = (ArrayList<Location>) allLocations.clone();
		
		for (int i = 0; i <  Electrons.size(); i++){
			
			Location eLoc = Electrons.get(i).location();
			Location tempLoc = new Location(eLoc.x, eLoc.y);
			
			removeLocationFromLocationArrayList(validLocations, tempLoc);
			tempLoc = Direction.North(tempLoc);
			
			removeLocationFromLocationArrayList(validLocations, tempLoc);
			tempLoc = new Location(eLoc.x, eLoc.y);
			
			tempLoc = Direction.NorthEast(tempLoc);
			removeLocationFromLocationArrayList(validLocations, tempLoc);
			tempLoc = new Location(eLoc.x, eLoc.y);
			
			tempLoc = Direction.NorthWest(tempLoc);
			removeLocationFromLocationArrayList(validLocations, tempLoc);
			tempLoc = new Location(eLoc.x, eLoc.y);
			
			tempLoc = Direction.South(tempLoc);
			removeLocationFromLocationArrayList(validLocations, tempLoc);
			tempLoc = new Location(eLoc.x, eLoc.y);
			
			tempLoc = Direction.SouthWest(tempLoc);
			removeLocationFromLocationArrayList(validLocations, tempLoc);
			tempLoc = new Location(eLoc.x, eLoc.y);
			
			tempLoc = Direction.SouthEast(tempLoc);
			removeLocationFromLocationArrayList(validLocations, tempLoc);
		}
		
		return validLocations;
		
	};
	
	
	public void removeLocationFromLocationArrayList(ArrayList<Location> validLocations, Location loc){
		for (int i = 0; i < validLocations.size(); i++){
			Location compLoc = validLocations.get(i);
			if (compLoc.equals(loc)){
				validLocations.remove(i);
			}
		}
	}
	
	public void createElectronShell(int radius) {
		for (int i = 1; i <= radius; i++){
			cycleRing(i);
		}
		
	}
	
	public void cycleRing(int d){
		int a = grid.length;
		int mX = a / 2 ;
		int mY = mX;
		
		Location currentTile = new Location(mX,mY);
		
		for (int i = 0; i < d; i++){
			currentTile = Direction.SouthWest(currentTile);
		}
		
		colorTile(currentTile);
		allLocations.add(new Location(currentTile.x, currentTile.y));
		
		for (int i = 0; i < d; i++){
			currentTile = Direction.SouthEast(currentTile);
			colorTile(currentTile);
			allLocations.add(new Location(currentTile.x, currentTile.y));
		}
		
		for (int i = 0; i < d; i++){
			currentTile = Direction.NorthEast(currentTile);
			colorTile(currentTile);
			allLocations.add(new Location(currentTile.x, currentTile.y));
		}
		
		for (int i = 0; i < d; i++){
			currentTile = Direction.North(currentTile);
			colorTile(currentTile);
			allLocations.add(new Location(currentTile.x, currentTile.y));
		}
		
		for (int i = 0; i < d; i++){
			currentTile = Direction.NorthWest(currentTile);
			colorTile(currentTile);
			allLocations.add(new Location(currentTile.x, currentTile.y));
		}
		
		for (int i = 0; i < d; i++){
			currentTile = Direction.SouthWest(currentTile);
			colorTile(currentTile);
			allLocations.add(new Location(currentTile.x, currentTile.y));
		}
		
		for (int i = 0; i < d - 1; i++){
			currentTile = Direction.South(currentTile);
			colorTile(currentTile);
			allLocations.add(new Location(currentTile.x, currentTile.y));
		}
	}
	
	public void colorTile(Location tile){
		// Generates coordinates of Hexagon and its outline
        int[][] coordinates = generateHexagon(HEX_WIDTH / 2);
        int [] xPoly = coordinates[0];
        int [] yPoly = coordinates[1];
        
        int[][] borderCoordinates = generateHexagonBorder(generateHexagon(HEX_WIDTH / 2), border);
        int [] xBorder = borderCoordinates[0];
        int [] yBorder = borderCoordinates[1];
        
        //updateGrid();
        this.remove(grid[tile.y][tile.x]);
        //this.repaint();
		grid[tile.y][tile.x] = new JLayeredPane() {

			@Override                                    
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                //draws hexagon to be outline
                g.setColor(Color.BLACK);
                g.drawPolygon(xPoly, yPoly, 6);
                g.fillPolygon(xPoly, yPoly, 6);
                //draws front solid hexagon
                g.setColor(Color.GRAY);
                g.fillPolygon(xBorder, yBorder, 6);	
			}
		};

		//sets JLayeredPane to be transparent
		grid[tile.y][tile.x].setOpaque(false);
        
        //Determines placement of Hexagon based on column
        if (tile.x % 2 != 0) {
        	grid[tile.y][tile.x].setBounds((HEX_WIDTH * 3 / 4 ) * tile.x, (HEX_HEIGHT / 2) + HEX_HEIGHT * tile.y, HEX_WIDTH, HEX_HEIGHT);
        } else {
        	grid[tile.y][tile.x].setBounds((HEX_WIDTH * 3 / 4 ) * tile.x, HEX_HEIGHT * tile.y, HEX_WIDTH, HEX_HEIGHT);
        }
        
        add(grid[tile.y][tile.x]);
        grid[tile.y][tile.x].repaint();
	}
	
	public void createNucleus(){
		int a = grid.length;
		int mX = a / 2 ;
		int mY = mX;
		
		// Generates coordinates of Hexagon and its outline
        int[][] coordinates = generateHexagon(HEX_WIDTH / 2);
        int [] xPoly = coordinates[0];
        int [] yPoly = coordinates[1];
        
        int[][] borderCoordinates = generateHexagonBorder(generateHexagon(HEX_WIDTH / 2), border);
        int [] xBorder = borderCoordinates[0];
        int [] yBorder = borderCoordinates[1];
		
		JLayeredPane d = new JLayeredPane() {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                //draws hexagon to be outline
                g.setColor(Color.WHITE);
                g.fillPolygon(xPoly, yPoly, 6);
                //draws front solid hexagon
                g.setColor(Color.RED);
                g.fillPolygon(xBorder, yBorder, 6);             
            }
        } ;
		
		if (mX % 2 != 0) {
        	d.setBounds((HEX_WIDTH * 3 / 4 ) * mX, (HEX_HEIGHT / 2) + HEX_HEIGHT * mY, HEX_WIDTH, HEX_HEIGHT);
        } else {
        	d.setBounds((HEX_WIDTH * 3 / 4 ) * mX, HEX_HEIGHT * mY, HEX_WIDTH, HEX_HEIGHT);
        }
		
		JLabel nukeLabel = new JLabel("(+)");
        nukeLabel.setFont(new Font("Times", Font.BOLD, HEX_WIDTH / 2));
        nukeLabel.setBounds( (HEX_WIDTH / 5),(HEX_HEIGHT / 4),(2 * HEX_WIDTH / 3), (2 * HEX_HEIGHT / 3));
        
        d.setLayer(nukeLabel, 1);
		
		d.add(nukeLabel);
		d.setBackground(Color.RED);
		d.setVisible(true);
		d.setOpaque(false);
		this.add(d);
	}
	
	public void createBoundedEnv(){
		// Diameter of Atom
		int VerticleDiameter = grid.length;

		// Places Panels next to each other
		for (int i = 0; i < VerticleDiameter; i++){
			for (int k = 0; k < VerticleDiameter; k++) {
		        grid[i][k] = new JLayeredPane();
			}
		}
	}
	
	public void placeElectron(int x, int y){
		
		// Creates new JLabel to represent locatable
        JLabel Electron = new JLabel() {
        	/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			// Draw green label with white border
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
 				
                g.setColor(Color.WHITE);
                g.fillOval((HEX_WIDTH / 10), (HEX_HEIGHT / 10), (3 *HEX_WIDTH / 4), (4 * HEX_HEIGHT / 5));
                g.setColor(Color.GREEN);
                g.fillOval((HEX_WIDTH / 10) + border, (HEX_HEIGHT / 10) + border, (3 *HEX_WIDTH / 4) - (2 * border), (4 * HEX_HEIGHT / 5) - 2 * border);
            }
        } ;
        
        Electron.setBounds(0, 0, HEX_WIDTH, HEX_HEIGHT);
        Electron.setVisible(true);
        grid[y][x].add(Electron);
        
        //Adds text label to locatable 
        JLabel label = new JLabel("e-");
        label.setFont(new Font("Serif", Font.PLAIN, HEX_WIDTH / 2));
        label.setBounds( (HEX_WIDTH / 3),(HEX_HEIGHT / 4),(HEX_WIDTH / 2), (HEX_HEIGHT / 2));
        
        grid[y][x].add(label);
        //makes Label above Locatable
        grid[y][x].setLayer(label, 1);
        
        grid[y][x].validate();
        grid[y][x].repaint();
	}

	public int[][] generateHexagon(double hexagonCenterToCorner) {
		
    	int [][] coordinates = new int[6][6];
    	coordinates[0][0] = (int) ((hexagonCenterToCorner / 2.0) + 0.5);
    	coordinates[0][1] = 0;
    	coordinates[0][2] = coordinates[0][0];
    	coordinates[0][3] = (int) (((3.0  * hexagonCenterToCorner)/ 2.0) + 0.5);
    	coordinates[0][4] = (int) ((2.0 * hexagonCenterToCorner) + 0.5);
    	coordinates[0][5] = coordinates[0][3];
    	
    	coordinates[1][0] = 0;
    	coordinates[1][1] = (int) (((Math.sqrt(3) / 2.0) * hexagonCenterToCorner) + 0.5);
    	coordinates[1][2] = (int) ((Math.sqrt(3) * hexagonCenterToCorner) + 0.5);
    	coordinates[1][3] = coordinates[1][2];
    	coordinates[1][4] = coordinates[1][1];
    	coordinates[1][5] = coordinates[1][0];
    	
		return coordinates;
    }
	
	public int[][] generateHexagonBorder(int[][] coordinates, int border){
    	coordinates[0][0] += border;
    	coordinates[0][1] += border;
    	coordinates[0][2] += border;
    	coordinates[0][3] -= border;
    	coordinates[0][4] -= border;
    	coordinates[0][5] -= border;
    	
    	coordinates[1][0] += border;
    	
    	coordinates[1][2] -= border;
    	coordinates[1][3] -= border;
    	
    	coordinates[1][5] += border;
    	return coordinates;
    	
    }

	public void updateGrid() {
		int a = grid.length;
		for (int i = 0; i < a; i++){
			for (int k = 0; k < a; k++) {
				grid[i][k].removeAll();
				add(grid[i][k]);
				this.repaint();
			}
		}
		
	}

	@Override
	public int numRows() {
		// TODO Auto-generated method stub
		return grid.length;
	}

	@Override
	public int numCols() {
		// TODO Auto-generated method stub
		return grid[0].length;
	}

	@Override
	public boolean isValid(Location loc) {
		

		boolean validity = false;
		
		for (int i = 0; i < allLocations.size(); i++){
			if (loc.x != allLocations.get(i).x || loc.y != allLocations.get(i).y){	
			}
		}
		
		return validity;
	}

	@Override
	public int numCellSides() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int numAdjacentNeighbors() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Direction randomDirection() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Direction getDirection(Location fromLoc, Location toLoc) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Location getNeighbor(Location fromLoc, Direction compassDir) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Location> neighborsOf(Location ofLoc) {
		// TODO Auto-generated method stub
		ArrayList<Location> validNeighbors = new ArrayList<Location>();
		
		if (isValid(Direction.North(ofLoc))){
			validNeighbors.add(Direction.North(ofLoc));
		}
		
		if (isValid(Direction.NorthWest(ofLoc))){
			validNeighbors.add(Direction.NorthWest(ofLoc));
		}
		
		if (isValid(Direction.SouthWest(ofLoc))){
			validNeighbors.add(Direction.SouthWest(ofLoc));
		}
		
		if (isValid(Direction.South(ofLoc))){
			validNeighbors.add(Direction.South(ofLoc));
		}
		
		if (isValid(Direction.SouthEast(ofLoc))){
			validNeighbors.add(Direction.SouthEast(ofLoc));
		}
		
		if (isValid(Direction.NorthEast(ofLoc))){
			validNeighbors.add(Direction.NorthEast(ofLoc));
		}
		
		return validNeighbors;
	}

	@Override
	public int numObjects() {
		// TODO Auto-generated method stub
		return Electrons.size();
	}

	@Override
	public Locatable[] allObjects() {
		// TODO Auto-generated method stub
		Locatable[] locatablesToReturn = new Locatable[Electrons.size()];
		for (int i = 0; i < Electrons.size(); i++){
			locatablesToReturn[i] = Electrons.get(i);
		}
		return locatablesToReturn;
	}

	@Override
	public boolean isEmpty(Location loc) {
		// TODO Auto-generated method stub
		for (int i = 0; i < Electrons.size(); i++){
			if (Electrons.get(i).equals(loc)){
				return false;
			}
			
		}
		return true;
	}

	@Override
	public Locatable objectAt(Location loc) {
		// TODO Auto-generated method stub
		for (int i = 0; i < Electrons.size(); i++){
			if (Electrons.get(i).equals(loc)){
				return Electrons.get(i);
			}
			
		}
		return null;
	}

	@Override
	public void add(Locatable obj) {
		// TODO Auto-generated method stub
		Electrons.add(new Electron(this));
		updateGrid();
		
	}

	@Override
	public void remove(Locatable obj) {
		// TODO Auto-generated method stub
		Electrons.get(0).theEnv = null;
		Electrons.remove(0);
		updateGrid();
	}

	@Override
	public void recordMove(Locatable obj, Location oldLoc) {
		// TODO Auto-generated method stub
		
	}

}