package id.ac.itb.stei.informatika.fifteenp.util;

/**
 * {@code Direction} enumerates 4 equidistant discrete
 * directions.
 */
public enum Direction {
    /**
     * Upwards direction.
     */
    UP,
    /**
     * Downwards direction.
     */
    DOWN,
    /**
     * Rightwards direction.
     */
    RIGHT,
    /**
     * Leftwards direction.
     */
    LEFT;

    /**
     * All available directions.
     */
    public static final Direction[] DIRECTIONS = {
            Direction.DOWN,
            Direction.RIGHT,
            Direction.LEFT,
            Direction.UP,
    };

    /**
     * Flips a {@code Direction} into its opposite
     * direction, identical to rotating the direction
     * 180 degrees.
     * @return the opposite direction of this {@code Direction}
     */
    public Direction flip() {
        return switch (this) {
            case UP -> Direction.DOWN;
            case DOWN -> Direction.UP;
            case RIGHT -> Direction.LEFT;
            case LEFT -> Direction.RIGHT;
        };
    }
}
