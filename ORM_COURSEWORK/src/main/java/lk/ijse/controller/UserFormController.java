package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import lk.ijse.bo.custom.BOFactory;
import lk.ijse.bo.custom.UserBO;
import lk.ijse.dto.UserDTO;
import lk.ijse.util.PasswordUtils;
import lk.ijse.util.Regex;
import lk.ijse.util.TextFields;
import lk.ijse.view.tm.UserTm;

import java.util.List;

public class UserFormController {

    @FXML
    private TableColumn<?, ?> colContact;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colPassword;

    @FXML
    private TableColumn<?, ?> colUserId;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUserName;

    @FXML
    private Pane userForm;

    @FXML
    private Text userHeading;

    @FXML
    private AnchorPane userPage;

    @FXML
    private TableView<UserTm> userTbl;

    @FXML
    private Button clearBtn;

    @FXML
    private Button updateBtn;

    @FXML
    private Button deleteBtn;

    @FXML
    private TextField txtUserId;

    UserBO userBO = (UserBO) BOFactory.getBoFactory().getBOTypes(BOFactory.BOTypes.USER);


    public void initialize(){
        setCellValueFactory();
        loadAllUsers();
        addTableSelectionListener();
    }

    private void clearFields(){
        txtUserId.setText("");
        txtUserName.setText("");
        txtPassword.setText("");
        txtEmail.setText("");
        txtPassword.setText("");
        txtContact.setText("");
    }

    private void addTableSelectionListener() {
        userTbl.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                getUserDetails(newValue);
            }
        });
    }
    private void getUserDetails(UserTm userTm) {
        txtUserId.setText(userTm.getUserId());
        txtUserName.setText(userTm.getUserName());
        txtPassword.setText(userTm.getPassword());
        txtEmail.setText(userTm.getEmail());
        txtContact.setText(userTm.getContact());

    }
    private void setCellValueFactory() {
        colUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
    }

    private void loadAllUsers(){
        ObservableList<UserTm> obList = FXCollections.observableArrayList();

        try {
            List<UserDTO> userList = userBO.getAllUsers();
            for (UserDTO userDTO : userList){
                UserTm userTm = new UserTm(
                        userDTO.getUserId(),
                        userDTO.getUsername(),
                        userDTO.getPassword(),
                        userDTO.getEmail(),
                        userDTO.getMobile()
                );
                obList.add(userTm);
            }
            userTbl.setItems(obList);
        }
        catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    @FXML
    void clearBtnOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    void deleteBtnOnAction(ActionEvent event) {

        String userId = txtUserId.getText();

        boolean isDeleted = userBO.deleteUser(userId);

            if (isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION,"User deleted!").show();
                loadAllUsers();
                clearFields();
            }

    }

    @FXML
    void updateBtnOnAction(ActionEvent event) {

        String userId = txtUserId.getText();
        String name = txtUserName.getText();
        String password = txtPassword.getText();
        String email = txtEmail.getText();
        String contact = txtContact.getText();


        try{

         if ((isContactValid()) && (isEmailValid()) && (isNameValid())) {

             String hashedPassword = PasswordUtils.hashPassword(password);

             boolean isUpdated = userBO.updateUser(new UserDTO(userId,name,hashedPassword,email,contact));

             if (isUpdated) {
                 new Alert(Alert.AlertType.CONFIRMATION, "User updated!").show();
                 loadAllUsers();
             }
         }

     } catch (Exception e) {
         new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
     }

    }

    @FXML
    void passwordVisible(MouseEvent event) {
        if (txtPassword.isVisible()) {

            txtPassword.setVisible(false);
            txtPassword.setManaged(false);
            txtPassword.setVisible(true);
            txtPassword.setManaged(true);


            txtPassword.setText(txtPassword.getText());
        } else {

            txtPassword.setVisible(true);
            txtPassword.setManaged(true);
            txtPassword.setVisible(false);
            txtPassword.setManaged(false);

            txtPassword.setText(txtPassword.getText());
        }
    }

    @FXML
    void txtContactOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(TextFields.Contact,txtContact);
    }

    @FXML
    void txtEmailOnAction(ActionEvent event) {
        if (isEmailValid()){
            new Alert(Alert.AlertType.INFORMATION,"Invalid email!Try again").show();
        }
    }

    @FXML
    void txtEmailOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(TextFields.EMAIL,txtEmail);
    }

    @FXML
    void txtNameOnAction(ActionEvent event) {
        if(isNameValid()){
            new Alert(Alert.AlertType.INFORMATION,"Should have to consist of numbers and letters with 6-16 characters!" +"\n"+
                    "ex:Password1").show();
        }
    }

    @FXML
    void txtPasswordOnAction(ActionEvent event) {

        if(isPasswordValid()){
            new Alert(Alert.AlertType.INFORMATION,"Invalid format!").show();
        }
    }

    @FXML
    void txtPasswordOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(TextFields.Password,txtPassword);
    }

    @FXML
    void txtUsernameOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(TextFields.UserName,txtUserName);
    }

    public boolean isContactValid(){
        if (!Regex.setTextColor(TextFields.Contact,txtContact)) return false;
        return true;
    }
    public boolean isNameValid(){
        if(!Regex.setTextColor(TextFields.UserName,txtUserName));
        return true;
    }
    public boolean isEmailValid(){
        if(!Regex.setTextColor(TextFields.EMAIL,txtEmail));
        return true;
    }
    public boolean isPasswordValid(){
        if (!Regex.setTextColor(TextFields.Password,txtPassword)) return false;
        return true;
    }
}
