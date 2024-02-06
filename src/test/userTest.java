package test;

import models.dao.UserDAO;
import models.dao.UserDAOImpl;
import models.dto.UserDTO;
import org.junit.jupiter.api.Test;

public class userTest {
	UserDAO userDAO = new UserDAOImpl();

	@Test
	void insertTest() {
		UserDTO userDTO = new UserDTO("01075743839", "1반", "김영웅", "1234321", 0);
		userDAO.insertUser(userDTO);
	}

//	@Test
//	void readTest() {
//		UserDTO user = userDAO.readID("01075743839");
//		System.out.println(user);
//	}
//
//	@Test
//	void updateTest() {
//		UserDTO userDTO = new UserDTO("01075743839", "1반", "류치호", "1234321", 1);
//		userDAO.updateUser(userDTO);
//	}
//
//	@Test
//	void deleteTest() {
//		userDAO.deleteUser("01075743839");
//	}
}
