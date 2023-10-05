package sourcecode;

public class Cell {
    //COORDINATES
    private final int row;
    private final int col;

    //REGARDING OPENNESS
    public int numOpenNeighbors; //increment for each new open neighbor
    public boolean isOpen; //initialized false

    //REACHING NEIGHBORS OF CELLS
    public Cell up;
    public Cell down;
    public Cell left;
    public Cell right;
    public boolean isVisited;

    //IDENTITY
    public boolean isBot;
    public boolean isButton;

    //FLAMMABILITY
    private boolean onFire; //initialized false
    public double flammability; //flammability of the cell based on neighbors, initially 0
    public int k; //number of fire neighbours

    /**
     * Cell Constructor given row and col
     */
    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        this.numOpenNeighbors = 0;
        this.isOpen = false;
        this.onFire = false;
        this.k = 0;
        this.flammability = 1-Math.pow((1-Main.q),k);
        this.isBot = false;
        this.isButton = false;
        try {
            this.up = Ship.ship[row-1][col];
        } catch (ArrayIndexOutOfBoundsException ignore){}
        try {
             this.down = Ship.ship[row+1][col];
        } catch (ArrayIndexOutOfBoundsException ignore){}
        try {
            this.left = Ship.ship[row][col-1];
        } catch (ArrayIndexOutOfBoundsException ignore){}
        try {
            this.right = Ship.ship[row][col+1];
        } catch (ArrayIndexOutOfBoundsException ignore){}
        
        this.isVisited = false;
    }

    public void incNumOpenNeighbors() {
        this.numOpenNeighbors++;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void incK() {
        k++;
        flammability = 1 - Math.pow((1 - Main.q), k);
    }

    public boolean getOnFire() {
        return onFire;
    }

    /**
     * If set cell on fire to true, update the neighbors automatically
     * @param value the
     */
    public void setOnFire(boolean value) {
        onFire = value;
        if (value) {
            if(up != null)
                up.incK();
            if(down != null)
                down.incK();
            if(left != null)
                left.incK();
            if(right != null)
                right.incK();
        }
    }

    /*
    private void changeNeighborK(int row, int col) {
        try {

        } catch (ArrayIndexOutOfBoundsException ignore) { }
    }
     */


    /**
     * Two Cells are equal if they have the same row and col
     * @param obj Cell object
     * @return true if equal
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
