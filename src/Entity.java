
/**
 * Abstract class Entity
 */

public abstract class Entity
{
    // instance variables 
    protected Location loc;

    /**
     * Constructors
     */
    public Entity()
    {
        // initialize instance variables
        this.loc = new Location(0, 0);
    }

    /**
     * Construct an Entity at the specified row and column.
     */
    public Entity(Location theLocation)
    {
        // initialize instance variables
        this.loc = theLocation;
    }        
    
    /**
      * getLocation:
      * @param none
      * @return the location
      */ 
     public Location getLocation() 
     {
         return this.loc;
     }
    
    /**
     * toString:
     * @param none
     * @return a String representation of this Entity.
     */
    public String toString()
    {
        return this.loc.toString();   
    }
}
