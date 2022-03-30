package id.ac.itb.stei.informatika.fifteenp.util;

import java.util.ArrayList;

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

    public FifteenMatrix build() {
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
