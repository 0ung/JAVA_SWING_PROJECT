package models;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.mysql.cj.QueryReturnType;

import models.dto.AttendanceStatusDTO;

public class AttendanceCheckDAOImpl extends commonDAO implements AttendanceCheckDAO {
	private static AttendanceCheckDAOImpl instance = new AttendanceCheckDAOImpl();

	public static AttendanceCheckDAOImpl getInstance() {
		return instance;
	}

	@Override
	public List<AttendanceStatusDTO> readID(String userId) {
		List<AttendanceStatusDTO> list = new ArrayList<>();
		connect();
		String sql = "SELECT * FROM attendancestatus where userId = ? ";

		try {
			getPstmt().setString(1, userId);
			setRs(getPstmt().executeQuery());

			while (getRs().next()) {
				AttendanceStatusDTO dto = new AttendanceStatusDTO();
				setPstmt(getConn().prepareStatement(sql));
				dto.setUserId(getRs().getString("userId"));
				dto.setLateCnt(getRs().getInt("lateCnt"));
				dto.setEarlyleaveCnt(getRs().getInt("earlyleaveCnt"));
				dto.setOutingCnt(getRs().getInt("outingCnt"));
				dto.setAbsentCnt(getRs().getInt("absentCnt")); // 결석
				dto.setStartTime(getRs().getString("starttime"));
				dto.setEndTime(getRs().getString("endTime"));
				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return list;
	}

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

//				int lateCnt = getRs().getInt("lateCnt");
//				int earlyLeaveCnt = getRs().getInt("earlyLeaveCnt");
//				int outingCnt = getRs().getInt("outingCnt");
//				int absentCnt = getRs().getInt("absentCnt");
//
//				// 결과 출력
//				System.out.println("지각: " + lateCnt);
//				System.out.println("조퇴: " + earlyLeaveCnt);
//				System.out.println("외출: " + outingCnt);
//				System.out.println("결석: " + absentCnt);
				//System.out.println(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return dto;
	}
}
