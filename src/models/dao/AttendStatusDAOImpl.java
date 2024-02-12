package models.dao;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import models.dto.AttendanceStatusDTO;
import models.dto.AvailableDayDTO;

public class AttendStatusDAOImpl extends CommonDAO implements AttendStautsDAO {

	@Override
	public void insertAttendance(AttendanceStatusDTO data) {
		try {
			connect();
			String sql = "INSERT INTO attendance (userId, lateCnt, earlyLeaveCnt, outingCnt, absentCnt, startTime, endTime, yearMonthDay) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			setPstmt(getConn().prepareStatement(sql));
			getPstmt().setString(1, data.getUserId());
			getPstmt().setInt(2, data.getLateCnt());
			getPstmt().setInt(3, data.getEarlyleaveCnt());
			getPstmt().setInt(4, data.getOutingCnt());
			getPstmt().setInt(5, data.getAbsentCnt());
			getPstmt().setString(6, data.getStartTime());
			getPstmt().setString(7, data.getEndTime());
			getPstmt().setString(8, data.getYearMonthDay());
			getPstmt().executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}

	@Override
	public AttendanceStatusDTO calculateAttendanceRate(String userId, String yearMonth) {
		connect();
		AttendanceStatusDTO attendanceStatusDTO = new AttendanceStatusDTO();
		try {
			String sql = """
						SELECT
					    SUM(CASE WHEN `attendancestatus`.`lateCnt` > 0 THEN 1 ELSE 0 END) AS `LateCount`,
					    SUM(CASE WHEN `attendancestatus`.`earlyleaveCnt` > 0 THEN 1 ELSE 0 END) AS `EarlyLeaveCount`,
					    SUM(CASE WHEN `attendancestatus`.`outingCnt` > 0 THEN 1 ELSE 0 END) AS `OutingCount`,
					    SUM(CASE WHEN `attendancestatus`.`absentCnt` > 0 THEN 1 ELSE 0 END) AS `AbsentCount`
					FROM
					    `attendancestatus`
					WHERE
					    `userId` = ?
					    AND `yearMonthDay` LIKE ?
					""";
			setPstmt(getConn().prepareStatement(sql));
			getPstmt().setString(1, userId);
			getPstmt().setString(2, yearMonth+"%");
			setRs(getPstmt().executeQuery());
			if (getRs().next()) {
				attendanceStatusDTO.setLateCnt(getRs().getInt("LateCount"));
				attendanceStatusDTO.setEarlyleaveCnt(getRs().getInt("EarlyLeaveCount"));
				attendanceStatusDTO.setOutingCnt(getRs().getInt("OutingCount"));
				attendanceStatusDTO.setAbsentCnt(getRs().getInt("AbsentCount"));
			}

		} catch (NumberFormatException e1) {
			System.err.println("월을 숫자로 변환하는 중 오류 발생: " + e1.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return attendanceStatusDTO;
	}

	@Override
	public void insertDay(AvailableDayDTO dayDTO) {
		connect();
		String sql = "insert into availableday(availableYearMonth,className,availableDay) values (?,?,?)";
		try {
			setPstmt(getConn().prepareStatement(sql));
			getPstmt().setString(1, dayDTO.getAvailableYearMonth());
			getPstmt().setString(2, dayDTO.getClassName());
			getPstmt().setInt(3, dayDTO.getAvailableDay());
			getPstmt().execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		close();
	};

	@Override
	public AvailableDayDTO readClassName(String className, String available) {
		AvailableDayDTO availableDayDTO = new AvailableDayDTO();
		connect();
		String sql = "select * from availableday where className = ? and availableYearMonth =?";
		try {
			setPstmt(getConn().prepareStatement(sql));
			getPstmt().setString(1, className);
			getPstmt().setString(2, available);
			setRs(getPstmt().executeQuery());
			while (getRs().next()) {
				availableDayDTO.setAvailableDay(getRs().getInt("availableDay"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		close();
		return availableDayDTO;
	}
}
