package test;

import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;

import org.junit.jupiter.api.Test;

import views.Calendars;
import views.Notice;

public class CalendarTest {

	@Test
	void cla() {
		Calendars cal = new Calendars();
		cal.setVisible(true);
	}

	@Test
	void table() {
		Notice notice = new Notice();
		DefaultTableModel model = (DefaultTableModel) notice.getTable().getModel();
		String[] arr = { "테스트1", "테스트", "김영웅", "0204" };
		model.addRow(arr);
		notice.setVisible(true);
	}

}
