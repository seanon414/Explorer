
/**
 * A Wanderer is an Enemy.
 */

import java.util.Random;

public class Wanderer extends Enemy
{
    // instance variables - replace the example below with your own

    /**
     * Constructor for objects of class Wanderer
     */
    public Wanderer()
    {
        // initialise instance variables
        super();
    }

    public Wanderer(Location theLocation)
    {
        // initialize instance variables
        super(theLocation);
    }
    
    /**
     * move:
     * a wanderer moves by picking a direction randomly.
     */
    public void move(Island isle)
    {
        Random rand = new Random();
        
        int randomInt = rand.nextInt(4);
        
        Location dest;
        
        if (randomInt == 0) { 
           dest = Location.computeDestination(this.loc, Location.Direction.UP);
        }
        else if (randomInt == 1) {
                dest = Location.computeDestination(this.loc, Location.Direction.DOWN);
             }
             else if (randomInt == 2) {
                      dest = Location.computeDestination(this.loc, Location.Direction.LEFT);
                  }
                  else {
                      dest = Location.computeDestination(this.loc, Location.Direction.RIGHT);
                  }
                  
        // If the destination is a valid location (i.e. on the island and unoccupied), then move the creature to the destination.
        if (isle.isValidLocation(dest)) {
            isle.transfer(this, this.loc, dest);  // update the island
            this.loc = dest;                      // update the creature
        }
    }
}
