package maze.routing;

/**
 * The type No route found exception.
 */
public class NoRouteFoundException extends Exception{
    /**
     * Instantiates a new No route found exception.
     */
    NoRouteFoundException(){
        super("The maze has no route");
    }
}
