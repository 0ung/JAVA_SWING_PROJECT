package test;

import java.util.Calendar;

import javax.swing.JFrame;

import org.junit.jupiter.api.Test;

import views.Calendars;

public class CalendarTest {

	@Test
	void cla() {
		Calendars cal = new Calendars();
		cal.setVisible(true);
	}

}
