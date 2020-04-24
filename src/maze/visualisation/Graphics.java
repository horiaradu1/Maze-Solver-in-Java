package maze.visualisation;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import maze.Maze;
import maze.Tile;
import maze.routing.RouteFinder;

import java.util.Stack;

/**
 * The type Graphics.
 */
public class Graphics {

    private  static int tileSize = 50;

    /**
     * Gets tile size.
     *
     * @return the tile size
     */
    public static int getTileSize() {
        return tileSize;
    }

    /**
     * Sets tile size.
     *
     * @param size the size
     */
    public void setTileSize(int size) {
        tileSize = size;
    }

    /**
     * Make canvas canvas.
     *
     * @param maze the maze
     * @return the canvas
     */
    public static Canvas makeCanvas (Maze maze){
        Canvas canvas = new Canvas(maze.getCols()* tileSize, maze.getRows()* tileSize);
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setFill(Color.CORNSILK);
        canvas.setWidth(maze.getCols()* tileSize);
        canvas.setHeight(maze.getRows()* tileSize);
        graphicsContext.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        drawMaze(canvas, maze);
        return canvas;
    }

    /**
     * Make canvas re.
     *
     * @param canvas the canvas
     * @param maze   the maze
     */
    public static void makeCanvasRe (Canvas canvas, Maze maze){
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setFill(Color.CORNSILK);
        canvas.setWidth(maze.getCols()* tileSize);
        canvas.setHeight(maze.getRows()* tileSize);
        graphicsContext.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        drawMaze(canvas, maze);
    }

    /**
     * Draw maze.
     *
     * @param canvas the canvas
     * @param maze   the maze
     */
    public static void drawMaze(Canvas canvas, Maze maze){
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setFill(Color.CORNSILK);
        for (int i = 0; i < maze.getRows(); i++){
            for (int j = 0; j < maze.getCols(); j++){
//                if (maze.getTiles().get(i).get(j).isNavigable()){
                    if (maze.getTiles().get(i).get(j).getType() == Tile.Type.WALL){
                        graphicsContext.setFill(Color.BLACK);
                        graphicsContext.fillRoundRect(j* tileSize, i* tileSize, tileSize, tileSize, tileSize /3, tileSize /3);
//                    }else if (maze.getTiles().get(i).get(j).getType() == Type.CORRIDOR){
//                        graphicsContext.setFill(Color.WHITE);
//                        graphicsContext.fillRoundRect(j* tileSize, i* tileSize, tileSize, tileSize, tileSize /3, tileSize /3);
                    }else if (maze.getTiles().get(i).get(j).getType() == Tile.Type.ENTRANCE){
                        graphicsContext.setFill(Color.TOMATO);
                        graphicsContext.fillRoundRect(j* tileSize, i* tileSize, tileSize, tileSize, tileSize /3, tileSize /3);
                    }else if (maze.getTiles().get(i).get(j).getType() == Tile.Type.EXIT){
                        graphicsContext.setFill(Color.CHARTREUSE);
                        graphicsContext.fillRoundRect(j* tileSize, i* tileSize, tileSize, tileSize, tileSize /3, tileSize /3);
                    }
//                }
            }
        }
    }

    /**
     * Draw route.
     *
     * @param canvas      the canvas
     * @param routeFinder the route finder
     */
    public static void drawRoute(Canvas canvas, RouteFinder routeFinder) {
        Stack<Tile> tiles = (Stack<Tile>) routeFinder.getRoute();
        for (Tile tile : tiles){
            //drawTile(canvas, routeFinder.getMaze(), tile);
            Maze maze = routeFinder.getMaze();
            Maze.Coordinate coords = maze.getTileLocation(tile).changeToRead(maze.getRows());
            GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
            graphicsContext.setFill(Color.DEEPSKYBLUE);
            graphicsContext.fillRoundRect(coords.getY()* tileSize, coords.getX()* tileSize, tileSize, tileSize, tileSize /3, tileSize /3);

        }
    }

    //    public static void drawTile(Canvas canvas, Maze maze, Tile tile) {
//        Coordinate coords = maze.getTileLocation(tile).changeToRead(maze.getRows());
//        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
//        graphicsContext.setFill(Color.RED);
//        graphicsContext.fillRoundRect(coords.getY()* tileSize, coords.getX()* tileSize, tileSize, tileSize, tileSize /3, tileSize /3);
//    }

}
