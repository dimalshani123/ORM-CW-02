package lk.ijse.bo.custom;

import lk.ijse.bo.custom.SuperBO;
import lk.ijse.dto.UserDTO;

import java.util.List;

public interface UserBO extends SuperBO {

    public boolean saveUser(UserDTO userDTO);

    public String getUserRole(String username);

    public boolean checkCredentials(String username,String password);

    public UserDTO searchByID(String userID);

    public List<UserDTO> getAllUsers();
    public boolean deleteUser(String userId);

    public boolean updateUser(UserDTO userDTO);

    public UserDTO searchByEmail(String email);

    public String getHashedPassword(String username);
    public String getCurrentUserId();
}
