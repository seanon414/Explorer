
/**
 * CreatureCollection 
 * Currently the creatures are stored in an array.
 * Exercise: replace array with a List...
 */

public class CreatureCollectionArrayBased implements CreatureCollectionInterface
{
    // constants
    private static final int MAX_NUM_CREATURES = 1000; 
   
    // instance variables 
    private Creature[] creatures = new Creature[MAX_NUM_CREATURES];
    private int numCreatures;
    private int creatureIndex; // used for iterating over the collection
    
    /**
     * Constructor for objects of class CreatureCollection
     */
    public CreatureCollectionArrayBased()
    {
        // initialise instance variables
        this.creatures = new Creature[MAX_NUM_CREATURES]; 
        // Exercise: replace array with a List  
        
        this.numCreatures = 0;
        this.creatureIndex = 0;
    }

    /**
     * add: add the given creature to the array creatures.
     * @param a Creature
     * @return none
     */
    public void add(Creature c)
    {
        DebugHandler d = new DebugHandler(1);
        d.print("inside add");
        this.creatures[this.numCreatures] = c;
        d.print("updated creatures array");
        this.numCreatures++;
        d.print("updated numCreatures");
    }
    
    /**
     * hasMoreCreatures:
     * @param none
     * @return a boolean, true if there are more creatures in the collection, false otherwise
     */
    public boolean hasMoreCreatures()
    {
        DebugHandler d = new DebugHandler(0);
        d.print("numCreatures = " + numCreatures);
        d.print("creatureIndex = " + creatureIndex);
        
        boolean result = ((this.numCreatures > 0) && (this.creatureIndex < this.numCreatures));
        
        // If done with this iteration, reset index.
        if (this.creatureIndex == this.numCreatures)
            this.creatureIndex = 0;
        
        return result;
    }
    
    /**
     * nextCreature: used to iterate over the collection of creatures,
     * i.e. return a different creature each time it is called, and
     * if called numCreatures times, will return all creatures in the collection.
     * 
     * @param none
     * @return a Creature, the next one in the collection, or null if there are no creatures or none left.
     */
    public Creature nextCreature()
    {
        if ((this.numCreatures == 0) || (this.creatureIndex == this.numCreatures))
            return null;    
            
        Creature nextCreature = creatures[creatureIndex];   
        
        this.creatureIndex ++; 
            
        return nextCreature;
    }
    
    /**
     * toString:
     * @param none
     * @return a String, including all creatures and their locations.
     */
    public String toString()
    {
        String result = "\nCreatures: \n";
        
        for (int i=0; i<numCreatures; i++)
        {
            Creature aCreature = creatures[i];
            
            if (aCreature instanceof Explorer)
                result += "Explorer at " + aCreature.toString() + "\n";
            else if (aCreature instanceof Wanderer)
                     result += "Wanderer at " + aCreature.toString() + "\n";
                 else if (aCreature instanceof Chaser)
                          result += "Chaser at " + aCreature.toString() + "\n";
                      else 
                          result += "Unknown creature at " + aCreature.toString() + "\n";                  
        }
        
        result += "End of creature list.\n";
        return result;
    }
    
    
    
    /**
     * remove: removes the creature a destination from the list.
     * @param destination of the explorer
     * @return none
     */
    public void remove(Location destination)
    {
        boolean found = false;
        int position = -1;
        
        for(int i = 0; i < this.numCreatures && !found; i++)
        {
            Creature aCreature = creatures[i];
            
            if(aCreature.getLocation().equals(destination)) {
                found = true;
                position = i;
            }
        }
        
        if (found) {
            // shift...
            for (int j = position+1; j < numCreatures; j++) {
                
                 creatures[j-1] = creatures[j];    
            } 
            
            this.numCreatures --;
        }
    }
    
}
