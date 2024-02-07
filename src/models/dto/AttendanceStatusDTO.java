package models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceStatusDTO {
	
	private String yearMonthDay;
	private String userId;
	private int lateCnt;
	private int earlyleaveCnt;
	private int outingCnt;
	private int absentCnt;
	private String startTime;
	private String endTime;

}
