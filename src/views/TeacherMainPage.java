package views;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import models.dto.UserDTO;

public class TeacherMainPage extends JFrame {

	private JPanel classManage, registration, calendar, studentAttendance, studentManage, importantNotice;
	private JPanel panel1, panel2, panel3;
	private UserDTO user;
	private JFrame main = this;

	public TeacherMainPage(UserDTO user) {
		this.user = user;
		this.setSize(1800, 1400);
		this.setResizable(false);
		this.setTitle("강사 메인 페이지");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setLayout(new GridLayout(1, 3));

		panel1 = new JPanel();
		panel2 = new JPanel();
		panel3 = new JPanel();

		panel1.setLayout(new GridLayout(2, 1));
		
		panel2.setLayout(new GridLayout(2, 1));
		
		panel3.setLayout(new GridLayout(2, 1));


		panel1.add(getStudentManage());
		panel1.add(getclassManage());

		panel2.add(getAttendanceManage());
		panel2.add(getRegistration());

		panel3.add(getCalendar());
		panel3.add(getImprotantNotice());

		this.add(panel1);
		this.add(panel2);
		this.add(panel3);
	}

	public JPanel getStudentManage() {
		if (studentManage == null) {

			studentManage = new JPanel();
			studentManage.add(new StudentManage(user));
		}
		return studentManage;
	}

	public JPanel getAttendanceManage() {
		if (studentAttendance == null) {

			studentAttendance = new JPanel();
			studentAttendance.add(new StudentAttendanceManagement(user));
		}
		return studentAttendance;
	}

	public JPanel getCalendar() {
		if (calendar == null) {
			calendar = new JPanel();
			calendar.add(new Calendars(main));

		}
		return calendar;
	}

	public JPanel getclassManage() {
		if (classManage == null) {

			classManage = new JPanel();
			classManage.add(new ClassManage(user));

		}
		return classManage;
	}

	public JPanel getRegistration() {
		if (registration == null) {

			registration = new JPanel();
			registration.add(new RegistrationApprovalPanel());

		}
		return registration;
	}

	public JPanel getImprotantNotice() {
		if (importantNotice == null) {
			importantNotice = new JPanel();
			importantNotice.add(NoticeFactory.createNoticePanel(user, main));
		}
		return importantNotice;
	}

}
