package test;

import models.dao.UserDAO;
import models.dao.UserDAOImpl;
import models.dto.UserDTO;
import models.service.UserService;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class userTest {
	UserDAO userDAO = new UserDAOImpl();
	UserService service = new UserService();
//
//	@Test
//	void insertTest() {
//		UserDTO userDTO = new UserDTO("01075763839", "1반", "김영웅", "1234321", 0);
//		userDAO.insertUser(userDTO);
//	}
//
//	@Test
//	void readTest() {
//		UserDTO user = userDAO.readID("01075763839");
//		System.out.println(user);
//	}
//
//	@Test
//	void updateTest() {
//		UserDTO userDTO = new UserDTO("01075763839", "1반", "류치호", "1234321", 1);
//		userDAO.updateUser(userDTO);
//	}
//
//	@Test
//	void deleteTest() {
//		userDAO.deleteUser("01075763839");
//	}
	
//	@Test
//	void readbyakk() {
//		 System.out.println(userDAO.approvalUsers());
//	}
//	
//	@Test
//	void asd() {
//		System.out.println(userDAO.readInfoDTO("01075743839"));
//	}
//	
	@Test
	void asdasd() {
		ArrayList<UserDTO> list = service.getSameClassMember("01075743839");
		System.out.println(list);
	}
}
