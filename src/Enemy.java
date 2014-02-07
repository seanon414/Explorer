
/**
 * Abstract class Enemy 
 */
public abstract class Enemy extends Creature
{
    // instance variables 
    
    /**
     * Constructors 
     */
    public Enemy()
    {
        // initialise instance variables
        super();
    }

    public Enemy(Location theLocation)
    {
        super(theLocation);
    }
    
    /**
     * move:
     * Each kind of creature moves in a different way,
     * e.g. The explorer moves according to keyboard input,
     * a wanderer moves by picking a direction randomly,
     * a chaser moves by picking a direction based on where the explorer is.
     */
    public abstract void move(Island isle);  
}
