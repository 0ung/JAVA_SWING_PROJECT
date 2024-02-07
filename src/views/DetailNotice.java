package views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import models.dao.NoticeDAOImpl;
import models.dto.NoticeDto;
import models.dto.UserDTO;

public class DetailNotice extends JDialog {
	private JPanel titlePanel, createTimePanel, writerPanel;
	private JLabel titleLabel, createTimeLabel, writerLabel, statusLabel;
	private JTextArea contentTextArea;
	private JTextField titleTextField, createTimeTextField, writerTextField;
	private JComboBox<String> statusComboBox; // 상태를 선택하기 위한 콤보박스
	private UserDTO user;
	private JButton jButton;
	private NoticeDto noticeDto;

	public DetailNotice(JFrame jframe, UserDTO user) {
		super(jframe, true);
		this.user = user;

		initializeUI();
	}

	private void initializeUI() {

		setSize(new Dimension(700, 800));
		setLayout(new BorderLayout(5, 5));
		setAlwaysOnTop(true);
		add(getTitlePanel(), BorderLayout.NORTH);
		add(getContentPanel(), BorderLayout.CENTER);
		add(getInfoPanel(), BorderLayout.SOUTH);

		// Set the dialog properties
		setTitle("상세보기");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null); // Center on screen
		writerTextField.setText(user.getUserName());
	}

	public void setFieldsEditable(boolean editable) {
		titleTextField.setEditable(editable);
		createTimeTextField.setEditable(editable);
		writerTextField.setEditable(editable);
		contentTextArea.setEditable(editable);
	}

	private JPanel getTitlePanel() {
		if (titlePanel == null) {
			titlePanel = new JPanel();
			titleLabel = new JLabel("제목:");
			titleTextField = new JTextField(20);
			titleTextField.setEditable(false); // Make it non-editable
			titlePanel.add(titleLabel);
			titlePanel.add(titleTextField);

			// 콤보 박스
			statusLabel = new JLabel("선택:");
			statusComboBox = new JComboBox<>(new String[] { "중요한 게시물", "켈린더 게시물" }); // 콤보박스 항목 초기화
			titlePanel.add(statusLabel);
			titlePanel.add(statusComboBox);

			// 저장 버튼
			jButton = new JButton();
			jButton.setText("저장");
			titlePanel.add(jButton);
			jButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// 저장 버튼 클릭시
					System.out.println(statusComboBox.getSelectedItem());

					noticeDto = new NoticeDto();
					// 사용자 입력값을 NoticeDto 객체에 설정
					noticeDto.setUserId(user.getUserId());
					noticeDto.setTitle(titleTextField.getText());
					noticeDto.setContent(contentTextArea.getText());
					// 선택된 콤보박스 항목에 따라 important 값 설정
					noticeDto.setImportant(statusComboBox.getSelectedIndex() == 0);
					// 예를 들어 "중요한 게시물"이 첫 번째 항목일 경우 1로저장됨

					// 여기서 noticeDto의 나머지 필드(예: createTime, userId)를 적절히 설정해야 할 수 있습니다.
					// 예: noticeDto.setCreateTime(new java.sql.Date(System.currentTimeMillis()));
					// userId는 현재 로그인한 사용자의 ID 등으로 설정할 수 있습니다. user 객체를 사용할 수 있습니다.
					// noticeDto.setUserId(user.getUserId());

					// DAO를 사용하여 데이터베이스에 저장
					NoticeDAOImpl dao = new NoticeDAOImpl();
					dao.insertNotice(noticeDto);

					// 저장 후 알림
					JOptionPane.showMessageDialog(DetailNotice.this, "공지가 성공적으로 저장되었습니다.");

				}
			});

			if (user.getAuthority() == 1) {
				statusComboBox.setVisible(false);
				statusLabel.setVisible(false);
				jButton.setVisible(false);
			}
			if (user.getAuthority() == 2) {
				statusComboBox.setVisible(true);
				statusLabel.setVisible(true);
				jButton.setVisible(true);
			}

		}
		return titlePanel;
	}

	private JPanel getInfoPanel() {
		JPanel infoPanel = new JPanel();
		String[] arr = LocalDate.now().toString().split("-");
		createTimePanel = new JPanel();
		createTimeLabel = new JLabel("작성일:");
		createTimeTextField = new JTextField(arr[0] + "년" + arr[1] + "월" + arr[2] + "일");
		createTimeTextField.setEditable(false);
		createTimePanel.add(createTimeLabel);
		createTimePanel.add(createTimeTextField);

		writerPanel = new JPanel();
		writerLabel = new JLabel("작성자:");
		writerTextField = new JTextField(20);
		writerTextField.setEditable(false);
		writerPanel.add(writerLabel);
		writerPanel.add(writerTextField);

		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
		infoPanel.add(createTimePanel);
		infoPanel.add(writerPanel);

		return infoPanel;
	}

	private JScrollPane getContentPanel() {
		contentTextArea = new JTextArea(10, 20);
		contentTextArea.setEditable(false);
		JScrollPane contentScrollPane = new JScrollPane(contentTextArea);
		return contentScrollPane;
	}

	public void setData(String title, String createTime, String writer, String content) {
		titleTextField.setText(title);
		createTimeTextField.setText(createTime);
		writerTextField.setText(writer);
		contentTextArea.setText(content);
	}

}
