package sourcecode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class Main {
    public static double q;
    private static final int numTests = 100;
    private static final double numQTests = 1.0;
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
    private static Boolean runBot1(Cell[][] ship) {
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
            return null;
        }

        //initialize the fire
        LinkedList<Cell> fireCells = new LinkedList<>();
        randIndex = Main.rand(0, openCells.size()-1);
        Cell initialFire = openCells.get(randIndex);
        initialFire.setOnFire(true); //setting on fire automatically updates neighbors
        fireCells.add(initialFire);

        if (bot.getOnFire() || button.getOnFire()) {
            //System.out.println("Fire spawns on bot or button.");
            return null;
        }

        //If the bot is closer than the fire, it will definitely win. Return true.
        if(checkDistBotVsFire(bot, initialFire, button, ship))
            return null;

        //printShip(ship, bot, button, initialFire);

        //BFS Shortest Path from bot -> button
        LinkedList<Cell> shortestPath = Bfs.shortestPathBFS(bot, button, ship);
        if (shortestPath == null) {
            //System.out.println("Shortest Path is null.");
            return null;
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
        return null;
    }

    /**
     * Run an experiment for Bot 2
     * @param ship the ship to run the experiment on
     * @return true if the bot made it to the button
     */
    private static Boolean runBot2(Cell[][] ship) {
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
            return null;
        }

        //initialize the fire
        LinkedList<Cell> fireCells = new LinkedList<>();
        randIndex = Main.rand(0, openCells.size()-1);
        Cell initialFire = openCells.get(randIndex);
        initialFire.setOnFire(true); //setting on fire automatically updates neighbors
        fireCells.add(initialFire);

        if (bot.getOnFire() || button.getOnFire()) {
            //System.out.println("Fire spawns on bot or button.");
            return null;
        }

        //If the bot is closer than the fire, it will definitely win. Return true.
        if(checkDistBotVsFire(bot, initialFire, button, ship))
            return null;

        //printShip(ship, bot, button, initialFire);


        //int t = 0;
        //System.out.println(t);
        while (!bot.isButton && !bot.getOnFire() && !button.getOnFire()) {
            //BFS Shortest Path from bot -> button
            LinkedList<Cell> shortestPath = Bfs.shortestPathBFS(bot, button, ship);
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
        return null;
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
    private static Boolean runBot3(Cell[][] ship) {
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
            return null;
        }

        //initialize the fire
        LinkedList<Cell> fireCells = new LinkedList<>();
        randIndex = Main.rand(0, openCells.size()-1);
        Cell initialFire = openCells.get(randIndex);
        initialFire.setOnFire(true); //setting on fire automatically updates neighbors
        fireCells.add(initialFire);

        if (bot.getOnFire() || button.getOnFire()) {
            //System.out.println("Fire spawns on bot or button.");
            return null;
        }

        //If the bot is closer than the fire, it will definitely win. Return true.
        if(checkDistBotVsFire(bot, initialFire, button, ship))
            return null;

        //printShip(ship, bot, button, initialFire);


        //int t = 0;
        //System.out.println(t);
        while (!bot.isButton && !bot.getOnFire() && !button.getOnFire()) {
            //BFS Shortest Path from bot -> button
            LinkedList<Cell> shortestPath;

            //Avoid fire neighbors if possible
            shortestPath = Bfs.shortestPathBFS_Bot3(bot, button, ship);
            if (shortestPath == null) {
                //if not possible, do the Bot 2 method
                shortestPath = Bfs.shortestPathBFS(bot, button, ship);
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
            //t++;
        }
        //if the shortest path is fully traversed and bot didn't reach button, loss
        //System.out.println("Bot never reached button...");
        return null;
    }

    /**
     * Runs simulation using bot 3 logic
     */
    /*
    private static boolean runSimulation_Bot4(Cell bot, Cell button, LinkedList<Cell> fireCells, Cell[][] ship) {
        while (!bot.isButton && !bot.getOnFire() && !button.getOnFire()) {
            //BFS Shortest Path from bot -> button
            LinkedList<Cell> shortestPath;

            //Avoid fire neighbors if possible
            shortestPath = Bfs.shortestPathBFS_Bot3(bot, button, ship);
            if (shortestPath == null) {
                //if not possible, do the Bot 2 method
                shortestPath = Bfs.shortestPathBFS(bot, button, ship);
                if (shortestPath == null)
                    return false;
                //System.out.println("Shortest Path is null.");
            }
            //shortestPath.removeFirst();

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
    */


    /**
     * Run an experiment for Bot 4
     */
    /*
    private static boolean runBot4(Cell[][] ship) {
        //initialize the bot
        int randIndex = Main.rand(0, openCells.size() - 1);
        Cell bot = openCells.get(randIndex);
        bot.isBot = true;

        //initialize the button
        randIndex = Main.rand(0, openCells.size() - 1);
        Cell button = openCells.get(randIndex);
        button.isButton = true;

        if (bot.isButton) {
            return true;
        }

        //initialize the fire
        LinkedList<Cell> fireCells = new LinkedList<>();
        randIndex = Main.rand(0, openCells.size() - 1);
        Cell initialFire = openCells.get(randIndex);
        initialFire.setOnFire(true); //setting on fire automatically updates neighbors
        fireCells.add(initialFire);

        if (bot.getOnFire() || button.getOnFire()) {
            return false;
        }

        printShip(ship, bot, button, initialFire);

        int t = 0;
        while (!bot.isButton && !bot.getOnFire() && !button.getOnFire()) {
            t++;
            int[] wins = new int[4]; //0 up, 1 down, 2 left, 3 right

            int runs = 4;
            for (int i = 0; i < runs; i++) {
                if (bot.up != null && bot.up.isOpen) {
                    Cell[][] tempShip = copyShip(ship);
                    Cell tempBot = tempShip[bot.up.getRow()][bot.up.getCol()];
                    Cell tempButton = tempShip[button.getRow()][button.getCol()];
                    LinkedList<Cell> tempFireCells = new LinkedList<>();
                    for (Cell fireCell : fireCells) {
                        tempFireCells.add(tempShip[fireCell.getRow()][fireCell.getCol()]);
                    }
                    if (runSimulation_Bot4(tempBot, tempButton, tempFireCells, tempShip))
                        wins[0]++;
                }
                if (bot.down != null && bot.down.isOpen) {
                    Cell[][] tempShip = copyShip(ship);
                    Cell tempBot = tempShip[bot.down.getRow()][bot.down.getCol()];
                    Cell tempButton = tempShip[button.getRow()][button.getCol()];
                    LinkedList<Cell> tempFireCells = new LinkedList<>();
                    for (Cell fireCell : fireCells) {
                        tempFireCells.add(tempShip[fireCell.getRow()][fireCell.getCol()]);
                    }
                    if (runSimulation_Bot4(tempBot, tempButton, tempFireCells, tempShip))
                        wins[1]++;
                }
                if (bot.left != null && bot.left.isOpen) {
                    Cell[][] tempShip = copyShip(ship);
                    Cell tempBot = tempShip[bot.left.getRow()][bot.left.getCol()];
                    Cell tempButton = tempShip[button.getRow()][button.getCol()];
                    LinkedList<Cell> tempFireCells = new LinkedList<>();
                    for (Cell fireCell : fireCells) {
                        tempFireCells.add(tempShip[fireCell.getRow()][fireCell.getCol()]);
                    }
                    if (runSimulation_Bot4(tempBot, tempButton, tempFireCells, tempShip))
                        wins[2]++;
                }
                if (bot.right != null && bot.right.isOpen) {
                    Cell[][] tempShip = copyShip(ship);
                    Cell tempBot = tempShip[bot.right.getRow()][bot.right.getCol()];
                    Cell tempButton = tempShip[button.getRow()][button.getCol()];
                    LinkedList<Cell> tempFireCells = new LinkedList<>();
                    for (Cell fireCell : fireCells) {
                        tempFireCells.add(tempShip[fireCell.getRow()][fireCell.getCol()]);
                    }
                    if (runSimulation_Bot4(tempBot, tempButton, tempFireCells, tempShip))
                        wins[3]++;
                }
            }

            System.out.println("time t = " + t + "   BOT POSITION: " + "(" + bot.getRow() + ", " + bot.getCol() + ")");
            int indexOfMax = 0;
            int max = wins[0];
            /*if(wins[0] > 0 || wins[1] > 0 || wins[2] > 0 || wins[3] > 0){
                for (int i = 1; i < wins.length; i++) {
                    if (max < wins[i] && ) {
                        max = wins[i];
                        indexOfMax = i;
                    }
                }
            }

            if(bot.up != null && bot.up.isOpen && max < wins[0]) {
                max = wins[0];
                indexOfMax = 0;
            }
            if(bot.down != null && bot.down.isOpen && max < wins[1]) {
                max = wins[1];
                indexOfMax = 1;
            }
            if(bot.left != null && bot.left.isOpen && max < wins[2]) {
                max = wins[2];
                indexOfMax = 2;
            }
            if(bot.right != null && bot.right.isOpen && max < wins[3]) {
                max = wins[3];
                indexOfMax = 3;
            }


            if (bot.up != null && indexOfMax == 0 && bot.up.isOpen) {
                try {
                    bot.isBot = false;
                    bot.up.isBot = true;
                    bot = bot.up;
                } catch (NullPointerException ignore) { }
            }
            else if (bot.down != null && indexOfMax == 1 && bot.down.isOpen) {
                try {
                    bot.isBot = false;
                    bot.down.isBot = true;
                    bot = bot.down;
                } catch (NullPointerException ignore) { }
            }
            else if (bot.left != null && indexOfMax == 2 && bot.left.isOpen) {
                try {
                    bot.isBot = false;
                    bot.left.isBot = true;
                    bot = bot.left;
                } catch (NullPointerException ignore) { }
            }
            else if (bot.right != null && indexOfMax == 3 && bot.right.isOpen){
                try {
                    bot.isBot = false;
                    bot.right.isBot = true;
                    bot = bot.right;
                } catch (NullPointerException ignore) { }
            }


            if (bot.isButton) {
                return true;
            } else {
                //else, potentially advance fire
                LinkedList<Cell> copyFireCells = (LinkedList<Cell>) fireCells.clone();
                while (!copyFireCells.isEmpty()) {
                    Cell fireCell = copyFireCells.removeFirst();
                    tryFireNeighbor(fireCell.up, fireCells);
                    tryFireNeighbor(fireCell.down, fireCells);
                    tryFireNeighbor(fireCell.left, fireCells);
                    tryFireNeighbor(fireCell.right, fireCells);
                }

                if (bot.getOnFire()) {
                    System.out.println("bot on fire");
                    return false;
                }
                if (button.getOnFire()) {
                    System.out.println("button on fire");
                    return false;
                }
            }
        }
        return false;
    }
    */




    private static boolean runSimulation_Bot4(Cell ogBot, Cell ogButton, LinkedList<Cell> ogFireCells, Cell[][] ogShip) {
        //make copies
        Cell[][] ship = copyShip(ogShip);
        Cell bot = ship[ogBot.getRow()][ogBot.getCol()];
        Cell button = ship[ogButton.getRow()][ogButton.getCol()];
        LinkedList<Cell> fireCells = new LinkedList<>();
        for (Cell ogFireCell : ogFireCells) {
            fireCells.add(ship[ogFireCell.getRow()][ogFireCell.getCol()]);
        }


        //bot 2
        while (!bot.isButton && !bot.getOnFire() && !button.getOnFire()) {
            //BFS Shortest Path from bot -> button
            LinkedList<Cell> shortestPath = Bfs.shortestPathBFS(bot, button, ship);
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
        }
        return false;
    }


    private static boolean runBot4(Cell[][] ship) {
        //initialize the bot
        int randIndex = Main.rand(0, openCells.size() - 1);
        Cell bot = openCells.get(randIndex);
        bot.isBot = true;

        //initialize the button
        randIndex = Main.rand(0, openCells.size() - 1);
        Cell button = openCells.get(randIndex);
        button.isButton = true;

        if (bot.isButton) {
            //System.out.println("Bot spawns on button.");
            return true;
        }

        //initialize the fire
        LinkedList<Cell> fireCells = new LinkedList<>();
        randIndex = Main.rand(0, openCells.size() - 1);
        Cell initialFire = openCells.get(randIndex);
        initialFire.setOnFire(true); //setting on fire automatically updates neighbors
        fireCells.add(initialFire);

        if (bot.getOnFire() || button.getOnFire()) {
            //System.out.println("Fire spawns on bot or button.");
            return false;
        }

        printShip(ship, bot, button, initialFire);

        int t = 0;
        while (!bot.isButton && !bot.getOnFire() && !button.getOnFire()) {
            t++;
            int[] wins = new int[4]; //0 up, 1 down, 2, left, 3 right

            for (int i = 1; i <= 10; i++) {
                if (bot.up != null && bot.up.isOpen) {
                    if (runSimulation_Bot4(bot.up, button, fireCells, ship))
                        wins[0]++;
                }
                if (bot.down != null && bot.down.isOpen) {
                    if (runSimulation_Bot4(bot.down, button, fireCells, ship))
                        wins[1]++;
                }
                if (bot.left != null && bot.left.isOpen) {
                    if (runSimulation_Bot4(bot.left, button, fireCells, ship))
                        wins[2]++;
                }
                if (bot.right != null && bot.right.isOpen) {
                    if (runSimulation_Bot4(bot.right, button, fireCells, ship))
                        wins[3]++;
                }
            }

            System.out.println("time t = " + t + "   BOT POSITION: " + "(" + bot.getRow() + ", " + bot.getCol() + ")");
            int max = 0;
            int indexOfMax = -1;
            for (int i = 0; i < wins.length; i++) {
                if (max < wins[i]) {
                    max = wins[i];
                    indexOfMax = i;
                }
            }

            LinkedList<Cell> maxNeighbors = new LinkedList<>();
            if (bot.up != null && bot.up.isOpen && max == wins[0]) {
                maxNeighbors.add(bot.up);
            }
            if (bot.down != null && bot.down.isOpen && max == wins[1]) {
                maxNeighbors.add(bot.down);
            }
            if (bot.left != null && bot.left.isOpen && max == wins[2]) {
                maxNeighbors.add(bot.left);
            }
            if (bot.right != null && bot.right.isOpen && max == wins[3]) {
                maxNeighbors.add(bot.right);
            }

            //decide where to move the bot
            if (maxNeighbors.size() == 1) {
                bot.isBot = false;
                Cell neighbor;

                if (indexOfMax == 0) neighbor = bot.up;
                else if (indexOfMax == 1) neighbor = bot.down;
                else if (indexOfMax == 2) neighbor = bot.left;
                else neighbor = bot.right;

                neighbor.isBot = true;
                bot = neighbor;
            }
            else {
                //get the shortest paths of all the bots in the hashmap
                for ()
            }



            System.out.println("Has the bot moved?: " + "(" + bot.getRow() + ", " + bot.getCol() + ")");

            if (bot.isButton) {
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
                    return false;
                }
            }
        }
        return false;
    }




    /**
     * Deep copy of given ship
     */
    private static Cell[][] copyShip(Cell[][] ship) {
        Cell[][] newShip = new Cell[Ship.D][Ship.D];
        for (int i = 0; i < ship.length; i++){
            for (int j = 0; j < ship[0].length; j++){
                newShip[i][j] = new Cell(ship[i][j]);
            }
        }
        for (int r = 0; r < Ship.D; r++) {
            for (int c = 0; c < Ship.D; c++) {
                newShip[r][c].setNeighbors(newShip);
            }
        }
        return newShip;
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
            Boolean result = false;
            if (bot == 1) {
                result = (runBot1(tempShip));
            }
            else if (bot == 2) {
                result = (runBot2(tempShip));
            }
            else if (bot == 3) {
                result = (runBot3(tempShip));
            }

            if (result == null) //if null, forget this test (bot just got lucky)
                test--;
            else {
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

        Cell[][] ship = Ship.makeShip();
        for (int i = 0; i < ship.length; i++){
            for (int j = 0; j < ship[0].length; j++){
                if (ship[i][j].isOpen)
                    openCells.add(ship[i][j]);
            }
        }

        //System.out.println("Initial Cell is at row " + Ship.initial.getRow() +
        //        " and col " + Ship.initial.getCol() + "\n\n\n");
        //System.out.println("0123456789");
        //System.out.println();
        //printShip();


        /*
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
         */

        q = 0;
        System.out.println("Bot 4 output: " + runBot4(ship));
    }

    private static void runQTests(int bot) {
        int sum = 0;
        for (int i = 1; i <= numQTests; i++) {
            sum += runTests(bot);
        }
        double avg = sum/numQTests;
        System.out.println("Avg Success Rate for q = " + q + " is " + avg);
    }

    /**
     * Check the distance from bot to button and fire to button.
     * @return true if the bot is closer to the button than the fire
     */
    private static boolean checkDistBotVsFire(Cell bot, Cell fire, Cell button, Cell[][] ship) {
        //BFS Shortest Path from bot -> button
        LinkedList<Cell> shortestPath_Bot = Bfs.shortestPathBFS(bot, button, ship);
        //BFS Shortest Path from fire -> button
        LinkedList<Cell> shortestPath_Fire = Bfs.shortestPathBFS(fire, button, ship);
        try {
            return shortestPath_Bot.size() <= shortestPath_Fire.size();
        }
        catch (NullPointerException e) { return false; }
    }
}