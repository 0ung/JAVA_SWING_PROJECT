package models.dao;

import java.sql.SQLException;

import models.dto.AttendanceStatusDTO;

public class AttendInfoDAOImpl extends commonDAO implements AttendInfoDAO{
	
	@Override
	public void insertAttendInfo(AttendanceStatusDTO attendInfo) {
		connect();
		String sql = "INSERT INTO attendanceStatus(userId, lateCnt, earlyleaveCnt, outingCnt, absentCnt) values ( ?, ?, ?, ?, ?)";
		
		try {
			setPstmt(getConn().prepareStatement(sql));
			getPstmt().setString(1, attendInfo.getUserId());
			getPstmt().setInt(2, attendInfo.getLateCnt());
			getPstmt().setInt(3, attendInfo.getEarlyleaveCnt());
			getPstmt().setInt(4, attendInfo.getOutingCnt());
			getPstmt().setInt(5, attendInfo.getAbsentCnt());
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		close();
	}
	

}
