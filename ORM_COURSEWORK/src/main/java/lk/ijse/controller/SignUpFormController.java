package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lk.ijse.bo.custom.BOFactory;
import lk.ijse.bo.custom.UserBO;
import lk.ijse.dto.UserDTO;
import lk.ijse.util.PasswordUtils;
import lk.ijse.util.Regex;
import lk.ijse.util.TextFields;

import java.io.IOException;

public class SignUpFormController {

    @FXML
    private ComboBox<String> cmbUser;

    @FXML
    private TextField txtUserId;

    @FXML
    private AnchorPane signForm;

    @FXML
    private Button signUpBtn;

    @FXML
    private Pane signUpDetails;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtMobile;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private PasswordField txtPassword2;

    @FXML
    private TextField txtPassword2Visible;
    @FXML
    private TextField txtPasswordVisible;

    @FXML
    private TextField txtUsername;

    @FXML
    private Hyperlink loginHyperlink;

    UserBO userBO = (UserBO) BOFactory.getBoFactory().getBOTypes(BOFactory.BOTypes.USER);

    public void initialize(){
        setUsers();
        getCurrentUserId();
    }

    @FXML
    void signUpBtnOnAction(ActionEvent event) {
        String userId = txtUserId.getText();
        String userType = cmbUser.getValue();
        String userName = txtUsername.getText();
        String password = txtPassword.getText();
        String password2 = txtPassword2.getText();
        String email = txtEmail.getText();
        String mobile = txtMobile.getText();

        // Debugging output
        System.out.println("userId: " + userId + ", userType: " + userType);

        if (userId.trim().isEmpty() || userType.trim().isEmpty() || userName.trim().isEmpty() || password.trim().isEmpty() || password2.trim().isEmpty() || email.trim().isEmpty() || mobile.trim().isEmpty()) {
            new Alert(Alert.AlertType.INFORMATION, "Empty fields!").show();
            return; // Exit if fields are empty
        } else if (!password.equals(password2)) {
            new Alert(Alert.AlertType.ERROR, "Passwords do not match!").show();
            return; // Exit if passwords do not match
        } else if (!isPasswordValid() || !isNameValid() || !isEmailValid() || !isContactValid()) {
            // You can debug each validation separately
            if (!isPasswordValid()) System.out.println("Password is invalid!");
            if (!isNameValid()) System.out.println("Username is invalid!");
            if (!isEmailValid()) System.out.println("Email is invalid!");
            if (!isContactValid()) System.out.println("Mobile is invalid!");

            new Alert(Alert.AlertType.ERROR, "Invalid input!").show();
            return; // Exit if any validation fails
        }

        String hashedPassword = PasswordUtils.hashPassword(password);
        UserDTO userDTO = new UserDTO(userId, userType, userName, hashedPassword, email, mobile);

        boolean isSaved = userBO.saveUser(userDTO);
        System.out.println("User saved: " + isSaved); // Debugging output

        if (isSaved) {
            new Alert(Alert.AlertType.CONFIRMATION, "User saved!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "User not saved!").show();
        }
    }

    public void setUsers(){
        cmbUser.getItems().addAll("Admin","Admission coordinator");
    }

    @FXML
    void hyperlinkLoginOnAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/login_form.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
        signForm.getScene().getWindow().hide();
    }

    @FXML
    void confirmPasswordVisible(MouseEvent event) {
        togglePasswordVisibility(txtPassword2, txtPassword2Visible);
    }

    @FXML
    void passwordVisible(MouseEvent event) {
        togglePasswordVisibility(txtPassword, txtPasswordVisible);
    }

    private void togglePasswordVisibility(PasswordField passwordField, TextField textField) {
        if (textField.isVisible()) {
            textField.setVisible(false);
            textField.setManaged(false);
            passwordField.setVisible(true);
            passwordField.setManaged(true);

            passwordField.setText(textField.getText());
        } else {
            textField.setVisible(true);
            textField.setManaged(true);
            passwordField.setVisible(false);
            passwordField.setManaged(false);

            textField.setText(passwordField.getText());
        }
    }

    @FXML
    void txtEmailOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(TextFields.EMAIL, txtEmail);
    }

    @FXML
    void txtMobileOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(TextFields.Contact, txtMobile);
    }

    @FXML
    void txtPassword2OnKeyReleased(KeyEvent event) {
        Regex.setTextColor(TextFields.Password, txtPassword2);
    }

    @FXML
    void txtPasswordOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(TextFields.Password, txtPassword);
    }

    @FXML
    void txtUsernameOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(TextFields.UserName, txtUsername);
    }

    @FXML
    void txtPasswordOnAction(ActionEvent event) {
        // Skip password validation as any string is allowed.
    }

    @FXML
    void txtPassword2OnAction(ActionEvent event) {
        if (!(txtPassword.getText().equals(txtPassword2.getText()))) {
            new Alert(Alert.AlertType.INFORMATION, "Passwords do not match!").show();
        }
    }

    @FXML
    void txtEmailOnAction(ActionEvent event) {
        if (!isEmailValid()) {
            new Alert(Alert.AlertType.INFORMATION, "Invalid email! Try again").show();
        }
    }

    @FXML
    void txtMobileOnAction(ActionEvent event) {
        if (!isContactValid()) {
            new Alert(Alert.AlertType.INFORMATION, "Invalid contact type!").show();
        }
    }

    @FXML
    void txtUsernameOnAction(ActionEvent event) {
        if (!isNameValid()) {
            new Alert(Alert.AlertType.INFORMATION, "Username should consist of numbers and letters with 6-16 characters! ex: Password1").show();
        }
    }

    public boolean isNameValid() {
        return Regex.setTextColor(TextFields.UserName, txtUsername);
    }

    public boolean isContactValid() {
        return Regex.setTextColor(TextFields.Contact, txtMobile);
    }

    public boolean isEmailValid() {
        return Regex.setTextColor(TextFields.EMAIL, txtEmail);
    }

    // Always returns true to allow any string as a password
    public boolean isPasswordValid() {
        return true;  // No password validation required
    }

    private void getCurrentUserId() {
        String currentId = userBO.getCurrentUserId();
        String nextId = generateNextUserId(currentId);
        txtUserId.setText(nextId);
    }

    private String generateNextUserId(String currentId) {
        if (currentId != null && currentId.matches("U\\d+")) {
            int idNum = Integer.parseInt(currentId.substring(1));
            return "U" + String.format("%03d", ++idNum);
        }
        return "U001";
    }
}
