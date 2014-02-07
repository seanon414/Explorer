/**
 * There is one and only one Explorer (Singleton pattern).
 */

import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Explorer extends Creature 
{
    // instance variables 
    private static Explorer instance = null;
    private int numCoins = 0, numTrees = 3, health = 100, armor = 2, damage = 0;
    private boolean haveRaft = false, chest = false, legs = false, helm = false, boots = false;

    /**
     * getInstance:
     * @return the first (and only) instance of Explorer.
     * A new Explorer is constructed only the first time this method is called.
     * Otherwise, the Explorer already exists and is returned upon subsequent calls to this method.
     */
    public static Explorer getInstance()
    {
       if (instance == null)
           instance = new Explorer();

       return instance;
    }

    /**
     * Constructor for objects of class Explorer.
     * This constructor is private to ensure that outside classes do not create more than one Explorer.
     * This constructor is only invoked by the getInstance method.
     */
    private Explorer()
    {
        // initialize instance variables
        super();
    }
    
    /**
     * Construct an Explorer at the specified location.
     * This constructor is private to ensure that outside classes do not create more than one Explorer.
     * This constructor is only invoked by the getInstance method.
     */
    private Explorer(Location theLocation)
    {
        super(theLocation);
    }

    /**
     * getInstance:
     * @param a Location
     * @return the first (and only) instance of Explorer.
     * A new Explorer is constructed only the first time this method is called.
     * Otherwise, the Explorer already exists and is returned upon subsequent calls to this method.
     */
    public static Explorer getInstance(Location theLocation)
    {
       if (instance == null)
           instance = new Explorer(theLocation);

       return instance;
    }
    
    /**
     * move:
     * The explorer moves according to keyboard input.
     */
    @Override
    public void move(Island isle) throws GameOverException
    {
        Scanner keyboard = new Scanner(System.in);

        checkHealth();
        
        // Get the explorer's move from the user via the keyboard.
        System.out.println("Explorer, enter your move:");
        String input = keyboard.next();
        
        Location dest = this.loc;  // initialize destination to source location.
        
        // Compute the destination based on the user's input and current (source) location.
        if (input.equalsIgnoreCase("w")) {
            dest = Location.computeDestination(this.loc, Location.Direction.UP);
        }
        else if (input.equalsIgnoreCase("s")) {
                 dest = Location.computeDestination(this.loc, Location.Direction.DOWN);
             }
             else if (input.equalsIgnoreCase("a")) {
                      dest = Location.computeDestination(this.loc, Location.Direction.LEFT);
                  }
                  else if (input.equalsIgnoreCase("d")) {
                           dest = Location.computeDestination(this.loc, Location.Direction.RIGHT);
                       }
                       else if (input.equalsIgnoreCase("h")) {
                                help();
                            }
                            else if (input.equalsIgnoreCase("q")) {
                                     throw new GameOverException("Explorer chose to quit this game.");
                                 }
                                  else if(input.equalsIgnoreCase("x")){
                                    try {
                                     store();
                                    } catch (InterruptedException ex) {
                                         Logger.getLogger(Explorer.class.getName()).log(Level.SEVERE, null, ex);
                                     }
                                  }
                                

        
        
        
        checkWater(isle, dest);  // if destination cell outOfBounds and the Explorer does not have a raft, he will fall into the water.                       
        
        checkTree(isle, dest); // if destination cell has a tree, Explorer will cut down the tree. Up to 3 trees.
        
        checkRaft(isle, dest); // if destination cell has a raft, Explorer wins the game.
        
        checkCoin(isle, dest); // if destination cell has a coin, increment numCoins and delete the coin
        
        if (attacking(isle, dest)) 
        {
            try {
                isle.transfer(this, this.loc, dest);  // update the island
                this.loc = dest;                      // update the creature
                System.out.println("You have entered combat...");
                Thread.sleep(1000);
                System.out.println("You received " + getDamage() + " points of damage during combat.");
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Explorer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
        {
         // If the destination is a valid location (i.e. on the island and unoccupied), then move the creature to the destination.
         if (isle.isValidLocation(dest)) 
         {
             isle.transfer(this, this.loc, dest);  // update the island
             this.loc = dest;                      // update the creature
         }
        } // update the creature
    }
    
    /**
     * remove: remove the one instance of Explorer
     * Precondition: game is over
     * Postcondition: Explorer is reinitialized for the next game
     * @param none
     * @return none
     */
    public static void reset()
    {
        instance = null;
    }
    
    /**
     * Displays helpful information for the explorer.
     */
    public static void help()
    {
        for(int i = 0; i < 5; i++)
            System.out.println("\n\n\n\n\n");
            
        System.out.println("Welcome to \"Explore!\" The object of the game is to collect all the coins and not die");
        System.out.println("You are this little guy: #");
        System.out.println("And the trees of the forest look like this: X");
        System.out.println("You can navigate the woods by using the w, a, s, and d keys.");
        System.out.println("Left: 'a', Up: 'w', Down: 's', Right: 'd'");
        System.out.println("Entering 'x' will bring you to the store.");
        System.out.println("If you move onto a space with a creature on it you will attack it");
        System.out.println("Moving onto a coin(O) will add it to your bag");
        System.out.println("You can buy itmes with your coins. To do so enter (X) to open the store.");
        System.out.println("Moving onto a tree(X) will cut it down, you only can only cut down 3 trees unless you buy a new axe from the store.");
        System.out.println("Your coordinate is displayed at the top.");
        //System.out.println("If you start trapped, or want to begin all over again, type 'n' to start a new game and generate a new forest.");
        //System.out.println("Finally, when you're done playing, just type 'q' for the game to stop for easy quitting.");
        System.out.println("Enjoy! Type anything to continue back to your game.");
        
        Scanner keyboard = new Scanner(System.in);
        keyboard.next();
    }
        
    /**
     * checkWater: check if the specified location is in the water (i.e. not on the specified island) and if so, throw a GameOverException; otherwise do nothing.
     * @param an Island
     * @param a Location 
     * @return none
     */
    public void checkWater(Island isle, Location loc) throws GameOverException
    {
        if(!isle.onTheIsland(loc))
        {
            if(haveRaft == false)
                throw new GameOverException("Explorer fell off the island. Game Over.");
            else
                throw new GameOverException("Congratulations you have made it off the island using the raft. You win!!!");
        }
    }

    /**
     * checkRaft: check if the specified location is a raft and if so, you will pick up the raft.
     * @param an Island
     * @param a Location 
     * @return none
     */
    public void checkRaft(Island isle, Location loc)
    {
        Cell cell = isle.getCell(loc);
        
        Entity entity = cell.getEntity();
        if (entity instanceof Raft)
        {
            haveRaft = true;
            cell.deleteEntity();
        }
    }
    
    /**
     * checkCoin: check if the specified location is a Coin and if so, you will pick up the Coin and add it to your bag.
     * @param an Island
     * @param a Location 
     * @return none
     */
    public void checkCoin(Island isle, Location loc)
    {
        Cell cell2 = isle.getCell(loc);
        
        Entity entity = cell2.getEntity();
        if (entity instanceof Coin)
        {
            numCoins++;
            cell2.deleteEntity();
        }
        
    }
    
    public void spendCoin(){
        numCoins--;
    }
    
    /**
     * checkTree: check if the specified location is a Tree and if so, you will cut it down.
     * @param an Island
     * @param a Location 
     * @return none
     */
    public void checkTree(Island isle,  Location loc)
    {
        Cell cell3 = isle.getCell(loc);
        
        Entity entity = cell3.getEntity();
        if (entity instanceof Tree && numTrees > 0)
        {
            numTrees--;
            cell3.deleteEntity();
        }
    }
    
    public void addTree(){
        numTrees += 3;
    }
    
    /**
     * checkHealth: check if the Explorer's health is less then zero, if so throws a GameOverException.
     * @param an Island
     * @param a Location 
     * @return none
     */
    public void checkHealth() throws GameOverException
    {
        if(health <= 0)
            throw new GameOverException("Explorer has lost all his health and died. Game over");
    }
        
    public void setHealth(int num)
    {
        health = num;
    } 
    
    public void addHealth(){
        health += 25;
        if(health > 100)
            health = 100;
    }
    
    public void addChest() {
        chest = true;
    }
    
    public void addHelm() {
        helm = true;
    }
    
    public void addLegs() {
        legs = true;
    }
    
    public void addBoots() {
        boots = true;
    }
    
    /**
     * attacking: if it lands on a space with a creature on it returns true
     * @param a Location, the desired destination on the island
     * @return a boolean, if destination has a creature then returns true
     */
    public boolean attacking(Island isle, Location destination)
    {
        boolean outcome = false;
        Cell cell4 = isle.getCell(destination);
        
        Entity entity = cell4.getEntity();
        
        // If there is a creature in the desired location, then method returns true
        if(entity instanceof Enemy)
        {
            outcome = true;

            cell4.deleteEntity();
            
            isle.removeCreature(destination); // precondition: there is a creature in destination that needs to be removed.
                
            // Takes damage when moving onto an Enemy
            int newHealth = Explorer.getInstance().getHealth() - combat();
            setHealth(newHealth);
        }

        return outcome;
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
        int randomInt = rand2.nextInt(15) + 8 - armor;
        // Subtracts from health
        damage = randomInt;
        if (damage < 0)
            damage = 0;
        return damage;
    }
    
    /**
     * isEnemy: returns true or false if an Enemy is there
     * @param a Location, the desired destination on the island
     * @param 
     */
    public boolean isEnemy(Island isle, Location destination)
    {
        boolean outcome = false;
        Cell cell5 = isle.getCell(destination);
        
        Entity entity = cell5.getEntity();
        
        // If there is a creature in the desired location, then method returns true
        if(entity instanceof Enemy || entity instanceof Thing)
        {
            outcome = true;
        }

        return outcome;
    }
    
    /*
     * Getter method for all the variables
     */
    public int getNumCoins()
    {
        return numCoins;
    }
    
    public int getNumTrees()
    {
        return numTrees;
    }
    
    public boolean getRaft()
    {
        return haveRaft;
    }
    
    public int getHealth()
    {
        return health;
    }
    
    public int getDamage(){
        return damage;
    }

    public boolean getChest(){
        return chest;
    }
    
    public boolean getHelm() {
        return helm;
    }
    
    public boolean getLegs() {
        return legs;
    }
    
    public boolean getBoots() {
        return boots;
    }
    
    public int getArmorLevel() {
        return armor;
    }
    
    // Prints what armor the explorer currently has
    public String printArmor() {
        String output = "";
        if(chest == true)
            output = output + " Chest";
        if(helm == true) 
            output = output + " Helm";
        if(legs == true)
            output = output + " Legs";
        if(boots == true)
            output = output + " Boots";
        return output;
    }

    
    /*
     * Store for the explorer to buy items in 
     */
    private void store() throws InterruptedException {
        Scanner keyboard = new Scanner(System.in);
        
        String more;
        String input;
        
        for (int i = 0; i < 5; i++)
            System.out.println("\n\n\n\n\n");
        
            System.out.println("Welcome to the island store.");
            System.out.println("You have " + getNumCoins() + " coins remaining.");
            System.out.println("These are the current items in stock.");
            System.out.println("Armor reduces damage received in combat.");
            System.out.println("a - Health Pack, increases health by 25 points. Cost 1 coin.(Max health is 100)");
            System.out.println("b - New axe, lets you cut down 3 more trees. Cost 1 coin.");
            System.out.println("c - New armor for chest. Cost 2 coins.");
            System.out.println("d - New armor for head. Cost 1 coin.");
            System.out.println("e - New armor for legs. Cost 2 coins.");
            System.out.println("f - New boots. Cost 1 coin.");
            System.out.println("What would you like to purchase?");

        
        
            do {      
                input = keyboard.next();
                if(numCoins < 1 && !input.equalsIgnoreCase("n") ) {
                    System.out.println("You dont have enough coins to make a purchase.");
                    Thread.sleep(500);
                    input = "n";}
                else{
                    // Health
                    if(input.equalsIgnoreCase("a")) {
                        addHealth();
                        numCoins--;
                        System.out.println("Thank you for purchasing a health pack");
                    }
                    // Axe
                    else if(input.equalsIgnoreCase("b")){
                        addTree();
                        numCoins--;
                        System.out.println("Thank you for purchasing a new axe.\n");
                    }
                    // Chest
                    else if(input.equalsIgnoreCase("c")){
                        if(numCoins > 1) {
                            if(getChest() == true)
                                System.out.println("You already own chest armor. \n");
                            else{
                                addChest();
                                numCoins -= 2;
                                armor += 2;
                                System.out.println("Thank you for purchasing new chest armor. \n");
                            }
                        }
                        else
                            System.out.println("Sorry, you dont have enough coins for chect armor.");
                    }
                    // Helm
                    else if(input.equalsIgnoreCase("d")){
                        if(getHelm() == true)
                                System.out.println("You already own head armor. \n");
                        else{
                            addHelm();
                            numCoins--;
                            armor += 2;
                            System.out.println("Thank you for purchasing new head armor. \n");
                        }
                    }
                    // Legs
                    else if(input.equalsIgnoreCase("e")){
                        if(numCoins > 1) {
                            if(getLegs() == true)
                                System.out.println("You already own leg armor. \n");
                            else{
                                addLegs();
                                numCoins -= 2;
                                armor += 2;
                                System.out.println("Thank you for purchasing new leg armor. \n");
                            }
                        }
                        else
                            System.out.println("Sorry, you dont have enough coins for leg armor.");
                    }
                    // Boots
                    else if(input.equalsIgnoreCase("f")){
                        if(getBoots() == true)
                                System.out.println("You already own boots. n");
                            else{
                                addBoots();
                                numCoins--;
                                armor += 2;
                                System.out.println("Thank you for purchasing new boots. \n");
                            }
                    }
                    // Leaving the store
                    else if(input.equalsIgnoreCase("n")){
                        System.out.println("Thank you for visiting the store.");
                    }
                    else{
                        System.out.println("You have not entered a valid input!");}
                    if(!input.equalsIgnoreCase("n")){
                        Thread.sleep(1000);
                        System.out.println("You have " + getNumCoins() + " coins remaining.");   
                        System.out.println("Would you like to purchase anything else? ");
                        System.out.println("Or to leave please enter 'n'.");
                    }
                }

            }while(!input.equalsIgnoreCase("n"));
            System.out.println("Good Bye.");
            Thread.sleep(1750);
        }
        
}