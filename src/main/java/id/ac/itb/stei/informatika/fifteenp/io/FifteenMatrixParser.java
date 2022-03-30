package id.ac.itb.stei.informatika.fifteenp.io;

import id.ac.itb.stei.informatika.fifteenp.util.FifteenMatrix;
import id.ac.itb.stei.informatika.fifteenp.util.FifteenMatrixBuilder;

public class FifteenMatrixParser {
    private FifteenMatrix result;
    private FifteenMatrixBuilder builder;

    public FifteenMatrixParser() {
        this.builder = new FifteenMatrixBuilder();
    }

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

    public FifteenMatrix result() {
        return this.result;
    }

    private void parseLine(String line) {
        String[] values = line.split(" ");
        if (values.length != 4) {
            throw new IllegalArgumentException();
        }
        for (String value: values) {
            this.parseValue(value);
        }
    }

    private void parseValue(String valueString) {
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
