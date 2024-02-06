package models.dao;

import models.dto.UserDTO;

public interface UserDAO{
	public void insertUser(UserDTO user);
	public UserDTO readID(String userId);
	public void updateUser(UserDTO user);
	public void deleteUser(String userId);
}
