
/**
 * Island is represented by a 2D Grid.  Each Cell in the Grid may be occupied by an Entity or may be empty (i.e. traversable).
 * An Entity can be a Creature (e.g. Explorer or Enemy) or Thing (e.g. Valuable or Obstacle).
 * An Enemy can be a Wanderer or Chaser.
 */

import java.util.Random;

public class Island
{
    // instance variables 
        
    private Cell[][] grid; // the grid is a 2 dimensional array of cells
    private CreatureCollectionListBased creatures; // there is a collection of creatures
    
    public static final int NUM_ROWS = 20;
    public static final int NUM_COLS = 20;
    
    int raftRow = 0, raftCol = 0;
 
    /**
     * Constructor for objects of class Island.
     */
    public Island()
    {        
        // initialise instance variables
        
        this.grid = new Cell[NUM_ROWS][NUM_COLS]; // note: cells do not exist yet, until we construct them.
        
        this.creatures = new CreatureCollectionListBased();
        
        this.initializeCells();
    }

    public void removeCreature(Location loc)
    {
        creatures.remove(loc);
    }
    
    
    /**
     * initializeCells: for each cell, determine whether to leave it empty or put an entity in it.
     * 
     * @param  none
     * @return none
     */
    public void initializeCells()
    {
        Random rand = new Random();
         
         for (int row = 0; row < NUM_ROWS; row++)
         {
             for (int col = 0; col < NUM_COLS; col++)
             {
                 if (row == 0 && col == 0) {
                     // Place the explorer in the upper-left orner of the island.
                     Explorer explorer = Explorer.getInstance(new Location(0, 0));
                     this.grid[0][0] = new Cell(explorer); // Store in 2D grid 
                     
                     //DebugHandler d = new DebugHandler(0);
                     //d.print("initializeCells: before add");
                     
                     this.creatures.add(explorer); // Store in collection of creatures
                     
                     //d.print("initializeCells: after add");
                     
                 }
                 else {
                 
                     int randomInt = rand.nextInt(80);
                 
                     if (randomInt < 20) {
                         this.grid[row][col] = new Cell(new Tree());
                     }
                     else if (randomInt == 20) {
                         Wanderer w = new Wanderer(new Location(row, col));
                         this.grid[row][col] = new Cell(w); // Store in 2D grid
                         this.creatures.add(w); // Store in collection of creatures
                     }
                     else if (randomInt == 21) {
                         Chaser c = new Chaser(new Location(row, col));
                         this.grid[row][col] = new Cell(c); // Store in 2D grid
                         this.creatures.add(c); // Store in collection of creatures
                     }
                     else // empty cell
                            this.grid[row][col] = new Cell();
                 }
             } // end for
        } // end for
        
        // Creates 5 randomly placed coins
        for(int index = 0, row2, col2; index <= 8; index++)
        {
             row2 = rand.nextInt(19);
             col2 = rand.nextInt(19);
             Coin o = new Coin(new Location(row2, col2));
             this.grid[row2][col2] = new Cell(o);
        }
        // Place the raft in a random empty location
        int randomRow = rand.nextInt(NUM_ROWS);
        int randomCol = rand.nextInt(NUM_COLS);
        
        while (! this.grid[randomRow][randomCol].isEmpty()) {
            // The randomly chosen cell is occupied, i.e. not empty; try another.
            randomRow = rand.nextInt(NUM_ROWS);
            randomCol = rand.nextInt(NUM_COLS);            
        } // end while
        
       
        this.grid[randomRow][randomCol].setEntity(new Raft());
        // Saves the row and col of the raft so the explorer can know where to find it
        raftRow = randomRow + 1;
        raftCol = randomCol + 1;
    }
    
    /**
     * update: invoke the move method on each creature.
     * @param none
     * @return none
     */
    public void update() throws GameOverException, InterruptedException
    {
        //DebugHandler d = new DebugHandler(0);
     
        // Determine the next move for each creature.
        while (creatures.hasMoreCreatures()) {
          
              // Get the next creature in this world.
              Creature oneCreature = creatures.nextCreature();  //d.print("About to move: " + oneCreature);
              
              // Via polymorphism, the proper move method will be called depending on what kind of Creature this is.
              try{
              oneCreature.move(this);
             }
             catch(GameOverException e){
                
             }
        } // end for  
        
        //d.print("All creatures moved.");
    }
    
