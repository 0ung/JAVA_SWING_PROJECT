package models.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.dto.AttendanceStatusDTO;

public class AttendDAOImpl extends commonDAO implements AttendDAO {

	@Override
	/* public void insertStartTime(AttendanceStatusDTO startTime) */
	public void insertStartTime(String userId, String date, String startTime) throws SQLException {
		connect();
		String sql = "INSERT INTO attendanceStatus(userId,yearMonthDay, startTime) values ( ?, ?, ? ) ";

		try {
			setPstmt(getConn().prepareStatement(sql));
			getPstmt().setString(1, userId);
			getPstmt().setString(2, date);
			getPstmt().setString(3, startTime);
			getPstmt().executeUpdate();

		} catch (SQLException e) {
			throw new SQLException();
		} finally {
			close();
		}

	}

	@Override
	public void updateEndTime(String userId, String endTime, String yearMonthDay) {
		connect();
		String sql = "UPDATE attendanceStatus set endTime = ? where userId = ? and yearMonthDay = ?";
		try {
			setPstmt(getConn().prepareStatement(sql));
			getPstmt().setString(1, endTime);
			getPstmt().setString(2, userId);
			getPstmt().setString(3, yearMonthDay);

			getPstmt().executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}
	public List<AttendanceStatusDTO> getAttendBoards(AttendanceStatusDTO userId) {
		List<AttendanceStatusDTO> attendBoards = new ArrayList<>();

		
		connect();
		String sql = "SELECT yearMonthDay, startTime, endTime from attendanceStatus where userId = ? and yearMonthDay like ?";
		try {
			setPstmt(getConn().prepareStatement(sql));
			getPstmt().setString(1, userId.getUserId());
			
			getPstmt().setString(2, userId.getYearMonthDay().substring(0,7) + "%");
			setRs(getPstmt().executeQuery());
		

			while (getRs().next()) {
				
				AttendanceStatusDTO board = new AttendanceStatusDTO();
				board.setYearMonthDay(getRs().getString("yearMonthDay"));
				board.setStartTime(getRs().getString("startTime"));
				board.setEndTime(getRs().getString("endTime"));
				attendBoards.add(board);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return attendBoards;
	}

	@Override
	public void deleteAttend(String userId) {
		connect();

		close();

	}

}
