package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import constant.Member;
import exception.InvalidIdPasswordExecption;
import exception.MisMatchTypeExecption;
import models.service.UserService;

public class JoinScreen extends JFrame {
	String choice = null;
	private JTextField id;
	private JPasswordField password;
	private JPasswordField checkPassword;
	private JTextField userName;
	private JTextField className;
	private JTextField id1;
	private JPasswordField password1;
	private JPasswordField checkPassword1;
	private JTextField userName1;
	private JTextField className1;
	private String inId = null, inPassword = null, inPassword2 = null, inUserName = null, inClassName = null;
	private UserService joinService = new UserService();

	public JoinScreen() {
		initializeUI();
	}

	private JPanel createStudentPanel() {
		setTitle("회원가입 화면");
		// setSize(400, 800);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);

		// 탭에 추가될 패널을 생성
		JPanel signUpPanel = new JPanel(new BorderLayout());
		JLabel title = new JLabel("회원가입", JLabel.CENTER);
		title.setForeground(new Color(5, 0, 153));
		title.setFont(new Font("휴먼편지체", Font.BOLD, 30));
		signUpPanel.add(title, BorderLayout.NORTH);

		id = new JTextField(11);
		password = new JPasswordField(11);
		checkPassword = new JPasswordField(11);
		userName = new JTextField(11);
		className = new JTextField(11);
		// 폼 패널
		JPanel formPanel = new JPanel(new GridLayout(5, 1, 10, 10));
		formPanel.add(createInputPanel("아이디 :", id));
		formPanel.add(createInputPanel("비밀번호 :", password));
		formPanel.add(createInputPanel("비밀번호 확인:", checkPassword));
		formPanel.add(createInputPanel("이름 :", userName));
		formPanel.add(createInputPanel("반 :", className));
		signUpPanel.add(formPanel, BorderLayout.CENTER);

		// 버튼 패널
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JButton join = new JButton("회원가입");
		JButton cancel = new JButton("취소");
		buttonPanel.add(join);
		buttonPanel.add(cancel);
		signUpPanel.add(buttonPanel, BorderLayout.SOUTH);

		// 이벤트 처리
		join.addActionListener(e -> handleJoin(Member.Student));
		cancel.addActionListener(e -> dispose());
		return signUpPanel;
	}

	private JPanel createTeacherPanel() {
		setTitle("회원가입 화면");
		// setSize(400, 800);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);

		// 탭에 추가될 패널을 생성
		JPanel signUpPanel = new JPanel(new BorderLayout());
		JLabel title = new JLabel("회원가입", JLabel.CENTER);
		title.setForeground(new Color(5, 0, 153));
		title.setFont(new Font("휴먼편지체", Font.BOLD, 30));
		signUpPanel.add(title, BorderLayout.NORTH);
		id1 = new JTextField(11);
		password1 = new JPasswordField(11);
		checkPassword1 = new JPasswordField(11);
		userName1 = new JTextField(11);
		className1 = new JTextField(11);
		// 폼 패널
		JPanel formPanel = new JPanel(new GridLayout(5, 1, 10, 10));
		formPanel.add(createInputPanel("아이디 :", id1));
		formPanel.add(createInputPanel("비밀번호 :", password1));
		formPanel.add(createInputPanel("비밀번호 확인:", checkPassword1));
		formPanel.add(createInputPanel("이름 :", userName1));
		formPanel.add(createInputPanel("반 :", className1));
		signUpPanel.add(formPanel, BorderLayout.CENTER);

		// 버튼 패널
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JButton join = new JButton("회원가입");
		JButton cancel = new JButton("취소");
		buttonPanel.add(join);
		buttonPanel.add(cancel);
		signUpPanel.add(buttonPanel, BorderLayout.SOUTH);

		// 이벤트 처리
		join.addActionListener(e -> handleJoin(Member.Teacher));
		cancel.addActionListener(e -> dispose());
		return signUpPanel;
	}

	private void initializeUI() {
		setSize(260, 400);
		// 다른 탭들도 여기에 추가할 수 있습니다.
		// 예: tabbedPane.addTab("다른 탭", new JPanel());
		// JTabbedPane 생성 및 초기화
		JTabbedPane tabbedPane = new JTabbedPane();
		// JTabbedPane를 JFrame의 CENTER에 추가
		// 탭 추가
		tabbedPane.addTab("학생", createStudentPanel());
		tabbedPane.addTab("강사", createTeacherPanel());
		getContentPane().add(tabbedPane, BorderLayout.CENTER);
		locationCenter();
		// pack();
	}

	private JPanel createInputPanel(String labelText, JComponent inputField) {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panel.add(new JLabel(labelText));
		panel.add(inputField);
		return panel;
	}

	private void handleJoin(Member member) {
		switch (member) {
		case Teacher:
			inId = id1.getText();
			inPassword = String.valueOf(password1.getPassword());
			inPassword2 = String.valueOf(checkPassword1.getPassword());
			inUserName = userName1.getText();
			inClassName = className1.getText();
			break;
		case Student:
			inId = id.getText();
			inPassword = String.valueOf(password.getPassword());
			inPassword2 = String.valueOf(checkPassword.getPassword());
			inUserName = userName.getText();
			inClassName = className.getText();
			break;
		}

		try {
			System.out.println(inId + inClassName);
			joinService.validationId(inId);
			joinService.validationPassword(inPassword, inPassword2);
			joinService.validationUserName(inUserName);
			joinService.join(inId, inPassword, inUserName, inClassName);
			JOptionPane.showMessageDialog(this, "가입이 완료되었습니다.");
			LoginScreen loginScreen = new LoginScreen();
			this.dispose();
			loginScreen.setVisible(true);
		} catch (NullPointerException | MisMatchTypeExecption | InvalidIdPasswordExecption e) {
			JOptionPane.showMessageDialog(checkPassword, e.getMessage());
		}

	}

	private void locationCenter() {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point centerPoint = ge.getCenterPoint();
		int leftTopX = centerPoint.x - this.getWidth() / 2;
		int leftTopY = centerPoint.y - this.getHeight() / 2;
		this.setLocation(leftTopX, leftTopY);
	}

}