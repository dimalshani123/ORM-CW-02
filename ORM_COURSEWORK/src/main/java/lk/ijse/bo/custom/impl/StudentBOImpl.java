package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.StudentBO;
import lk.ijse.dao.custom.DAOFactory;
import lk.ijse.dao.custom.StudentDAO;
import lk.ijse.dao.custom.UserDAO;
import lk.ijse.dto.StudentDTO;
import lk.ijse.dto.UserDTO;
import lk.ijse.entity.Student;
import lk.ijse.entity.User;

import java.util.ArrayList;
import java.util.List;

public class StudentBOImpl implements StudentBO {

    StudentDAO studentDAO = (StudentDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.STUDENT);
    UserDAO userDAO = (UserDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.USER);

    @Override
    public boolean saveStudent(StudentDTO studentDTO) {
        return studentDAO.save(new Student(studentDTO.getStudent_id(),studentDTO.getUser(),studentDTO.getName(),studentDTO.getAddress(),studentDTO.getEmail(),studentDTO.getContact()));
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userDAO.getAll();
        List<UserDTO> userDTOList = new ArrayList<>();

        for (User user : users){
            UserDTO userDTO = new UserDTO(user.getUserId(),user.getUserType(),user.getUsername(),user.getPassword(),user.getEmail(),user.getMobile());
            userDTOList.add(userDTO);
        }
        return userDTOList;
    }

    @Override
    public String getCurrentStId() {
        return studentDAO.getCurrentID();
    }

    @Override
    public List<StudentDTO> getAllStudents() {
        List<Student> students = studentDAO.getAll();
        List<StudentDTO> stList = new ArrayList<>();

        for (Student student : students){
            StudentDTO studentDTO = new StudentDTO(student.getStudent_id(),student.getUser(),student.getName(),student.getAddress(),student.getEmail(),student.getContact());
            stList.add(studentDTO);
        }
        return stList;
    }

    @Override
    public boolean updateStudent(StudentDTO studentDTO) {
        return studentDAO.update(new Student(studentDTO.getStudent_id(),studentDTO.getUser(),studentDTO.getName(),studentDTO.getAddress(),studentDTO.getEmail(),studentDTO.getContact()));
    }

    @Override
    public boolean deleteStudent(String studentId) {
        return studentDAO.delete(studentId);
    }

    @Override
    public StudentDTO searchById(String studentId) {
        Student student = studentDAO.search(studentId);
        return new StudentDTO(student.getStudent_id(),student.getUser(),student.getName(),student.getAddress(),student.getEmail(),student.getContact());
    }

    @Override
    public List<StudentDTO> getRegisteredStudents() {
        List<Student> students = studentDAO.getStudent();
        List<StudentDTO> stList = new ArrayList<>();

        for (Student student : students){
            StudentDTO studentDTO = new StudentDTO(student.getStudent_id(),student.getUser(),student.getName());
            stList.add(studentDTO);
        }
        return stList;
    }

}
