package id.ac.itb.stei.informatika.fifteenp.util;

import java.util.ArrayList;

/**
 * {@code FifteenMatrixBuilder} is a builder class to
 * build a {@code FifteenMatrix} by enumerating its
 * elements sequentially. It will throw various exceptions
 * on any errors to prevent creating an invalid
 * {@code FifteenMatrix} object.
 * @see FifteenMatrix
 */
public class FifteenMatrixBuilder {
    private Integer[] values;
    private int cursor;

    /**
     * Creates a new {@code FifteenMatrixBuilder} object
     * and initialize its elements sequence to empty.
     */
    public FifteenMatrixBuilder() {
        this.values = new Integer[16];
        for (int i = 0; i < 16; i++) {
            this.values[i] = null;
        }
        this.cursor = 0;
    }

    /**
     * Appends an element into the elements sequence
     * of the {@code FifteenMatrixBuilder}. The appending
     * is done in-place and the modified
     * {@code FifteenMatrixBuilder} is also returned
     * to enable method chaining.
     * @param value the element to append
     * @return the appended {@code FifteenMatrixBuilder}
     * @throws IllegalArgumentException if the element to
     *         append is not a valid element
     * @throws IndexOutOfBoundsException if the builder
     *         has 16 elements sequence already
     */
    public FifteenMatrixBuilder append(Integer value)
        throws IllegalArgumentException, IndexOutOfBoundsException {
        if (value != null && (value < 0 || value > 15)) {
            throw new IllegalArgumentException();
        }

        if (this.cursor > 16) {
            throw new IndexOutOfBoundsException();
        }

        this.values[this.cursor] = value;
        this.cursor++;

        return this;
    }

    /**
     * Builds a {@code FifteenMatrix} object based on
     * the elements sequence appended to this
     * {@code FifteenMatrixBuilder}.
     * @return a new {@code FifteenMatrix} object
     * @throws IllegalArgumentException if there are
     *         any duplicate value in the elements
     *         sequence
     */
    public FifteenMatrix build() throws IllegalArgumentException {
        FifteenMatrix puzzle = new FifteenMatrix();
        ArrayList<Boolean> flags = new ArrayList<>();

        int count = 16;
        while (count-- != 0) {
            flags.add(false);
        }

        for (int i = 0; i < 16; i++) {
            int flagIndex;

            if (this.values[i] == null) {
                flagIndex = 15;
            } else {
                flagIndex = this.values[i] - 1;
            }

            int row = i / 4;
            int col = i % 4;
            puzzle.set(row, col, this.values[i]);

            if (flags.get(flagIndex)) {
                throw new IllegalArgumentException();
            } else {
                flags.set(flagIndex, true);
            }
        }

        return puzzle;
    }
}
