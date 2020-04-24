package maze;

public class MultipleExitException extends InvalidMazeException{
    public MultipleExitException() {
        super("The maze has multiple exits");
    }
}
