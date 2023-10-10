package sourcecode;


public class PQCell implements Comparable<PQCell> {
    public Cell cell;
    public int sizeOfSP;
    //public double flam;

    public PQCell(Cell cell, int sizeOfSP) {
        this.cell = cell;
        this.sizeOfSP = sizeOfSP;
        //this.flam = flam;
    }


    @Override
    public int compareTo(PQCell pqCell) {
        return Integer.compare(this.sizeOfSP, pqCell.sizeOfSP);
        /*
        else {
            if (this.cell.flammability > pqCell.cell.flammability)
                return 1;
            else if (this.cell.flammability < pqCell.cell.flammability)
                return -1;
            else
                return 0;
        }

         */
    }



    /*
    @Override
    public int compareTo(PQCell pqCell) {
        return Double.compare(this.cell.flammability, pqCell.cell.flammability);
    }

     */
}
