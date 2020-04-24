package maze;

import java.io.Serializable;

/**
 * The type Tile.
 */
public class Tile implements Serializable {
    private Type type;
    private int localIndex;
    private static int globalIndex = 0;

    private Tile (Type t) {
        this.type = t;
        this.localIndex = globalIndex;
        globalIndex += 1;
    }

    /**
     * From char tile.
     *
     * @param c the c
     * @return the tile
     * @throws InvalidMazeException the invalid maze exception
     */
    protected static Tile fromChar(char c) throws InvalidMazeException{
        if (c == '.'){
            return new Tile(Type.CORRIDOR);
        }
        else if (c == '#'){
            return new Tile(Type.WALL);
        }
        else if (c == 'e'){
            return new Tile(Type.ENTRANCE);
        }
        else if (c == 'x'){
            return new Tile(Type.EXIT);
        }
        throw new InvalidMazeException("Invalid Character Entered");
    }

    /**
     * Gets local index.
     *
     * @return the local index
     */
    public int getLocalIndex() {
        return localIndex;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public Type getType() {
        return type;
    }

    /**
     * Is navigable boolean.
     *
     * @return the boolean
     */
    public boolean isNavigable() {
        return this.type != Type.WALL;
    }

    /**
     * To string string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        if (this.type == Type.CORRIDOR){
            return ".";
        }
        else if (this.type == Type.WALL){
            return "#";
        }
        else if (this.type == Type.ENTRANCE){
            return "e";
        }
        else if (this.type == Type.EXIT){
            return "x";
        }
        return null;
    }

    /**
     * The enum Type.
     */
    public enum Type {
        /**
         * Corridor type.
         */
        CORRIDOR,
        /**
         * Entrance type.
         */
        ENTRANCE,
        /**
         * Exit type.
         */
        EXIT,
        /**
         * Wall type.
         */
        WALL;
    }
}
