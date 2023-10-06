package sourcecode;

import java.util.ArrayList;
import java.util.LinkedList;

public class Main {
    public static double q;
    private static final int numTests = 100;
    private static final double numQTests = 10.0;
    private static ArrayList<Cell> openCells = new ArrayList<>();


    /**
     * Get random int between min to max
     * @return randomly generated int
     */
    public static int rand(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    /**
     * Get random double between min to max
     * @return randomly generated double
     */
    public static double rand(double min, double max) {
        return ((Math.random() * (max - min)) + min);
    }





    /**
     * Run an experiment for Bot 1
     * @param ship the ship to run the experiment on
     * @return true if the bot made it to the button
     */
    private static boolean runBot1(Cell[][] ship) {
        //initialize the bot
        int randIndex = Main.rand(0, openCells.size()-1);
        Cell bot = openCells.get(randIndex);
        bot.isBot = true;

        //initialize the button
        randIndex = Main.rand(0, openCells.size()-1);
        Cell button = openCells.get(randIndex);
        button.isButton = true;

        if (bot.isButton) {
            //System.out.println("Bot spawns on button.");
            return true;
        }

        //initialize the fire
        LinkedList<Cell> fireCells = new LinkedList<>();
        randIndex = Main.rand(0, openCells.size()-1);
        Cell initialFire = openCells.get(randIndex);
        initialFire.setOnFire(true); //setting on fire automatically updates neighbors
        fireCells.add(initialFire);

        if (bot.getOnFire() || button.getOnFire()) {
            //System.out.println("Fire spawns on bot or button.");
            return false;
        }


        //printShip(ship, bot, button, initialFire);

        //BFS Shortest Path from bot -> button
        LinkedList<Cell> shortestPath = Bfs.shortestPathBFS(bot, button);
        if (shortestPath == null) {
            //System.out.println("Shortest Path is null.");
            return false;
        }

        shortestPath.removeFirst();

        //int t = 0;
        while (!shortestPath.isEmpty()) {
            //System.out.println("t = "+ t + " --> @(" + bot.getRow() + ", " + bot.getCol() + ")");
            //move the bot
            Cell neighbor = shortestPath.removeFirst();
            bot.isBot = false;
            neighbor.isBot = true;
            bot = neighbor;

            if (bot.isButton) {
                //System.out.println("Bot made it to the button!");
                return true;
            }
            else {
                //else, potentially advance fire
                LinkedList<Cell> copyFireCells = (LinkedList<Cell>) fireCells.clone();
                while (!copyFireCells.isEmpty()) {
                    Cell fireCell = copyFireCells.removeFirst();
                    tryFireNeighbor(fireCell.up, fireCells);
                    tryFireNeighbor(fireCell.down, fireCells);
                    tryFireNeighbor(fireCell.left, fireCells);
                    tryFireNeighbor(fireCell.right, fireCells);
                }

                if (bot.getOnFire() || button.getOnFire()) {
                    //System.out.println("The bot or button caught on fire :(");
                    return false;
                }
            }
            //t++;
        }
        //if the shortest path is fully traversed and bot didn't reach button, loss
        //System.out.println("Bot never reached button...");
        return false;
    }

    /**
     * Run an experiment for Bot 2
     * @param ship the ship to run the experiment on
     * @return true if the bot made it to the button
     */
    private static boolean runBot2(Cell[][] ship) {
        //initialize the bot
        int randIndex = Main.rand(0, openCells.size()-1);
        Cell bot = openCells.get(randIndex);
        bot.isBot = true;

        //initialize the button
        randIndex = Main.rand(0, openCells.size()-1);
        Cell button = openCells.get(randIndex);
        button.isButton = true;

        if (bot.isButton) {
            //System.out.println("Bot spawns on button.");
            return true;
        }

        //initialize the fire
        LinkedList<Cell> fireCells = new LinkedList<>();
        randIndex = Main.rand(0, openCells.size()-1);
        Cell initialFire = openCells.get(randIndex);
        initialFire.setOnFire(true); //setting on fire automatically updates neighbors
        fireCells.add(initialFire);

        if (bot.getOnFire() || button.getOnFire()) {
            //System.out.println("Fire spawns on bot or button.");
            return false;
        }


        //printShip(ship, bot, button, initialFire);


        //int t = 0;
        //System.out.println(t);
        while (!bot.isButton && !bot.getOnFire() && !button.getOnFire()) {
            //BFS Shortest Path from bot -> button
            LinkedList<Cell> shortestPath = Bfs.shortestPathBFS(bot, button);
            if (shortestPath == null) {
                //System.out.println("Shortest Path is null.");
                return false;
            }
            shortestPath.removeFirst();

            //System.out.println("t = "+ t + " --> @(" + bot.getRow() + ", " + bot.getCol() + ")");

            //move the bot
            Cell neighbor = shortestPath.removeFirst();
            bot.isBot = false;
            neighbor.isBot = true;
            bot = neighbor;

            if (bot.isButton) {
                //System.out.println("Bot made it to the button!");
                return true;
            }
            else {
                //else, potentially advance fire
                LinkedList<Cell> copyFireCells = (LinkedList<Cell>) fireCells.clone();
                while (!copyFireCells.isEmpty()) {
                    Cell fireCell = copyFireCells.removeFirst();
                    tryFireNeighbor(fireCell.up, fireCells);
                    tryFireNeighbor(fireCell.down, fireCells);
                    tryFireNeighbor(fireCell.left, fireCells);
                    tryFireNeighbor(fireCell.right, fireCells);
                }

                if (bot.getOnFire() || button.getOnFire()) {
                    //System.out.println("The bot or button caught on fire :(");
                    return false;
                }
            }
            //t++;
        }
        //if the shortest path is fully traversed and bot didn't reach button, loss
        //System.out.println("Bot never reached button...");
        return false;
    }




    /**
     * Try to ignite the given neighbor of a fire cell
     */
    private static void tryFireNeighbor(Cell neighbor, LinkedList<Cell> fireCells) {
        if (neighbor != null && Math.random() <= neighbor.flammability && neighbor.isOpen && !neighbor.getOnFire()) {
            neighbor.setOnFire(true);
            fireCells.add(neighbor);
        }
    }

    /**
     * Run an experiment for Bot 3
     * --> Very similar to bot 2, just changed shortestPath in BFS
     * @param ship the ship to run the experiment on
     * @return true if the bot made it to the button
     */
    private static boolean runBot3(Cell[][] ship) {
        //initialize the bot
        int randIndex = Main.rand(0, openCells.size()-1);
        Cell bot = openCells.get(randIndex);
        bot.isBot = true;

        //initialize the button
        randIndex = Main.rand(0, openCells.size()-1);
        Cell button = openCells.get(randIndex);
        button.isButton = true;

        if (bot.isButton) {
            //System.out.println("Bot spawns on button.");
            return true;
        }

        //initialize the fire
        LinkedList<Cell> fireCells = new LinkedList<>();
        randIndex = Main.rand(0, openCells.size()-1);
        Cell initialFire = openCells.get(randIndex);
        initialFire.setOnFire(true); //setting on fire automatically updates neighbors
        fireCells.add(initialFire);

        if (bot.getOnFire() || button.getOnFire()) {
            //System.out.println("Fire spawns on bot or button.");
            return false;
        }


        //printShip(ship, bot, button, initialFire);


        int t = 0;
        //System.out.println(t);
        while (!bot.isButton && !bot.getOnFire() && !button.getOnFire()) {
            //BFS Shortest Path from bot -> button
            LinkedList<Cell> shortestPath;

            //Avoid fire neighbors if possible
            shortestPath = Bfs.shortestPathBFS_Bot3(bot, button);
            if (shortestPath == null) {
                //if not possible, do the Bot 2 method
                shortestPath = Bfs.shortestPathBFS(bot, button);
                if (shortestPath == null)
                    return false;
                //System.out.println("Shortest Path is null.");
            }
            shortestPath.removeFirst();

            //System.out.println("t = "+ t + " --> @(" + bot.getRow() + ", " + bot.getCol() + ")");

            //move the bot
            Cell neighbor = shortestPath.removeFirst();
            bot.isBot = false;
            neighbor.isBot = true;
            bot = neighbor;

            if (bot.isButton) {
                //System.out.println("Bot made it to the button!");
                return true;
            }
            else {
                //else, potentially advance fire
                LinkedList<Cell> copyFireCells = (LinkedList<Cell>) fireCells.clone();
                while (!copyFireCells.isEmpty()) {
                    Cell fireCell = copyFireCells.removeFirst();
                    tryFireNeighbor(fireCell.up, fireCells);
                    tryFireNeighbor(fireCell.down, fireCells);
                    tryFireNeighbor(fireCell.left, fireCells);
                    tryFireNeighbor(fireCell.right, fireCells);
                }

                if (bot.getOnFire() || button.getOnFire()) {
                    //System.out.println("The bot or button caught on fire :(");
                    return false;
                }
            }
            t++;
        }
        //if the shortest path is fully traversed and bot didn't reach button, loss
        //System.out.println("Bot never reached button...");
        return false;
    }


    /**
     * Print the ship
     */
    private static void printShip(Cell[][] ship, Cell bot, Cell button, Cell fire) {
        System.out.println("x| 0 1 2 3 4 5 6 7 8 9");
        System.out.println(" ---------------------");
        for (int r = 0; r < Ship.D; r++) {
            System.out.print(r + "| ");
            for (int c = 0; c < Ship.D; c++) {
                if (ship[r][c].isOpen) {
                    if (ship[r][c].isBot)
                        System.out.print('s');
                    else if (ship[r][c].isButton)
                        System.out.print('g');
                    else if (ship[r][c].getOnFire())
                        System.out.print('f');
                    else
                        System.out.print(1);
                }
                else
                    System.out.print(0);
                System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println("\n\n");
    }


    /**
     * Run tests on the given bot number
     * @param bot the bot number
     * @return the number of total wins
     */
    private static int runTests(int bot) {
        LinkedList<Boolean> testResults = new LinkedList<>();
        //System.out.println("*********************************************");
        //System.out.println("Run " + numTests + " tests on Bot " + bot);
        for (int test = 1; test <= numTests; test++) {
            //System.out.print("TEST " + test + ": ");
            Cell[][] tempShip = Ship.makeShip();
            openCells = new ArrayList<>();
            for (int i = 0; i < tempShip.length; i++){
                for (int j = 0; j < tempShip[0].length; j++){
                    if (tempShip[i][j].isOpen)
                        openCells.add(tempShip[i][j]);
                }
            }
            boolean result = false;
            if (bot == 1) {
                result = (runBot1(tempShip));
                testResults.add(result);
            }
            else if (bot == 2) {
                result = (runBot2(tempShip));
                testResults.add(result);
            }
            else if (bot == 3) {
                result = (runBot3(tempShip));
                testResults.add(result);
            }

            //if (result) System.out.println("PASS"); else System.out.println("FAIL");
        }
        int totalWins = 0;
        for (Boolean result : testResults) {
            if (result)
                totalWins++;
        }
        //System.out.println("q: " + q);
        //System.out.println("Total Wins: " + totalWins);
        //System.out.println("*********************************************\n\n");
        return totalWins;
    }


    public static void main(String[] args) {
        //Initialize the ship
        /*
        Cell[][] ship = Ship.makeShip();
        for (int i = 0; i < ship.length; i++){
            for (int j = 0; j < ship[0].length; j++){
                if (ship[i][j].isOpen)
                    openCells.add(ship[i][j]);
            }
        }
         */
        //System.out.println("Initial Cell is at row " + Ship.initial.getRow() +
        //        " and col " + Ship.initial.getCol() + "\n\n\n");
        //System.out.println("0123456789");
        //System.out.println();
        //printShip();

        //BOT 1
        System.out.println("Bot 1");
        q = 0.1; runQTests(1);
        q = 0.25; runQTests(1);
        q = 0.50; runQTests(1);
        q = 0.75; runQTests(1);
        q = 0.9; runQTests(1);
        System.out.println();

        //BOT 2
        System.out.println("Bot 2");
        q = 0.1; runQTests(2);
        q = 0.25; runQTests(2);
        q = 0.50; runQTests(2);
        q = 0.75; runQTests(2);
        q = 0.9; runQTests(2);
        System.out.println();

        //BOT 3
        System.out.println("Bot 3");
        q = 0.1; runQTests(3);
        q = 0.25; runQTests(3);
        q = 0.50; runQTests(3);
        q = 0.75; runQTests(3);
        q = 0.9; runQTests(3);
        System.out.println();
    }

    private static void runQTests(int bot) {
        int sum = 0;
        for (int i = 1; i <= numQTests; i++) {
            sum += runTests(bot);
        }
        double avg = sum/numQTests;
        System.out.println("Avg Success Rate for q = " + q + " is " + avg);
    }
}