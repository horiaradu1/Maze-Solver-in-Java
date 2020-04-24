package maze;

/**
 * The type Multiple entrance exception.
 */
public class MultipleEntranceException extends InvalidMazeException{
    /**
     * Instantiates a new Multiple entrance exception.
     */
    public MultipleEntranceException() {
        super("The maze has multiple entrances");
    }
}
