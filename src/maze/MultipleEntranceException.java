package maze;

public class MultipleEntranceException extends InvalidMazeException{
    public MultipleEntranceException() {
        super("The maze has multiple entrances");
    }
}
