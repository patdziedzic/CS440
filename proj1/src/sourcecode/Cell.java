package sourcecode;

public class Cell {
    //COORDINATES
    private int row;
    private int col;

    //REGARDING OPENNESS
    private int numOpenNeighbors; //increment for each new open neighbor
    private boolean isOpen; //initialized false

    //REGARDING FLAMMABILITY
    private boolean onFire; //initialized false
    private double flammability; //flammability of the cell based on neighbors, initially 0
    private int fireNeighbors; //K

    //REACHING NEIGHBORS OF CELLS
    public Cell up;
    public Cell down;
    public Cell left;
    public Cell right;

    //Default Cell Constructor
    public Cell() {
        this.numOpenNeighbors = 0;
        this.isOpen = false;
        this.onFire = false;
        this.flammability = 0;
        this.up = Main.ship[row-1][col];
        this.down = Main.ship[row+1][col];
        this.left = Main.ship[row][col-1];
        this.right = Main.ship[row][col+1];
    }

    //Cell Constructor given row and col
    public Cell(int row, int col) {
        this.row = row;
        this.col = col;

        this.numOpenNeighbors = 0;
        this.isOpen = false;
        this.onFire = false;
        this.flammability = 0;
        this.up = Main.ship[row-1][col];
        this.down = Main.ship[row+1][col];
        this.left = Main.ship[row][col-1];
        this.right = Main.ship[row][col+1];
    }

    public int getNumOpenNeighbors() {
        return numOpenNeighbors;
    }

    public void incNumOpenNeighbors() {
        this.numOpenNeighbors++;
    }


    public boolean getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(boolean open) {
        isOpen = open;
    }

    public boolean getOnFire() {
        return onFire;
    }

    public void setOnFire(boolean onFire) {
        this.onFire = onFire;
    }

    public double getFlammability() {
        return flammability;
    }

    public void setFlammability(double flammability) {
        this.flammability = flammability;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

/*
    private void changeNeighborK(int row, int col) {
        try {

        } catch (ArrayIndexOutOfBoundsException ignore) { }
    }
     */


    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Cell){
            Cell c = (Cell) obj;
            return c.row == this.row && c.col == this.col;
        }
        return false;
    }
}
