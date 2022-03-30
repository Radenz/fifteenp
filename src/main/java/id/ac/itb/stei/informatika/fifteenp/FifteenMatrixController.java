package id.ac.itb.stei.informatika.fifteenp;

import id.ac.itb.stei.informatika.fifteenp.util.Direction;
import id.ac.itb.stei.informatika.fifteenp.util.FifteenMatrix;
import id.ac.itb.stei.informatika.fifteenp.util.FifteenMatrixBuilder;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class FifteenMatrixController {
    @FXML
    private TextArea cell0;
    @FXML
    private TextArea cell1;
    @FXML
    private TextArea cell2;
    @FXML
    private TextArea cell3;
    @FXML
    private TextArea cell4;
    @FXML
    private TextArea cell5;
    @FXML
    private TextArea cell6;
    @FXML
    private TextArea cell7;
    @FXML
    private TextArea cell8;
    @FXML
    private TextArea cell9;
    @FXML
    private TextArea cell10;
    @FXML
    private TextArea cell11;
    @FXML
    private TextArea cell12;
    @FXML
    private TextArea cell13;
    @FXML
    private TextArea cell14;
    @FXML
    private TextArea cell15;

    private TextArea[] CELLS;

    @FXML
    public void initialize() {
        CELLS = new TextArea[]{
                cell0, cell1, cell2, cell3,
                cell4, cell5, cell6, cell7,
                cell8, cell9, cell10, cell11,
                cell12, cell13, cell14, cell15,
        };

        for (TextArea cell: CELLS) {

            cell.setFont(
                    Font.font("Arial", FontWeight.BOLD, 24)
            );

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

    public void setMatrix(FifteenMatrix matrix) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                TextArea cell = this.CELLS[i * 4 + j];
                Integer value = matrix.get(i, j);
                if (value == null) {
                    cell.setText("");
                } else {
                    cell.setText(value.toString());
                }
            }
        }
    }

    public void setDirection(Direction dir) {

    }

    public FifteenMatrix parse() {
        FifteenMatrixBuilder builder = new FifteenMatrixBuilder();
        for (TextArea cell: CELLS) {
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
