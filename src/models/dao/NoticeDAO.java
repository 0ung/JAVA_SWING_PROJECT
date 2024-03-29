package models.dao;

import models.dto.NoticeDto;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NoticeDAO extends CommonDAO {

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
		} finally {
			close();
		}
	}

	public List<NoticeDto> readID(int important) {
		List<NoticeDto> list = new ArrayList<>();
		connect();
		String sql = """
				SELECT n.noticeId, n.userId, n.title, n.content, n.createTime, u.userName
					from notice as n
					join user as u
				    on n.userId = u.userId
				    where important = ?
				    ORDER BY createTime DESC
				""";
		try {
			setPstmt(getConn().prepareStatement(sql));
			getPstmt().setInt(1, important);
			setRs(getPstmt().executeQuery());
			while (getRs().next()) {
				NoticeDto dto = new NoticeDto();
				dto.setNoticeId(getRs().getLong("noticeId"));
				dto.setUserId(getRs().getString("userId"));
				dto.setTitle(getRs().getString("title"));
				dto.setContent(getRs().getString("content"));
				dto.setCreateTime(getRs().getDate("createTime"));
				dto.setUserName(getRs().getString("userName"));
				list.add(dto);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return list;
	}

	public NoticeDto getNoticeDetailById(long noticeId) {
		NoticeDto noticeDetail = null;
		try {
			connect(); // 데이터베이스 연결
			String sql = "SELECT * FROM notice WHERE noticeId = ?";

			setPstmt(getConn().prepareStatement(sql));
			getPstmt().setLong(1, noticeId); // 선택된 공지사항의 ID 설정
			setRs(getPstmt().executeQuery());

			if (getRs().next()) {
				noticeDetail = new NoticeDto();
				// ResultSet에서 데이터 추출하여 NoticeDto 객체에 설정
				noticeDetail.setNoticeId(getRs().getLong("noticeId"));
				noticeDetail.setTitle(getRs().getString("title"));
				noticeDetail.setContent(getRs().getString("content"));
				noticeDetail.setUserId(getRs().getString("userId"));
				noticeDetail.setCreateTime(getRs().getDate("createTime")); // Timestamp 사용
				noticeDetail.setImportant(getRs().getBoolean("important"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(); // 데이터베이스 연결 종료
		}
		return noticeDetail;
	}

	public void updateNoticeById(NoticeDto notice) {
		connect();
		String sql = "update notice set title = ?, content = ? where noticeId = ?";
		try {
			setPstmt(getConn().prepareStatement(sql));
			getPstmt().setString(1, notice.getTitle());
			getPstmt().setString(2, notice.getContent());
			getPstmt().setLong(3, notice.getNoticeId());
			getPstmt().executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}

	public void deleteNoticeById(NoticeDto notice) {
		connect();
		String sql = "delete from notice where noticeId= ?";
		try {
			setPstmt(getConn().prepareStatement(sql));
			getPstmt().setLong(1, notice.getNoticeId());
			getPstmt().execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}

	}

	public List<NoticeDto> readbyday(String date) {
		List<NoticeDto> list = new ArrayList<>();
		connect();
		String sql = "SELECT n.noticeId, n.userId, n.title, n.content, n.createTime, u.userName from notice as n join user as u on n.userId = u.userId where important = 0 and createTime like ?";
		try {
			setPstmt(getConn().prepareStatement(sql));
			getPstmt().setString(1, date + "%");
			setRs(getPstmt().executeQuery());
			while (getRs().next()) {
				NoticeDto dto = new NoticeDto();
				dto.setNoticeId(getRs().getLong("noticeId"));
				dto.setUserId(getRs().getString("userId"));
				dto.setTitle(getRs().getString("title"));
				dto.setContent(getRs().getString("content"));
				dto.setCreateTime(getRs().getDate("createTime"));
				dto.setUserName(getRs().getString("userName"));
				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return list;
	}

}
