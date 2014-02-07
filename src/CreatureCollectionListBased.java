
/**
 * CreatureCollectionListBased:
 * Creatures are stored in a list.
 * Future exercise: complete this class and update Island to use this instead of CreatureCollectionArrayBased.
 */

public class CreatureCollectionListBased implements CreatureCollectionInterface
{   
    // instance variables 
    
    private ListInterface creatures;
    private int numCreatures;
    private int creatureIndex; // used for iterating over the collection
    
    /**
     * Constructor for objects of class CreatureCollection
     */
    public CreatureCollectionListBased()
    {
        this.creatures = new ListReferenceBased();
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
        this.creatures.add(this.numCreatures + 1, c);
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
            
        Creature nextCreature = (Creature)creatures.get(creatureIndex + 1);   
        
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
            Creature aCreature = (Creature)creatures.get(i);
            
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
        
        for(int i = 1; i <= this.numCreatures && !found; i++)
        {
            Creature aCreature = (Creature)this.creatures.get(i);
            
            if(aCreature.getLocation().equals(destination)) {
                found = true;
                position = i;
            }
        }
        
        if (found) {
            
            this.creatures.remove(position);
            
            this.numCreatures --;
        }
    }
    
}
