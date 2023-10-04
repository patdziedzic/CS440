package sourcecode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class bfs {
    public ArrayList<Cell> shortestPathBFS(Cell state, Cell button){
        Queue <Cell> fringe = new LinkedList<Cell>(); //tell us what to explore next
        //Set<Cell> closedSet = new HashSet<Cell>(); //keeps track of 'marked' cells (prevents cycles)
        ArrayList<Cell> shortestPath = new ArrayList<>(); //keeps track of where bot has visited
        fringe.add(state);
        shortestPath.add(state);

        
        while (!fringe.isEmpty()) {
            state = fringe.remove();
            if(state.equals(button)){
                return shortestPath;
            } else {
                shortestPathBFS(state, button);
            }
        }
        return null;
    }
}