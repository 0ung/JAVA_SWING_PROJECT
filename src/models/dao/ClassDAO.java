package models.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ClassDAO {
    public void insertClass(String className, String roomNum, String progress, String teacherName) {
        // 데이터베이스 연결 및 SQL 쿼리 준비
        String url = "jdbc:mysql://222.119.100.89:3382/attendance";
        String user = "attendance";
        String password = "codehows213";
        String sql = "INSERT INTO class (className, roomNum, progress, teacherName) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, className);
            pstmt.setString(2, roomNum);
            pstmt.setString(3, teacherName);
            pstmt.setString(4, progress);
            
            
            pstmt.executeUpdate();
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
    
    public void updateClass(String newClassName, String newroomNum, String newProgress, String newTeacherName) {
        String sql = "UPDATE class SET roomNum = ?, progress = ?, teacherName = ? WHERE className = ?";
        
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://222.119.100.89:3382/attendance", "attendance", "codehows213");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newroomNum);
            pstmt.setString(2, newProgress);
            pstmt.setString(3, newTeacherName);
            pstmt.setString(4, newClassName);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
