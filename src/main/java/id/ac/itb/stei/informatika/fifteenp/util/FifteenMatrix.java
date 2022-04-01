package id.ac.itb.stei.informatika.fifteenp.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

/**
 * {@code FifteenMatrix} is an integer {@code Matrix} that has
 * exactly 4 rows and 4 columns and contains only the integers
 * 0-15 each of which only appears exactly once and a single
 * null value which represents the blank tile (cell).
 */
public class FifteenMatrix extends Matrix<Integer> {

    /**
     * Creates a new {@code FifteenMatrix} object.
     */
    public FifteenMatrix() {
        super(4, 4);
    }

    /**
     * Converts the {@code FifteenMatrix} object into its
     * {@code String} representation.
     * @return string representation of this {@code FifteenMatrix}
     */
    @Override
    public String toString() {
        String res = "";
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                if (j != 0) {
                    res += " ";
                }
                Integer elem = this.get(i, j);
                if (elem == null) {
                    res += "  ";
                } else {
                    res += String.format("%2d", elem);
                }
            }
            res += "\n";
        }
        return res;
    }

    /**
     * Copies a {@code FifteenMatrix} object.
     * @return a copy of this {@code FifteenMatrix}
     */
    public FifteenMatrix copy() {
        FifteenMatrixBuilder builder = new FifteenMatrixBuilder();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Integer elem = this.values.get(i).get(j);
                builder.append(elem);
            }
        }
        return builder.build();
    }

    /**
     * Moves the blank tile of this {@code FifteenMatrix}
     * to a specified direction.
     * @param dir direction to move the blank tile
     * @return a new {@code FifteenMatrix} object after moving
     *         the blank tile
     */
    public FifteenMatrix moveBlankTile(Direction dir) {
        switch (dir) {
            case UP:
                return this.up();
            case DOWN:
                return this.down();
            case RIGHT:
                return this.right();
            case LEFT:
            default:
                return this.left();
        }
    }

    /**
     * Moves the blank tile of this {@code FifteenMatrix} upwards.
     * @return a new {@code FifteenMatrix} object after moving
     *         the blank tile upwards
     * @throws IndexOutOfBoundsException if the blank tile cannot
     *         be moved upwards
     */
    public FifteenMatrix up() throws IndexOutOfBoundsException {
        int blankTileIndex = this.blankTileIndex();
        if (blankTileIndex < 4) {
            throw new IndexOutOfBoundsException();
        }

        FifteenMatrix newMatrix = this.copy();
        newMatrix.swap(blankTileIndex, blankTileIndex - 4);

        return newMatrix;
    }

    /**
     * Moves the blank tile of this {@code FifteenMatrix} downwards.
     * @return a new {@code FifteenMatrix} object after moving
     *         the blank tile downwards
     * @throws IndexOutOfBoundsException if the blank tile cannot
     *         be moved downwards
     */
    public FifteenMatrix down() throws IndexOutOfBoundsException {
        int blankTileIndex = this.blankTileIndex();
        if (blankTileIndex > 12) {
            throw new IndexOutOfBoundsException();
        }

        FifteenMatrix newMatrix = this.copy();
        newMatrix.swap(blankTileIndex, blankTileIndex + 4);

        return newMatrix;
    }

    /**
     * Moves the blank tile of this {@code FifteenMatrix} rightwards.
     * @return a new {@code FifteenMatrix} object after moving
     *         the blank tile rightwards
     * @throws IndexOutOfBoundsException if the blank tile cannot
     *         be moved rightwards
     */
    public FifteenMatrix right() throws IndexOutOfBoundsException {
        int blankTileIndex = this.blankTileIndex();
        if (blankTileIndex % 4 == 3) {
            throw new IndexOutOfBoundsException();
        }

        FifteenMatrix newMatrix = this.copy();
        newMatrix.swap(blankTileIndex, blankTileIndex + 1);

        return newMatrix;
    }

    /**
     * Moves the blank tile of this {@code FifteenMatrix} leftwards.
     * @return a new {@code FifteenMatrix} object after moving
     *         the blank tile leftwards
     * @throws IndexOutOfBoundsException if the blank tile cannot
     *         be moved leftwards
     */
    public FifteenMatrix left() throws IndexOutOfBoundsException {
        int blankTileIndex = this.blankTileIndex();
        if (blankTileIndex % 4 == 0) {
            throw new IndexOutOfBoundsException();
        }

        FifteenMatrix newMatrix = this.copy();
        newMatrix.swap(blankTileIndex, blankTileIndex - 1);

        return newMatrix;
    }

    /**
     * @deprecated
     * Compares two {@code FifteenMatrix} objects based on their
     * elements' layout.
     * @param other an {@code FifteenMatrix} to compare this
     *              {@code FifteenMatrix} with
     * @return true if all elements of both matrices are in the
     *         same exact cells
     */
    @Deprecated
    public boolean equals(FifteenMatrix other) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (!Objects.equals(this.values.get(i).get(j), other.values.get(i).get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Calculates the sum of all l values and x value of
     * this {@code FifteenMatrix}.
     * @see <a href="http://www.cs.umsl.edu/~sanjiv/classes/cs5130/lectures/bb.pdf">
     *     Branch and Bound lecture note</a>
     * @return the sum of all l values and x value of this
     *         {@code FifteenMatrix}
     */
    public int lowerSum() {
        int blankTileIndex = this.blankTileIndex();
        int factor = (blankTileIndex / 4 + blankTileIndex % 4) % 2;
        int sum = 0;
        for (int i = 1; i < 16; i++) {
            sum += this.lower(i);
        }
        return sum + this.lowerNull() + factor;
    }

    /**
     * Calculates the l value of a specified element of
     * this {@code FifteenMatrix}.
     * @param value the element value to calculate the l
     *              value of
     * @see <a href="http://www.cs.umsl.edu/~sanjiv/classes/cs5130/lectures/bb.pdf">
     *     Branch and Bound lecture note</a>
     * @return the l value of the specified element
     * @throws IllegalArgumentException if the specified
     *         element is not a valid {@code FifteenMatrix}
     *         element.
     */
    public int lower(Integer value) throws IllegalArgumentException {
        if (value == null) {
            return this.lowerNull();
        }

        if (value < 1 || value > 15) {
            throw new IllegalArgumentException();
        }

        int opposite = 0;

        iteration:
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Integer elem = this.values.get(i).get(j);

                if (elem != null && elem < value) {
                    opposite++;
                }

                if (elem != null && elem == value) {
                    break iteration;
                }
            }
        }

        return value - 1 - opposite;
    }

    /**
     * Calculates the l value of the blank tile of
     * this {@code FifteenMatrix}.
     * @see <a href="http://www.cs.umsl.edu/~sanjiv/classes/cs5130/lectures/bb.pdf">
     *     Branch and Bound lecture note</a>
     * @return the l value of the blank tile
     */
    public int lowerNull() {
        int opposite = 0;

        iteration:
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Integer elem = this.values.get(i).get(j);
                if (elem != null) {
                    opposite++;
                } else {
                    break iteration;
                }
            }
        }

        return 15 - opposite;
    }

    /**
     * Counts the amount of elements which is
     * not correctly placed on their own tiles,
     * excluding the blank tile.
     * @return the amount of misplaced elements
     */
    public int mismatchedTiles() {
        int mismatches = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Integer elem = this.values.get(i).get(j);
                if (elem != null && elem != 4 * i + j + 1) {
                    mismatches++;
                }
            }
        }
        return mismatches;
    }

    /**
     * Retrieves the blank tile index of this
     * {@code FifteenMatrix} object.
     * @return the blank tile index of the {@code FifteenMatrix}
     */
    public int blankTileIndex() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (this.values.get(i).get(j) == null) {
                    return 4 * i + j;
                }
            }
        }

        return -1;
    }

    /**
     * Swaps a tile with another tile within this {@code FifteenMatrix}.
     * The swapping process is done in-place, thus not creating a new
     * {@code FifteenMatrix} object.
     * @param firstIndex    the first tile index to swap
     * @param secondIndex   the second tile index to swap
     * @throws IndexOutOfBoundsException if either tile index is invalid
     */
    private void swap(int firstIndex, int secondIndex)
            throws IndexOutOfBoundsException {
        if (firstIndex < 0 || firstIndex > 15
        || secondIndex < 0 || secondIndex > 15) {
            throw new IndexOutOfBoundsException();
        }

        Integer a = this.values.get(firstIndex / 4)
                .get(firstIndex % 4);
        Integer b = this.values.get(secondIndex / 4)
                .get(secondIndex % 4);

        this.values.get(firstIndex / 4)
                .set(firstIndex % 4, b);
        this.values.get(secondIndex / 4)
                .set(secondIndex % 4, a);
    }

    /**
     * Decodes a long integer into its represented
     * {@code FifteenMatrix} object. The decoding is
     * done by splitting 64 bits long integer into
     * 16 4-bits integers and converting each of them
     * to the integers 0-15 with 0 being a blank tile.
     * @see FifteenMatrix#identity()
     * @param id the long integer representation of
     *           the matrix to decode
     * @return {@code FifteenMatrix} object represented
     *         by the long integer
     */
    public static FifteenMatrix from(long id) {
        ArrayList<Integer> values = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int value = (int) (((id % 16) + 16) % 16);
                if (value == 0) {
                    values.add(null);
                } else {
                    values.add(value);
                }
                id >>= 4;
            }
        }
        Collections.reverse(values);
        FifteenMatrixBuilder builder = new FifteenMatrixBuilder();
        for (Integer value: values) {
            builder.append(value);
        }
        return builder.build();
    }

    /**
     * Encodes this {@code FifteenMatrix} object into its
     * long integer representation. The encoding is done
     * by compressing each element into 4 bits of data
     * and concatenating them all into a single 64 bits
     * long integer.
     * @see FifteenMatrix#from(long)
     * @return long integer representation of this {@code FifteenMatrix}
     */
    public long identity() {
        long id = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                id <<= 4;
                Integer elem = this.values.get(i).get(j);
                if (elem != null) {
                    id += elem;
                }
            }
        }
        return id;
    }

}
