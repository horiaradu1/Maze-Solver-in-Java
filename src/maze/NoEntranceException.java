package maze;

/**
 * The type No entrance exception.
 */
public class NoEntranceException extends InvalidMazeException{
    /**
     * Instantiates a new No entrance exception.
     */
    public NoEntranceException() {
        super("The maze has no entrance");
    }
}
