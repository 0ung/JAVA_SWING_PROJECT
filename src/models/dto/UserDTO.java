package models.dto;

import lombok.Data;

@Data
public class UserDTO {
	private String userID;
	private String className;
	private String userName;
	private String password;
	private int authority;
}
