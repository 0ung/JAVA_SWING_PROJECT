package views;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import exception.InvalidIdPasswordExecption;
import models.service.UserService;

public class LoginScreen extends JFrame {
	String choice = null;
	private UserService userService = new UserService();
	private LoginScreen loginScreen = this;

	public LoginScreen() {
		setTitle("로그인 ");

		JPanel title = new JPanel();

		// title 컨테이너에 들어갈 컴포넌트를 만들어 보자.
		JLabel login = new JLabel("로그인 화면");

		login.setForeground(new Color(5, 0, 153));
		login.setFont(new Font("맑은 고딕", Font.BOLD, 25));

		title.add(login);

		JPanel jp1 = new JPanel();
		jp1.setLayout(new GridLayout(3, 2));

		JPanel idPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JLabel jlb1 = new JLabel("아이디 : ", JLabel.CENTER);

		idPanel.add(jlb1);

		JPanel idPanel2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JTextField jtf1 = new JTextField(10);

		idPanel2.add(jtf1);

		jp1.add(idPanel);
		jp1.add(idPanel2);

		JPanel pwdPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JLabel jlb2 = new JLabel("비밀번호 : ", JLabel.CENTER);

		JPanel pwdPanel2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPasswordField jtf2 = new JPasswordField(10);

		pwdPanel.add(jlb2);
		pwdPanel2.add(jtf2);

		jp1.add(pwdPanel);
		jp1.add(pwdPanel2);

		JPanel loginPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton jLogin = new JButton("로그인");

		JPanel joinPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JButton join = new JButton("회원가입");

		loginPanel.add(jLogin);
		joinPanel.add(join);

		jp1.add(loginPanel);
		jp1.add(joinPanel);

		JPanel jp2 = new JPanel();
		jp2.setLayout(new FlowLayout());
		jp2.add(jp1);

		setLayout(new BorderLayout());

		add(title, BorderLayout.NORTH);
		add(jp2, BorderLayout.CENTER);

		setSize(300, 250);
		locationCenter();

		setResizable(false); // 화면 크기 고정하는 작업

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setVisible(true);

		// 이벤트 처리
		jLogin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String myId = jtf1.getText();
				String myPwd = new String(jtf2.getPassword());
				try {
					userService.validationIdPassword(myId, myPwd);
					switch (userService.getAuth(myId)) {
					case 0:
						JOptionPane.showMessageDialog(jp2, "회원가입이 승인되지 않았습니다. 강사에게 문의바랍니다.");
						break;
					case 1:
						StudentMainPage studentMainPage = new StudentMainPage(userService.getUser(myId));
						studentMainPage.setVisible(true);
						loginScreen.dispose();
						break;
					case 2:
						TeacherMainPage teacherMainPage = new TeacherMainPage(userService.getUser(myId));
						teacherMainPage.setVisible(true);
						loginScreen.dispose();
						break;
					default:
						JOptionPane.showMessageDialog(jp2, "잘못된 입력입니다.");
						break;
					}
				} catch (NullPointerException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(jp2, e1.getCause());
				} catch (InvalidIdPasswordExecption e1) {
					JOptionPane.showMessageDialog(jp2, "아이디 or 비밀번호가 일치하지 않습니다.");
				}
			}
		});

		join.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				JoinScreen j = new JoinScreen();
				j.setVisible(true);
				dispose(); // 현재의 frame을 종료시키는 메서드.

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

	public static void main(String[] args) {
		LoginScreen loginScreen = new LoginScreen();
		loginScreen.setVisible(true);
	}
}
