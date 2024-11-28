package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.UserBO;
import lk.ijse.dao.custom.DAOFactory;
import lk.ijse.dao.custom.UserDAO;
import lk.ijse.dto.UserDTO;
import lk.ijse.entity.User;

import java.util.ArrayList;
import java.util.List;

public class UserBOImpl implements UserBO {

    UserDAO userDAO = (UserDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.USER);
    @Override
    public boolean saveUser(UserDTO userDTO) {
        return userDAO.save(new User(userDTO.getUserId(),userDTO.getUserType(),userDTO.getUsername(),userDTO.getPassword(),userDTO.getEmail(),userDTO.getMobile()));
    }

    @Override
    public String getUserRole(String username) {
         return userDAO.getUser(username);
    }

    @Override
    public boolean checkCredentials(String username, String password) {
        return userDAO.check(username,password);
    }

    @Override
    public UserDTO searchByID(String userID) {
        User user = userDAO.search(userID);
        return new UserDTO(user.getUserId(),user.getUserType(),user.getUsername(),user.getPassword(),user.getEmail(),user.getMobile());
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
    public boolean deleteUser(String userId) {
        return userDAO.delete(userId);
    }

    @Override
    public boolean updateUser(UserDTO userDTO) {
        return userDAO.update(new User(userDTO.getUserId(),userDTO.getUsername(),userDTO.getPassword(),userDTO.getEmail(),userDTO.getMobile()));
    }

    @Override
    public UserDTO searchByEmail(String email) {
        User user = userDAO.searchEmail(email);
        return new UserDTO(user.getUserId(),user.getUserType(),user.getUsername(),user.getPassword(),user.getEmail(),user.getMobile());
    }

    @Override
    public String getHashedPassword(String username) {
        try {
            // Fetch the hashed password from the database using the DAO
            return userDAO.getPasswordByUsername(username);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getCurrentUserId() {
        return userDAO.getCurrentID();
    }
}
