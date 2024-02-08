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
			e.printStackTrace();
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

	public List<AttendanceStatusDTO> getAttendBoards(String userId) {
		List<AttendanceStatusDTO> attendBoards = new ArrayList<>();

		connect();
		String sql = "SELECT yearMonthDay, startTime, endTime from attendanceStatus where userId = ? ";
		try {
			setPstmt(getConn().prepareStatement(sql));
			getPstmt().setString(1, userId);
			setRs(getPstmt().executeQuery());

			while (getRs().next()) {
				AttendanceStatusDTO board = new AttendanceStatusDTO();
				board.setUserId(userId);
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

	@Override
	public List<AttendanceStatusDTO> getClassAttendance(String userId) {
		connect();
		List<AttendanceStatusDTO> list = new ArrayList<>();
		String sql = "select * from attendancestatus where userId in (select userId from user where className = (select className from user where userId = ?) and authority = 1);";
		try {
			setPstmt(getConn().prepareStatement(sql));
			getPstmt().setString(1, userId);
			setRs(getPstmt().executeQuery());
			while (getRs().next()) {
				AttendanceStatusDTO dto = new AttendanceStatusDTO();
				dto.setStartTime(getRs().getString("startTime"));
				dto.setEndTime(getRs().getString("endTime"));
				dto.setYearMonthDay(getRs().getString("yearMonthDay"));
				dto.setUserId(getRs().getString("userId"));
				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		close();
		return list;
	}

	@Override
	public void updateClass(String userId, AttendanceStatusDTO user) {
		connect();
		String sql = "update attendancestatus set lateCnt =?, earlyleaveCnt = ?,outingCnt = ?, absentCnt = ? where userId in (select userId from user where className = (select className from user where userId = ?) and authority = 1 and yearMonthDay = ?)";

		try {
			setPstmt(getConn().prepareStatement(sql));
			getPstmt().setLong(1, user.getLateCnt());
			getPstmt().setLong(2, user.getEarlyleaveCnt());
			getPstmt().setLong(3, user.getOutingCnt());
			getPstmt().setLong(4, user.getAbsentCnt());
			getPstmt().setString(5, userId);
			getPstmt().setString(6, user.getYearMonthDay());
			getPstmt().executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		close();
	}

}
