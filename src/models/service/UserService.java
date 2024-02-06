package models.service;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import exception.InvalidIdPasswordExecption;
import exception.MisMatchTypeExecption;
import models.dao.UserDAO;
import models.dao.UserDAOImpl;
import models.dto.UserDTO;
import models.dto.UserInfoDTO;

public class UserService {
	private Pattern idPattern = Pattern.compile("^010\\d{8}$");
	private Pattern userNamePattern = Pattern.compile("^[가-힣]{2,5}$");
	private UserDAO userDAO = new UserDAOImpl();

	public void validationId(String id) throws MisMatchTypeExecption, NullPointerException {
		Matcher matcher = idPattern.matcher(id);
		if (!matcher.matches())
			throw new MisMatchTypeExecption("아이디를 잘못입력하셧습니다. ex) 01000000000 의 형태로 입력해주세요");
		if (id == null)
			throw new NullPointerException("아이디가 없습니다. 다시 확인해주세요");
	}

	public void validationUserName(String userName) throws MisMatchTypeExecption {
		Matcher matcher = userNamePattern.matcher(userName);
		if (!matcher.matches())
			throw new MisMatchTypeExecption("이름의 형식이 맞지않습니다.");
		if (userName == null)
			throw new NullPointerException("아이디가 비어있습니다.");
	}

	public void validationPassword(String password, String password2) throws InvalidIdPasswordExecption {
		if (!password.equals(password2))
			throw new InvalidIdPasswordExecption("비밀번호가 일치하지 않습니다.");
	}

	public void join(String id, String password, String userName, String className) {
		UserDTO user = new UserDTO();
		user.setUserId(id);
		user.setPassword(password);
		user.setUserName(userName);
		user.setClassName(className);
		userDAO.insertUser(user);
	}

	public void validationIdPassword(String id, String password) throws InvalidIdPasswordExecption {
		UserDTO user = userDAO.readID(id);
		if (user.getUserId().equals(id) && user.getPassword().equals(password)) {
		} else {
			throw new InvalidIdPasswordExecption();
		}
	}

	public UserDTO getUser(String userId) {
		return userDAO.readID(userId);
	}

	public int getAuth(String userId) {
		UserDTO user = userDAO.readID(userId);
		return user.getAuthority();
	}

	public ArrayList<UserDTO> getAuthMember() {
		return userDAO.approvalUsers();
	}

	public int checkAuth(String data) {
		if (data.equals("강사")) {
			return 2;
		} else if (data.equals("학생")) {
			return 1;
		} else
			return 3;
	}

	public void updateAuth(UserDTO user) {
		userDAO.updateAuthUser(user);
	}
	
	public UserInfoDTO getInfo(String userId) {
		return userDAO.readInfoDTO(userId);
	}
}
