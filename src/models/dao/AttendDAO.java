package models.dao;

import java.sql.SQLException;
import java.util.List;

import models.dto.AttendanceStatusDTO;

public interface AttendDAO {
	
	// public void insertStartTime(AttendanceStatusDTO startTime);
	public void insertStartTime(String userId, String date, String startTime) throws SQLException;
	public List<AttendanceStatusDTO> getAttendBoards(String userId);
	public void updateEndTime(String userId, String endTime, String yearMonthDay);
	public void deleteAttend(String userId);

}
