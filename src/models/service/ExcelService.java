package models.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import models.dao.AttendanceCheckDAO;
import models.dao.AttendanceCheckDAOImpl;
import models.dto.AttendanceStatusDTO;

public class ExcelService {
	private String workingDir = System.getProperty("user.dir") + "/AttendanceManage/";
	private AttendanceCheckDAO checkDAO = new AttendanceCheckDAOImpl();

	public void createExcel(String name, String userId) {
		ArrayList<String[]> data = getUserData(userId);
		File file = new File(workingDir);
		file.mkdirs();
		try (XSSFWorkbook workbook = new XSSFWorkbook()) {
			XSSFSheet sheet = workbook.createSheet("출결 정보");
			XSSFRow head = sheet.createRow(0);
			String[] header = { "출석 시간", "퇴근 시간", "결과" };
			for (int i = 0; i < header.length; i++) {
				Cell cell = head.createCell(i);
				cell.setCellValue(header[i]);
			}

			for (int i = 1; i < data.size(); i++) {
				XSSFRow row = sheet.createRow(i);
				for (int j = 0; j < data.get(i).length; j++) {
					Cell cell = row.createCell(j);
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
