
/**
 * DebugHandler 
 */

public class DebugHandler
{
    // instance variables - replace the example below with your own
    private int debugLevel;

    /**
     * Constructor for objects of class DebugHandler
     */
    public DebugHandler(int level)
    {
        // initialise instance variables
        this.debugLevel = level;
    }

    /**
     * print: 
     * @param a String, to print if debugLevel is 1, or to ignore otherwise.
     * @return none
     */
    public void print(String message)
    {
        if (this.debugLevel == 1)
            System.out.println (message);
    }
}