    /**
     * isValidLocation:
     * @param a Location, the desired destination on the island
     * @return a boolean, true if the desired destination is on the island and not occupied, false otherwise.
     */
    public boolean isValidLocation(Location destination)
    {
        return (this.onTheIsland(destination) && !this.occupied(destination));
    }
    
    /**
     * onTheIsland:
     * @param a Location
     * @return a boolean, true if the location is within the bounds of the grid, false otherwise.
     */
    public boolean onTheIsland(Location theLocation)
    {
         int row = theLocation.getRow();
         int col = theLocation.getCol();
        
         return (row >= 0 && row < NUM_ROWS && col >= 0 && col < NUM_COLS);
    }
    
    /**
     * occupied:
     * A grid location, or cell, is considered occupied if it is not empty.
     * @param a Location
     * @return a boolean, true if the grid location is not empty, false otherwise.
     */
    public boolean occupied(Location theLocation)
    {
        int row = theLocation.getRow();
        int col = theLocation.getCol();
        
        return (! grid[row][col].isEmpty() );   
    }
   
    /**
     * transfer: transfer an entity from old to new location in the grid.
     * @param Entity to move
     * @param a Location, the old location
     * @param a Location, the new location
     * @return none
     */
    public void transfer(Entity entity, Location oldLocation, Location newLocation)
    {
        int oldRow = oldLocation.getRow();
        int oldCol = oldLocation.getCol();
        int newRow = newLocation.getRow();
        int newCol = newLocation.getCol();
        
        this.grid[newRow][newCol].setEntity(entity); // put the entity in the new cell
        this.grid[oldRow][oldCol].deleteEntity();    // delete the entity in the old cell
    }
    
    /**
     * getCell:
     * @param a Location
     * @return the Cell at the specified location.
     */
    public Cell getCell(Location theLocation)
    {
        int row = theLocation.getRow();
        int col = theLocation.getCol();
        
        return this.grid[row][col];
    }
    
    /**
     * toString: 
     * @param none
     * @return a String representation of this island.
     */
    
    public String toString()
    {
        clearScreen();
        
        String result = "Explore!\n\n Is this your first time playing? \n*For help, type \"h\"*\nCurrent Explorer Position: " + 
                        Explorer.getInstance().getLocation() + "\nTop Left Position- x: 0  y: 0\n" + "You currently have " + 
                        Explorer.getInstance().getNumCoins() + " Coin(s) in your bag\n" + "You can cut down " + 
                        Explorer.getInstance().getNumTrees() + " more tree(s)\n" + "Your remaining health: " + 
                        Explorer.getInstance().getHealth() + "\n" + "Your current armor level is " + 
                        Explorer.getInstance().getArmorLevel() + ". " + "The armor your currently wearing is: " +
                        Explorer.getInstance().printArmor() + "\n";
                        
        if(Explorer.getInstance().getRaft() == true)
            result += ("You have the raft, head to the water to escape the island!!!\n");
        else {
            result += ("You still have not found the raft, if you want to escape get the raft.\n");            
            result += ("The rafts currnet location is (" + raftRow + ", " + raftCol + ") \n");
        }                

        for (int row = 0; row < NUM_ROWS; row++)
        {
            for(int col = 0; col < NUM_COLS; col++)
            {
                Cell nextCell = this.grid[row][col];
               
                if (nextCell.isEmpty()) {
                    result += "_";
                }
                else {
                
                    // The next cell is not empty. Get the entity in it.
                    Entity entity = nextCell.getEntity();
                    
                    // Append the appropriate character representing each entity.
                    if (entity instanceof Tree)
                        result += "X";
                    else if (entity instanceof Explorer)
                             result += "#";
                         else if (entity instanceof Wanderer)
                                  result += "W";
                              else if (entity instanceof Chaser)
                                        result += "C";
                                   else if (entity instanceof Raft)
                                            result += "R";
                                       else if(entity instanceof Coin)
                                                 result += "0";
                }
                
                result += "  ";
            } // end for
                
            result += "\n";
        } // end for
        
        //result += creatures.toString();   // for debugging purposes
        
        return result;

    } // end toString
    
    public void clearScreen(){
    for (int i = 0; i < 5; i++)
       System.out.println("\n\n\n\n\n");    
    }
} // end Island
