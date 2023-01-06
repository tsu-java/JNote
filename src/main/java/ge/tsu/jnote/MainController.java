package ge.tsu.jnote;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.fxmisc.richtext.InlineCssTextArea;
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

    private final State.TextConverter textConverter = new State.TextConverter();

    private Stage stage;
    private Alert aboutAlert;

    private State state;
    private FileChooser fileChooser;

    public MenuItem menuItemSave;
    public MenuItem menuItemSaveAs;
    public InlineCssTextArea textArea;
    public Label totalLines;

    public void initStage(Stage stage) {
        log.info("Called initStage(..)");
        this.stage = stage;
        stage.titleProperty().bindBidirectional(
                state.pathProperty(),
                new State.TitleConverter()
        );
    }

    public void initAboutAlert(Parent aboutNode) {
        aboutAlert = new Alert(Alert.AlertType.NONE);
        aboutAlert.setTitle("About JNote");
        aboutAlert.getDialogPane().setContent(aboutNode);
        aboutAlert.getButtonTypes().addAll(ButtonType.CLOSE);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        log.info("Called initialize(..)");
        state = new State();

        // Listeners
        textArea.textProperty().addListener((observable, oldValue, newValue) -> {
            menuItemSave.setDisable(newValue.isEmpty());
            menuItemSaveAs.setDisable(newValue.isEmpty());

            // Update state's text property
            state.setText(textConverter.fromString(newValue));
        });

        // Display total
        textArea.getParagraphs().sizeProperty().addListener((observable, oldValue, newValue) -> {
            totalLines.setText(String.format("Lines: %d", textArea.getParagraphs().size()));
        });

        // File chooser
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("Text Files", "*.txt")
        );
    }

    public void onNewClick(ActionEvent e) {
        log.debug("Clicked 'File > New' menu button");
        textArea.replaceText("");
        state.setPath(null);
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
        aboutAlert.showAndWait();
    }

}