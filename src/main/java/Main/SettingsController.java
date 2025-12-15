package Main;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Alert;

public class SettingsController {

    @FXML
    private ChoiceBox<String> themeChoiceBox;

    @FXML
    private void initialize() {
        // Set default selection for the ChoiceBox
        themeChoiceBox.setValue("Light");
    }

    @FXML
    private void handleSaveSettings() {
        String selectedTheme = themeChoiceBox.getValue();
        // Logic for applying or saving the theme
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Settings Saved");
        alert.setHeaderText(null);
        alert.setContentText("Theme set to: " + selectedTheme);
        alert.showAndWait();
    }
}

