package models.dao;

import java.sql.SQLException;
import java.util.List;

import models.dto.AttendanceStatusDTO;

public interface AttendDAO {
	
	public void insertStartTime(String userId, String date, String startTime) throws SQLException;
	public List<AttendanceStatusDTO> getAttendBoards(AttendanceStatusDTO uerId);
	public void updateEndTime(String userId, String endTime, String yearMonthDay);
	public void deleteAttend(String userId);
	public List<AttendanceStatusDTO> getClassAttendance(String userId);
	public void updateClass(String userId,AttendanceStatusDTO user);
	public List<AttendanceStatusDTO> readID(String userId);
	public AttendanceStatusDTO calculateMonthlyAttendance(String userId, String yearMonth);
}
