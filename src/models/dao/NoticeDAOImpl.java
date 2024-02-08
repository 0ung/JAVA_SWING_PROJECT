package models.dao;

import java.sql.SQLException;

import models.dto.NoticeDto;

public class NoticeDAOImpl extends commonDAO implements NoticeDAO {

	@Override
	public void insertNotice(NoticeDto notice) {
		connect();
		String sql = "INSERT INTO notice(userId,title,content,createTime,important) values (?,?,?,now(),?)";
		try {
			setPstmt(getConn().prepareStatement(sql));
			getPstmt().setString(1, notice.getUserId());
			getPstmt().setString(2, notice.getTitle());
			getPstmt().setString(3, notice.getContent());
			getPstmt().setBoolean(4, notice.isImportant());
			getPstmt().executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		close();
	}

}
