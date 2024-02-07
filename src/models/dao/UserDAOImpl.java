package models.dao;

import models.dto.UserDTO;
import models.dto.UserInfoDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAOImpl extends commonDAO implements UserDAO {

	private void userSystem(String message, boolean err) {
		if (err) {
			System.err.println("[user]" + message);
		} else {
			System.out.println("[user]" + message);
		}
	}

	@Override
	public void insertUser(UserDTO user) {
		connect();
		String sql = "INSERT INTO user(userId,className,userName,password) values (?,?,?,?)";
		try {
			setPstmt(getConn().prepareStatement(sql));
			getPstmt().setString(1, user.getUserId());
			getPstmt().setString(2, user.getClassName());
			getPstmt().setString(3, user.getUserName());
			getPstmt().setString(4, user.getPassword());
			getPstmt().executeUpdate();

		} catch (SQLException e) {
			userSystem(e.getMessage(), true);
		}
		close();
	}

	@Override
	public UserDTO readID(String userId) {
		connect();
		String sql = "SELECT * from user where userId = ?";
		UserDTO dto = new UserDTO();
		try {
			setPstmt(getConn().prepareStatement(sql));
			getPstmt().setString(1, userId);
			setRs(getPstmt().executeQuery());
			while (getRs().next()) {
				dto.setUserId(getRs().getString("userId"));
				dto.setAuthority(getRs().getInt("authority"));
				dto.setClassName(getRs().getString("className"));
				dto.setPassword(getRs().getString("password"));
				dto.setUserName(getRs().getString("userName"));
			}

		} catch (SQLException e) {
			userSystem(e.getMessage(), true);
		}
		close();
		return dto;
	}

	@Override
	public void updateAuthUser(UserDTO user) {
		connect();
		String sql = "UPDATE user SET authority = ? where userId = ?";
		try {
			setPstmt(getConn().prepareStatement(sql));
			getPstmt().setInt(1, user.getAuthority());
			getPstmt().setString(2, user.getUserId());
			getPstmt().executeUpdate();
		} catch (SQLException e) {
			userSystem(sql, true);
		}
		close();
	}

	@Override
	public void deleteUser(String userId) {
		connect();
		String sql = "delete from user where userId = ?";
		try {
			setPstmt(getConn().prepareStatement(sql));
			getPstmt().setString(1, userId);
			getPstmt().execute();
		} catch (SQLException e) {
			userSystem(sql, true);
		}
		close();

	}

	@Override
	public ArrayList<UserDTO> approvalUsers() {
		connect();
		String sql = "select userId,userName,className,authority from user where authority Not in (1,2)";
		ArrayList<UserDTO> arrayList = new ArrayList<>();
		try {
			setPstmt(getConn().prepareStatement(sql));
			setRs(getPstmt().executeQuery());
			while (getRs().next()) {
				UserDTO userDTO = new UserDTO();
				userDTO.setUserId(getRs().getString("userId"));
				userDTO.setUserName(getRs().getString("userName"));
				userDTO.setClassName(getRs().getString("className"));
				userDTO.setAuthority(getRs().getInt("authority"));
				arrayList.add(userDTO);
			}
		} catch (SQLException e) {
			userSystem(sql, true);
		}
		close();
		return arrayList;
	}

	@Override
	public UserInfoDTO readInfoDTO(String userId) {
		connect();
		UserInfoDTO infoDTO = new UserInfoDTO();
		String sql = "SELECT u.userId,u.className, c.progress,c.roomNum,c.teacherName FROM User u JOIN Class c ON u.className= c.className and u.userId = ?";
		try {
			setPstmt(getConn().prepareStatement(sql));
			getPstmt().setString(1, userId);
			setRs(getPstmt().executeQuery());
			while (getRs().next()) {
				infoDTO.setUserId(getRs().getString("userId"));
				infoDTO.setClassName(getRs().getString("className"));
				infoDTO.setProgress(getRs().getString("progress"));
				infoDTO.setRoomNum(getRs().getString("roomNum"));
				infoDTO.setTeacherName(getRs().getString("teacherName"));
			}
		} catch (SQLException e) {
			userSystem(e.getMessage(), true);
		}
		close();
		return infoDTO;
	}

	@Override
	public ArrayList<UserDTO> classUsers(String userId) {
		connect();
		UserDTO user = null;
		ArrayList<UserDTO> list = new ArrayList<>();
		String sql = "SELECT u.userId, u.userName FROM user u JOIN user u2 ON u.className = u2.className WHERE u2.userId = ? and u.authority = 1";
		try {
			setPstmt(getConn().prepareStatement(sql));
			getPstmt().setString(1, userId);
			setRs(getPstmt().executeQuery());
			while (getRs().next()) {
				user = new UserDTO();
				user.setUserId(getRs().getString("userId"));
				user.setUserName(getRs().getString("userName"));
				list.add(user);
			}
		} catch (SQLException e) {
			userSystem(e.getMessage(), true);
		}
		close();
		return list;
	}
}
