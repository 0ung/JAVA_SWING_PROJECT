package views;

import models.dao.AttendStatusDAO;
import models.dto.AttendanceStatusDTO;

public class AttendanceManager {
	public static void main(String[] args) {
		AttendStatusDAO attendanceDAO = new AttendStatusDAO();
        AttendanceStatusDTO attendanceData = new AttendanceStatusDTO("23", "user123", 1, 0, 1, 0, "2022-01-01 08:00:00", "2022-01-01 16:00:00");
        
        // 데이터 삽입
        attendanceDAO.insertAttendance(attendanceData);
        
        // 필요한 경우 다른 DAO 메소드 호출
        // 예: attendanceDAO.updateAttendance(attendanceData);
        // 예: List<AttendanceStatusDTO> attendanceList = attendanceDAO.getAllAttendanceRecords();
    }
}
