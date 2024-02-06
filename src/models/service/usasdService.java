package models.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import exception.InvalidIdPasswordExecption;
import exception.MisMatchTypeExecption;
import models.dao.UserDAO;
import models.dao.UserDAOImpl;
import models.dto.UserDTO;

public class LoginService {
	private Pattern idPattern = Pattern.compile("^010\\d{8}$");
	private Pattern userNamePattern = Pattern.compile("^[가-힣]{2,5}$");
	private UserDAO userDAO = new UserDAOImpl();

	public void validationId(String id) throws MisMatchTypeExecption, NullPointerException {
		Matcher matcher = idPattern.matcher(id);
		if (!matcher.matches())
			throw new MisMatchTypeExecption();
		if (id == null)
			throw new NullPointerException();
	}

	public void validationIdPassword(String id, String password) throws InvalidIdPasswordExecption {
		UserDTO user = userDAO.readID(id);
		if (user.getUserId().equals(id) && user.getPassword().equals(password)) {
		} else {
			throw new InvalidIdPasswordExecption();
		}
	}

	public void validationUserName(String userName) throws MisMatchTypeExecption {
		Matcher matcher = userNamePattern.matcher(userName);
		if (!matcher.matches())
			throw new MisMatchTypeExecption();
		if (userName == null)
			throw new NullPointerException();
	}

	public int getAuth(String userId) {
		UserDTO user = userDAO.readID(userId);
		return user.getAuthority();
	}
}
