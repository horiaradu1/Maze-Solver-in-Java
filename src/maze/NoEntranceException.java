package maze;

public class NoEntranceException extends InvalidMazeException{
    public NoEntranceException() {
        super("The maze has no entrance");
    }
}
