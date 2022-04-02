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

/**
 * The controller of main scene root component.
 */
public class MainController {
    /**
     * Controller of the matrix grid display.
     */
    @FXML
    private FifteenMatrixController matrixController;
    /**
     * The root element of the main scene.
     */
    @FXML
    private GridPane mainContainer;
    /**
     * Label for the value of l(1).
     */
    @FXML
    private Label labelCell0;
    /**
     * Label for the value of l(2).
     */
    @FXML
    private Label labelCell1;
    /**
     * Label for the value of l(3).
     */
    @FXML
    private Label labelCell2;
    /**
     * Label for the value of l(4).
     */
    @FXML
    private Label labelCell3;
    /**
     * Label for the value of l(5).
     */
    @FXML
    private Label labelCell4;
    /**
     * Label for the value of l(6).
     */
    @FXML
    private Label labelCell5;
    /**
     * Label for the value of l(7).
     */
    @FXML
    private Label labelCell6;
    /**
     * Label for the value of l(8).
     */
    @FXML
    private Label labelCell7;
    /**
     * Label for the value of l(9).
     */
    @FXML
    private Label labelCell8;
    /**
     * Label for the value of l(10).
     */
    @FXML
    private Label labelCell9;
    /**
     * Label for the value of l(11).
     */
    @FXML
    private Label labelCell10;
    /**
     * Label for the value of l(12).
     */
    @FXML
    private Label labelCell11;
    /**
     * Label for the value of l(13).
     */
    @FXML
    private Label labelCell12;
    /**
     * Label for the value of l(14).
     */
    @FXML
    private Label labelCell13;
    /**
     * Label for the value of l(15).
     */
    @FXML
    private Label labelCell14;
    /**
     * Label for the value of l(16).
     */
    @FXML
    private Label labelCell15;
    /**
     * The button to navigate displayed state backwards.
     */
    @FXML
    private Button prevButton;
    /**
     * The button to navigate displayed state forwards.
     */
    @FXML
    private Button nextButton;
    /**
     * The button to choose a file input.
     */
    @FXML
    private Button chooseFileButton;
    /**
     * The button to start solving the given 15-puzzle.
     */
    @FXML
    private Button solveButton;
    /**
     * The button to randomize displayed 15-puzzle.
     */
    @FXML
    private Button randomButton;
    /**
     * Label to display current state depth.
     */
    @FXML
    private Label depthLabel;
    /**
     * Label to display total generated states.
     */
    @FXML
    private Label stateLabel;
    /**
     * Label to display the sum of all l values and x value.
     */
    @FXML
    private Label sumLowerLabel;
    /**
     * Label that display the text "l values sum".
     */
    @FXML
    private Label lowerLabel;
    /**
     * Label to display total execution time.
     */
    @FXML
    private Label execTimeLabel;
    /**
     * Array of all available l value labels.
     */
    private Label[] labelCells;
    /**
     * Current displayed solution path as an array
     * of {@code FifteenMatrix} objects.
     */
    private ArrayList<FifteenMatrix> solutionPath;
    /**
     * Current displayed solution path as an array
     * of {@code Direction} objects.
     */
    private ArrayList<Direction> solutionDir;
    /**
     * Current displayed state depth.
     */
    private int currentDepth;
    /**
     * Default font of the display with normal font size.
     */
    private final Font defaultFont = Font.font("Arial", FontWeight.BOLD, 16);
    /**
     * Default font of the display with small font size.
     */
    private final Font defaultFontSmall = Font.font("Arial", FontWeight.BOLD, 12);
    /**
     * Default font of the display with big font size.
     */
    private final Font defaultFontBig = Font.font("Arial", FontWeight.BOLD, 20);
    /**
     * Time units to display the execution time.
     */
    private final String[] postfix = {"ns", "us", "ms", "s"};

    /**
     * Initializes this controller.
     */
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
        this.depthLabel.setFont(this.defaultFontBig);
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

    /**
     * The solve button click event handler.
     */
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

    /**
     * The next button click event handler.
     */
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

    /**
     * The previous button click event handler.
     */
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

    /**
     * The random button click event handler.
     */
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

    /**
     * The choose file button click event handler.
     */
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

    /**
     * Display the depth of the state of the current displayed
     * state matrix.
     * @param depth     current displayed state matrix depth
     * @param maxDepth  the goal state matrix depth
     */
    private void setLabelText(int depth, int maxDepth) {
        this.depthLabel.setText(depth + "/" + maxDepth);
    }

    /**
     * Display total generated states.
     * @param states total generated states
     */
    private void setGeneratedStatesText(Integer states) {
        this.stateLabel.setText("Generated "
                + states.toString() + " states");
    }

    /**
     * Disables the previous button.
     */
    private void disablePrevButton() {
        prevButton.setDisable(true);
    }

    /**
     * Enables the previous button.
     */
    private void enablePrevButton() {
        prevButton.setDisable(false);
    }

    /**
     * Disables the next button.
     */
    private void disableNextButton() {
        nextButton.setDisable(true);
    }

    /**
     * Enables the previous button.
     */
    private void enableNextButton() {
        nextButton.setDisable(false);
    }

    /**
     * Displays all l values in the corresponding l value labels.
     */
    private void displayLowerValues(FifteenMatrix matrix) {
        for (int i = 1; i < 16; i++) {
            Integer l = matrix.lower(i);
            this.labelCells[i - 1].setText(l.toString());
        }
        Integer l = matrix.lowerNull();
        this.labelCells[15].setText(l.toString());
    }

    /**
     * Displays the execution time duration.
     * @param duration the execution time to display
     */
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

    /**
     * Shows an error alerts to the user.
     * @param message error message to show
     */
    private void alert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.show();
    }

    /**
     * Displays the sum of all l values and x value.
     * @param sum the sum to display
     */
    private void displayLowerValuesSum(Integer sum) {
        this.sumLowerLabel.setText(sum.toString());
    }

}