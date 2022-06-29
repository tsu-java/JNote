package ge.tsu.jnote;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ResourceBundle;

import static javafx.stage.FileChooser.ExtensionFilter;

public class MainController implements Initializable {
    private static final Logger log = LoggerFactory.getLogger(MainController.class);

    private Stage stage;

    private State state;
    private FileChooser fileChooser;

    public MenuItem menuItemSave;
    public MenuItem menuItemSaveAs;
    public TextArea textArea;

    public void initStage(Stage stage) {
        log.info("Called initStage(..)");
        this.stage = stage;
        stage.titleProperty().bindBidirectional(
                state.pathProperty(),
                new State.TitleConverter()
        );
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        log.info("Called initialize(..)");
        state = new State();

        // Property bindings
        menuItemSave.disableProperty().bind(textArea.textProperty().isEmpty());
        menuItemSaveAs.disableProperty().bind(textArea.textProperty().isEmpty());
        textArea.textProperty().bindBidirectional(
                state.textProperty(),
                new State.TextConverter()
        );

        // File chooser
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("Text Files", "*.txt")
        );
    }

    public void onNewClick(ActionEvent e) {
        log.debug("Clicked 'File > New' menu button");
        state.clear();
    }

    public void onOpenClick(ActionEvent e) throws IOException {
        log.debug("Clicked 'File > Open' menu button");
        fileChooser.setTitle("Open File");
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null && !selectedFile.isDirectory()) {
            state.setPath(selectedFile.toPath());
            state.setText(Files.readAllBytes(state.getPath()));
        }
    }

    public void onSaveClick(ActionEvent e) throws IOException {
        log.debug("Clicked 'File > Save' menu button");
        if (state.getPath() == null) {
            fileChooser.setTitle("Save File");
            File selectedFile = fileChooser.showSaveDialog(stage);
            if (selectedFile != null) {
                state.setPath(selectedFile.toPath());
            }
        }
        if (state.getPath() != null) {
            Files.write(state.getPath(), state.getText());
        }
    }

    public void onSaveAsClick(ActionEvent e) throws IOException {
        log.debug("Clicked 'File > Save As' menu button");
        fileChooser.setTitle("Save File");
        File selectedFile = fileChooser.showSaveDialog(stage);
        if (selectedFile != null) {
            state.setPath(selectedFile.toPath());
            Files.write(state.getPath(), state.getText());
        }
    }

    public void onExitClick(ActionEvent e) {
        log.debug("Clicked 'File > Exit' menu button");
        Platform.exit();
    }

    public void onAboutClick(ActionEvent e) {
        log.debug("Clicked 'Help > About' menu button");
    }

}