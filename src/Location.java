
/**
 * Location is indicated by a row and column.
 */
public class Location
{
    public enum Direction {UP, DOWN, LEFT, RIGHT}

    // instance variables 
    private int row, col;

    /**
     * Constructors for objects of class Location
     */
    public Location()
    {
        // initialize instance variables
        this.row = 0;
        this.col = 0;
    }

    public Location(int theRow, int theCol)
    {
        // initialize instance variables
        this.row = theRow;
        this.col = theCol;
    }
    
    /**
     * getRow:
     * @param none
     * @return an int, the row
     */
    public int getRow()
    {
        return this.row;
    }
    
    /**
     * getCol:
     * @param none
     * @return an int, the column
     */
    public int getCol()
    {
        return this.col;
    }    
    
    /**
     * toString:
     * @param none
     * @return a String representation of this Location.
     */
    public String toString()
    {
        return "(" + this.row + ", " + this.col + ")";
    }
    
    /**
     * computeDestination: compute the desired new location based on the current (source) location and the desired direction 
     * @param a Location, the source location
     * @param a Direction, the desired direction 
     * @return a Location, the destination 
     */
    public static Location computeDestination(Location source, Direction direction)
    {
        int sourceRow = source.getRow();
        int sourceCol = source.getCol();
        int destRow, destCol;
        
        switch (direction) {
            case UP :
                   destRow = sourceRow - 1;
                   destCol = sourceCol;  
                   break;
            
            case DOWN : 
                   destRow = sourceRow + 1;
                   destCol = sourceCol;
                   break;
                   
            case LEFT :
                   destRow = sourceRow;
                   destCol = sourceCol - 1;  
                   break;
            
            case RIGHT :
                   destRow = sourceRow;
                   destCol = sourceCol + 1;    
                   break;
                   
            default:
                    destRow = sourceRow;
                    destCol = sourceCol;
        } // end switch
        
        Location destination = new Location(destRow, destCol);
        
        return destination;
    }
    
    public boolean equals(Location loc)
    {
        return (this.getRow() == loc.getRow() && this.getCol() == loc.getCol()); 
    }
}
