package maze;

/**
 * The type No exit exception.
 */
public class NoExitException  extends InvalidMazeException{
    /**
     * Instantiates a new No exit exception.
     */
    public NoExitException() {
        super("The maze has no exits");
    }
}
