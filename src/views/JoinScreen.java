package views;

import exception.InvalidIdPasswordExecption;
import exception.MisMatchTypeExecption;
import models.dao.ClassDAO;
import models.dao.ClassDAO;
import models.service.UserService;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class JoinScreen extends JFrame {
	String choice = null;
	private JTextField id;
	private JPasswordField password;
	private JPasswordField checkPassword;
	private JTextField userName;
	private JComboBox<String> className;
	private String inId = null, inPassword = null, inPassword2 = null, inUserName = null, inClassName = null;
	private UserService joinService = new UserService();
	private final ClassDAO classDAO = new ClassDAO();

	public JoinScreen() {
		initializeUI();
	}

	private JPanel createJoinPanel() {
		setTitle("회원가입 화면");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);

		JPanel signUpPanel = new JPanel(new BorderLayout());
		JLabel title = new JLabel("회원가입", JLabel.CENTER);
		title.setForeground(new Color(5, 0, 153));
		title.setFont(new Font("맑은 고딕", Font.BOLD, 25));
		signUpPanel.add(title, BorderLayout.NORTH);

		id = new JTextField(11);
		password = new JPasswordField(11);
		checkPassword = new JPasswordField(11);
		userName = new JTextField(11);
		className = new JComboBox<String>();
		ArrayList<String> list = classDAO.fetchAllClasses();
		for (String string : list) {
			className.addItem(string);
		}
		JPanel formPanel = new JPanel(new GridLayout(5, 1, 10, 10));
		formPanel.add(createInputPanel("아이디 :", id));
		formPanel.add(createInputPanel("비밀번호 :", password));
		formPanel.add(createInputPanel("비밀번호 확인:", checkPassword));
		formPanel.add(createInputPanel("이름 :", userName));
		formPanel.add(createInputPanel("반 :", className));
		signUpPanel.add(formPanel, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JButton join = new JButton("회원가입");
		join.setBorder(new RoundedBorder(10));
		join.setForeground(Color.WHITE);
		join.setBackground(Color.DARK_GRAY);
		JButton cancel = new JButton("취소");
		cancel.setBorder(new RoundedBorder(10));
		cancel.setBackground(Color.DARK_GRAY);
		cancel.setForeground(Color.WHITE);
		buttonPanel.add(join);
		buttonPanel.add(cancel);
		signUpPanel.add(buttonPanel, BorderLayout.SOUTH);

		join.addActionListener(e -> handleJoin());
		cancel.addActionListener((e) -> {
			LoginScreen screen = new LoginScreen();
			screen.setVisible(true);
			dispose();
		});
		return signUpPanel;
	}

	private void initializeUI() {
		setSize(260, 400);
		getContentPane().add(createJoinPanel(), BorderLayout.CENTER);
		locationCenter();
	}

	private JPanel createInputPanel(String labelText, JComponent inputField) {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panel.add(new JLabel(labelText));
		panel.add(inputField);
		return panel;
	}

	private void handleJoin() {
		inId = id.getText();
		inPassword = String.valueOf(password.getPassword());
		inPassword2 = String.valueOf(checkPassword.getPassword());
		inUserName = userName.getText();
		inClassName = (String) className.getSelectedItem();
		try {
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
