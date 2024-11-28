package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import lk.ijse.bo.custom.BOFactory;
import lk.ijse.bo.custom.StudentBO;
import lk.ijse.bo.custom.UserBO;
import lk.ijse.dto.StudentDTO;
import lk.ijse.dto.UserDTO;
import lk.ijse.entity.User;
import lk.ijse.util.Regex;
import lk.ijse.util.TextFields;
import lk.ijse.view.tm.StudentTm;

import java.io.IOException;
import java.util.List;

public class StudentFormController {

    private AnchorPane centerNode;

    public void setCenterNode(AnchorPane centerNode) {
        this.centerNode = centerNode;
    }

    @FXML
    private ComboBox<String> cmbUser;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colContact;


    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colStudentId;

    @FXML
    private TableColumn<?, ?> colUserID;

    @FXML
    private Button deleteBtn;

    @FXML
    private Button saveBtn;

    @FXML
    private Button updateBtn;

    @FXML
    private Button clearBtn;

    @FXML
    private Button searchBtn;

    @FXML
    private Pane studentForm;

    @FXML
    private Text studentHeading;

    @FXML
    private AnchorPane studentPage;

    @FXML
    private TableView<StudentTm> studentsTbl;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtSearchId;

    @FXML
    private TextField txtStudentId;

    @FXML
    private TextField txtStudentName;

    @FXML
    private Button viewBtn;

    StudentBO studentBO = (StudentBO) BOFactory.getBoFactory().getBOTypes(BOFactory.BOTypes.STUDENT);
    UserBO userBO = (UserBO) BOFactory.getBoFactory().getBOTypes(BOFactory.BOTypes.USER);

    public void initialize(){
       getUserIds();
       getCurrentStudentId();
       setCellValueFactory();
       loadAllStudents();
       addTableSelectionListener();
    }

    private void getUserIds(){

        List<UserDTO> userList = studentBO.getAllUsers();

        for (UserDTO userDTO : userList){
            cmbUser.getItems().add(userDTO.getUserId());
        }
    }

    private void getCurrentStudentId(){

        String currentId = studentBO.getCurrentStId();
        String nextId = generateNextStudentId(currentId);
        txtStudentId.setText(nextId);

    }

