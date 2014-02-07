
/**
 * A Chaser is an Enemy.
 */

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Chaser extends Enemy
{
    int damage;

    // instance variables

    /**
     * Constructor for objects of class Wanderer
     */
    public Chaser()
    {
        // initialize instance variables
        super();
    }

    public Chaser(Location theLocation)
    {
        // initialize instance variables
        super(theLocation);
    }
    
    /**
     * move:
     * a chaser moves by picking a direction based on where the explorer is.
     */
    public void move(Island isle) 
    {
        // Locate the explorer.
        Explorer explorer = Explorer.getInstance();
        Location explorerLocation = explorer.getLocation();
        int explorerRow = explorerLocation.getRow();
        int explorerCol = explorerLocation.getCol();
        
        int diffRow = this.loc.getRow() - explorerRow; // difference in rows
        int diffCol = this.loc.getCol() - explorerCol; // difference in columns
        
        // Compute desired destination based on the explorer's location.
        
        Location dest;
        
        if (Math.abs(diffRow) > Math.abs(diffCol)) {
            // Difference in rows is greater than difference in columns,
            // so move vertically to get closer to the explorer.
            if (diffRow > 0) {
                dest = Location.computeDestination(this.loc, Location.Direction.UP);
                if(checkAttack(isle, dest))
                {
                    try {
                        chaserAttack(isle, dest);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Chaser.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                else
                {
                    if (isle.isValidLocation(dest)) {
                        isle.transfer(this, this.loc, dest);  // update the island
                        this.loc = dest;                      // update the creature
                    }
                    else{
                        dest = Location.computeDestination(this.loc, Location.Direction.LEFT);
                        if(checkAttack(isle, dest))
                        {
                            try {
                                chaserAttack(isle, dest);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(Chaser.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        if (isle.isValidLocation(dest)) {
                            isle.transfer(this, this.loc, dest);  // update the island
                            this.loc = dest;                      // update the creature
                        }
                        else{
                            dest = Location.computeDestination(this.loc, Location.Direction.RIGHT);
                            if(checkAttack(isle, dest))
                            {
                                try {
                                    chaserAttack(isle, dest);
                                } catch (InterruptedException ex) {
                                    Logger.getLogger(Chaser.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                            if (isle.isValidLocation(dest)) {
                                isle.transfer(this, this.loc, dest);  // update the island
                                this.loc = dest;                      // update the creature
                            }
                        }
                    }
                }
            }
            else {
                dest = Location.computeDestination(this.loc, Location.Direction.DOWN);
                 if (isle.isValidLocation(dest)) {
                    isle.transfer(this, this.loc, dest);  // update the island
                    this.loc = dest;                      // update the creature
                }
                else{
                    dest = Location.computeDestination(this.loc, Location.Direction.LEFT);
                    if (isle.isValidLocation(dest)) {
                        isle.transfer(this, this.loc, dest);  // update the island
                        this.loc = dest;                      // update the creature
                    }
                        else{
                            dest = Location.computeDestination(this.loc, Location.Direction.RIGHT);
                        if (isle.isValidLocation(dest)) {
                            isle.transfer(this, this.loc, dest);  // update the island
                            this.loc = dest;                      // update the creature
                        }
                    }
                }
            
            }
        }
        else {
            // Move horizontally to get closer to the explorer.
            if (diffCol > 0) {
                dest = Location.computeDestination(this.loc, Location.Direction.LEFT);
                 if (isle.isValidLocation(dest)) {
                    isle.transfer(this, this.loc, dest);  // update the island
                    this.loc = dest;                      // update the creature
                }
                else{
                    dest = Location.computeDestination(this.loc, Location.Direction.UP);
                    if (isle.isValidLocation(dest)) {
                        isle.transfer(this, this.loc, dest);  // update the island
                        this.loc = dest;                      // update the creature
                    }
                        else{
                        dest = Location.computeDestination(this.loc, Location.Direction.DOWN);
                        if (isle.isValidLocation(dest)) {
                            isle.transfer(this, this.loc, dest);  // update the island
                            this.loc = dest;                      // update the creature
                        }
                    }
                }
            }
            else {
                dest = Location.computeDestination(this.loc, Location.Direction.RIGHT);
                 if (isle.isValidLocation(dest)) {
                    isle.transfer(this, this.loc, dest);  // update the island
                    this.loc = dest;                      // update the creature
                }
                else{
                    dest = Location.computeDestination(this.loc, Location.Direction.UP);
                    if (isle.isValidLocation(dest)) {
                        isle.transfer(this, this.loc, dest);  // update the island
                        this.loc = dest;                      // update the creature
                    }
                        else{
                            dest = Location.computeDestination(this.loc, Location.Direction.DOWN);
                        if (isle.isValidLocation(dest)) {
                            isle.transfer(this, this.loc, dest);  // update the island
                            this.loc = dest;                      // update the creature
                        }
                    }
                }
            }
        } // end if..else
    }



    /**
     * chaserAttack: check to see if the space a chaser is moving to is an Explorer.
     */
    public boolean checkAttack(Island isle, Location destination)
    {
        boolean outcome;
        
        Cell cell4 = isle.getCell(destination);
        
        Entity entity = cell4.getEntity();
        
        if(entity instanceof Explorer){
            outcome = true;
        }
        else
            outcome = false;
        return outcome;
    }
    
    /**
     * chaserAttack: will 
     * 
     */
    public void chaserAttack(Island isle, Location destination) throws InterruptedException
    {
        Cell chaserCell = isle.getCell(this.getLocation());

        chaserCell.deleteEntity();
            
        isle.removeCreature(this.getLocation()); // precondition: there is a creature in destination that needs to be removed.
                
        // Takes damage when chaser attacks the Explorer
        int newHealth = Explorer.getInstance().getHealth() - combat();
        Explorer.getInstance().setHealth(newHealth);
        
        System.out.println("A chaser has attacked you, you have entered combat...");
        Thread.sleep(1000);
        System.out.println("You received " + damage + " points of damage during combat.");
        Thread.sleep(1000);
    }
    
    /**
     * combat: computs the amount of health lost from combat with the creature
     * @param none
     * @return none
     */
    public int combat()
    {
        // Creates a random number to subtract from the health of the explorer
        Random rand2 = new Random();
        int randomInt = rand2.nextInt(15) + 8 - Explorer.getInstance().getArmorLevel();
        // Subtracts from health
        damage = randomInt;
        if (damage < 0)
            damage = 0;
        return damage;
    }
}
