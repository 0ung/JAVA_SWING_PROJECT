package models.dao;

import models.dto.AttendanceStatusDTO;
import models.dto.AvailableDayDTO;

public interface AttendStautsDAO {

	public void insertAttendance(AttendanceStatusDTO data);

	public AttendanceStatusDTO calculateAttendanceRate(String userId, String yearMonth);

	public void insertDay(AvailableDayDTO dayDTO);

	public AvailableDayDTO readClassName(String className, String available);

}