package Main;
import UML.Project;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class OpeningWindowController {

    @FXML
    private Button startProjectButton;

    @FXML
    private Button openProjectButton;

    private Stage primaryStage;

    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    @FXML
    private void onStartProjectClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save New Project");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Files", "*.json"));
        File selectedFile = fileChooser.showSaveDialog(primaryStage);

        if (selectedFile != null) {
            Project newProject = new Project();
            newProject.setProjectFilePath(selectedFile.getAbsolutePath());
            newProject.saveProject();

            HelloApplication.navigateToMain(newProject); // Navigate to Main window
        }
    }

    @FXML
    private void onOpenProjectClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Existing Project");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Files", "*.json"));
        File selectedFile = fileChooser.showOpenDialog(primaryStage);

        if (selectedFile != null) {
            Project loadedProject = Project.loadProject(selectedFile.getAbsolutePath());
            HelloApplication.navigateToMain(loadedProject); // Navigate to Main window
        }
    }
}
