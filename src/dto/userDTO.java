package dto;

import lombok.Data;

@Data
public class userDTO {
	private String userID;
	private String className;
	private String userName;
	private String password;
	private int authority;
}
