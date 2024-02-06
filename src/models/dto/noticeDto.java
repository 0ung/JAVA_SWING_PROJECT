package models.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class noticeDto {
	private long noticeId;
	private String userId;
	private String title;
	private String Text;
	private Date TimeStamp;
	private boolean TinyInt;

}
