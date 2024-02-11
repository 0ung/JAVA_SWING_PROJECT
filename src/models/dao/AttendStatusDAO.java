package models.dao;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import models.dto.AttendanceStatusDTO;

public class AttendStatusDAO {

	private commonDAO dao;

	public AttendStatusDAO() {
		dao = new commonDAO();
	}

	public void insertAttendance(AttendanceStatusDTO data) {
		try {

			dao.connect();
			String sql = "INSERT INTO attendance (userId, lateCnt, earlyLeaveCnt, outingCnt, absentCnt, startTime, endTime, yearMonthDay) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

			dao.setPstmt(dao.getConn().prepareStatement(sql));

			dao.getPstmt().setString(1, data.getUserId());
			dao.getPstmt().setInt(2, data.getLateCnt());
			dao.getPstmt().setInt(3, data.getEarlyleaveCnt());
			dao.getPstmt().setInt(4, data.getOutingCnt());
			dao.getPstmt().setInt(5, data.getAbsentCnt());
			dao.getPstmt().setString(6, data.getStartTime());
			dao.getPstmt().setString(7, data.getEndTime());
			dao.getPstmt().setString(8, data.getYearMonthDay());
			dao.getPstmt().executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {

			dao.close();

		}
	}

	public AttendanceStatusDTO calculateAttendanceRate(String userId, String yearMonth) {
		AttendanceStatusDTO attendanceStatusDTO = new AttendanceStatusDTO();
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://222.119.100.89:3382/attendance", "attendance",
					"codehows213");
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
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, userId);
			statement.setString(2, yearMonth);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				attendanceStatusDTO.setLateCnt(resultSet.getInt("LateCount"));
				attendanceStatusDTO.setEarlyleaveCnt(resultSet.getInt("EarlyLeaveCount"));
				attendanceStatusDTO.setOutingCnt(resultSet.getInt("OutingCount"));
				attendanceStatusDTO.setAbsentCnt(resultSet.getInt("AbsentCount"));
			}

		} catch (NumberFormatException e1) {
			System.err.println("월을 숫자로 변환하는 중 오류 발생: " + e1.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dao.close();
		}
		return attendanceStatusDTO;
	}
}
