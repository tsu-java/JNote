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

        Parent aboutNode = loadAboutNode();

        Parent mainNode = loadMainNode(stage, aboutNode);
        Scene mainScene = new Scene(mainNode, 320, 240);
        addStyle(mainScene);

        addIcon(stage);
        stage.setScene(mainScene);
        stage.show();
    }

    private Parent loadAboutNode() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        try (var inputStream = JNote.class.getResourceAsStream("about.fxml")) {
            return fxmlLoader.load(inputStream);
        }
    }

    private Parent loadMainNode(Stage stage, Parent aboutNode) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent parent;
        try (var inputStream = JNote.class.getResourceAsStream("main.fxml")) {
            parent = fxmlLoader.load(inputStream);
        }
        MainController controller = fxmlLoader.getController();
        controller.initStage(stage);
        controller.initAboutAlert(aboutNode);
        return parent;
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