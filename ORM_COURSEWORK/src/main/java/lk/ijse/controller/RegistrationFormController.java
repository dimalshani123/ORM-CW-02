package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import lk.ijse.bo.custom.BOFactory;
import lk.ijse.bo.custom.ProgramBO;
import lk.ijse.bo.custom.RegistrationBO;
import lk.ijse.bo.custom.StudentBO;
import lk.ijse.dto.ProgramDTO;
import lk.ijse.dto.RegistrationDTO;
import lk.ijse.dto.StudentDTO;
import lk.ijse.entity.Program;
import lk.ijse.entity.Student;
import lk.ijse.view.tm.RegistrationTm;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class RegistrationFormController {

    @FXML
    private ComboBox<String> cmbProgramID;

    @FXML
    private ComboBox<String> cmbStudentId;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colProgramFee;

    @FXML
    private TableColumn<?, ?> colProgramID;

    @FXML
    private TableColumn<?, ?> colProgramName;

    @FXML
    private TableColumn<?, ?> colRegisterID;

    @FXML
    private TableColumn<?, ?> colStudentID;

    @FXML
    private TableColumn<?, ?> colStudentName;

    @FXML
    private TableColumn<?, ?> colUpfrontPayment;

    @FXML
    private Text courseHeading;

    @FXML
    private Pane registerDetailPane;

    @FXML
    private AnchorPane registerPane;

    @FXML
    private TableView<RegistrationTm> registrationTbl;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtProgramFee;

    @FXML
    private TextField txtProgramName;

    @FXML
    private TextField txtRegisterID;

    @FXML
    private TextField txtStudentName;

    @FXML
    private TextField txtUpfrontPayment;

    @FXML
    private Button clearBtn;

    @FXML
    private Button saveBtn;

    @FXML
    private Button updateBtn;

    @FXML
    private Button deleteBtn;

    RegistrationBO registrationBO = (RegistrationBO) BOFactory.getBoFactory().getBOTypes(BOFactory.BOTypes.REGISTRATION);
    StudentBO studentBO = (StudentBO) BOFactory.getBoFactory().getBOTypes(BOFactory.BOTypes.STUDENT);
    ProgramBO programBO = (ProgramBO) BOFactory.getBoFactory().getBOTypes(BOFactory.BOTypes.PROGRAM);

    public void initialize(){
        getStudentIds();
        gerProgramIds();
        setDate();
        getCurrentRegisterId();
        setCellValueFactory();
        loadAllRegistrations();
        addTableSelectionListener();
    }

    private void setDate() {
        LocalDate now = LocalDate.now();
        txtDate.setText(String.valueOf(now));
    }

    private void getCurrentRegisterId(){

        String currentId = registrationBO.getCurrentReId();
        String nextId = generateNextRegisterId(currentId);
        txtRegisterID.setText(nextId);

    }

    private String generateNextRegisterId(String currentId) {
        if (currentId != null && currentId.matches("R\\d+")) {
            int idNum = Integer.parseInt(currentId.substring(1));
            return "R" + String.format("%03d", ++idNum);
        }
        return "R001";
    }

    private void addTableSelectionListener() {
        registrationTbl.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                getRegisterDetails(newValue);
            }
        });
    }
    private void getRegisterDetails(RegistrationTm registrationTm) {
        txtRegisterID.setText(registrationTm.getRegistrationID());
        txtDate.setText(registrationTm.getDate());
        cmbStudentId.setValue(registrationTm.getStudent_id());
        cmbProgramID.setValue(registrationTm.getProgram_id());
        txtStudentName.setText(registrationTm.getStudentName());
        txtProgramName.setText(registrationTm.getProgramName());
        txtProgramFee.setText(String.valueOf(registrationTm.getProgramFee()));
        txtUpfrontPayment.setText(String.valueOf(registrationTm.getUpfrontPayment()));
    }
    private void clearFields(){
        txtRegisterID.setText("");
        txtDate.setText("");
        cmbStudentId.setValue("");
        cmbProgramID.setValue("");
        txtStudentName.setText("");
        txtProgramName.setText("");
        txtProgramFee.setText("");
        txtUpfrontPayment.setText("");
        getCurrentRegisterId();
    }

    private void setCellValueFactory(){
        colRegisterID.setCellValueFactory(new PropertyValueFactory<>("registrationID"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colStudentID.setCellValueFactory(new PropertyValueFactory<>("student_id"));
        colProgramID.setCellValueFactory(new PropertyValueFactory<>("program_id"));
        colStudentName.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        colProgramName.setCellValueFactory(new PropertyValueFactory<>("programName"));
        colProgramFee.setCellValueFactory(new PropertyValueFactory<>("programFee"));
        colUpfrontPayment.setCellValueFactory(new PropertyValueFactory<>("upfrontPayment"));
        System.out.println("done");
    }

    private void loadAllRegistrations(){

        ObservableList<RegistrationTm> obList = FXCollections.observableArrayList();

//        try {
            List<RegistrationDTO> registerList = registrationBO.getAllRegistrations();

            for (RegistrationDTO registrationDTO : registerList) {

                RegistrationTm registrationTm = new RegistrationTm(
                        registrationDTO.getRegistrationID(),
                        registrationDTO.getDate(),
                        registrationDTO.getStudent().getStudent_id(),
                        registrationDTO.getProgram().getProgram_id(),
                        registrationDTO.getStudentName(),
                        registrationDTO.getProgramName(),
                        registrationDTO.getProgramFee(),
                        registrationDTO.getUpfrontPayment()
                );
                obList.add(registrationTm);
            }
            registrationTbl.setItems(obList);

//        } catch (Exception e) {
//            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
//        }

    }
    private void getStudentIds(){

        List<StudentDTO> studentsList = studentBO.getAllStudents();

        for (StudentDTO studentDTO : studentsList){
            cmbStudentId.getItems().add(studentDTO.getStudent_id());
        }
    }

    private void gerProgramIds(){

        List<ProgramDTO> programsList = programBO.getAllPrograms();

        for (ProgramDTO programDTO : programsList){
            cmbProgramID.getItems().add(programDTO.getProgram_id());
        }
    }
    @FXML
    void clearBtnOnAction(ActionEvent event) {

        clearFields();
    }

    @FXML
    void cmbProgramIDOnAction(ActionEvent event) {
         String programId = cmbProgramID.getValue();

         ProgramDTO programDTO = registrationBO.searchProgram(programId);

        if(programDTO != null){
            txtProgramName.setText(programDTO.getProgram_name());
            txtProgramFee.setText(String.valueOf(programDTO.getFee()));
        }
        txtUpfrontPayment.requestFocus();
    }

    @FXML
    void cmbStudentIDOnAction(ActionEvent event) {
        String studentId = cmbStudentId.getValue();

        StudentDTO studentDTO = registrationBO.searchStudent(studentId);

        if(studentDTO != null){
            txtStudentName.setText(studentDTO.getName());
        }
    }

    @FXML
    void deleteBtnOnAction(ActionEvent event) {

        String registerId = txtRegisterID.getText();

        try {
            boolean isDeleted = registrationBO.deleteRegistration(registerId);
            if (isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION,"Registration deleted!").show();
                loadAllRegistrations();
                clearFields();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void saveBtnOnAction(ActionEvent event) {

        String registerId = txtRegisterID.getText();
        String date = String.valueOf(Date.valueOf(LocalDate.now()));
        String studentId = cmbStudentId.getValue();
        String programId = cmbProgramID.getValue();
        String studentName = txtStudentName.getText();
        String programName = txtProgramName.getText();
        double programFee = Double.parseDouble(txtProgramFee.getText());
        double upfrontPayment = Double.parseDouble(txtUpfrontPayment.getText());

        StudentDTO studentDTO = studentBO.searchById(studentId);

        Student student = new Student();
        student.setStudent_id(studentId);
        student.setUser(studentDTO.getUser());
        student.setName(studentDTO.getName());
        student.setAddress(studentDTO.getAddress());
        student.setEmail(studentDTO.getEmail());
        student.setContact(studentDTO.getContact());

        ProgramDTO programDTO = programBO.searchById(programId);

        Program program = new Program();

        program.setProgram_id(programId);
        program.setProgram_name(programDTO.getProgram_name());
        program.setDuration(programDTO.getDuration());
        program.setFee(programDTO.getFee());

        boolean isSaved = registrationBO.saveRegistration(new RegistrationDTO(registerId,date,student,program,studentName,programName,programFee,upfrontPayment));

        if (isSaved){
            new Alert(Alert.AlertType.CONFIRMATION,"Registration completed!").show();
            loadAllRegistrations();
        }
        else {
            new Alert(Alert.AlertType.ERROR,"Registration not completed!").show();
        }
    }

    @FXML
    void updateBtnOnAction(ActionEvent event) {

        String registerId = txtRegisterID.getText();
        String date = String.valueOf(Date.valueOf(LocalDate.now()));
        String studentId = cmbStudentId.getValue();
        String programId = cmbProgramID.getValue();
        String studentName = txtStudentName.getText();
        String programName = txtProgramName.getText();
        double programFee = Double.parseDouble(txtProgramFee.getText());
        double upfrontPayment = Double.parseDouble(txtUpfrontPayment.getText());

        StudentDTO studentDTO = studentBO.searchById(studentId);

        Student student = new Student();
        student.setStudent_id(studentId);
        student.setUser(studentDTO.getUser());
        student.setName(studentDTO.getName());
        student.setAddress(studentDTO.getAddress());
        student.setEmail(studentDTO.getEmail());
        student.setContact(studentDTO.getContact());

        ProgramDTO programDTO = programBO.searchById(programId);

        Program program = new Program();

        program.setProgram_id(programId);
        program.setProgram_name(programDTO.getProgram_name());
        program.setDuration(programDTO.getDuration());
        program.setFee(programDTO.getFee());

        boolean isUpdated = registrationBO.updateRegistration(new RegistrationDTO(registerId,date,student,program,studentName,programName,programFee,upfrontPayment));

        if (isUpdated){
            new Alert(Alert.AlertType.CONFIRMATION,"Registration updated!").show();
            loadAllRegistrations();
        }
        else {
            new Alert(Alert.AlertType.ERROR,"Registration not updated!").show();
        }
    }

}
