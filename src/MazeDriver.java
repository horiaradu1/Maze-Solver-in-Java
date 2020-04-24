import maze.InvalidMazeException;
import maze.Maze;
import maze.routing.NoRouteFoundException;
import maze.routing.RouteFinder;

import java.io.IOException;

public class MazeDriver {
    public static void main (String[] args) {

        try {
            Maze maze = Maze.fromTxt("/home/horia/Documents/JavaStuff/comp16412-coursework-2_k55592hr/src/maze/maze1.txt");
            System.out.println(maze.toString());
            RouteFinder routeFinder = new RouteFinder(maze);
            routeFinder.step();
            routeFinder.step();
            routeFinder.step();
            routeFinder.step();
            routeFinder.step();
            routeFinder.step();
            routeFinder.step();
            routeFinder.step();
            routeFinder.step();
            routeFinder.step();
            routeFinder.printRoute();
            System.out.println(routeFinder.toString());
        } catch (InvalidMazeException | IOException e) {
            e.printStackTrace();
        }


    }
}
