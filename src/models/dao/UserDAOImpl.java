package models.dao;

import models.dto.UserDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
	public void updateUser(UserDTO user) {
		connect();
		String sql = "UPDATE user SET className = ?, userName = ?, authority = ? where userId = ?";
		try {
			setPstmt(getConn().prepareStatement(sql));
			getPstmt().setString(1, user.getClassName());
			getPstmt().setString(2, user.getUserName());
			getPstmt().setInt(3, user.getAuthority());
			getPstmt().setString(4, user.getUserId());
			getPstmt().executeUpdate();
		} catch (SQLException e) {
			userSystem(sql, false);
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

	}
}
