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
import lk.ijse.dto.ProgramDTO;
import lk.ijse.view.tm.ProgramTm;

import java.util.List;

public class ProgramFormController {

    @FXML
    private TableColumn<?, ?> colDuration;

    @FXML
    private TableColumn<?, ?> colFee;

    @FXML
    private TableColumn<?, ?> colProgramId;

    @FXML
    private TableColumn<?, ?> colProgramName;

    @FXML
    private Pane courseForm;

    @FXML
    private Text courseHeading;

    @FXML
    private AnchorPane programPane;

    @FXML
    private TableView<ProgramTm> programTbl;

    @FXML
    private TextField txtDuration;

    @FXML
    private TextField txtProgramFee;

    @FXML
    private TextField txtProgramID;

    @FXML
    private TextField txtProgramName;

    @FXML
    private Button deleteBtn;

    @FXML
    private Button saveBtn;

    @FXML
    private Button updateBtn;

    @FXML
    private Button clearBtn;

    ProgramBO programBO = (ProgramBO) BOFactory.getBoFactory().getBOTypes(BOFactory.BOTypes.PROGRAM);

    public void initialize(){
        getCurrentProgramId();
        setCellValueFactory();
        loadAllPrograms();
        addTableSelectionListener();
    }

    private void addTableSelectionListener() {
        programTbl.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                getProgramDetails(newValue);
            }
        });
    }
    private void getProgramDetails(ProgramTm programTm) {
        txtProgramID.setText(programTm.getProgram_id());
        txtProgramName.setText(programTm.getProgram_name());
        txtDuration.setText(programTm.getDuration());
        txtProgramFee.setText(String.valueOf(programTm.getFee()));

    }

    private void clearFields(){
        txtProgramID.setText("");
        txtProgramName.setText("");
        txtDuration.setText("");
        txtProgramFee.setText("");
        getCurrentProgramId();
    }
    private void setCellValueFactory(){
        colProgramId.setCellValueFactory(new PropertyValueFactory<>("program_id"));
        colProgramName.setCellValueFactory(new PropertyValueFactory<>("program_name"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        colFee.setCellValueFactory(new PropertyValueFactory<>("fee"));

    }

    private void loadAllPrograms(){
        ObservableList<ProgramTm> obList = FXCollections.observableArrayList();

        try {
            List<ProgramDTO> programList = programBO.getAllPrograms();
            for (ProgramDTO programDTO : programList){
                ProgramTm programTm = new ProgramTm(
                        programDTO.getProgram_id(),
                        programDTO.getProgram_name(),
                        programDTO.getDuration(),
                        programDTO.getFee()
                );
                obList.add(programTm);
            }
            programTbl.setItems(obList);
        }
        catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    private String generateNextProgramId(String currentId) {
        if (currentId != null && currentId.matches("P\\d+")) {
            int idNum = Integer.parseInt(currentId.substring(1));
            return "P" + String.format("%03d", ++idNum);
        }
        return "P001";
    }

    private void getCurrentProgramId(){

        String currentId = programBO.getCurrentPrId();
        String nextId = generateNextProgramId(currentId);
        txtProgramID.setText(nextId);


    }

    @FXML
    void clearBtnOnAction(ActionEvent event) {
         clearFields();
    }

    @FXML
    void deleteBtnOnAction(ActionEvent event) {

        String programId = txtProgramID.getText();

        try {
            boolean isDeleted = programBO.deleteProgram(programId);
            if (isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION,"Program deleted!").show();
                loadAllPrograms();
                clearFields();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void saveBtnOnAction(ActionEvent event) {

        String programId = txtProgramID.getText();
        String name = txtProgramName.getText();
        String duration = txtDuration.getText();
        double fee = Double.parseDouble(txtProgramFee.getText());

        try {
            boolean isSaved = programBO.saveProgram(new ProgramDTO(programId,name,duration,fee));

            if (isSaved){
                new Alert(Alert.AlertType.CONFIRMATION,"Program saved!").show();
                loadAllPrograms();
            }
            else {
                new Alert(Alert.AlertType.ERROR,"Program not saved!").show();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void updateBtnOnAction(ActionEvent event) {

        String programId = txtProgramID.getText();
        String name = txtProgramName.getText();
        String duration = txtDuration.getText();
        double fee = Double.parseDouble(txtProgramFee.getText());

        try {
            boolean isUpdated = programBO.updateProgram(new ProgramDTO(programId,name,duration,fee));

            if (isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION,"Program updated!").show();
                loadAllPrograms();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

}

