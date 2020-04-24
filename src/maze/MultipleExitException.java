package maze;

/**
 * The type Multiple exit exception.
 */
public class MultipleExitException extends InvalidMazeException{
    /**
     * Instantiates a new Multiple exit exception.
     */
    public MultipleExitException() {
        super("The maze has multiple exits");
    }
}
