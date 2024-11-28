package lk.ijse.dao.custom;

import lk.ijse.dao.custom.CrudDAO;
import lk.ijse.entity.Student;

import java.util.List;

public interface StudentDAO extends CrudDAO<Student> {

    public List<Student> getStudent();
}
