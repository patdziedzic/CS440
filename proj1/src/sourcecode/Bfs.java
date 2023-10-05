package sourcecode;

import java.util.*;

public class Bfs {

    public static LinkedList<Cell> shortestPathBFS(Cell bot, Cell button){
        //make sure isVisited is false for all cells
        for (int r = 0; r < Ship.D; r++) {
            for (int c = 0; c < Ship.D; c++) {
                Ship.ship[r][c].isVisited = false;
            }
        }

        Queue<Cell> Q = new LinkedList<>(); //tell us what to explore next
        HashMap<Cell, Cell> parentNodes = new HashMap<>(); //keeps track of where bot has visited
        //^ Map the previous to the next by using .put(next, prev)
        Q.add(bot);
        
        while (!Q.isEmpty()) {
            bot = Q.remove();
            bot.isVisited = true;
            //shortestPath.add(bot);

            if(bot.equals(button)) {
                //Once path found, start from end and go back and store the path into LinkedList
                LinkedList<Cell> shortestPath = new LinkedList<>();
                Cell ptr = button;
                while (ptr != null) {
                    shortestPath.add(ptr);
                    ptr = parentNodes.get(ptr);
                }
                Collections.reverse(shortestPath);
                return shortestPath;
            }
    
            if(isValid(bot.up)) {
                parentNodes.put(bot.up, bot); //next, previous
                Q.add(bot.up);
            }
            if(isValid(bot.down)) {
                parentNodes.put(bot.down, bot);
                Q.add(bot.down);
            }
            if(isValid(bot.left)) {
                parentNodes.put(bot.left, bot);
                Q.add(bot.left);
            }
            if(isValid(bot.right)) {
                parentNodes.put(bot.right, bot);
                Q.add(bot.right);
            }
        }
        return null;
    }

    private static boolean isValid(Cell c) {
        return (c != null && !c.getOnFire() && c.isOpen && !c.isVisited);
    }
}