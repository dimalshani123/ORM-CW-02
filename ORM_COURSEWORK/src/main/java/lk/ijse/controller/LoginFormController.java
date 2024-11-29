package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.bo.custom.BOFactory;
import lk.ijse.bo.custom.UserBO;
import lk.ijse.util.PasswordUtils; // Import utility class for password verification
import lk.ijse.util.Regex;
import lk.ijse.util.TextFields;

import java.io.IOException;

public class LoginFormController {

    @FXML
    private AnchorPane loginForm;

    @FXML
    private Button loginBtn;

    @FXML
    private AnchorPane loginSide1;

    @FXML
    private AnchorPane loginSide2;

    @FXML
    private AnchorPane loginSide3;

    @FXML
    private AnchorPane loginSide4;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtPasswordVisible;

    @FXML
    private TextField txtUsername;

    @FXML
    private Hyperlink signUpHyperlink;

    UserBO userBO = (UserBO) BOFactory.getBoFactory().getBOTypes(BOFactory.BOTypes.USER);

    @FXML
    void loginBtnOnAction(ActionEvent event) throws IOException {

        String username = txtUsername.getText();
        String plainPassword = txtPassword.getText();

        // Validate empty fields
        if (username.isEmpty() || plainPassword.isEmpty()) {
            new Alert(Alert.AlertType.INFORMATION, "Please fill in all fields!").show();
            return;
        }

        // Retrieve the hashed password and user type from the database
        String hashedPassword = userBO.getHashedPassword(username);
        String userType = userBO.getUserRole(username);

        // Verify the password
        boolean isPasswordMatch = PasswordUtils.isPasswordMatch(plainPassword, hashedPassword);

        if (isPasswordMatch) {
            new Alert(Alert.AlertType.CONFIRMATION, "Login successful!").show();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dashboard_form.fxml"));
            Parent root = loader.load();

            DashboardFormController dashboardController = loader.getController();

            // Check if the user is not an admin
            if (!userType.equalsIgnoreCase("admin")) {
                dashboardController.disablePaymentButton();
            }

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();

            loginForm.getScene().getWindow().hide();

        } else {
            new Alert(Alert.AlertType.ERROR, "Invalid credentials!").show();
        }
    }

    @FXML
    void signUpHyperlinkOnAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/SignupForm.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
        loginForm.getScene().getWindow().hide();
    }

    @FXML
    void passwordVisible(MouseEvent event) {
        if (txtPasswordVisible.isVisible()) {
            // Hide the visible TextField and show the PasswordField
            txtPasswordVisible.setVisible(false);
            txtPasswordVisible.setManaged(false);
            txtPassword.setVisible(true);
            txtPassword.setManaged(true);

            // Copy text back to the PasswordField
            txtPassword.setText(txtPasswordVisible.getText());
        } else {
            // Show the visible TextField and hide the PasswordField
            txtPasswordVisible.setVisible(true);
            txtPasswordVisible.setManaged(true);
            txtPassword.setVisible(false);
            txtPassword.setManaged(false);

            // Copy text to the visible TextField
            txtPasswordVisible.setText(txtPassword.getText());
        }
    }

    @FXML
    void txtPasswordOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(TextFields.Password, txtPassword);
    }

    @FXML
    void txtUsernameOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(TextFields.UserName, txtUsername);
    }
}
