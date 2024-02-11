package views;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import models.dto.UserDTO;
import models.dto.UserInfoDTO;
import models.service.UserService;

public class Information extends JPanel {
	private JPanel pClassName, pUserName, pTeacherName, pRoomNum, pProgress;
	private JLabel className, userName, teacherName, roomNum, progress, titleLabel;
	private JTextField txtClassName, txtUserName, txtTeacherName, txtRoomNum, txtProgress;
	private UserDTO user;
	private UserInfoDTO info;
	private UserService service = new UserService();
	private Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	private int width = (int) screen.getWidth() / 3;
	private int height = (int) screen.getHeight() / 2;

	public Information(UserDTO user) {
		this.user = user;
		this.info = service.getInfo(user.getUserId());
		this.setPreferredSize(new Dimension(width, height));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // 세로로 정렬
		this.add(getPuserName());
		this.add(getPclassName());
		this.add(getPprogress());
		this.add(getProomNum());
		this.add(getPteacherName());

	}

	public JPanel getPuserName() {
		if (pUserName == null) {
			pUserName = new JPanel();

			userName = new JLabel();
			userName.setText("이름");
			userName.setFont(new Font("맑은 고딕", Font.BOLD, 15));
			userName.setPreferredSize(new Dimension(100, (int) (height / 5.5)));
			userName.setHorizontalAlignment(JLabel.CENTER);
			txtUserName = new JTextField(user.getUserName());
			txtUserName.setPreferredSize(new Dimension(280, 35));
			txtUserName.setEditable(false);
			pUserName.add(userName);
			pUserName.add(txtUserName);

		}
		return pUserName;
	}

	public JPanel getPclassName() {
		if (pClassName == null) {
			pClassName = new JPanel();

			className = new JLabel();
			className.setText("반");
			className.setFont(new Font("맑은 고딕", Font.BOLD, 15));
			className.setPreferredSize(new Dimension(100, (int) (height / 5.5)));
			className.setHorizontalAlignment(JLabel.CENTER);
			txtClassName = new JTextField(info.getClassName());
			txtClassName.setPreferredSize(new Dimension(280, 35));
			txtClassName.setEditable(false);
			pClassName.add(className);
			pClassName.add(txtClassName);

		}
		return pClassName;
	}

	public JPanel getPprogress() {
		if (pProgress == null) {
			pProgress = new JPanel();
			progress = new JLabel();
			progress.setText("반진도");
			progress.setFont(new Font("맑은 고딕", Font.BOLD, 15));
			progress.setPreferredSize(new Dimension(100, (int) (height / 5.5)));
			progress.setHorizontalAlignment(JLabel.CENTER);
			txtProgress = new JTextField(info.getProgress());
			txtProgress.setPreferredSize(new Dimension(280, 35));
			txtProgress.setEditable(false);
			pProgress.add(progress);
			pProgress.add(txtProgress);

		}
		return pProgress;
	}

	public JPanel getProomNum() {
		if (pRoomNum == null) {
			pRoomNum = new JPanel();
			roomNum = new JLabel();
			roomNum.setText("반호실");
			roomNum.setFont(new Font("맑은 고딕", Font.BOLD, 15));
			roomNum.setPreferredSize(new Dimension(100, (int) (height / 5.5)));
			roomNum.setHorizontalAlignment(JLabel.CENTER);
			txtRoomNum = new JTextField(info.getRoomNum());
			txtRoomNum.setPreferredSize(new Dimension(280, 35));
			txtRoomNum.setEditable(false);
			pRoomNum.add(roomNum);
			pRoomNum.add(txtRoomNum);

		}
		return pRoomNum;
	}

	public JPanel getPteacherName() {
		if (pTeacherName == null) {
			pTeacherName = new JPanel();
			teacherName = new JLabel();
			teacherName.setText("강사이름");
			teacherName.setFont(new Font("맑은 고딕", Font.BOLD, 15));
			teacherName.setPreferredSize(new Dimension(100, (int) (height / 5.5)));
			teacherName.setHorizontalAlignment(JLabel.CENTER);
			txtTeacherName = new JTextField(info.getTeacherName());
			txtTeacherName.setPreferredSize(new Dimension(280, 35));
			txtTeacherName.setEditable(false);
			pTeacherName.add(teacherName);
			pTeacherName.add(txtTeacherName);

		}
		return pTeacherName;
	}

}
