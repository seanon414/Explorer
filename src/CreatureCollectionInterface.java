
/**
 * CreatureCollectionInterface:
 * Any class that implements this interface
 * must keep track of a collection of creatures (e.g. in an array or list...)
 * and provide methods to add a creature to the collection,
 * allow access to each creature in the collection (hasMoreCreatures and nextCreature methods),
 * and a toString method that returns a String representation of all creatures. 
 */

public interface CreatureCollectionInterface
{  
    /**
     * add: add the given creature to the array creatures.
     * @param a Creature
     * @return none
     */
    public void add(Creature c);
    
    /**
     * hasMoreCreatures: used to iterate over the collection of creatures,
     * i.e. tell us if there are more creatures that we have not yet accessed.
     * @param none
     * @return a boolean, true if there are more creatures in the collection, false otherwise.
     */
    public boolean hasMoreCreatures();

    /**
     * nextCreature: used to iterate over the collection of creatures,
     * i.e. return a different creature each time it is called, and
     * if called numCreatures times, will return all creatures in the collection.
     * 
     * @param none
     * @return a Creature, the next one in the collection, or null if there are no creatures or none left.
     */
    public Creature nextCreature();
    
    /**
     * toString:
     * @param none
     * @return a String, including all creatures and their locations.
     */
    public String toString();
    
    /**
     * remove: removes a creature from the list of creatures
     * @param destination of the explorer
     * @return none
     */
    public void remove(Location desination);
}
