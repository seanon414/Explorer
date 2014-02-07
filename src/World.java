/**
 * Each World consists of one island.
 * Possible extension: each world may have more than island...
 */
public class World
{
    // instance variables 
    private Island island;
    
    /**
     * Constructor for objects of class World
     */
    public World()
    {
        // initialise instance variables
        island = new Island(); 
    }

    /**
     * update: update all components of this world.
     * @param none
     * @return none
     */
    public void update() throws GameOverException, InterruptedException 
    {
        // The world has only one island, so we only need to update one island.
        island.update();
    }
    
    /**
     * toString: 
     * @param none
     * @return a String representation of this world.
     */
    public String toString()
    { 
        return (island.toString());   
    }
    
}

