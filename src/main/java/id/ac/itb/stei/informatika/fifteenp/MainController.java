package id.ac.itb.stei.informatika.fifteenp;

import id.ac.itb.stei.informatika.fifteenp.util.Direction;
import id.ac.itb.stei.informatika.fifteenp.util.FifteenMatrix;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.ArrayList;

public class MainController {
    @FXML
    private Label welcomeText;

    @FXML
    private FifteenMatrixController matrixController;

    @FXML
    private Label labelCell0;
    @FXML
    private Label labelCell1;
    @FXML
    private Label labelCell2;
    @FXML
    private Label labelCell3;
    @FXML
    private Label labelCell4;
    @FXML
    private Label labelCell5;
    @FXML
    private Label labelCell6;
    @FXML
    private Label labelCell7;
    @FXML
    private Label labelCell8;
    @FXML
    private Label labelCell9;
    @FXML
    private Label labelCell10;
    @FXML
    private Label labelCell11;
    @FXML
    private Label labelCell12;
    @FXML
    private Label labelCell13;
    @FXML
    private Label labelCell14;
    @FXML
    private Label labelCell15;

    @FXML
    private Button prevButton;
    @FXML
    private Button nextButton;
    @FXML
    private Label dirLabel;

    @FXML
    private Button randomButton;
    @FXML
    private Button chooseFileButton;

    @FXML
    private Label execTimeLabel;

    private Label[] labelCells;
    private ArrayList<FifteenMatrix> solutionPath;
    private ArrayList<Direction> solutionDir;
    private int currentDepth;

    @FXML
    private void initialize() {
        this.labelCells = new Label[]{
                labelCell0, labelCell1, labelCell2, labelCell3,
                labelCell4, labelCell5, labelCell6, labelCell7,
                labelCell8, labelCell9, labelCell10, labelCell11,
                labelCell12, labelCell13, labelCell14, labelCell15,
        };
    }

    @FXML
    protected void onSolve() {
        try {
            this.currentDepth = 0;
            FifteenMatrix matrix = matrixController.parse();
            FifteenPuzzle solver = new FifteenPuzzle(matrix);
            solver.solve();
            this.solutionPath = solver.getSolutionPathMatrix();
            this.solutionDir = solver.getSolutionPathDir();
            this.matrixController.setMatrix(this.solutionPath.get(0));
            this.disablePrevButton();
            if (this.solutionDir.size() != 0) {
                this.matrixController.setDirection(this.solutionDir.get(0));
                this.enableNextButton();
            }
        } catch (IllegalArgumentException ignored) {

        }
    }

    @FXML
    protected void onNext() {
        this.enablePrevButton();
        this.currentDepth++;
        this.matrixController.setMatrix(this.solutionPath.get(
                        this.currentDepth));
        if (this.currentDepth == this.solutionDir.size()) {
            this.matrixController.setDirection(null);
        } else {
            this.matrixController.setDirection(this.solutionDir.get(
                    this.currentDepth));
        }

        if (this.currentDepth + 1 == this.solutionPath.size()) {
            this.disableNextButton();
        } else {
            this.enableNextButton();
        }
    }

    @FXML void onPrev() {
        this.enableNextButton();
        this.currentDepth--;
        this.matrixController.setMatrix(this.solutionPath.get(
                this.currentDepth));
        this.matrixController.setDirection(this.solutionDir.get(
                this.currentDepth));
        if (this.currentDepth == 0) {
            this.disablePrevButton();
        } else {
            this.enablePrevButton();
        }
    }

    private void disablePrevButton() {
        prevButton.setDisable(true);
    }

    private void enablePrevButton() {
        prevButton.setDisable(false);
    }

    private void disableNextButton() {
        nextButton.setDisable(true);
    }

    private void enableNextButton() {
        nextButton.setDisable(false);
    }
}