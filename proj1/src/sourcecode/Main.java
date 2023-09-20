package sourcecode;

public class Main {
    public static final int DIM = 100;


    //the ship layout - 2D array of Cell objects
    public static Cell[][] ship = new Cell[DIM][DIM];






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
        //ship is a DIM x DIM 2D array of Cell objects

    }



    public static void main(String[] args) {
        System.out.println("Hello World!");
    }
}