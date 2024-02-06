package models.dao;

import java.util.ArrayList;

import models.dto.UserDTO;
import models.dto.UserInfoDTO;

public interface UserDAO {
	public void insertUser(UserDTO user);

	public UserDTO readID(String userId);
	
	public UserInfoDTO readInfoDTO(String userId);

	public void updateAuthUser(UserDTO user);

	public void deleteUser(String userId);

	public ArrayList<UserDTO> approvalUsers();
}
