package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import models.dto.UserDTO;

public class TeacherMainPage extends JFrame {

	private JPanel ptitle, ptitle2, ptitle3, ptitle4, ptitle5, classManage, registration, calendar, studentAttendance,
			studentManage, importantNotice;
	private JPanel panel1, panel2, panel3;
	private JLabel titleLabel, titleLabel2, titleLabel3, titleLabel4, titleLabel5;
	private UserDTO user;
	private JFrame main = this;
	private Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

	public TeacherMainPage(UserDTO user) {
		this.user = user;
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		this.setSize(screen);
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

	public JPanel getTitleLabel() {
		if (ptitle == null) {
			ptitle = new JPanel(new FlowLayout());

			titleLabel = new JLabel();
			titleLabel.setText(user.getClassName() + "의 학생 정보");
			titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 20));
			ptitle.add(titleLabel);

		}
		return ptitle;
	}

	public JPanel getTitleLabel2() {
		if (ptitle2 == null) {
			ptitle2 = new JPanel(new FlowLayout());

			titleLabel2 = new JLabel();
			titleLabel2.setText(user.getClassName() + "의 출결관리");
			titleLabel2.setFont(new Font("맑은 고딕", Font.BOLD, 20));
			ptitle2.add(titleLabel2);

		}
		return ptitle2;
	}

	public JPanel getTitleLabel3() {
		if (ptitle3 == null) {
			ptitle3 = new JPanel(new FlowLayout());

			titleLabel3 = new JLabel();
			titleLabel3.setText("로그인 권한 승인");
			titleLabel3.setFont(new Font("맑은 고딕", Font.BOLD, 20));
			ptitle3.add(titleLabel3);

		}
		return ptitle3;
	}

	public JPanel getTitleLabel4() {
		if (ptitle4 == null) {
			ptitle4 = new JPanel(new FlowLayout());

			titleLabel4 = new JLabel();
			titleLabel4.setText("반 관리");
			titleLabel4.setFont(new Font("맑은 고딕", Font.BOLD, 20));
			ptitle4.add(titleLabel4);

		}
		return ptitle4;
	}

	public JPanel getTitleLabel5() {
		if (ptitle5 == null) {
			ptitle5 = new JPanel(new FlowLayout());
			titleLabel5 = new JLabel();
			titleLabel5.setText("중요 공지사항");
			titleLabel5.setFont(new Font("맑은 고딕", Font.BOLD, 20));
			ptitle5.add(titleLabel5);

		}
		return ptitle5;
	}

	public JPanel getStudentManage() {
		if (studentManage == null) {

			studentManage = new JPanel();
			CodeHows codeHows = new CodeHows(user);
			studentManage.add(getTitleLabel());
			studentManage.add(codeHows.getStudentMange());

		}
		return studentManage;
	}

	public JPanel getAttendanceManage() {
		if (studentAttendance == null) {

			studentAttendance = new JPanel();
			studentAttendance.add(getTitleLabel2());
			studentAttendance.add(new StudentAttendanceManagement(user));
		}
		return studentAttendance;
	}

	public JPanel getCalendar() {
		if (calendar == null) {
			calendar = new JPanel();
			calendar.add(new Calendars(main, user));

		}
		return calendar;
	}

	public JPanel getclassManage() {
		if (classManage == null) {

			classManage = new JPanel(new BorderLayout());
			classManage.add(getTitleLabel4(), BorderLayout.NORTH);
			classManage.add(new ClassManage(user), BorderLayout.SOUTH);

		}
		return classManage;
	}

	public JPanel getRegistration() {
		if (registration == null) {

			registration = new JPanel();
			registration.add(getTitleLabel3());
			registration.add(new RegistrationApprovalPanel());

		}
		return registration;
	}

	public JPanel getImprotantNotice() {
		if (importantNotice == null) {
			importantNotice = new JPanel(new BorderLayout());
			Notice notice = new Notice(user);
			importantNotice.add(getTitleLabel5(), BorderLayout.NORTH);
			importantNotice.add(notice.getNotice(1), BorderLayout.SOUTH);
		}
		return importantNotice;
	}

}
