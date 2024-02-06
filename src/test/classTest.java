package test;

import org.junit.jupiter.api.Test;

import models.dao.AttendDAO;
import models.dao.AttendDAOImpl;
import models.dto.AttendanceStatusDTO;

public class classTest {
	AttendDAO attendDAO = new AttendDAOImpl();

//	@Test
//	void insertTest() {
//		AttendanceStatusDTO attendDTO = new AttendanceStatusDTO();
//		attendDTO.setYearMonthDay("2024-02-06");
//		attendDTO.setUserId("01075743839");
//		attendDTO.setStartTime("2024-02-06 09:00:00");
//		attendDAO.insertStartTime(attendDTO);
//	}

	@Test
	void updateTest() {
		AttendanceStatusDTO attendDTO = new AttendanceStatusDTO();
		attendDTO.setYearMonthDay("2024-02-06");
		attendDTO.setUserId("01075743839");
		attendDTO.setEndTime("2024-02-06 18:00:00");
		attendDAO.updateEndTime(attendDTO);
	}

}
