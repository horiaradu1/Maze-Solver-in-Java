package maze.routing;

import maze.InvalidMazeException;
import maze.Maze;
import maze.Tile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * The type Route finder.
 */
public class RouteFinder implements Serializable {
    private Maze maze;
    private Stack<Tile> route;
    private boolean finished;
    private int distances[][];

    /**
     * Instantiates a new Route finder.
     *
     * @param m the m
     */
    public RouteFinder(Maze m){
        this.maze = m;
        this.route = new Stack<Tile>();
        this.finished = false;
        distances = new int[this.maze.getRows()][this.maze.getCols()];
        for (int i = 0; i < this.maze.getRows(); i++){
            for (int j = 0; j < this.maze.getCols(); j++){
                Maze.Coordinate location = new Maze.Coordinate(j,i);
                Maze.Coordinate changed = location.changeToRead(this.maze.getRows());
                if (this.maze.getTileAtLocation(location).isNavigable()){
                    distances[changed.getX()][changed.getY()] = 0;
                }else{
                    distances[changed.getX()][changed.getY()] = (int) Double.POSITIVE_INFINITY;
                }
            }
        }
        System.out.println();
        Maze.Coordinate exitLoc = this.maze.getTileLocation(this.maze.getExit()).changeToRead(this.maze.getRows());
        flood(exitLoc.getX(), exitLoc.getY(), 1);
//        Maze.Coordinate entranceLoc = this.maze.getTileLocation(this.maze.getEntrance()).changeToRead(this.maze.getRows());
//        if (distances[entranceLoc.getX()][entranceLoc.getY()] == 0){
//            throw new NoRouteFoundException();
//        }
        for (int i = 0; i < this.maze.getRows(); i++) {
            for (int j = 0; j < this.maze.getCols(); j++) {
                System.out.print(distances[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * Is there any route.
     *
     * @throws NoRouteFoundException the no route found exception
     */
    public void isThereAnyRoute() throws NoRouteFoundException {
        Maze.Coordinate entranceLoc = this.maze.getTileLocation(this.maze.getEntrance()).changeToRead(this.maze.getRows());
        if (distances[entranceLoc.getX()][entranceLoc.getY()] == 0){
            throw new NoRouteFoundException();
        }
    }

    //Flooding algorithm
    private void flood(int i, int j, int distance){
        if (this.distances[i][j]==0){
            distances[i][j] = distance;
            if (i >= 1) {
                flood(i - 1, j, distance + 1);
            }
            if (i <= this.maze.getRows()-2) {
                flood(i + 1, j, distance + 1);
            }
            if (j >= 1) {
                flood(i, j - 1, distance + 1);
            }
            if (j <= this.maze.getCols()-2) {
                flood(i, j + 1, distance + 1);
            }
        }
    }

    /**
     * Gets maze.
     *
     * @return the maze
     */
    public Maze getMaze() {
        return maze;
    }

    /**
     * Gets route.
     *
     * @return the route
     */
    public List<Tile> getRoute() {
        return (List<Tile>) route;
    }

    /**
     * Is finished boolean.
     *
     * @return the boolean
     */
    public boolean isFinished() {
        return finished;
    }

    /**
     * Save.
     *
     * @param file the file
     * @throws IOException the io exception
     */
//CHANGE
    public void save(String file) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(this);
        oos.close();
    }

    /**
     * Load route finder.
     *
     * @param file the file
     * @return the route finder
     * @throws IOException            the io exception
     * @throws ClassNotFoundException the class not found exception
     */
    public static RouteFinder load(String file) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(file);
        if (fis.available() == 0){
            throw new ClassNotFoundException();
        }
        ObjectInputStream ois = new ObjectInputStream(fis);
        RouteFinder result = (RouteFinder) ois.readObject();
        ois.close();
        return result;
    }

    /**
     * Load map route finder.
     *
     * @param file the file
     * @return the route finder
     */
    public static RouteFinder loadMap(String file){
        Maze maze = null;
        try {
            maze = Maze.fromTxt(file);
        } catch (InvalidMazeException | IOException e) {
            System.out.println("file error");
            e.printStackTrace();
        }
        try {
            return new RouteFinder(maze);
        }catch(NullPointerException e){
            System.out.println("file error");
            e.printStackTrace();
        }
        return loadMap("/home/horia/Documents/JavaStuff/comp16412-coursework-2_k55592hr/mazes/maze1.txt");
    }

    /**
     * Step boolean.
     *
     * @return the boolean
     */
//CHANGE
    public boolean step(){
        if (this.finished){
            return true;
        }
        if (this.route.size() == 0){
            route.add((this.maze.getEntrance()));
            return false;
        }
        Maze.Coordinate lastLoc = this.maze.getTileLocation(this.route.lastElement()).changeToRead(this.maze.getRows());
        System.out.println((lastLoc));
        Maze.Coordinate nextLoc = lastLoc;
        int x = lastLoc.getX();
        int y = lastLoc.getY();
        int minDistance = distances[x][y];

        if (lastLoc.getX() >= 1){
            //System.out.println("Got up");
            //System.out.println(distances[x-1][y]);
            if (distances[x-1][y] < minDistance){
                minDistance = distances[x-1][y];
                nextLoc = new Maze.Coordinate(x-1, y);
            }
        }
        if (lastLoc.getY() >= 1){
            //System.out.println("Got left");
            //System.out.println(distances[x][y-1]);
            if (distances[x][y-1] < minDistance){
                minDistance = distances[x][y-1];
                nextLoc = new Maze.Coordinate(x, y-1);
            }
        }
        if (lastLoc.getX() <= this.maze.getRows()-2){
            //System.out.println("Got down");
            //System.out.println(distances[x+1][y]);
            if (distances[x+1][y] < minDistance){
                minDistance = distances[x+1][y];
                nextLoc = new Maze.Coordinate(x+1, y);
            }
        }
        if (lastLoc.getY() <= this.maze.getCols()-2){
            //System.out.println("Got right");
            //System.out.println(distances[x][y+1]);
            if (distances[x][y+1] < minDistance){
                minDistance = distances[x][y+1];
                nextLoc = new Maze.Coordinate(x, y+1);
            }
        }
        Tile next = this.maze.getTileAtLocation((nextLoc.changeToMaze(this.maze.getRows())));
        this.route.add(next);
        if (next == this.maze.getExit()){
            this.finished = true;
            return true;
        }
        return false;
    }

    /**
     * To string string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        StringBuilder mazeString = new StringBuilder();
        for (int i = 0; i < this.maze.getRows(); i++) {
            for (int j = 0; j < this.maze.getCols(); j++) {
                if (inRoute(this.maze.getTiles().get(i).get(j))) {
                    mazeString.append("*");
                }else{
                mazeString.append(this.maze.getTiles().get(i).get(j).toString());
                }
            }
            mazeString.append("\n");
        }
        return mazeString.toString();
    }

    /**
     * In route boolean.
     *
     * @param tile the tile
     * @return the boolean
     */
    public boolean inRoute (Tile tile){
        for (Tile t : route){
            if (t.getLocalIndex() == tile.getLocalIndex())
                return true;
        }
        return false;
    }

    /**
     * Print route.
     */
    public void printRoute(){
        for (Tile t : route){
            System.out.println(("Tile at " + maze.getTileLocation(t).toString()));
        }
    }
}
