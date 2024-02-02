package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class JoinScreen extends JFrame {
	String choice = null;
	JPanel idPanel;
	private JoinScreen joinScreen = this;

	public JPanel getIdPanel() {
		if (idPanel == null) {
			JTextField id = new JTextField(11);
			JPanel idPanel = new JPanel();
			idPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
			idPanel.add(new JLabel("아이디 : "));
			idPanel.add(id);
		}
		return idPanel;
	}

	public JoinScreen() {

		setTitle("회원가입 화면");

		// 1. 컴포넌트들을 만들어 보자.
		JLabel title = new JLabel("회원가입", JLabel.CENTER);

		title.setForeground(new Color(5, 0, 153));
		title.setFont(new Font("휴먼편지체", Font.BOLD, 30));

		JButton join = new JButton("회원가입");
		JButton cancel = new JButton("취소");

		
		JPasswordField pwd = new JPasswordField(11);
		JPasswordField pwd2 = new JPasswordField(11);
		JTextField name = new JTextField(11);
		JTextField studentClass = new JTextField(11);

		JRadioButton client = new JRadioButton("학생");
		JRadioButton manager = new JRadioButton("강사");

		manager.addActionListener((e) -> {
			if (manager.isSelected()) {
				System.out.println("매니저 선택됨");
				joinScreen.setVisible(false);
				JoinScreen joinScreen = new JoinScreen();
				joinScreen.setVisible(true);
			}
		});

		ButtonGroup bg = new ButtonGroup();
		bg.add(client);
		bg.add(manager);

		// radio panel
		JPanel radioPanel = new JPanel();
		radioPanel.add(client);
		radioPanel.add(manager);

		// form panel

		JPanel pwdPanel = new JPanel();
		pwdPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		pwdPanel.add(new JLabel("비밀번호 : "));
		pwdPanel.add(pwd);

		JPanel pwdPanel2 = new JPanel();
		pwdPanel2.setLayout(new FlowLayout(FlowLayout.RIGHT));
		pwdPanel2.add(new JLabel("비밀번호 확인: "));
		pwdPanel2.add(pwd2);

		JPanel namePanel = new JPanel();
		namePanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		namePanel.add(new JLabel("이름 : "));
		namePanel.add(name);

		JPanel classPanel = new JPanel();
		classPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		classPanel.add(new JLabel("반 : "));
		classPanel.add(studentClass);

		JPanel formPanel = new JPanel();
		formPanel.setLayout(new GridLayout(5, 1));
		formPanel.add(idPanel);
		formPanel.add(pwdPanel);
		formPanel.add(pwdPanel2);
		formPanel.add(namePanel);
		formPanel.add(classPanel);

		// radio + form panel
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new FlowLayout());
		contentPanel.add(radioPanel);
		contentPanel.add(formPanel);

		// button panel
		JPanel panel = new JPanel();
		panel.add(join);
		panel.add(cancel);

		add(title, BorderLayout.NORTH);
		add(contentPanel, BorderLayout.CENTER);
		add(panel, BorderLayout.SOUTH);

//		setBounds(200, 200, 250, 300);
		setSize(250, 300);
		locationCenter();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);

		// 이벤트 처리
		join.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String myId = id.getText();
				String myPwd = new String(pwd.getPassword());
				String myName = name.getText();
				String myClass = studentClass.getText();

				JOptionPane.showMessageDialog(null, "아이디 : " + myId + ", 비밀번호 : " + myPwd + ", 이 름 : " + myName
						+ ", 반 : " + myClass + ", 가입유형 : " + choice);
			}
		});

		// 취소 버튼을 클릭했을 때 이벤트 처리
		cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				new LoginScreen();
				dispose();

			}
		});

	}

	private void locationCenter() {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point centerPoint = ge.getCenterPoint();
		int leftTopX = centerPoint.x - this.getWidth() / 2;
		int leftTopY = centerPoint.y - this.getHeight() / 2;
		this.setLocation(leftTopX, leftTopY);
	}

}
