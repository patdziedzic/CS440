package sourcecode;

import java.util.ArrayList;

public class Main {
    public static final int D = 500;
    public static final double q = 0.5;


    //the ship layout - 2D array of Cell objects
    public static Cell[][] ship;
    private static Cell initial;


    /**
     * Get random int between min to max
     * @return randomly generated int
     */
    private static int rand(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    /**
     * Get random double between min to max
     * @return randomly generated double
     */
    private static double rand(double min, double max) {
        return ((Math.random() * (max - min)) + min);
    }





    private static void makeShip() {
        //ship is a D x D 2D array of Cell objects
        int row = rand(0, D-1);
        int col = rand(0, D-1);

        //open the initial cell
        initial = openCell(ship[row][col]);
        //printShip();

        openBlockedCandidates();
        System.out.println("Deadends are opening");
        openDeadEnds();
    }

    private static void openBlockedCandidates() {

        //populating blocked candidates
        ArrayList<Cell> blockedCandidates = new ArrayList<>(); //blocked cells with one open neighbor
        for (int r = 0; r < D; r++) {
            for (int c = 0; c < D; c++) {
                /*if (!ship[r][c].getIsOpen() && ship[r][c].getNumOpenNeighbors() == 1) {
                    blockedCandidates.add(ship[r][c]);
                }*/
                addBlockedCandidate(ship[r][c], blockedCandidates);
            }
        }

        //opening blocked candidates
        while (!blockedCandidates.isEmpty()) {
            int randomIndex = rand(0, blockedCandidates.size() - 1);
            Cell randomBlocked = blockedCandidates.get(randomIndex);
            openCell(randomBlocked);
            //printShip();
            updateBlockedArrayList(blockedCandidates);

            //Check U/D/L/R if it is now a candidate, if it is then add it
            //up
            try {
                addBlockedCandidate(ship[randomBlocked.getRow() - 1][randomBlocked.getCol()], blockedCandidates);
            } catch (ArrayIndexOutOfBoundsException ignore) { }
            //down
            try {
                addBlockedCandidate(ship[randomBlocked.getRow() + 1][randomBlocked.getCol()], blockedCandidates);
            } catch (ArrayIndexOutOfBoundsException ignore) { }
            //left
            try {
                addBlockedCandidate(ship[randomBlocked.getRow()][randomBlocked.getCol() - 1], blockedCandidates);
            } catch (ArrayIndexOutOfBoundsException ignore) { }
            //right
            try {
                addBlockedCandidate(ship[randomBlocked.getRow()][randomBlocked.getCol() + 1], blockedCandidates);
            } catch (ArrayIndexOutOfBoundsException ignore) { }

            updateBlockedArrayList(blockedCandidates);
        }
    }

    private static void addBlockedCandidate(Cell cell, ArrayList<Cell> a) {
        if (!cell.getIsOpen() && cell.getNumOpenNeighbors() == 1) {
            a.add(cell);
        }
    }

    private static void openDeadEnds() {
        //handling dead ends
        ArrayList<Cell> deadEnds = new ArrayList<>();
        for (int r = 0; r < D; r++) {
            for (int c = 0; c < D; c++) {
                if (ship[r][c].getIsOpen() && ship[r][c].getNumOpenNeighbors() == 1) {
                    deadEnds.add(ship[r][c]);
                }
            }
        }

        int threshold = deadEnds.size() / 2;

        //opening dead ends
        while (deadEnds.size() > threshold) {
            int randomIndex = rand(0, deadEnds.size()-1);
            Cell randomDeadEnd = deadEnds.get(randomIndex);
            ArrayList<Cell> blockedNeighbors = new ArrayList<>();
            try {
                if(!ship[randomDeadEnd.getRow() - 1][randomDeadEnd.getCol()].getIsOpen())
                    blockedNeighbors.add(ship[randomDeadEnd.getRow() - 1][randomDeadEnd.getCol()]);
            } catch (ArrayIndexOutOfBoundsException ignore) { }
            try {
                if(!ship[randomDeadEnd.getRow() + 1][randomDeadEnd.getCol()].getIsOpen())
                    blockedNeighbors.add(ship[randomDeadEnd.getRow() + 1][randomDeadEnd.getCol()]);
            } catch (ArrayIndexOutOfBoundsException ignore) { }
            try {
                if(!ship[randomDeadEnd.getRow()][randomDeadEnd.getCol()-1].getIsOpen())
                    blockedNeighbors.add(ship[randomDeadEnd.getRow()][randomDeadEnd.getCol()-1]);
            } catch (ArrayIndexOutOfBoundsException ignore) { }
            try {
                if(!ship[randomDeadEnd.getRow()][randomDeadEnd.getCol()+1].getIsOpen())
                    blockedNeighbors.add(ship[randomDeadEnd.getRow()][randomDeadEnd.getCol()+1]);
            } catch (ArrayIndexOutOfBoundsException ignore) { }
            
            randomIndex = rand(0, blockedNeighbors.size()-1);
            if(blockedNeighbors.size() != 0) {
                Cell randomNeighbor = blockedNeighbors.get(randomIndex);
                openCell(randomNeighbor);
            }
            //printShip();
            updateDeadendsArrayList(deadEnds);
        }

        //get in an arraylist all cells that are open and have 1 open neighbors
    }


    /**
     * Updates blockedCandidates
     */
    private static void updateBlockedArrayList(ArrayList<Cell> a) {
        for (int i = 0; i < a.size(); i++) {
            Cell c = a.get(i);
            if (c.getIsOpen() || c.getNumOpenNeighbors() > 1)
                a.remove(c);
        }
    }

 /**
     * Updates deadEnds
     */
    private static void updateDeadendsArrayList(ArrayList<Cell> a) {
        for (int i = 0; i < a.size(); i++) {
            Cell c = a.get(i);
            if (c.getNumOpenNeighbors() > 1)
                a.remove(c);
        }
    }


    /**
     * Opens a cell
     */
    private static Cell openCell(Cell cell) {
        //open the given cell
        cell.setIsOpen(true);

        //Set Neighbors' numOpenNeighbors
        //up
        changeNumOpenNeighbors(cell.getRow() - 1, cell.getCol());
        //down
        changeNumOpenNeighbors(cell.getRow() + 1, cell.getCol());
        //left
        changeNumOpenNeighbors(cell.getRow(), cell.getCol() - 1);
        //right
        changeNumOpenNeighbors(cell.getRow(), cell.getCol() + 1);

        return cell;
    }

    private static void changeNumOpenNeighbors(int row, int col) {
        try {
            ship[row][col].incNumOpenNeighbors();
        } catch (ArrayIndexOutOfBoundsException ignore) { }
    }



    public static void main(String[] args) {
        //Initialize the ship
        ship = new Cell[D][D];
        for (int r = 0; r < D; r++) {
            for (int c = 0; c < D; c++) {
                ship[r][c] = new Cell(r, c);
            }
        }
        makeShip();

        System.out.println("Initial Cell is at row " + initial.getRow() + " and col " + initial.getCol() + "\n\n\n");
        System.out.println("0123456789");
        System.out.println();

        //printShip();

    }

    private static void printShip() {
        for (int r = 0; r < D; r++) {
            for (int c = 0; c < D; c++) {
                if (ship[r][c].getIsOpen())
                    System.out.print(1);
                else
                    System.out.print(0);
                System.out.print(" ");
            }
            System.out.println();
        }

        System.out.println("\n\n");
    }
}