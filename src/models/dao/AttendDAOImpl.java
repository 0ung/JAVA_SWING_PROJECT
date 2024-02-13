package models.dao;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import models.dto.AttendanceStatusDTO;

public class AttendDAOImpl extends CommonDAO implements AttendDAO {

	@Override
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
		String sql = "SELECT * from attendanceStatus where userId = ? and yearMonthDay like ?";
		try {
			setPstmt(getConn().prepareStatement(sql));
			getPstmt().setString(1, userId.getUserId());

			getPstmt().setString(2, userId.getYearMonthDay().substring(0, 7) + "%");
			setRs(getPstmt().executeQuery());

			while (getRs().next()) {
				AttendanceStatusDTO board = new AttendanceStatusDTO();
				board.setYearMonthDay(getRs().getString("yearMonthDay"));
				board.setStartTime(getRs().getString("startTime"));
				board.setEndTime(getRs().getString("endTime"));
				board.setEarlyleaveCnt(getRs().getInt("earlyLeaveCnt"));
				board.setLateCnt(getRs().getInt("lateCnt"));
				board.setOutingCnt(getRs().getInt("outingCnt"));
				board.setAbsentCnt(getRs().getInt("absentCnt"));
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
		String sql = "select * from attendancestatus where userId in (select userId from user where className = (select className from user where userId = ?) and authority = 1 and yearMonthDay = ?);";
		try {
			setPstmt(getConn().prepareStatement(sql));
			getPstmt().setString(1, userId);
			getPstmt().setString(2, LocalDate.now().toString());
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
		String sql = "update attendancestatus set lateCnt =?, earlyleaveCnt = ?,outingCnt = ?, absentCnt = ? where userId = (select userId from user where className = (select className from user where userId = ?) and authority = 1 and yearMonthDay = ? and userId = ?)";
		try {
			setPstmt(getConn().prepareStatement(sql));
			getPstmt().setLong(1, user.getLateCnt());
			getPstmt().setLong(2, user.getEarlyleaveCnt());
			getPstmt().setLong(3, user.getOutingCnt());
			getPstmt().setLong(4, user.getAbsentCnt());
			getPstmt().setString(5, userId);
			getPstmt().setString(6, user.getYearMonthDay());
			getPstmt().setString(7, user.getUserId());
			getPstmt().executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		close();
	}
	
	@Override
	public List<AttendanceStatusDTO> readID(String userId) {
		List<AttendanceStatusDTO> list = new ArrayList<>();
		connect();
		String sql = "SELECT * FROM attendancestatus where userId = ? ";
		try {
			setPstmt(getConn().prepareStatement(sql));
			getPstmt().setString(1, userId);
			setRs(getPstmt().executeQuery());
			while (getRs().next()) {
				AttendanceStatusDTO dto = new AttendanceStatusDTO();
				setPstmt(getConn().prepareStatement(sql));
				dto.setUserId(getRs().getString("ㅜuserId"));
				dto.setLateCnt(getRs().getInt("lateCnt"));
				dto.setEarlyleaveCnt(getRs().getInt("earlyleaveCnt"));
				dto.setOutingCnt(getRs().getInt("outingCnt"));
				dto.setAbsentCnt(getRs().getInt("absentCnt")); // 결석
				dto.setStartTime(getRs().getString("starttime"));
				dto.setEndTime(getRs().getString("endTime"));
				dto.setYearMonthDay(getRs().getString("yearMonthDay"));
				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return list;
	}

	@Override
	public AttendanceStatusDTO calculateMonthlyAttendance(String userId, String yearMonth) {
		AttendanceStatusDTO dto = new AttendanceStatusDTO();
		connect();
		// SQL 쿼리 수정
		String sql = "SELECT userId, SUM(lateCnt) AS lateCnt, SUM(earlyleaveCnt) AS earlyLeaveCnt, SUM(outingCnt) AS outingCnt, SUM(absentCnt) AS absentCnt "
				+ "FROM attendancestatus " + "WHERE userId = ? AND yearMonthDay like ?" + "GROUP BY userId";

		try {
			setPstmt(getConn().prepareStatement(sql));
			getPstmt().setString(1, userId);
			getPstmt().setString(2, yearMonth + "%");
			setRs(getPstmt().executeQuery());

			if (getRs().next()) {
				dto.setUserId(getRs().getString("userId"));
				dto.setLateCnt(getRs().getInt("lateCnt"));
				dto.setEarlyleaveCnt(getRs().getInt("earlyLeaveCnt"));
				dto.setOutingCnt(getRs().getInt("outingCnt"));
				dto.setAbsentCnt(getRs().getInt("absentCnt"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return dto;
	}
}
