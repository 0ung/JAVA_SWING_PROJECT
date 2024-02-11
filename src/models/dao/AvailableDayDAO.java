package models.dao;

import java.sql.SQLException;

import models.dto.AvailableDayDTO;
import models.dto.UserDTO;

public class AvailableDayDAO extends commonDAO {
	public void insertDay(AvailableDayDTO dayDTO) {
		connect();
		String sql = "insert into availableday(availableYearMonth,className,availableDay) values (?,?,?)";
		try {
			setPstmt(getConn().prepareStatement(sql));
			getPstmt().setString(1, dayDTO.getAvailableYearMonth());
			getPstmt().setString(2, dayDTO.getClassName());
			getPstmt().setInt(3, dayDTO.getAvailableDay());
			getPstmt().execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		close();
	};

	public AvailableDayDTO readClassName(String className, String available) {
		AvailableDayDTO availableDayDTO = new AvailableDayDTO();
		connect();
		String sql = "select * from availableday where className = ? and availableYearMonth =?";
		try {
			setPstmt(getConn().prepareStatement(sql));
			getPstmt().setString(1, className);
			getPstmt().setString(2, available);
			setRs(getPstmt().executeQuery());
			while (getRs().next()) {
				availableDayDTO.setAvailableDay(getRs().getInt("availableDay"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		close();
		return availableDayDTO;
	}
}
