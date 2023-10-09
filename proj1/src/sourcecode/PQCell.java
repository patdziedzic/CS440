package sourcecode;


public class PQCell implements Comparable<PQCell> {
    public Cell cell;
    public int sizeOfSP;

    public PQCell(Cell cell, int sizeOfSP) {
        this.cell = cell;
        this.sizeOfSP = sizeOfSP;
    }

    @Override
    public int compareTo(PQCell pqCell) {
        if (this.sizeOfSP > pqCell.sizeOfSP)
            return 1;
        else if (this.sizeOfSP < pqCell.sizeOfSP)
            return -1;
        return 0;
    }
}
