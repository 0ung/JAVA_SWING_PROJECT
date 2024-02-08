package views;

import java.text.SimpleDateFormat;
import java.util.Date;

import models.dao.AttendStatusDAO;
import models.dto.AttendanceStatusDTO;

public class AttendanceManager {
	public static void main(String[] args) {
		Date nowDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM--dd");
		String NowDate = sdf.format(nowDate);
		
		AttendStatusDAO attendanceDAO = new AttendStatusDAO();
        AttendanceStatusDTO attendanceData = new AttendanceStatusDTO("user123", 1, 0, 1, 0, "2022-01-01 08:00:00", "2022-01-01 16:00:00", NowDate);
        
        // 데이터 삽입
        attendanceDAO.insertAttendance(attendanceData);
       
    }
}
