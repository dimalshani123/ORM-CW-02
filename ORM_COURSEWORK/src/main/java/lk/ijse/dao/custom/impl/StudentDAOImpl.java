package lk.ijse.dao.custom.impl;

import lk.ijse.config.FactoryConfiguration;
import lk.ijse.dao.custom.StudentDAO;
import lk.ijse.entity.Student;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class StudentDAOImpl implements StudentDAO {

    @Override
    public boolean save(Student student) {

        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        Object isSaved = session.save(student);

        if(isSaved != null){
            transaction.commit();
            session.close();
            return true;
        }
        return false;
    }

    @Override
    public List<Student> getAll() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("from Student");
        List<Student> students = query.list();
        transaction.commit();
        session.close();
        return students;
    }

    @Override
    public String getCurrentID() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("select student_id from Student ORDER BY student_id DESC LIMIT 1");
        String currentId = (String) query.uniqueResult();

        transaction.commit();
        session.close();
        return currentId;
    }

    @Override
    public boolean update(Student student) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.update(student);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean delete(String id) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Student student = session.get(Student.class, id);

        if (student != null) {
            // Delete the Student entity
            session.delete(student);
            transaction.commit(); // Commit the transaction if successful
            return true;
        }
        session.close();
        return false;
    }

    @Override
    public Student search(String id) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("from Student where student_id =?1");
        query.setParameter(1, id);
        Student student = (Student) query.uniqueResult();
        transaction.commit();
        return student;
    }

    @Override
    public List<Student> getStudent() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("SELECT s " +
                "FROM Student s " +
                "JOIN s.registrations r " +
                "JOIN r.program p " +
                "GROUP BY s.student_id, s.name, s.user " +
                "HAVING COUNT(DISTINCT p.program_id) = (" +
                "  SELECT COUNT(DISTINCT p1.program_id) FROM Program p1" +
                ")");
        List<Student> students = query.list();
        transaction.commit();
        session.close();
        return students;
    }
}
