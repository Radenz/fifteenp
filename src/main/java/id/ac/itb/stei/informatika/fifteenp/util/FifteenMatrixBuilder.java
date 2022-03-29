package id.ac.itb.stei.informatika.fifteenp.util;

public class FifteenMatrixBuilder {
    private Integer[] values;
    private int cursor;

    public FifteenMatrixBuilder() {
        this.values = new Integer[16];
        for (int i = 0; i < 16; i++) {
            this.values[i] = null;
        }
        this.cursor = 0;
    }

    public FifteenMatrixBuilder append(Integer value) {
        if (this.cursor > 15) {
            throw new IndexOutOfBoundsException();
        }

        this.values[this.cursor] = value;
        this.cursor++;

        return this;
    }

    public FifteenMatrix build() {
        FifteenMatrix puzzle = new FifteenMatrix();

        int nulls = 0;
        for (int i = 0; i < 16; i++) {
            if (this.values[i] == null) {
                nulls += 1;
            }

            int row = i / 4;
            int col = i % 4;
            puzzle.set(row, col, this.values[i]);
        }

        if (nulls != 1) {
            throw new IllegalArgumentException(
                    "15-Puzzle matrix must contains exactly 1 empty cell."
            );
        }

        return puzzle;
    }
}
