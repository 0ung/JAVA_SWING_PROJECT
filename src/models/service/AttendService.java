package models.service;

import java.sql.SQLException;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

import models.dao.AttendDAO;
import models.dao.AttendDAOImpl;
import models.dto.AttendanceStatusDTO;
import models.dto.UserDTO;

public class AttendService {
	private AttendDAO attendStatusDAO = new AttendDAOImpl();

	public List<String> setTime() {

		String total = LocalDateTime.now().toString();

		String[] day = total.split("T");
		String time = day[1].substring(0, 8);
		ArrayList<String> list = new ArrayList<>();
		list.add(day[0]);
		list.add(time);
		return list;
	}

	public boolean insertStartTime(String userId) {
		List<String> currentTime = setTime();
		String yearMonthDay = currentTime.get(0);
		String startTime = currentTime.get(1);
		try {
			attendStatusDAO.insertStartTime(userId, yearMonthDay, startTime);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}

	public void updateEndTime(String userId) {
		List<String> currentTime = setTime();
		String yearMonthDay = currentTime.get(0);
		String endTime = currentTime.get(1);
		try {
			attendStatusDAO.updateEndTime(userId, endTime, yearMonthDay);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<AttendanceStatusDTO> getAttendTime(AttendanceStatusDTO userId) {
		return attendStatusDAO.getAttendBoards(userId);
	}

}
