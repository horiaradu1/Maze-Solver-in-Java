package maze.routing;

public class NoRouteFoundException extends Exception{
    NoRouteFoundException(){
        super("The maze has no route");
    }
}
