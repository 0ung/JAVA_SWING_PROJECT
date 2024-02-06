package models.service;

import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import models.dao.AttendDAO;
import models.dao.AttendDAOImpl;

public class AttendService{
	
	private AttendDAO attendStatusDAO = new AttendDAOImpl();
	
	public List<String> setTime() {
		String total= LocalDateTime.now().toString();
		String[] day = total.split("T");
		String time = day[1].substring(0, 8);
		ArrayList<String> list = new ArrayList<>();
		list.add(day[0]);
		list.add(time);
		return list;

	}
	
	public void insertStartTime(String userId) {
		List<String> currentTime = setTime();
		String yearMonthDay = currentTime.get(0);
		String startTime = currentTime.get(1);
		
		
	}
	

}
