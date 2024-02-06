package models.dao;

import models.dto.AttendanceStatusDTO;

public interface AttendDAO {
	
	public void insertStartTime(AttendanceStatusDTO startTime);
	public void updateEndTime(AttendanceStatusDTO endTime);

}
