package models.dao;

import java.util.List;

import models.dto.AttendanceStatusDTO;

public interface AttendanceCheckDAO {
	public List<AttendanceStatusDTO> readID(String userId);
	public AttendanceStatusDTO calculateMonthlyAttendance(String userId, String yearMonth);
}
