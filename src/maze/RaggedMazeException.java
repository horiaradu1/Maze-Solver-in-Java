package maze;

public class RaggedMazeException extends InvalidMazeException{
    public RaggedMazeException() {
        super("The maze has inconsistent dimensions, it is ragged");
    }
}
