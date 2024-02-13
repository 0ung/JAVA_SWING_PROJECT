package models.dao;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.util.ArrayList;

public class ClassDAOImpl extends CommonDAO implements ClassDAO {
	@Override
	public void insertClass(String className, String roomNum, String progress, String teacherName) {
		// 데이터베이스 연결 및 SQL 쿼리 준비
		connect();
		String sql = "INSERT INTO class (className, teacherName, roomNum, progress ) VALUES (?, ?, ?, ?)";
		String check = "SELECT COUNT(*) AS count FROM class WHERE className = ?";
		try {
			setPstmt(getConn().prepareStatement(sql));
			PreparedStatement pstmtCheck = getConn().prepareStatement(check);
			pstmtCheck.setString(1, className);
			setRs(pstmtCheck.executeQuery());
			getRs().next();
			int count = getRs().getInt("count");
			if (count > 0) {
				JOptionPane.showMessageDialog(null, "이미 동일한 이름의 반이 존재합니다.");
				return;
			}
			getPstmt().setString(1, className);
			getPstmt().setString(2, teacherName);
			getPstmt().setString(3, roomNum);
			getPstmt().setString(4, progress);
			getPstmt().executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		close();
	}

	@Override
	public ArrayList<String> fetchAllClasses() {
		connect();
		ArrayList<String> classList = new ArrayList<>();
		String sql = "SELECT className FROM class"; // 가정: 'class' 테이블에 'classNumber' 컬럼이 반의 이름을 저장

		try {
			setPstmt(getConn().prepareStatement(sql));
			setRs(getPstmt().executeQuery());
			while (getRs().next()) {
				String classNumber = getRs().getString("className");
				classList.add(classNumber);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return classList;
	}

	@Override
	public void deleteClass(String className) {
		String sql = "DELETE FROM class WHERE className = ?";
		connect();
		try {
			setPstmt(getConn().prepareStatement(sql));
			getPstmt().setString(1, className);
			getPstmt().executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean updateClass(String newClassName, String newRoomNum, String newProgress, String newTeacherName) {
		String sql = "UPDATE class SET roomNum = ?, progress = ?, teacherName = ? WHERE className = ?";

		try {
			setPstmt(getConn().prepareStatement(sql));
			getPstmt().setString(1, newRoomNum);
			getPstmt().setString(2, newProgress);
			getPstmt().setString(3, newTeacherName);
			getPstmt().setString(4, newClassName);

			int rowsAffected = getPstmt().executeUpdate(); // 영향 받은 행의 수를 얻음
			return rowsAffected > 0; // 영향 받은 행의 수가 1 이상이면 true 반환, 그렇지 않으면 false 반환
		} catch (Exception e) {
			e.printStackTrace();
			return false; // 예외가 발생하면 false 반환
		} finally {
			close();
		}
	}

	@Override
	public String[] getClassInfo(String className) {
		String[] classInfo = new String[4]; // 클래스 정보를 저장할 배열

		String sql = "SELECT className, roomNum, progress, teacherName FROM class WHERE className = ?";

		try {
			setPstmt(getConn().prepareStatement(sql));
			getPstmt().setString(1, className);
			setRs(getPstmt().executeQuery());
			if (getRs().next()) {
				classInfo[0] = getRs().getString("className"); // 반
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		close();
		return classInfo;
	}

}
