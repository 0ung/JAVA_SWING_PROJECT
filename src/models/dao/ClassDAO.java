package models.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class ClassDAO {
    public void insertClass(String className, String roomNum, String progress, String teacherName) {
        // 데이터베이스 연결 및 SQL 쿼리 준비
        String url = "jdbc:mysql://222.119.100.89:3382/attendance";
        String user = "attendance";
        String password = "codehows213";
        String sql = "INSERT INTO class (className, teacherName, roomNum, progress ) VALUES (?, ?, ?, ?)";
        String check = "SELECT COUNT(*) AS count FROM class WHERE className = ?";
        
        try (Connection conn = DriverManager.getConnection(url, user, password);
        	 PreparedStatement pstmtCheck = conn.prepareStatement(check);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
        	
        		pstmtCheck.setString(1, className);
        		ResultSet rs = pstmtCheck.executeQuery();
        		rs.next();
        		int count = rs.getInt("count");
        		if (count > 0) {
        			// 동일한 이름의 반이 이미 존재함
        			JOptionPane.showMessageDialog(null, "이미 동일한 이름의 반이 존재합니다.");
        			return;
        		}
        		
            pstmt.setString(1, className);
            pstmt.setString(2, teacherName);
            pstmt.setString(3, roomNum);
            pstmt.setString(4, progress);
            pstmt.executeUpdate();
            
            // 성공 메시지 표시
            JOptionPane.showMessageDialog(null, "반 정보가 성공적으로 생성되었습니다.");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public ArrayList<String> fetchAllClasses() {
        ArrayList<String> classList = new ArrayList<>();
        String sql = "SELECT className FROM class"; // 가정: 'class' 테이블에 'classNumber' 컬럼이 반의 이름을 저장

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://222.119.100.89:3382/attendance", "attendance", "codehows213");
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String classNumber = rs.getString("className");
                classList.add(classNumber);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return classList;
    }
    
    public void deleteClass(String className) {
        String sql = "DELETE FROM class WHERE className = ?";
        
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://222.119.100.89:3382/attendance", "attendance", "codehows213");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, className);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public boolean updateClass(String newClassName, String newRoomNum, String newProgress, String newTeacherName) {
        String sql = "UPDATE class SET roomNum = ?, progress = ?, teacherName = ? WHERE className = ?";
        
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://222.119.100.89:3382/attendance", "attendance", "codehows213");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newRoomNum);
            pstmt.setString(2, newProgress);
            pstmt.setString(3, newTeacherName);
            pstmt.setString(4, newClassName);
            
            int rowsAffected = pstmt.executeUpdate(); // 영향 받은 행의 수를 얻음
            return rowsAffected > 0; // 영향 받은 행의 수가 1 이상이면 true 반환, 그렇지 않으면 false 반환
        } catch (Exception e) {
            e.printStackTrace();
            return false; // 예외가 발생하면 false 반환
        }
    }
    
    public String[] getClassInfo(String className) {
        String[] classInfo = new String[4]; // 클래스 정보를 저장할 배열

        String sql = "SELECT className, roomNum, progress, teacherName FROM class WHERE className = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://222.119.100.89:3382/attendance", "attendance", "codehows213");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, className);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // 클래스 정보를 배열에 저장
                    classInfo[0] = rs.getString("className"); // 반
//                    classInfo[1] = rs.getString("teacherName"); // 담당 교사
//                    classInfo[2] = rs.getString("roomNum"); // 반 호실
//                    classInfo[3] = rs.getString("progress"); // 진도
                    
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return classInfo;
    }
    
}
