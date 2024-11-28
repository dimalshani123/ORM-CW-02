package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import lk.ijse.bo.custom.BOFactory;
import lk.ijse.bo.custom.StudentBO;
import lk.ijse.dto.StudentDTO;
import lk.ijse.view.tm.StudentTm;

import java.util.List;

public class ViewRegistrationsFormController {



    @FXML
    private TableColumn<?, ?> colCoordinatorId;

    @FXML
    private TableColumn<?, ?> colStudentId;

    @FXML
    private TableColumn<?, ?> colStudentName;

    @FXML
    private Text studentHeading;

    @FXML
    private Button viewBtn;

    @FXML
    private AnchorPane viewRegistrationForm;

    @FXML
    private TableView<StudentTm> viewTbl;

    StudentBO studentBO = (StudentBO) BOFactory.getBoFactory().getBOTypes(BOFactory.BOTypes.STUDENT);

    public void initialize(){
        setCellValueFactory();
    }

    private void setCellValueFactory() {
        colStudentId.setCellValueFactory(new PropertyValueFactory<>("student_id"));
        colCoordinatorId.setCellValueFactory(new PropertyValueFactory<>("user_id"));
        colStudentName.setCellValueFactory(new PropertyValueFactory<>("name"));
    }

    @FXML
    void viewBtnOnAction(ActionEvent event) {
         viewAllStudents();
    }

    private void viewAllStudents(){
        ObservableList<StudentTm> obList = FXCollections.observableArrayList();


            List<StudentDTO> studentList = studentBO.getRegisteredStudents();

            for(StudentDTO studentDTO : studentList){

                StudentTm studentTm = new StudentTm(
                        studentDTO.getStudent_id(),
                        studentDTO.getUser().getUserId(),
                        studentDTO.getName()
                );

                obList.add(studentTm);
            }
            System.out.println("done1");
            viewTbl.setItems(obList);

//        } catch (Exception e) {
//            new Alert(Alert.AlertType.ERROR, "Error loading students: " + e.getMessage()).show();
//        }

    }
}
