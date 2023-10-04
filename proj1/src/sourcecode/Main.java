package sourcecode;

import java.util.ArrayList;
import java.util.LinkedList;

public class Main {
    public static final double q = 0.5;


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
        int row = Main.rand(0, Ship.D-1);
        int col = Main.rand(0, Ship.D-1);
        Cell bot = ship[row][col];
        bot.isBot = true;

        //initialize the button
        row = Main.rand(0, Ship.D-1);
        col = Main.rand(0, Ship.D-1);
        Cell button = ship[row][col];
        button.isButton = true;

        if (bot.isButton)
            return true;

        //initialize the fire
        ArrayList<Cell> fireCells = new ArrayList<>();
        row = Main.rand(0, Ship.D-1);
        col = Main.rand(0, Ship.D-1);
        Cell initialFire = ship[row][col];
        initialFire.setOnFire(true); //setting on fire automatically updates neighbors
        fireCells.add(initialFire);

        if (bot.getOnFire() || button.getOnFire())
            return false;

        //BFS Shortest Path from bot -> button
        LinkedList<Cell> shortestPath = Bfs.shortestPathBFS(bot, button);
        if (shortestPath == null)
            return false;

        while (!shortestPath.isEmpty()) {
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
                    tryFireNeighbor(fireCell.up);
                    tryFireNeighbor(fireCell.down);
                    tryFireNeighbor(fireCell.left);
                    tryFireNeighbor(fireCell.right);
                }

                if (bot.getOnFire() || button.getOnFire())
                    return false;
            }
        }
        //if the shortest path is fully traversed and bot didn't reach button, loss
        return false;
    }

    /**
     * Try to ignite the given neighbor of a fire cell
     */
    private static void tryFireNeighbor(Cell neighbor) {
        if (Math.random() <= neighbor.flammability) {
            neighbor.setOnFire(true);
        }
    }


    public static void main(String[] args) {
        //Initialize the ship
        Cell[][] ship = Ship.makeShip();

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