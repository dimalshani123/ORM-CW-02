package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;

import static javafx.fxml.FXMLLoader.load;

public class DashboardFormController {

    @FXML
    private AnchorPane centerNode;

    @FXML
    private Text count1;

    @FXML
    private Text count2;

    @FXML
    private Text count3;

    @FXML
    private Button courseBtn;

    @FXML
    private Button dashboardBtn;

    @FXML
    private AnchorPane graphSection;

    @FXML
    private Button paymentBtn;

    @FXML
    private Button studentBtn;

    @FXML
    private Button userBtn;

    @FXML
    private Button registrationBtn;

    // Method to load different forms into the centerNode area
    private void loadForm(String fxmlFile) throws IOException {
        // Check the file path to ensure it's correct
        System.out.println("Loading FXML: " + fxmlFile);

        AnchorPane pane = load(getClass().getResource(fxmlFile));
        if (pane == null) {
            System.out.println("Failed to load " + fxmlFile);
            return;
        }
        centerNode.getChildren().clear();
        centerNode.getChildren().add(pane);
    }


    @FXML
    void btnCourseOnAction(ActionEvent event)  {
        try {
            loadForm("/ProgramForm .fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnPaymentOnAction(ActionEvent event) {
        try {
            loadForm("/PaymentForm.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnDashboardOnAction(ActionEvent event) {
        // Implement dashboard view logic if needed
    }

    @FXML
    void btnStudentOnAction(ActionEvent event) {
        try {
            loadForm("/StudentForm.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnUserOnAction(ActionEvent event) {
        try {
            loadForm("/UserForm.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void registrationBtnOnAction(ActionEvent event) {
        try {
            loadForm("/RegistrationForm.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to disable buttons based on the user role
    public void disableButtons(String userType) {
        if ("Admission coordinator".equalsIgnoreCase(userType)) {
//            courseBtn.setDisable(true);
//            paymentBtn.setDisable(true);
        }
    }

    // Initialize method to setup user info and buttons
    @FXML
    public void initialize() {
        // Example initialization logic (you can load username and role here)
        String userType = "Admission coordinator"; // Replace with actual logic
        disableButtons(userType);
    }
}
