package lk.ijse.dao.custom.impl;

import lk.ijse.config.FactoryConfiguration;
import lk.ijse.dao.custom.UserDAO;
import lk.ijse.entity.Student;
import lk.ijse.entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class UserDAOImpl implements UserDAO {

    @Override
    public boolean save(User user) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        Object isSaved = session.save(user);

        if(isSaved != null){
            transaction.commit();
            session.close();
            return true;
        }
        return false;
    }

    @Override
    public List<User> getAll() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("from User");
        List<User> userList = query.list();

        transaction.commit();
        session.close();
        return userList;
    }

    @Override
    public String getCurrentID() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("select userId from User ORDER BY userId DESC LIMIT 1");
        String currentId = (String) query.uniqueResult();

        transaction.commit();
        session.close();
        return currentId;
    }

    @Override
    public boolean update(User user) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.update(user);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean delete(String id) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        User user = session.get(User.class, id);

        if (user != null) {
            // Delete the Student entity
            session.delete(user);
            transaction.commit(); // Commit the transaction if successful
            return true;
        }
        session.close();
        return false;
    }

    @Override
    public String getUser(String username) {
         Session session = FactoryConfiguration.getInstance().getSession();
         Transaction transaction = session.beginTransaction();

         Query query = session.createQuery("select userType from User where username = :username");
         query.setParameter("username",username);

         String role = (String) query.uniqueResult();

         transaction.commit();
         session.close();
         return role;
    }

    @Override
    public boolean check(String username, String password) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("select password from User where username = :username");
        query.setParameter("username",username);

        String pw = (String) query.uniqueResult();

        transaction.commit();
        session.close();

        if (password.equalsIgnoreCase(pw)){
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public User search(String userID) {

        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("from User where userId =?1");
        query.setParameter(1, userID);
        User user = (User) query.uniqueResult();
        transaction.commit();
        return user;
    }

    @Override
    public boolean deleteByName(String userName) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

//        Query query = session.createQuery("delete from User where username = ?1");
//        query.setParameter(1, userName);
//        boolean isDeleted = query.executeUpdate() > 0;
//
//        if (isDeleted) {
//            transaction.commit();
//            session.close();
//            return true;
//        }
//        return false;

        User user = session.get(User.class,userName);

        if (user != null) {
            // Delete the Student entity
            session.delete(user);
            transaction.commit(); // Commit the transaction if successful
            return true;
        }
        session.close();
        return false;
    }

    @Override
    public User searchEmail(String email) {

        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("from User where email =?1");
        query.setParameter(1, email);
        User user = (User) query.uniqueResult();
        transaction.commit();
        return user;
    }

    @Override
    public String getPasswordByUsername(String username) throws Exception {
             Session session = FactoryConfiguration.getInstance().getSession();
             Transaction transaction = session.beginTransaction();
            String hql = "SELECT u.password FROM User u WHERE u.username = :username";
            Query<String> query = session.createQuery(hql, String.class);
            query.setParameter("username", username);
            transaction.commit();
            return query.uniqueResult(); // Returns the hashed password or null if the username doesn't exist

    }
}
