package id.ac.itb.stei.informatika.fifteenp.util;

import java.util.ArrayList;

/**
 * {@code Matrix} is a two-dimensional array type that holds
 * a generic type of elements.
 * @param <T> type of the matrix elements.
 */
public class Matrix<T> {

    /**
     * The number of rows this {@code Matrix} has.
     */
    protected final int rows;
    /**
     * The number of columns this {@code Matrix} has.
     */
    protected final int cols;
    /**
     * Values stored in this {@code Matrix}.
     */
    ArrayList<ArrayList<T>> values;

    /**
     * Creates a matrix with predetermined rows and columns
     * and initializes its elements with null values.
     * @param rows number of rows the matrix has.
     * @param cols number of columns the matrix has.
     */
    public Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.values = new ArrayList<>(this.rows);
        for (int i = 0; i < this.rows; i++) {
            ArrayList<T> row = new ArrayList<>(this.cols);
            for (int j = 0; j < this.cols; j++) {
                row.add(null);
            }
            this.values.add(row);
        }
    }

    @Override
    public String toString() {
        String res = "";
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                if (j != 0) {
                    res += " ";
                }
                res += this.get(i, j).toString();
            }
            res += "\n";
        }
        return res;
    }

    /**
     * Returns the number of rows of the matrix.
     * @return the number of rows of the matrix.
     */
    public int rows() {
        return this.rows;
    }

    /**
     * Returns the number of columns of the matrix.
     * @return the number of columns of the matrix.
     */
    public int cols() {
        return this.cols;
    }

    /**
     * Checks if a pair of row and column indices
     * is a valid indices of the matrix.
     * @param rowIndex row index to check.
     * @param colIndex column index to check.
     * @return true if the pair of indices is a valid
     * indices, false otherwise.
     */
    public boolean hasIndices(int rowIndex, int colIndex) {
        return rowIndex >= 0 && rowIndex < this.rows &&
                colIndex >= 0 && colIndex < this.cols;
    }

    /**
     * Sets an element of the matrix at a specific index.
     * @param rowIndex the row index of the element to set.
     * @param colIndex the column index of the element to set.
     * @param element value of element to set.
     */
    public void set(int rowIndex, int colIndex, T element) {
        this.values.get(rowIndex).set(colIndex, element);
    }

    /**
     * Gets an element of the matrix at a specific index.
     * @param rowIndex the row index of the element to get.
     * @param colIndex the column index of the element to set.
     * @return the matrix element at the specified indices.
     */
    public T get(int rowIndex, int colIndex) {
        return this.values.get(rowIndex).get(colIndex);
    }
}