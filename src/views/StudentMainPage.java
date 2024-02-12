package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import models.dto.UserDTO;

public class StudentMainPage extends JFrame {
	private JPanel info, currentAttendance, monthLog, calendar, importantNotice, codeHows;
	private StudentMainPage page = this;
	private JFrame main = this;
	private UserDTO user;
	private Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	
	public StudentMainPage(UserDTO user) {
		this.user = user;
		this.setSize(screen);
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLayout(new GridLayout(1, 3));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel jPanel = new JPanel(new GridLayout(2, 1));
		JPanel jPanel2 = new JPanel(new GridLayout(2, 1));
		JPanel jPanel3 = new JPanel(new FlowLayout());
		setBackground(Color.white);

		jPanel2.add(getInfo());
		jPanel2.add(getCurrentAttendance());

		jPanel3.add(getCodehows());

		jPanel.add(getcalendar());
		jPanel.add(getImprotantNotice());

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
			currentAttendance.add(new AttendStatus(user));
		}
		return currentAttendance;
	}

	public JPanel getcalendar() {
		if (calendar == null) {
			calendar = new JPanel();
			calendar.add(new Calendars(main, user));
		}
		return calendar;
	}

	public JPanel getImprotantNotice() {
		if (importantNotice == null) {
			importantNotice = new JPanel();
			Notice notice = new Notice(user);
			importantNotice.add(notice.getNotice(1));
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

}
