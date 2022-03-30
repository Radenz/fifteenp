package id.ac.itb.stei.informatika.fifteenp;

import id.ac.itb.stei.informatika.fifteenp.util.Direction;
import id.ac.itb.stei.informatika.fifteenp.util.FifteenMatrix;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.ArrayList;

public class MainController {
    @FXML
    private Label welcomeText;

    @FXML
    private FifteenMatrixController matrixController;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    ArrayList<FifteenMatrix> solutionPath;
    ArrayList<Direction> solutionDir;

    @FXML
    protected void onSolve() {
        try {
            FifteenMatrix matrix = matrixController.parse();
        } catch (IllegalArgumentException ignored) {

        }
    }
}