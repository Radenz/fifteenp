package id.ac.itb.stei.informatika.fifteenp.util;

public enum Direction {
    UP,
    DOWN,
    RIGHT,
    LEFT;

    public static final Direction[] DIRECTIONS = {
            Direction.DOWN,
            Direction.RIGHT,
            Direction.LEFT,
            Direction.UP,
    };

    public Direction flip() {
        return switch (this) {
            case UP -> Direction.DOWN;
            case DOWN -> Direction.UP;
            case RIGHT -> Direction.LEFT;
            case LEFT -> Direction.RIGHT;
        };
    }
}
