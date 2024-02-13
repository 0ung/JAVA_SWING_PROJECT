package models.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.dao.AttendDAO;
import models.dao.AttendDAO;
import models.dto.AttendanceStatusDTO;

public class AttendService {
	private AttendDAO attendStatusDAO = new AttendDAO();

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

	public int attendAlgorithm(AttendanceStatusDTO attendanceStatusDTO) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String startTimeStr = attendanceStatusDTO.getStartTime();
		String endTimeStr = attendanceStatusDTO.getEndTime();
		String yearMonthDay = attendanceStatusDTO.getYearMonthDay();

		Date startDateTime = dateFormat.parse(yearMonthDay + " " + startTimeStr);
		Date endDateTime = dateFormat.parse(yearMonthDay + " " + endTimeStr);

		long difference = endDateTime.getTime() - startDateTime.getTime();

		long eightHoursInMilliSeconds = 8 * 60 * 60 * 1000;
		long fourHoursInMilliSeconds = 4 * 60 * 60 * 1000;

		// 09시 10분과 17시 50분 기준 시간 생성
		Date lateTime = createSpecificTime(yearMonthDay, "09:10:00");
		Date earlyLeaveTime = createSpecificTime(yearMonthDay, "17:50:00");

		if (difference < fourHoursInMilliSeconds) {
			return 0;
		} else if (startDateTime.after(lateTime)) {
			return 1;
		} else if (endDateTime.before(earlyLeaveTime)) {
			return 2;
		} else if (difference >= eightHoursInMilliSeconds) {
			return 3;
		} else {
			return 4;
		}
	}

	public Date createSpecificTime(String dateTimeStr, String timePart) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String datePart = dateTimeStr.substring(0, 10); // 날짜 부분만 추출
		return dateFormat.parse(datePart + " " + timePart);
	}

	public ArrayList<AttendanceStatusDTO> getList(String userId) {
		return (ArrayList<AttendanceStatusDTO>) attendStatusDAO.getClassAttendance(userId);
	}

	public void updateAttend(String userId, AttendanceStatusDTO user) {
		attendStatusDAO.updateClass(userId, user);
	}

	public String validaiton(AttendanceStatusDTO attendanceStatusDTO) {
		if (attendanceStatusDTO.getEarlyleaveCnt() == 1) {
			return "조퇴";
		} else if (attendanceStatusDTO.getLateCnt() == 1) {
			return "지각";
		} else if (attendanceStatusDTO.getOutingCnt() == 1) {
			return "외출";
		} else if (attendanceStatusDTO.getAbsentCnt() == 1) {
			return "결석";
		} else {
			return "출석";
		}
	}
}
