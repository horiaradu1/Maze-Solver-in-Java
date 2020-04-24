package maze;

public class NoExitException  extends InvalidMazeException{
    public NoExitException() {
        super("The maze has no exits");
    }
}
