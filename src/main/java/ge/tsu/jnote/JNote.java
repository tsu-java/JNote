package ge.tsu.jnote;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class JNote extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent parent;
        try (var inputStream = JNote.class.getResourceAsStream("main.fxml")) {
            parent = fxmlLoader.load(inputStream);
        }
        Scene scene = new Scene(parent, 320, 240);
        MainController controller = fxmlLoader.getController();
        controller.initStage(stage);
        addIcon(stage);
        stage.setScene(scene);
        stage.show();
    }

    private void addIcon(Stage stage) throws IOException {
        try (var inputStream = JNote.class.getResourceAsStream("icon.png")) {
            stage.getIcons().add(new Image(inputStream));
        }
    }

    public static void main(String[] args) {
        launch();
    }
}