package id.ac.itb.stei.informatika.fifteenp.util;

import java.util.Objects;

public class FifteenMatrix extends Matrix<Integer> {

    public FifteenMatrix() {
        super(4, 4);
    }

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

    public FifteenMatrix up() {
        int blankTileIndex = this.blankTileIndex();
        if (blankTileIndex < 4) {
            throw new IndexOutOfBoundsException();
        }

        FifteenMatrix newMatrix = this.copy();
        newMatrix.swap(blankTileIndex, blankTileIndex - 4);

        return newMatrix;
    }

    public FifteenMatrix down() {
        int blankTileIndex = this.blankTileIndex();
        if (blankTileIndex > 12) {
            throw new IndexOutOfBoundsException();
        }

        FifteenMatrix newMatrix = this.copy();
        newMatrix.swap(blankTileIndex, blankTileIndex + 4);

        return newMatrix;
    }

    public FifteenMatrix right() {
        int blankTileIndex = this.blankTileIndex();
        if (blankTileIndex % 4 == 3) {
            throw new IndexOutOfBoundsException();
        }

        FifteenMatrix newMatrix = this.copy();
        newMatrix.swap(blankTileIndex, blankTileIndex + 1);

        return newMatrix;
    }

    public FifteenMatrix left() {
        int blankTileIndex = this.blankTileIndex();
        if (blankTileIndex % 4 == 0) {
            throw new IndexOutOfBoundsException();
        }

        FifteenMatrix newMatrix = this.copy();
        newMatrix.swap(blankTileIndex, blankTileIndex - 1);

        return newMatrix;
    }

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

    public int lowerSum() {
        int sum = 0;
        for (int i = 1; i < 16; i++) {
            sum += this.lower(i);
        }
        return sum + this.lowerNull();
    }

    public int lower(Integer value) {
        if (value == null) {
            return this.lowerNull();
        }

        if (value < 0 || value > 15) {
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

    public int mismatchedTiles() {
        int mismatches = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Integer elem = this.values.get(i).get(j);

                if (elem == null) {
                    mismatches++;
                }
                if (elem != null && elem != 4 * i + j + 1) {
                    mismatches++;
                }
            }
        }
        return mismatches;
    }

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

    private void swap(int firstIndex, int secondIndex) {
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

}
