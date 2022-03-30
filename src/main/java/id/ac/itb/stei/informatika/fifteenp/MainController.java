package id.ac.itb.stei.informatika.fifteenp;

import id.ac.itb.stei.informatika.fifteenp.util.Direction;
import id.ac.itb.stei.informatika.fifteenp.util.FifteenMatrix;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;
import java.util.Random;

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
    private Button chooseFileButton;

    @FXML
    private Label execTimeLabel;

    private Label[] labelCells;
    private ArrayList<FifteenMatrix> solutionPath;
    private ArrayList<Direction> solutionDir;
    private int currentDepth;
    private final Font defaultFont = Font.font("Arial", FontWeight.BOLD, 16);
    private final String[] postfix = {"ns", "us", "ms", "s"};

    @FXML
    private void initialize() {
        this.labelCells = new Label[]{
                labelCell0, labelCell1, labelCell2, labelCell3,
                labelCell4, labelCell5, labelCell6, labelCell7,
                labelCell8, labelCell9, labelCell10, labelCell11,
                labelCell12, labelCell13, labelCell14, labelCell15,
        };
        this.dirLabel.setFont(this.defaultFont);
        this.nextButton.setFont(this.defaultFont);
        this.prevButton.setFont(this.defaultFont);
    }

    @FXML
    protected void onSolve() {
        try {
            this.currentDepth = 0;
            FifteenMatrix matrix = matrixController.parse();
            this.displayLowerValues(matrix);
            FifteenPuzzle solver = new FifteenPuzzle(matrix);
            long start = System.nanoTime();
            solver.solve();
            long end = System.nanoTime();
            this.displayExecutionTime(end - start);
            this.solutionPath = solver.getSolutionPathMatrix();
            this.solutionDir = solver.getSolutionPathDir();
            this.matrixController.setMatrix(this.solutionPath.get(0));
            this.disablePrevButton();
            if (this.solutionDir.size() != 0) {
                this.matrixController.setDirection(this.solutionDir.get(0));
                this.enableNextButton();
            }
            this.setLabelText(1, this.solutionPath.size());
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
        this.setLabelText(this.currentDepth + 1, this.solutionPath.size());
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
        this.setLabelText(this.currentDepth + 1, this.solutionPath.size());
        if (this.currentDepth == 0) {
            this.disablePrevButton();
        } else {
            this.enablePrevButton();
        }
    }

    @FXML
    protected void onRandom() {
        Random random = new Random();
        int steps = (random.nextInt() % 30 + 30) % 30 + 8;
        FifteenMatrix matrix = FifteenPuzzle.SOLUTION.copy();
        for (int i = 0; i < steps; i++) {
            while (true) {
                int dirIndex = (random.nextInt() % 4 + 4) % 4;
                try {
                    matrix = matrix.moveBlankTile(
                            Direction.DIRECTIONS[dirIndex]
                    );
                    break;
                } catch (IndexOutOfBoundsException ignored) {
                }
            }
        }
        this.matrixController.setMatrix(matrix);
    }

    private void setLabelText(int depth, int maxDepth) {
        this.dirLabel.setText(depth + "/" + maxDepth);
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

    private void displayLowerValues(FifteenMatrix matrix) {
        for (int i = 1; i < 16; i++) {
            Integer l = matrix.lower(i);
            this.labelCells[i - 1].setText(l.toString());
        }
        Integer l = matrix.lowerNull();
        this.labelCells[15].setText(l.toString());
    }

    private void displayExecutionTime(double duration) {
        int i = 0;
        double factor = 1;
        while (i < 3 && duration * factor > 100) {
            factor *= 1e-3;
            i++;
        }
        String durationString = String.format("%.3f", duration * factor);
        String execTime = "Solved in " + durationString
                + " " + this.postfix[i];
        this.execTimeLabel.setText(execTime);
    }
}