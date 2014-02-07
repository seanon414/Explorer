
/**
 * Abstract class Creature 
 */
public abstract class Creature extends Entity
{    
    /**
     * Constructors
     */
    public Creature()
    {
       super();
    }

    public Creature(Location theLocation)
    {
        super(theLocation);
    }
        
    /**
     * move:
     * Each kind of creature moves in a different way,
     * e.g. The explorer moves according to keyboard input,
     * a wanderer moves by picking a direction randomly,
     * a chaser moves by picking a direction based on where the explorer is.
     * If the move is a valid one, i.e. the target cell is on the island and is not occupied,
     * then the appropriate cell of the island is updated by transferring the creature to that cell.
     * @param an Island
     * @return none
     */
    public abstract void move(Island isle) throws InterruptedException ; // throws GameOverException;    
    
}
