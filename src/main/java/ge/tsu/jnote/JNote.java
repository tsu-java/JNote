package ge.tsu.jnote;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class JNote extends Application {
    private static final Logger log = LoggerFactory.getLogger(JNote.class);

    @Override
    public void start(Stage stage) throws IOException {
        log.info("Application has started");
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent parent;
        try (var inputStream = JNote.class.getResourceAsStream("main.fxml")) {
            parent = fxmlLoader.load(inputStream);
        }
        Scene scene = new Scene(parent, 320, 240);
        addStyle(scene);
        MainController controller = fxmlLoader.getController();
        controller.initStage(stage);
        addIcon(stage);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() {
        log.info("Application has stopped");
    }

    /**
     * We add style.css here and also in main.fxml file for development purposes.
     *
     * @param scene
     */
    private void addStyle(Scene scene) {
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
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