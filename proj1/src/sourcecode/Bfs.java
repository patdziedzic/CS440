package sourcecode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Bfs {
    public static LinkedList<Cell> shortestPathBFS(Cell bot, Cell button){
        Queue<Cell> Q = new LinkedList<>(); //tell us what to explore next
        LinkedList<Cell> shortestPath = new LinkedList<>(); //keeps track of where bot has visited
        //^ LinkedList bc better for adding to the end and removing the first than an ArrayList
        Q.add(bot);
        
        while (!Q.isEmpty()) {
            bot = Q.remove();
            bot.isVisited = true;
            shortestPath.add(bot);
            
            if(bot.equals(button))
                return shortestPath;
    
            if(isValid(bot.up)) 
                Q.add(bot.up);
            if(isValid(bot.down)) 
                Q.add(bot.down);
            if(isValid(bot.left))
                Q.add(bot.left);
            if(isValid(bot.right))
                Q.add(bot.right);
    
        }
        return null;
    }

    private static boolean isValid(Cell c) {
        return (c != null && !c.getOnFire() && c.isOpen && !c.isVisited);
    }
}