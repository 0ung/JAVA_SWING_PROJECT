package test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

//	@Test
//	void updateTest() {
//		AttendanceStatusDTO attendDTO = new AttendanceStatusDTO();
//		attendDTO.setYearMonthDay("2024-02-06");
//		attendDTO.setUserId("01075743839");
//		attendDTO.setEndTime("2024-02-06 18:00:00");
//		attendDAO.updateEndTime(attendDTO);
//	}

//	@Test
//	void asd() {
//		String asd = LocalDateTime.now().toString();
//		String[] a = asd.split("T");
//		for (String string : a) {
//			System.out.println(string);
//		}
//		String asdz = a[1].substring(0, 8);
//		System.out.println(a[0] + " " + asdz);
//		AttendanceStatusDTO attendanceStatusDTO = new AttendanceStatusDTO();
//		attendanceStatusDTO.setStartTime(a[0]);
//		attendanceStatusDTO.setUserId("01075743839");
//		attendanceStatusDTO.setYearMonthDay(asdz);
//		attendDAO.insertStartTime(attendanceStatusDTO);
//	}

	//@Test
	/*void zzxc() {
		System.out.println(zxc().get(0));
		System.out.println(zxc().get(1));
	}

	public List<String> zxc() {
		String asd = LocalDateTime.now().toString();
		String[] a = asd.split("T");
		String asdz = a[1].substring(0, 8);
		ArrayList<String> list = new ArrayList<>();
		list.add(a[0]);
		list.add(asdz);
		return list;

	}*/

}
