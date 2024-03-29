package id.ac.itb.stei.informatika.fifteenp;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * {@code Application} is the main class of the 15-puzzle
 * solver application.
 */
public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1080, 540);
        stage.setTitle("15-Puzzle Solver");
        stage.setScene(scene);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("fifteenp.png")));
        stage.show();
    }

    /**
     * Entry point of the 15-puzzle solver application.
     * @param args cli arguments
     */
    public static void main(String[] args) {
        launch();
    }
}