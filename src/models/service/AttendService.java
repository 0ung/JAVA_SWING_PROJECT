package models.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import models.dao.AttendDAO;
import models.dao.AttendDAOImpl;
import models.dto.UserDTO;
import views.MonthlyAttendanceLog;


public class AttendService{
	private UserDTO dto;
	private AttendDAO attendStatusDAO = new AttendDAOImpl();
	private MonthlyAttendanceLog monthlyAttendanceLog;
	
	
	public AttendService(UserDTO dto, MonthlyAttendanceLog monthlyAttendanceLog) {
		this.dto = dto;
		this.monthlyAttendanceLog = monthlyAttendanceLog;
	}
	public List<String> setTime() {;
		String total = LocalDateTime.now().toString();
		String[] day = total.split("T");
		String time = day[1].substring(0, 8);
		ArrayList<String> list = new ArrayList<>();
		list.add(day[0]);
		list.add(time);
		return list;
	}
	
	public void insertStartTime(String userId) {
		List<String> currentTime = setTime();
		String yearMonthDay = currentTime.get(0);
		String startTime = currentTime.get(1);
		
		try {
			attendStatusDAO.insertStartTime(userId, yearMonthDay, startTime);
			MonthlyAttendanceLog month = new MonthlyAttendanceLog(dto);
			month.addRowToTable();
		}catch(Exception e) {
			e.printStackTrace();
		}

	} 
}

