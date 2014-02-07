
/**
 * Driver has the main method that creates a World and controls all the objects in this world.
 * 
 * @author David Chays, based on Nicholas Miceli's RunManRun, and Jeroo
 * @version March 20, 2011
 */

import java.util.Scanner;

public class Driver
{
    /**
     * Main: allow the user to play games until (s)he decides to quit.
     */
    public static void main(String[] args)
    {
        // Play games, one at a time, until user decides to quit.
        
        String answer;
                
        // Set up Scanner object to read input from the keyboard.
        Scanner scan = new Scanner (System.in);

        do 
        {
           // Play one game.
           playGame();
           
           // Ask the user whether or not to play again.
           System.out.print ("Do you want to play again? Enter 'y' or 'n': ");
           answer = scan.next();
        } while (answer.equalsIgnoreCase("y"));
        
        System.out.println ("End of run.");
    }
        
    /**
     * playGame:
     * Display instructions, update the world and show the world.
     * Extension: exception will be thrown to indicate that game is over...
     */
    public static void playGame()    
    {    
        //DebugHandler d = new DebugHandler(0);
        
        try {
            
            // Print instructions.
            Explorer.help(); 
            
            // Create a new world.
            World myWorld = new World();
            //d.print("world created");
            
            System.out.println(myWorld);
            //d.print("initial world printed");
            
            while (true) // loop until GameOverException occurs
            {
               // Update the status of this world.
               myWorld.update();
               //d.print("world updated");
            
               // Show the current status of this world, after all creatures have moved.
               System.out.println(myWorld);
               
               //d.print("world printed");
            } // end while
            
        } // end try
        catch (GameOverException e) {
            System.out.println(e);
            Explorer.reset(); // reinitialize the explorer in case a new game is played
        }
        catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }
}
