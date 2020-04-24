package maze;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Maze.
 */
public class Maze implements Serializable {

    private Tile entrance;
    private Tile exit;
    //private ArrayList<ArrayList<Tile>> tiles;
    private List<List<Tile>> tiles = new ArrayList<List<Tile>>();
    private int rows;
    private int cols;

    private  Maze() {
        this.entrance = null;
        this.exit = null;
        //this.tiles = new ArrayList<ArrayList<Tile>>();
    }

    /**
     * From txt maze.
     *
     * @param txt the txt
     * @return the maze
     * @throws InvalidMazeException the invalid maze exception
     * @throws IOException          the io exception
     */
    public static Maze fromTxt(String txt) throws InvalidMazeException, IOException {
//        try(
//                FileReader fileReader = new FileReader(txt);
//                BufferedReader bufferedReader = new BufferedReader(fileReader))
//        {
            FileReader fileReader = new FileReader(txt);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            Maze maze = new Maze();
            maze.rows = 0;
            maze.cols = 0;
            while ((line = bufferedReader.readLine()) != null){
                maze.rows++;
                if(maze.rows == 1)
                    maze.cols = line.length();
                ArrayList<Tile> list = new ArrayList<Tile>();
                for (char character : line.toCharArray()){
                    Tile tile = Tile.fromChar(character);
                    list.add(tile);
                    if (tile.getType() == Tile.Type.ENTRANCE){
                        if (maze.entrance != null){
                            throw new MultipleEntranceException();
                        }
                        maze.entrance = tile;
                    }
                    if (tile.getType() == Tile.Type.EXIT){
                        if (maze.exit != null){
                            throw new MultipleExitException();
                        }
                        maze.exit = tile;
                    }
                }
                maze.tiles.add(list);
                if (maze.cols != list.size()){
                    throw new RaggedMazeException();
                }
            }
            if (maze.entrance == null){
                throw new NoEntranceException();
            }
            if (maze.exit == null){
                throw new NoExitException();
            }
            return maze;
//        }catch(IOException e){
//            System.out.println("Error: IOException when reading " + txt + ".txt");
//            return null;
//        }
    }

    /**
     * Gets tile at location.
     *
     * @param c the c
     * @return the tile at location
     */
    public Tile getTileAtLocation(Coordinate c) {
        Coordinate coordsLocation = c.changeToRead(this.rows);
        return tiles.get(coordsLocation.getX()).get(coordsLocation.getY());
    }

    /**
     * Gets tile location.
     *
     * @param t the t
     * @return the tile location
     */
    public Coordinate getTileLocation(Tile t) {
        for (int i = 0; i < this.rows; i++)
            for (int j = 0; j < this.cols; j++)
                if (tiles.get(i).get(j).getLocalIndex() == t.getLocalIndex()){
                    return new Coordinate(i,j).changeToMaze(this.rows);
                }
        return null;
    }

    /**
     * Gets adjacent tile.
     *
     * @param t the t
     * @param d the d
     * @return the adjacent tile
     */
    public Tile getAdjacentTile(Tile t, Direction d) {
        Coordinate tileLoc = getTileLocation(t);
        if (d == Direction.NORTH){
            if (tileLoc.getY()+1 < this.rows)
                return getTileAtLocation(new Coordinate(tileLoc.getX(), tileLoc.getY()+1));
        }else if (d == Direction.SOUTH){
            if (tileLoc.getY()-1 >= 0)
                return getTileAtLocation(new Coordinate(tileLoc.getX(), tileLoc.getY()-1));
        }else if (d == Direction.WEST){
            if (tileLoc.getX()-1 >= 0)
                return getTileAtLocation(new Coordinate(tileLoc.getX()-1, tileLoc.getY()));
        }else if (d == Direction.EAST){
            if (tileLoc.getX()+1 < this.cols)
                return getTileAtLocation(new Coordinate(tileLoc.getX()+1, tileLoc.getY()));
        }
        return null;
    }

    /**
     * Gets tiles.
     *
     * @return the tiles
     */
    public List<List<Tile>> getTiles() {
        return tiles;
    }

    /**
     * Gets entrance.
     *
     * @return the entrance
     */
    public Tile getEntrance() {
        return entrance;
    }

    /**
     * Gets exit.
     *
     * @return the exit
     */
    public Tile getExit() {
        return exit;
    }

    private void setEntrance(Tile entrance) throws MultipleEntranceException {
        if (this.getTileLocation(entrance) == null){
            throw new IllegalArgumentException();
        }
        else if (this.getEntrance() != null){
            throw new MultipleEntranceException();
        }
        this.entrance = entrance;
    }

    private void setExit(Tile exit) throws MultipleExitException {
        if (this.getTileLocation(exit) == null){
            throw new IllegalArgumentException();
        }
        else if (this.getExit() != null){
            throw new MultipleExitException();
        }
        this.exit = exit;
    }

    /**
     * Gets rows.
     *
     * @return the rows
     */
    public int getRows() {
        return rows;
    }

    /**
     * Gets cols.
     *
     * @return the cols
     */
    public int getCols() {
        return cols;
    }

    /**
     * To string string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        StringBuilder mazeString = new StringBuilder();
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                mazeString.append(this.tiles.get(i).get(j).toString());
            }
            mazeString.append("\n");
        }
        return mazeString.toString();
    }

    /**
     * The type Coordinate.
     */
    public static class Coordinate {

        private int x;
        private int y;

        /**
         * Instantiates a new Coordinate.
         *
         * @param x the x
         * @param y the y
         */
        public Coordinate(int x, int y){
            this.x = x;
            this.y = y;
        }

        /**
         * Change to maze coordinate.
         *
         * @param rowNumber the row number
         * @return the coordinate
         */
        public Coordinate changeToMaze (int rowNumber){
            int x = this.y;
            int y = rowNumber - 1 - this.x;
            return new Coordinate(x,y);
        }

        /**
         * Change to read coordinate.
         *
         * @param rowNumber the row number
         * @return the coordinate
         */
        public Coordinate changeToRead (int rowNumber){
            int x = rowNumber - 1 - this.y;
            int y = this.x;
            return new Coordinate(x,y);
        }

        /**
         * Gets x.
         *
         * @return the x
         */
        public int getX() {
            return x;
        }

        /**
         * Gets y.
         *
         * @return the y
         */
        public int getY() {
            return y;
        }

        /**
         * To string string.
         *
         * @return the string
         */
        @Override
        public String toString() {
            return "(" + getX() + ", " + getY() + ")";
        }
    }

    /**
     * The enum Direction.
     */
    public enum Direction {
        /**
         * North direction.
         */
        NORTH,
        /**
         * South direction.
         */
        SOUTH,
        /**
         * East direction.
         */
        EAST,
        /**
         * West direction.
         */
        WEST;
    }
}
