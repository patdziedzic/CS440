package sourcecode;

public class Cell {
    //REGARDING OPENNESS
    private int numOpenNeighbors; //increment for each new open neighbor
    private boolean canOpen; //if numOpenNeighbors > 1, make false
    private boolean isOpen; //initialized false

    //REGARDING FLAMMABILITY
    private boolean onFire; //initialized false
    private double flammability; //flammability of the cell based on neighbors, initially 0


    public Cell() {
        this.numOpenNeighbors = 0;
        this.canOpen = true;
        this.isOpen = false;
        this.onFire = false;
        this.flammability = 0;
    }
}
