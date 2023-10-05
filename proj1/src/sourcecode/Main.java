package sourcecode;

import java.util.ArrayList;
import java.util.LinkedList;

public class Main {
    public static final double q = .00;
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

    private static boolean runBot1(Cell[][] ship) {
        //initialize the bot
        int randIndex = Main.rand(0, openCells.size()-1);
        Cell bot = openCells.get(randIndex);
        bot.isBot = true;

        //initialize the button
        randIndex = Main.rand(0, openCells.size()-1);
        Cell button = openCells.get(randIndex);
        button.isButton = true;

        if (bot.isButton)
            return true;

        //initialize the fire
        ArrayList<Cell> fireCells = new ArrayList<>();
        randIndex = Main.rand(0, openCells.size()-1);
        Cell initialFire = openCells.get(randIndex);
        initialFire.setOnFire(true); //setting on fire automatically updates neighbors
        fireCells.add(initialFire);

        if (bot.getOnFire() || button.getOnFire())
            return false;


        printShip(ship, bot, button, initialFire);
        
        //BFS Shortest Path from bot -> button
        LinkedList<Cell> shortestPath = Bfs.shortestPathBFS(bot, button);
        if (shortestPath == null)
            return false;

        shortestPath.removeFirst();

        int t = 0;
        while (!shortestPath.isEmpty()) {
            System.out.println("t = "+ t);
            //move the bot
            Cell neighbor = shortestPath.removeFirst();
            bot.isBot = false;
            neighbor.isBot = true;
            bot = neighbor;

            if (bot.isButton)
                return true;
            else {
                //else, potentially advance fire
                for (Cell fireCell : fireCells) {
                    tryFireNeighbor(fireCell.up, fireCells);
                    tryFireNeighbor(fireCell.down, fireCells);
                    tryFireNeighbor(fireCell.left, fireCells);
                    tryFireNeighbor(fireCell.right, fireCells);
                }

                if (bot.getOnFire() || button.getOnFire())
                    return false;
            }
            t++;
        }
        //if the shortest path is fully traversed and bot didn't reach button, loss
        return false;
    }

    /**
     * Try to ignite the given neighbor of a fire cell
     */
    private static void tryFireNeighbor(Cell neighbor, ArrayList<Cell> fireCells) {
        if (neighbor != null && Math.random() <= neighbor.flammability && neighbor.isOpen) {
            neighbor.setOnFire(true);
            fireCells.add(neighbor);
        }
    }



    private static void printShip(Cell[][] ship, Cell bot, Cell button, Cell fire) {
        for (int r = 0; r < Ship.D; r++) {
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


    public static void main(String[] args) {
        //Initialize the ship
        Cell[][] ship = Ship.makeShip();
        for(int i = 0; i < ship.length; i++){
            for(int j = 0; j < ship[0].length; j++){
                if(ship[i][j].isOpen)
                    openCells.add(ship[i][j]);
            }
        }
        System.out.println("Initial Cell is at row " + Ship.initial.getRow() +
                " and col " + Ship.initial.getCol() + "\n\n\n");
        //System.out.println("0123456789");
        System.out.println();
        //printShip();

        //BOT 1
        boolean result = runBot1(ship);
        System.out.println("Did Bot 1 make it to the button? " + result);
    }
}