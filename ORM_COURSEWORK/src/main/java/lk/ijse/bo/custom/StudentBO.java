package lk.ijse.bo.custom;

import lk.ijse.dto.StudentDTO;
import lk.ijse.dto.UserDTO;
import lk.ijse.entity.Student;

import java.util.List;

public interface StudentBO extends SuperBO {

    public boolean saveStudent(StudentDTO studentDTO);

    public List<UserDTO> getAllUsers();

    public String getCurrentStId();

    public List<StudentDTO> getAllStudents();

    public boolean updateStudent(StudentDTO studentDTO);

    public boolean deleteStudent(String studentId);
    public StudentDTO searchById(String studentId);
    public List<StudentDTO> getRegisteredStudents();
}
