package models.dao;

import java.util.List;

import models.dto.NoticeDto;

public interface NoticeDAO {
	public void insertNotice(NoticeDto notice);

	public List<NoticeDto> readID(int important);

}
