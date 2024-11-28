package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import lk.ijse.bo.custom.BOFactory;
import lk.ijse.bo.custom.PaymentBO;
import lk.ijse.bo.custom.RegistrationBO;
import lk.ijse.dto.PaymentDTO;
import lk.ijse.dto.RegistrationDTO;
import lk.ijse.entity.Registration;
import lk.ijse.view.tm.PaymentTm;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class PaymentFormController {

    @FXML
    private ComboBox<String> cmbRegisterID;

    @FXML
    private TableColumn<?, ?> colDueAmount;

    @FXML
    private TableColumn<?, ?> colPaymentDate;

    @FXML
    private TableColumn<?, ?> colPaymentId;

    @FXML
    private TableColumn<?, ?> colRegisterId;

    @FXML
    private TableColumn<?, ?> colTotalAmount;

    @FXML
    private TableColumn<?, ?> colUpfrontPayment;

    @FXML
    private Pane paymentDetailPane;

    @FXML
    private AnchorPane paymentPane;

    @FXML
    private TableView<PaymentTm> paymentTbl;

    @FXML
    private Text studentHeading;

    @FXML
    private TextField txtDueAmount;

    @FXML
    private TextField txtPaymentDate;

    @FXML
    private TextField txtPaymentID;

    @FXML
    private TextField txtTotalAmount;

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

    PaymentBO paymentBO = (PaymentBO) BOFactory.getBoFactory().getBOTypes(BOFactory.BOTypes.PAYMENT);
    RegistrationBO registrationBO = (RegistrationBO) BOFactory.getBoFactory().getBOTypes(BOFactory.BOTypes.REGISTRATION);

    public void initialize(){
        getRegisterIds();
        setDate();
        setCellValueFactory();
        loadAllPayments();
        getCurrentPaymentId();
        addTableSelectionListener();
    }

    private void initUI(){
        txtUpfrontPayment.setDisable(true);
    }
    private void setDate() {
        LocalDate now = LocalDate.now();
        txtPaymentDate.setText(String.valueOf(now));
    }

    private void getCurrentPaymentId(){

        String currentId = paymentBO.getCurrentPmId();
        String nextId = generateNextPaymentId(currentId);
        txtPaymentID.setText(nextId);

    }

    private String generateNextPaymentId(String currentId) {
        if (currentId != null && currentId.matches("PA\\d+")) {
            int idNum = Integer.parseInt(currentId.substring(2));
            return "PA" + String.format("%03d", ++idNum);
        }
        return "PA001";
    }

    private void addTableSelectionListener() {
       paymentTbl.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                getProgramDetails(newValue);
            }
        });
    }
    private void getProgramDetails(PaymentTm paymentTm) {
        txtPaymentID.setText(paymentTm.getPayment_id());
        cmbRegisterID.setValue(paymentTm.getRegistration_id());
        txtPaymentDate.setText(paymentTm.getPayment_date());
        txtUpfrontPayment.setText(String.valueOf(paymentTm.getUpfront_payment()));
        txtTotalAmount.setText(String.valueOf(paymentTm.getTotal_amount()));
        txtDueAmount.setText(String.valueOf(paymentTm.getDue_amount()));
    }
    private void clearFields(){
        txtPaymentID.setText("");
        cmbRegisterID.setValue("");
        txtPaymentDate.setText("");
        txtUpfrontPayment.setText("");
        txtTotalAmount.setText("");
        txtDueAmount.setText("");
        getCurrentPaymentId();
    }
    private void setCellValueFactory() {
        colPaymentId.setCellValueFactory(new PropertyValueFactory<>("payment_id"));
        colRegisterId.setCellValueFactory(new PropertyValueFactory<>("registration_id"));
        colPaymentDate.setCellValueFactory(new PropertyValueFactory<>("payment_date"));
        colUpfrontPayment.setCellValueFactory(new PropertyValueFactory<>("upfront_payment"));
        colTotalAmount.setCellValueFactory(new PropertyValueFactory<>("total_amount"));
        colDueAmount.setCellValueFactory(new PropertyValueFactory<>("due_amount"));
    }

    private void loadAllPayments(){

        ObservableList<PaymentTm> obList = FXCollections.observableArrayList();

        try {
            List<PaymentDTO> paymentList = paymentBO.getAllPayments();

            for(PaymentDTO paymentDTO : paymentList){

                PaymentTm paymentTm = new PaymentTm(
                        paymentDTO.getPayment_id(),
                        paymentDTO.getRegistration().getRegistrationID(),
                        paymentDTO.getPayment_date(),
                        paymentDTO.getUpfront_payment(),
                        paymentDTO.getTotal_amount(),
                        paymentDTO.getDue_amount()
                );
                obList.add(paymentTm);
            }
           paymentTbl.setItems(obList);

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }
    private void getRegisterIds(){

        List<RegistrationDTO> registerList = registrationBO.getAllRegistrations();

        for (RegistrationDTO registrationDTO : registerList){
            cmbRegisterID.getItems().add(registrationDTO.getRegistrationID());
        }
    }
    @FXML
    void clearBtnOnAction(ActionEvent event) {
          clearFields();
    }

    @FXML
    void cmbRegisterIDOnAction(ActionEvent event) {

        String registerId = cmbRegisterID.getValue();

        RegistrationDTO registrationDTO = registrationBO.searchRegistration(registerId);

        if(registrationDTO != null){
            txtUpfrontPayment.setText(String.valueOf(registrationDTO.getUpfrontPayment()));
            txtTotalAmount.setText(String.valueOf(registrationDTO.getProgramFee()));

            double upfrontPayment = Double.parseDouble(String.valueOf(registrationDTO.getUpfrontPayment()));
            double totalAmount = registrationDTO.getProgramFee();
            double dueAmount = totalAmount - upfrontPayment;


            txtDueAmount.setText(String.valueOf(dueAmount));
        }
    }

    @FXML
    void deleteBtnOnAction(ActionEvent event) {
         String paymentId = txtPaymentID.getText();

         boolean isDeleted = paymentBO.deletePayment(paymentId);

        if (isDeleted){
            new Alert(Alert.AlertType.CONFIRMATION,"Payment deleted!").show();
            loadAllPayments();
            clearFields();
        }
        else {
            new Alert(Alert.AlertType.ERROR,"Payment not deleted!").show();
        }
    }


    @FXML
    void saveBtnOnAction(ActionEvent event) {

        String paymentId = txtPaymentID.getText();
        String registerId = cmbRegisterID.getValue();
        String date = String.valueOf(Date.valueOf(LocalDate.now()));
        double upfrontPayment = Double.parseDouble(txtUpfrontPayment.getText());
        double totalAmount = Double.parseDouble(txtTotalAmount.getText());
        double dueAmount = Double.parseDouble(txtDueAmount.getText());

        RegistrationDTO registrationDTO = registrationBO.searchRegistration(registerId);

        Registration registration = new Registration();

        registration.setRegistrationID(registerId);
        registration.setDate(registrationDTO.getDate());
        registration.setStudent(registrationDTO.getStudent());
        registration.setProgram(registrationDTO.getProgram());
        registration.setStudentName(registrationDTO.getStudentName());
        registration.setProgramName(registrationDTO.getProgramName());
        registration.setProgramFee(registrationDTO.getProgramFee());
        registration.setUpfrontPayment(registrationDTO.getUpfrontPayment());

        boolean isSaved = paymentBO.savePayment(new PaymentDTO(paymentId,registration,date,upfrontPayment,totalAmount,dueAmount));

        if (isSaved){
            new Alert(Alert.AlertType.CONFIRMATION,"Payment saved!").show();
            loadAllPayments();
        }
        else {
            new Alert(Alert.AlertType.ERROR,"Payment not saved!").show();
        }
        initUI();
    }

    @FXML
    void updateBtnOnAction(ActionEvent event) {

        String paymentId = txtPaymentID.getText();
        String registerId = cmbRegisterID.getValue();
        String date = String.valueOf(Date.valueOf(LocalDate.now()));
        double upfrontPayment = Double.parseDouble(txtUpfrontPayment.getText());
        double totalAmount = Double.parseDouble(txtTotalAmount.getText());
        double dueAmount = Double.parseDouble(txtDueAmount.getText());

        RegistrationDTO registrationDTO = registrationBO.searchRegistration(registerId);

        Registration registration = new Registration();

        registration.setRegistrationID(registerId);
        registration.setDate(registrationDTO.getDate());
        registration.setStudent(registrationDTO.getStudent());
        registration.setProgram(registrationDTO.getProgram());
        registration.setStudentName(registrationDTO.getStudentName());
        registration.setProgramName(registrationDTO.getProgramName());
        registration.setProgramFee(registrationDTO.getProgramFee());
        registration.setUpfrontPayment(registrationDTO.getUpfrontPayment());

        boolean isUpdated = paymentBO.updatePayment(new PaymentDTO(paymentId,registration,date,upfrontPayment,totalAmount,dueAmount));

        if (isUpdated){
            new Alert(Alert.AlertType.CONFIRMATION,"Payment updated!").show();
            loadAllPayments();
        }
        else {
            new Alert(Alert.AlertType.ERROR,"Payment not updated!").show();
        }
    }

}

