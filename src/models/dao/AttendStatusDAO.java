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
	
	public double calculateAttendanceRate(String userId, String yearMonth) {
	    double attendanceRate = 0.0;
	    
	    try {
	    	String[] parts = yearMonth.split("-");
	    	int month = Integer.parseInt(parts[1]);
	    	
	        Connection conn = DriverManager.getConnection("jdbc:mysql://222.119.100.89:3382/attendance", "attendance", "codehows213");
	        String sql = "SELECT COUNT(*) AS totalDays, SUM(case when absentCnt = 0 then 1 else 0 end) AS attendedDays FROM attendancestatus WHERE userId = ? AND MONTH(startTime) = ?";
	        

	        PreparedStatement statement = conn.prepareStatement(sql);
	        statement.setString(1, userId);
	        statement.setInt(2, month);
	        
	        ResultSet resultSet = statement.executeQuery();
	        
	        if (resultSet.next()) {
	        	
	            int totalDays = resultSet.getInt("totalDays");
	            int attendedDays = resultSet.getInt("attendedDays");
	            
	            if (totalDays > 0) {
	                attendanceRate = (double) attendedDays / totalDays * 100;
	            } else {
	                attendanceRate = 0.0;
	            }
	        }
	        
	    } catch (NumberFormatException e1){
	    	System.err.println("월을 숫자로 변환하는 중 오류 발생: " + e1.getMessage());
	    	
	    }catch (SQLException e) {
	    	 e.printStackTrace();
	    }
	    dao.close();
	    return attendanceRate;
	}
}
