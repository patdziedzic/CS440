package sourcecode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Bfs {
    public static LinkedList<Cell> shortestPathBFS(Cell state, Cell button){
        Queue<Cell> Q = new LinkedList<>(); //tell us what to explore next
        LinkedList<Cell> shortestPath = new LinkedList<>(); //keeps track of where bot has visited
        //^ LinkedList bc better for adding to the end and removing the first than an ArrayList
        Q.add(state);
        shortestPath.add(state);
        state.isVisited = true;
        
        while (!Q.isEmpty()) {
            state = Q.remove();
            state.isVisited = true;
            shortestPath.add(state);
            
            if(state.equals(button))
                return shortestPath;
    
            if(isValid(state.up)) 
                Q.add(state.up);
            if(isValid(state.down)) 
                Q.add(state.down);
            if(isValid(state.left))
                Q.add(state.left);
            if(isValid(state.right))
                Q.add(state.right);
    
        }
        return null;
    }

    private static boolean isValid(Cell c) {
        return (c != null && !c.getOnFire() && c.isOpen && !c.isVisited);
    }
}