    private String generateNextStudentId(String currentId) {
        if (currentId != null && currentId.matches("S\\d+")) {
            int idNum = Integer.parseInt(currentId.substring(1));
            return "S" + String.format("%03d", ++idNum);
        }
        return "S001";
    }
    private void clearFields(){
        txtStudentId.setText("");
        cmbUser.setValue("");
        txtStudentName.setText("");
        txtAddress.setText("");
        txtContact.setText("");
        txtEmail.setText("");
        getCurrentStudentId();
    }
    private void setCellValueFactory(){
        colStudentId.setCellValueFactory(new PropertyValueFactory<>("student_id"));
        colUserID.setCellValueFactory(new PropertyValueFactory<>("user_id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
    }

    private void addTableSelectionListener() {
        studentsTbl.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                getStudentDetails(newValue);
            }
        });
    }
    private void getStudentDetails(StudentTm studentTm) {
        txtStudentId.setText(studentTm.getStudent_id());
        txtStudentName.setText(studentTm.getName());
        txtAddress.setText(studentTm.getAddress());
        txtEmail.setText(studentTm.getEmail());
        txtContact.setText(studentTm.getContact());
        cmbUser.setValue(studentTm.getUser_id()); // Assuming cmbUser holds user IDs
    }
    private void loadAllStudents(){
        ObservableList<StudentTm> obList = FXCollections.observableArrayList();

        try {
            List<StudentDTO> studentList = studentBO.getAllStudents();
            for(StudentDTO studentDTO : studentList){

                StudentTm studentTm = new StudentTm(
                        studentDTO.getStudent_id(),
                        studentDTO.getUser().getUserId(),
                        studentDTO.getName(),
                        studentDTO.getAddress(),
                        studentDTO.getEmail(),
                        studentDTO.getContact()
                );

                obList.add(studentTm);
            }
            System.out.println("done1");
            studentsTbl.setItems(obList);

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error loading students: " + e.getMessage()).show();
        }

    }
    @FXML
    void clearBtnOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    void cmbUserOnAction(ActionEvent event) {

    }

    @FXML
    void deleteBtnOnAction(ActionEvent event) {
        String studentId = txtStudentId.getText();

        try {
            boolean isDeleted = studentBO.deleteStudent(studentId);
            if (isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION,"Student deleted!").show();
                loadAllStudents();
                clearFields();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


    @FXML
    void saveBtnOnAction(ActionEvent event) {

        String studentID = txtStudentId.getText();
        String name = txtStudentName.getText();
        String address = txtAddress.getText();
        String email = txtEmail.getText();
        String contact = txtContact.getText();
        String userId = cmbUser.getValue();

        UserDTO userDTO = userBO.searchByID(userId);

        User user = new User();

        user.setUserId(userId);
        user.setUserType(userDTO.getUserType());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        user.setMobile(userDTO.getMobile());

        try {

            if (isValid() && (isEmailValid()) && (isNameValid())) {

                boolean isSaved = studentBO.saveStudent(new StudentDTO(studentID, user, name, address, email, contact));

                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Student saved!").show();
                    loadAllStudents();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Student not saved!").show();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void searchBtnOnAction(ActionEvent event) {

    }

    @FXML
    void updateBtnOnAction(ActionEvent event) {

        String studentID = txtStudentId.getText();
        String name = txtStudentName.getText();
        String address = txtAddress.getText();
        String email = txtEmail.getText();
        String contact = txtContact.getText();
        String userId = cmbUser.getValue();

        UserDTO userDTO = userBO.searchByID(userId);

        User user = new User();

        user.setUserId(userId);
        user.setUserType(userDTO.getUserType());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        user.setMobile(userDTO.getMobile());

        try {

            if (isValid() && (isEmailValid()) && (isNameValid())){

                boolean isUpdated = studentBO.updateStudent(new StudentDTO(studentID, user, name, address, email, contact));

                 if (isUpdated) {
                      new Alert(Alert.AlertType.CONFIRMATION, "Student updated!").show();
                      loadAllStudents();
                 }
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void txtContactOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(TextFields.Contact,txtContact);
    }

    @FXML
    void txtEmailOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(TextFields.EMAIL,txtEmail);
    }

    @FXML
    void txtNameOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(TextFields.Name,txtStudentName);
    }

    @FXML
    void txtStudentIdOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(TextFields.StudentID,txtStudentId);
    }

    @FXML
    void txtUserIdOnKeyReleased(KeyEvent event) {

    }
    public boolean isValid(){
        if (!Regex.setTextColor(TextFields.StudentID,txtStudentId)) return false;

        if (!Regex.setTextColor(TextFields.Contact,txtContact)) return false;
        return true;
    }

    public boolean isNameValid(){
        if(!Regex.setTextColor(TextFields.Name,txtStudentName));
        return true;
    }

    public boolean isEmailValid(){
        if(!Regex.setTextColor(TextFields.EMAIL,txtEmail));
        return true;
    }

    @FXML
    void txtStudentNameOnAction(ActionEvent event) {

        if(isNameValid()){
            new Alert(Alert.AlertType.INFORMATION,"Should have to consist two names!" +"\n"+
                    "ex:Amali Perera").show();
        }
    }

    @FXML
    void txtEmailOnAction(ActionEvent event) {

        if (isEmailValid()){
            new Alert(Alert.AlertType.INFORMATION,"Invalid email!Try again").show();
        }
    }

    @FXML
    void studentViewBtnOnAction(ActionEvent event) throws IOException {

        AnchorPane detailPane = FXMLLoader.load(getClass().getResource("/view/viewAllRegistration_form.fxml"));
        studentPage.getChildren().clear();
        studentPage.getChildren().add(detailPane);
    }
}





