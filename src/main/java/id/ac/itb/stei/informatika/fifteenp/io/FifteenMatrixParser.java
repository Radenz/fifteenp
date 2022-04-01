package id.ac.itb.stei.informatika.fifteenp.io;

import id.ac.itb.stei.informatika.fifteenp.util.FifteenMatrix;
import id.ac.itb.stei.informatika.fifteenp.util.FifteenMatrixBuilder;

/**
 * {@code FifteenMatrixParser} is a parser that parses
 * a string input with specific format to a {@code FifteenMatrix}.
 * Each row in the string input must be separated with a
 * newline and each column/character must be separated
 * with a whitespace.
 */
public class FifteenMatrixParser {
    /**
     * The parsing result.
     */
    private FifteenMatrix result;
    /**
     * A {@code FifteenMatrixBuilder} object to build
     * the parsed {@code FifteenMatrix}.
     */
    private FifteenMatrixBuilder builder;

    /**
     * Creates a new {@code FifteenMatrixParser} object.
     */
    public FifteenMatrixParser() {
        this.builder = new FifteenMatrixBuilder();
    }

    /**
     * Parses a string into a {@code FifteenMatrix} object.
     * @param input the string to parse
     */
    public void parse(String input) {
        String[] lines = input.split("\n");
        if (lines.length != 4) {
            throw new IllegalArgumentException();
        }
        for (String line: lines) {
            this.parseLine(line);
        }
        this.result = builder.build();
    }

    /**
     * Retrieves the parsing result.
     * @return the parsing result
     */
    public FifteenMatrix result() {
        return this.result;
    }

    /**
     * Parses a single line into a collection of
     * values for a single row in the {@code FifteenMatrix}
     * object.
     * @param line the line string to parse
     * @throws IllegalArgumentException if the line does
     *         not contain exactly 4 values
     */
    private void parseLine(String line) throws IllegalArgumentException {
        String[] values = line.split(" ");
        if (values.length != 4) {
            throw new IllegalArgumentException();
        }
        for (String value: values) {
            this.parseValue(value);
        }
    }

    /**
     * Parses a single value into an integer as a
     * single element in the {@code FifteenMatrix} object.
     * @param valueString the element value as a string
     * @throws IllegalArgumentException if the value string
     *         is empty or does not represent an integer
     */
    private void parseValue(String valueString) throws IllegalArgumentException {
        if (valueString.equals("")) {
            throw new IllegalArgumentException();
        }

        if (valueString.equals("-")) {
            builder.append(null);
        } else {
            Integer value = Integer.valueOf(valueString);
            builder.append(value);
        }
    }
}
