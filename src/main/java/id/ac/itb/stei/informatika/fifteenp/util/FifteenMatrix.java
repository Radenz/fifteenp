package id.ac.itb.stei.informatika.fifteenp.util;

import java.security.InvalidParameterException;

public class FifteenMatrix extends Matrix<Integer> {

    public FifteenMatrix() {
        super(4, 4);
    }

    //    TODO: Impl
    public FifteenMatrix copy() {
        return this;
    }

    //    TODO: Impl
    public void up() {

    }

    //    TODO: Impl
    public void down() {

    }

    //    TODO: Impl
    public void right() {

    }

    //    TODO: Impl
    public void left() {

    }

    //    TODO: Impl
    public void solve() {

    }

    public int lower(int value) {
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

}
