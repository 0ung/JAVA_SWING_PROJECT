package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import models.dto.UserDTO;
import views.cal.Calendars;
import views.student.CodeHows;
import views.student.Information;

public class StudentMainPage extends JFrame {
	private JPanel info, currentAttendance, monthLog, calendar, importantNotice, codeHows;
	private UserDTO user;

	public StudentMainPage(UserDTO user) {
		this.user = user;
		this.setSize(new Dimension(1920, 1800));
		this.setResizable(false);
		this.setLayout(new GridLayout(1, 3));
		JPanel jPanel = new JPanel(new GridLayout(2, 1));
		JPanel jPanel2 = new JPanel(new GridLayout(2, 1));
		JPanel jPanel3 = new JPanel(new GridLayout(1, 1));
		setBackground(Color.white);

		jPanel2.add(getInfo());
		jPanel2.add(getCurrentAttendance());

		jPanel3.add(getCodehows());

		jPanel.add(getcalendar());
		jPanel.add(getImportNotice());

		this.add(jPanel2);
		this.add(jPanel3);
		this.add(jPanel);

	}

	public JPanel getInfo() {
		if (info == null) {
			info = new JPanel();
			info.add(new Information(user));
		}
		return info;
	}

	public JPanel getCurrentAttendance() {
		if (currentAttendance == null) {
			currentAttendance = new JPanel();
//			currentAttendance.add(new AttendStatus(user));
		}
		return currentAttendance;
	}

	public JPanel getcalendar() {
		if (calendar == null) {
			calendar = new JPanel();
			Calendars calendars = new Calendars();
			calendar.add(calendars.getCalendars());
		}
		return calendar;
	}

	public JPanel getImportNotice() {
		if (importantNotice == null) {
			importantNotice = new JPanel();
			Calendars calendars = new Calendars();
			importantNotice.add(calendars.getNotice(user));
		}
		return importantNotice;
	}

	public JPanel getCodehows() {
		if (codeHows == null) {
			codeHows = new JPanel();
			codeHows.add(new CodeHows(user));
		}
		return codeHows;
	}

	public static void main(String[] args) {
		UserDTO dto = new UserDTO();
		dto.setUserId("01075743839");
		StudentMainPage mainPage = new StudentMainPage(dto);
		mainPage.setVisible(true);
	}

}
