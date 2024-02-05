package views;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class TeacherMainPage extends JFrame{
	
	private JPanel classManage, registration,calendar, studentAttendance, studentManage, importNotice;
	private JPanel panel1, panel2, panel3;
	
	public TeacherMainPage() {
		
		this.setSize(1800,1400);
		this.setResizable(false);
		this.setTitle("강사 메인 페이지");
		
		this.setLayout(new GridLayout(1,3,0,10));
		
		
		panel1 = new JPanel();
		panel2 = new JPanel();
		panel3 = new JPanel();
		
		panel1.setLayout(new GridLayout(2,1));
		panel1.setBorder(BorderFactory.createEtchedBorder(Color.LIGHT_GRAY,Color.LIGHT_GRAY));
		panel2.setLayout(new GridLayout(2,1));
		panel2.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		panel3.setLayout(new GridLayout(2,1));
		panel3.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		
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
		if(studentManage==null) {
			
			studentManage = new JPanel();
			studentManage.add(new StudentManage());
		}
		return studentManage;
	}
	
	public JPanel getAttendanceManage() {
		if(studentAttendance==null) {
			
			studentAttendance = new JPanel();
			studentAttendance.add(new StudentAttendanceManagement());
		}
		return studentAttendance;
	}
	
	
	
	public JPanel getCalendar() {
		if(calendar == null) {
			
			calendar = new JPanel();
			
		}
		return calendar;
	}
	
	public JPanel getclassManage() {
		if(classManage == null) {
			
			classManage = new JPanel();
			classManage.add(new ClassManage());
			
		}
		return classManage;
	}
	
	public JPanel getRegistration() {
		if(registration == null ) {
			
			registration = new JPanel();
			registration.add(new RegistrationApprovalPanel());
			
		}
		return registration;
	}
	
	public JPanel getImprotantNotice() {
		if(importNotice==null) {
			importNotice = new JPanel();
		}
		return importNotice;
	}
	
	
	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				TeacherMainPage tMainP = new TeacherMainPage();
				tMainP.setVisible(true);
			}
		});

	}

}
