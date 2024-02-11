package models.dao;

import java.util.List;

import models.dto.NoticeDto;

public interface NoticeDAO {
	public void insertNotice(NoticeDto notice);

	public List<NoticeDto> readID(int important);

	public NoticeDto getNoticeDetailById(long noticeId);

	public void updateNoticeById(NoticeDto notice);

	public void deleteNoticeById(NoticeDto notice);

	public List<NoticeDto> readbyday(String date);
}
