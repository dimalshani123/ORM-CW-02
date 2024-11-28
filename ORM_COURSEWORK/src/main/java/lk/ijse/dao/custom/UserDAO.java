package lk.ijse.dao.custom;

import lk.ijse.dao.custom.CrudDAO;
import lk.ijse.entity.User;

public interface UserDAO extends CrudDAO<User> {

    public String getUser(String username);

    public boolean check(String username,String password);

    public User search(String userID);

    public boolean deleteByName(String userName);

    public User searchEmail(String email);
    public String getPasswordByUsername(String username) throws Exception;

}
