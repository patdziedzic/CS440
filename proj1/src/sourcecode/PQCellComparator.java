package sourcecode;

import java.util.Comparator;

public class PQCellComparator implements Comparator<PQCell> {

    // Overriding compare()method of Comparator
    // for descending order of cgpa
    public int compare(PQCell c1, PQCell c2) {
        if (c1.sizeOfSP > c2.sizeOfSP)
            return 1;
        else if (c1.sizeOfSP < c2.sizeOfSP)
            return -1;
        return 0;
    }
}
