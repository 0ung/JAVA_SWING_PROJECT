package models.dao;

import java.sql.SQLException;

import models.commonDAO;
import models.dto.AttendanceStatusDTO;

public class AttendDAOImpl extends commonDAO implements AttendDAO {

	@Override
	public void insertStartTime(AttendanceStatusDTO startTime) {
		connect();
		String sql = "INSERT INTO attendanceStatus(userId,yearMonthDay, startTime) values ( ?, ?, ? ) ";

		try {
			setPstmt(getConn().prepareStatement(sql));
			getPstmt().setString(1, startTime.getUserId());
			getPstmt().setString(2, startTime.getYearMonthDay());
			getPstmt().setString(3, startTime.getStartTime());
			getPstmt().executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		close();
	}

	@Override
	public void updateEndTime(AttendanceStatusDTO endTime) {
		connect();
		String sql = "UPDATE attendanceStatus set endTime = ? where userId = ? and yearMonthDay = ?";
		try {
			setPstmt(getConn().prepareStatement(sql));
			getPstmt().setString(1, endTime.getEndTime());
			getPstmt().setString(2, endTime.getUserId());
			getPstmt().setString(3, endTime.getYearMonthDay());
			getPstmt().executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		close();

	}

}
