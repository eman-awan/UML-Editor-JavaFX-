package Util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class MessageDialog {

    public MessageDialog() {
    }

    public static void showPositiveMessage(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText("Success");
        alert.setContentText(message);
        alert.getDialogPane().setStyle("-fx-font-size: 14px; -fx-font-family: Arial;");
        alert.showAndWait();
    }

    public static void showNegativeMessage(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText("Error");
        alert.setContentText(message);
        alert.getDialogPane().setStyle("-fx-font-size: 14px; -fx-font-family: Arial;");
        alert.showAndWait();
    }
}
