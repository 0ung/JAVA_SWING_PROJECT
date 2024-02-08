package models.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoticeDto {
	private long noticeId;
	private String userId;
	private String title;
	private String content;
	private Date createTime;
	private boolean important;
	private String userName;
}
