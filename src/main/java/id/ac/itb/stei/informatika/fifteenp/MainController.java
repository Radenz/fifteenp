package id.ac.itb.stei.informatika.fifteenp;

import id.ac.itb.stei.informatika.fifteenp.io.FifteenMatrixParser;
import id.ac.itb.stei.informatika.fifteenp.io.FileReader;
import id.ac.itb.stei.informatika.fifteenp.util.Direction;
import id.ac.itb.stei.informatika.fifteenp.util.FifteenMatrix;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class MainController {

    @FXML
    private FifteenMatrixController matrixController;
    @FXML
    private GridPane mainContainer;
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
    private Button chooseFileButton;
    @FXML
    private Button solveButton;
    @FXML
    private Button randomButton;
    @FXML
    private Label dirLabel;
    @FXML
    private Label stateLabel;
    @FXML
    private Label sumLowerLabel;
    @FXML
    private Label lowerLabel;
    @FXML
    private Label execTimeLabel;

    private Label[] labelCells;
    private ArrayList<FifteenMatrix> solutionPath;
    private ArrayList<Direction> solutionDir;
    private int currentDepth;
    private final Font defaultFont = Font.font("Arial", FontWeight.BOLD, 16);
    private final Font defaultFontSmall = Font.font("Arial", FontWeight.BOLD, 12);
    private final Font defaultFontBig = Font.font("Arial", FontWeight.BOLD, 20);
    private final String[] postfix = {"ns", "us", "ms", "s"};

    @FXML
    private void initialize() {
        this.mainContainer.setBackground(new Background(
                new BackgroundFill(Color.valueOf("#37474f"), null, null)
        ));
        Border border = new Border(
                new BorderStroke(
                        Color.WHITESMOKE,
                        BorderStrokeStyle.SOLID,
                        CornerRadii.EMPTY,
                        BorderStroke.DEFAULT_WIDTHS
                )
        );
        this.labelCells = new Label[]{
                labelCell0, labelCell1, labelCell2, labelCell3,
                labelCell4, labelCell5, labelCell6, labelCell7,
                labelCell8, labelCell9, labelCell10, labelCell11,
                labelCell12, labelCell13, labelCell14, labelCell15,
        };
        this.dirLabel.setFont(this.defaultFontBig);
        this.nextButton.setFont(this.defaultFont);
        this.prevButton.setFont(this.defaultFont);
        for (Label cell: this.labelCells) {
            cell.setFont(this.defaultFont);
            cell.getStyleClass().add("white");
            cell.setBorder(border);
            cell.getParent().getParent().prefHeight(Double.POSITIVE_INFINITY);
            cell.getParent().getParent().prefWidth(Double.POSITIVE_INFINITY);
            HBox parent = (HBox) cell.getParent();
            VBox grandParent = (VBox) parent.getParent();
            parent.prefHeightProperty().bind(grandParent.heightProperty());
            parent.prefWidthProperty().bind(grandParent.widthProperty());
            cell.prefHeightProperty().bind(parent.heightProperty());
            cell.prefWidthProperty().bind(parent.widthProperty());
            cell.setTextAlignment(TextAlignment.CENTER);
            cell.setAlignment(Pos.CENTER);
        }
        this.execTimeLabel.setFont(this.defaultFontSmall);
        this.stateLabel.setFont(this.defaultFontSmall);
        this.sumLowerLabel.setFont(this.defaultFontBig);
        this.solveButton.setFont(this.defaultFont);
        this.randomButton.setFont(this.defaultFont);
        this.chooseFileButton.setFont(this.defaultFont);
        this.lowerLabel.setFont(this.defaultFontSmall);
        this.randomButton.setStyle("-fx-background-color: transparent;" +
                "-fx-text-fill: white; -fx-cursor: hand;");
        this.randomButton.setBorder(border);
        this.chooseFileButton.setStyle("-fx-background-color: transparent;" +
                "-fx-text-fill: white; -fx-cursor: hand;");
        this.chooseFileButton.setBorder(border);
        this.solveButton.setStyle("-fx-background-color: transparent;" +
                "-fx-text-fill: white; -fx-cursor: hand;");
        this.solveButton.setBorder(border);
        this.nextButton.setStyle("-fx-background-color: transparent;" +
                "-fx-text-fill: white; -fx-cursor: hand;");
        this.nextButton.setBorder(border);
        this.prevButton.setStyle("-fx-background-color: transparent;" +
                "-fx-text-fill: white; -fx-cursor: hand;");
        this.prevButton.setBorder(border);
    }

    @FXML
    protected void onSolve() {
        FifteenMatrix matrix;
        try {
            matrix = matrixController.parse();
        } catch (IllegalArgumentException ignored) {
            this.alert("The given matrix is invalid.");
            return;
        }

        try {
            this.currentDepth = 0;
            this.displayLowerValues(matrix);
            this.displayLowerValuesSum(matrix.lowerSum());
            FifteenPuzzle solver = new FifteenPuzzle(matrix);
            long start = System.nanoTime();
            solver.solve();
            long end = System.nanoTime();
            this.displayExecutionTime(end - start);
            this.solutionPath = solver.getSolutionPathMatrix();
            this.solutionDir = solver.getSolutionPathDir();
            this.matrixController.setMatrix(this.solutionPath.get(0));
            this.disablePrevButton();
            this.setGeneratedStatesText(solver.generatedStates());
            if (this.solutionDir.size() != 0) {
                this.matrixController.setDirection(this.solutionDir.get(0));
                this.enableNextButton();
            }
            this.setLabelText(1, this.solutionPath.size());
        } catch (IllegalArgumentException ignored) {
            this.alert("This puzzle is unsolvable.");
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

    @FXML
    protected void onPrev() {
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
        int steps = (random.nextInt() % 28 + 28) % 28 + 12;
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

    @FXML
    private void onChooseFile() {
        FileChooser dialog = new FileChooser();
        File input = dialog.showOpenDialog(null);
        if (input != null) {
            String filename = input.toString();

            FileReader reader = new FileReader();
            try {
                reader.readFile(filename);
            } catch (Throwable ignored) {
                this.alert("There was an error reading the file.");
            }

            try {
                String fileContent = reader.result();
                FifteenMatrixParser parser = new FifteenMatrixParser();
                parser.parse(fileContent);
                this.matrixController.setMatrix(parser.result());
            } catch (Throwable ignored) {
                this.alert("The file has an invalid format.");
            }
        }
    }

    private void setLabelText(int depth, int maxDepth) {
        this.dirLabel.setText(depth + "/" + maxDepth);
    }

    private void setGeneratedStatesText(Integer states) {
        this.stateLabel.setText("Generated "
                + states.toString() + " states");
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

    private void alert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.show();
    }

    private void displayLowerValuesSum(Integer sum) {
        this.sumLowerLabel.setText(sum.toString());
    }

}