package models.dao;

import java.util.List;

import models.dto.AttendanceStatusDTO;

public interface AttendDAO {
	
	public void insertStartTime(AttendanceStatusDTO startTime);
	public List<AttendanceStatusDTO> getAttendBoards(String userId);
	public void updateEndTime(AttendanceStatusDTO endTime);
	public void deleteAttend(String userId);

}
