/**
 * Each cell in the grid is either empty or has one entity.
 */
public class Cell
{
    // instance variables 
    private Entity entity;

    /**
     * Constructor for objects of class Cell
     */
    public Cell()
    {
        // initialise instance variables
        this.entity = null;
    }

    public Cell(Entity e)
    {
        // initialise instance variables
        this.setEntity(e);
    }

    
    /**
     * isEmpty:
     * @param none
     * @return a boolean, true if this cell is empty, false otherwise
     */
    public boolean isEmpty()
    {
        return (this.entity == null);
    }
    
    /**
     * setEntity:
     * @param the entity to put in the cell
     */
    public void setEntity(Entity e)
    {
        this.entity = e;
    }
    
    /**
     * getEntity:
     * @param none
     * @return the entity in the cell, or null if the cell is empty
     */
    public Entity getEntity()
    {
        return this.entity;   
    }
    
    /**
     * delete: delete the entity in this cell.
     * @param none
     * @return none
     */
    public void deleteEntity()
    { 
        this.entity = null;
    }
}
