package maze;

/**
 * The type Ragged maze exception.
 */
public class RaggedMazeException extends InvalidMazeException{
    /**
     * Instantiates a new Ragged maze exception.
     */
    public RaggedMazeException() {
        super("The maze has inconsistent dimensions, it is ragged");
    }
}
