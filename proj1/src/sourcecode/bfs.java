package sourcecode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class bfs {
    public ArrayList<Cell> shortestPathBFS(Cell state, Cell button){
        Queue <Cell> Q = new LinkedList<Cell>(); //tell us what to explore next
        ArrayList<Cell> shortestPath = new ArrayList<>(); //keeps track of where bot has visited
        Q.add(state);
        shortestPath.add(state);
        state.setIsVisited(true);
        
        while (!Q.isEmpty()) {
            state = Q.remove();
            state.setIsVisited(true);
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

    private boolean isValid(Cell c) {
        return (!c.equals(null) && !c.getOnFire() && c.getIsOpen() && !c.getIsVisited());
    }
}