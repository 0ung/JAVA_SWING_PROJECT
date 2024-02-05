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

public class JoinScreen extends JFrame {
	String choice = null;

	public JoinScreen() {
		initializeUI();
	}

	private JPanel jPanel() {
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

		// 폼 패널
		JPanel formPanel = new JPanel(new GridLayout(5, 1, 10, 10));
		formPanel.add(createInputPanel("아이디 :", new JTextField(11)));
		formPanel.add(createInputPanel("비밀번호 :", new JPasswordField(11)));
		formPanel.add(createInputPanel("비밀번호 확인:", new JPasswordField(11)));
		formPanel.add(createInputPanel("이름 :", new JTextField(11)));
		formPanel.add(createInputPanel("반 :", new JTextField(11)));
		signUpPanel.add(formPanel, BorderLayout.CENTER);

		// 버튼 패널
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JButton join = new JButton("회원가입");
		JButton cancel = new JButton("취소");
		buttonPanel.add(join);
		buttonPanel.add(cancel);
		signUpPanel.add(buttonPanel, BorderLayout.SOUTH);

		// 이벤트 처리
		join.addActionListener(e -> handleJoin());
		cancel.addActionListener(e -> dispose());
		return signUpPanel;
	}

	private JPanel jPanel2() {
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

		// 폼 패널
		JPanel formPanel = new JPanel(new GridLayout(6, 1, 10, 10));
		formPanel.add(createInputPanel("아이디 :", new JTextField(11)));
		formPanel.add(createInputPanel("비밀번호 :", new JPasswordField(11)));
		formPanel.add(createInputPanel("비밀번호 확인:", new JPasswordField(11)));
		formPanel.add(createInputPanel("이름 :", new JTextField(11)));
		formPanel.add(createInputPanel("반 :", new JTextField(11)));
		formPanel.add(createInputPanel("강사코드 :", new JTextField(11)));
		signUpPanel.add(formPanel, BorderLayout.CENTER);

		// 버튼 패널
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JButton join = new JButton("회원가입");
		JButton cancel = new JButton("취소");
		buttonPanel.add(join);
		buttonPanel.add(cancel);
		signUpPanel.add(buttonPanel, BorderLayout.SOUTH);

		// 이벤트 처리
		join.addActionListener(e -> handleJoin());
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
		tabbedPane.addTab("학생", jPanel());
		tabbedPane.addTab("강사", jPanel2());
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

	private void handleJoin() {
		// 회원가입 처리 로직
		JOptionPane.showMessageDialog(this, "회원가입 처리");
	}

	private void locationCenter() {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point centerPoint = ge.getCenterPoint();
		int leftTopX = centerPoint.x - this.getWidth() / 2;
		int leftTopY = centerPoint.y - this.getHeight() / 2;
		this.setLocation(leftTopX, leftTopY);
	}

}