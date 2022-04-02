package id.ac.itb.stei.informatika.fifteenp;

import id.ac.itb.stei.informatika.fifteenp.util.Direction;
import id.ac.itb.stei.informatika.fifteenp.util.FifteenMatrix;
import id.ac.itb.stei.informatika.fifteenp.util.FifteenMatrixBuilder;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * {@code FifteenMatrixController} is a controller for the
 * matrix grid display.
 */
public class FifteenMatrixController {
    /**
     * Label for the matrix element in index (0, 0).
     */
    @FXML
    private TextArea cell0;
    /**
     * Label for the matrix element in index (0, 1).
     */
    @FXML
    private TextArea cell1;
    /**
     * Label for the matrix element in index (0, 2).
     */
    @FXML
    private TextArea cell2;
    /**
     * Label for the matrix element in index (0, 3).
     */
    @FXML
    private TextArea cell3;
    /**
     * Label for the matrix element in index (1, 0).
     */
    @FXML
    private TextArea cell4;
    /**
     * Label for the matrix element in index (1, 1).
     */
    @FXML
    private TextArea cell5;
    /**
     * Label for the matrix element in index (1, 2).
     */
    @FXML
    private TextArea cell6;
    /**
     * Label for the matrix element in index (1, 3).
     */
    @FXML
    private TextArea cell7;
    /**
     * Label for the matrix element in index (2, 0).
     */
    @FXML
    private TextArea cell8;
    /**
     * Label for the matrix element in index (2, 1).
     */
    @FXML
    private TextArea cell9;
    /**
     * Label for the matrix element in index (2, 2).
     */
    @FXML
    private TextArea cell10;
    /**
     * Label for the matrix element in index (2, 3).
     */
    @FXML
    private TextArea cell11;
    /**
     * Label for the matrix element in index (3, 0).
     */
    @FXML
    private TextArea cell12;
    /**
     * Label for the matrix element in index (3, 1).
     */
    @FXML
    private TextArea cell13;
    /**
     * Label for the matrix element in index (3, 2).
     */
    @FXML
    private TextArea cell14;
    /**
     * Label for the matrix element in index (3, 3).
     */
    @FXML
    private TextArea cell15;
    /**
     * Array of all available matrix element labels.
     */
    private TextArea[] cells;
    /**
     * Current displayed matrix.
     */
    private FifteenMatrix currentMatrix;
    /**
     * Border style for swapped tiles.
     */
    private Border swapBorderStyle;
    /**
     * Default border style for all tiles.
     */
    private Border defaultBorderStyle;

    /**
     * Initializes this controller.
     */
    @FXML
    public void initialize() {
        this.swapBorderStyle = new Border(new BorderStroke(
                Color.valueOf("#ffb300"),
                BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY,
                BorderStroke.THICK
        ));

        this.cells = new TextArea[]{
                cell0, cell1, cell2, cell3,
                cell4, cell5, cell6, cell7,
                cell8, cell9, cell10, cell11,
                cell12, cell13, cell14, cell15,
        };

        this.defaultBorderStyle = new Border(
                new BorderStroke(
                        Color.WHITESMOKE,
                        BorderStrokeStyle.SOLID,
                        CornerRadii.EMPTY,
                        BorderStroke.DEFAULT_WIDTHS
                )
        );

        for (TextArea cell: this.cells) {
            cell.setFont(
                    Font.font("Arial", FontWeight.BOLD, 24)
            );
            cell.setStyle("-fx-background-color: transparent;" +
                    "-fx-text-fill: whitesmoke;" +
                    "-fx-line-spacing: 0;");
            cell.setBorder(defaultBorderStyle);
            cell.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.matches("\\d*")) {
                    cell.setText(oldValue);
                } else if (!newValue.equals("")) {
                    Integer value = Integer.valueOf(newValue);
                    if (value > 15) {
                        cell.setText(oldValue);
                    }
                }
            });
        }
    }

    /**
     * Sets the displayed matrix into the specified {@code FifteenMatrix}
     * object.
     * @param matrix {@code FifteenMatrix} object to display
     */
    public void setMatrix(FifteenMatrix matrix) {
        this.currentMatrix = matrix;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                TextArea cell = this.cells[i * 4 + j];
                Integer value = matrix.get(i, j);
                if (value == null) {
                    cell.setText("");
                } else {
                    cell.setText(value.toString());
                }
            }
        }
    }

    /**
     * Sets the direction of blank tile movement. This will
     * add a border in the blank tile label and its destination
     * element label.
     * @param dir the direction of movement to set
     */
    public void setDirection(Direction dir) {
        for (TextArea cell: this.cells) {
            cell.setBorder(defaultBorderStyle);
        }
        if (dir != null) {
            int blankTileIndex = this.currentMatrix.blankTileIndex();
            int swapIndex = switch (dir) {
                case UP -> blankTileIndex - 4;
                case DOWN -> blankTileIndex + 4;
                case RIGHT -> blankTileIndex + 1;
                case LEFT -> blankTileIndex - 1;
            };
            TextArea blankTileCell = this.cells[blankTileIndex];
            TextArea swapCell = this.cells[swapIndex];
            blankTileCell.setBorder(this.swapBorderStyle);
            swapCell.setBorder(this.swapBorderStyle);
        }
    }

    /**
     * Parses the displayed matrix into a {@code FifteenMatrix}
     * object.
     * @return a {@code FifteenMatrix} displayed in the ui
     */
    public FifteenMatrix parse() {
        FifteenMatrixBuilder builder = new FifteenMatrixBuilder();
        for (TextArea cell: cells) {
            Integer value;
            if (cell.getText().equals("")) {
                value = null;
            } else {
                value = Integer.valueOf(cell.getText());
            }
            builder.append(value);
        }
        return builder.build();
    }
}
