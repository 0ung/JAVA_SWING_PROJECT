package models.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.core.tools.picocli.CommandLine.Help.TextTable.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import exception.InvalidIdPasswordExecption;
import exception.MisMatchTypeExecption;
import models.dao.AttendanceCheckDAO;
import models.dao.AttendanceCheckDAOImpl;
import models.dao.UserDAO;
import models.dao.UserDAOImpl;
import models.dto.AttendanceStatusDTO;
import models.dto.UserDTO;
import models.dto.UserInfoDTO;

public class UserService {
	private Pattern idPattern = Pattern.compile("^010\\d{8}$");
	private Pattern userNamePattern = Pattern.compile("^[가-힣]{2,5}$");
	private UserDAO userDAO = new UserDAOImpl();
	private String workingDir = System.getProperty("user.dir") + "/AttendanceManage/";
	private AttendanceCheckDAO checkDAO = new AttendanceCheckDAOImpl();

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

	public ArrayList<UserDTO> getSameClassMember(String userId) {
		return userDAO.classUsers(userId);
	}
	
	public void createExcel(String name, String userId) {
		ArrayList<String[]> data = getUserData(userId);
		File file = new File(workingDir);
		file.mkdirs();
		try (XSSFWorkbook workbook = new XSSFWorkbook()) {
			XSSFSheet sheet = workbook.createSheet("출결 정보");
			XSSFRow head = sheet.createRow(0);
			String[] header = { "출석 시간", "퇴근 시간", "결과" };
			for (int i = 0; i < header.length; i++) {
				XSSFCell cell = head.createCell(i);
				cell.setCellValue(header[i]);
			}

			for (int i = 1; i < data.size(); i++) {
				XSSFRow row = sheet.createRow(i);
				for (int j = 0; j < data.get(i).length; j++) {
					XSSFCell cell = row.createCell(j);
					cell.setCellValue(data.get(i)[j]);
				}
			}
			workbook.write(new FileOutputStream(workingDir + name + ".xlsx"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 출석부분 추가
	private ArrayList<String[]> getUserData(String userId) {
		ArrayList<String[]> list = new ArrayList<>();
		List<AttendanceStatusDTO> arr = checkDAO.readID(userId);
		for (AttendanceStatusDTO attendanceStatusDTO : arr) {
			String[] arr2 = new String[3];
			arr2[0] = attendanceStatusDTO.getStartTime();
			arr2[1] = attendanceStatusDTO.getEndTime();
			list.add(arr2);
		}
		return list;
	}
	
}
