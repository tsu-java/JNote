package ge.tsu.jnote;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ResourceBundle;

import static javafx.stage.FileChooser.ExtensionFilter;

public class MainController implements Initializable {

    private Stage stage;

    private State state;
    private FileChooser fileChooser;

    public MenuItem menuItemSave;
    public MenuItem menuItemSaveAs;
    public TextArea textArea;

    public void initStage(Stage stage) {
        this.stage = stage;
        stage.titleProperty().bindBidirectional(
                state.pathProperty(),
                new State.TitleConverter()
        );
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
        state.clear();
    }

    public void onOpenClick(ActionEvent e) throws IOException {
        fileChooser.setTitle("Open File");
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null && !selectedFile.isDirectory()) {
            state.setPath(selectedFile.toPath());
            state.setText(Files.readAllBytes(state.getPath()));
        }
    }

    public void onSaveClick(ActionEvent e) throws IOException {
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
        fileChooser.setTitle("Save File");
        File selectedFile = fileChooser.showSaveDialog(stage);
        if (selectedFile != null) {
            state.setPath(selectedFile.toPath());
            Files.write(state.getPath(), state.getText());
        }
    }

    public void onExitClick(ActionEvent e) {
        Platform.exit();
    }

    public void onAboutClick(ActionEvent e) {
    }

}