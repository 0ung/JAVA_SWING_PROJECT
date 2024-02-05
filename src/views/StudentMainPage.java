package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class StudentMainPage extends JFrame {
	private JPanel info, currentAttendance, monthLog, calnedar, importantNotice, codeHows;
	private StudentMainPage page = this;
	private JFrame main = this;

	public StudentMainPage() {
		this.setSize(new Dimension(1920, 1800));
		this.setResizable(false);
		this.setLayout(new GridLayout(1, 3));
		JPanel jPanel = new JPanel(new GridLayout(2, 1));
		JPanel jPanel3 = new JPanel(new GridLayout(2, 1));
		JPanel jPanel2 = new JPanel(new GridLayout(2, 1));
		setBackground(Color.white);

		jPanel2.add(getInfo());
		jPanel2.add(getCurrentAttendance());

		jPanel3.add(getCodehows());
		jPanel3.add(getMonthLog());

		jPanel.add(getCalnedar());
		jPanel.add(getImportNotice());

		this.add(jPanel2);
		this.add(jPanel3);
		this.add(jPanel);

	}

	public JPanel getInfo() {
		if (info == null) {
			info = new JPanel();
			info.add(new Information());
		}
		return info;
	}

	public JPanel getCurrentAttendance() {
		if (currentAttendance == null) {
			currentAttendance = new JPanel();
			currentAttendance.add(new AttendStatus());
		}
		return currentAttendance;
	}

	public JPanel getMonthLog() {
		if (monthLog == null) {
			monthLog = new JPanel();
			monthLog.add(new MonthlyAttendanceLog());
		}
		return monthLog;
	}

	public JPanel getCalnedar() {
		if (calnedar == null) {
			calnedar = new JPanel();
			calnedar.add(new Calendars(main));
		}
		return calnedar;
	}

	public JPanel getImportNotice() {
		if (importantNotice == null) {
			importantNotice = new JPanel();
			importantNotice.add(NoticeFactory.createNoticePanel());
		}
		return importantNotice;
	}

	public JPanel getCodehows() {
		if (codeHows == null) {
			codeHows = new JPanel();
			codeHows.add(new CodeHows());
		}
		return codeHows;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			StudentMainPage mainPage = new StudentMainPage();
			mainPage.setVisible(true);
		});
	}
}