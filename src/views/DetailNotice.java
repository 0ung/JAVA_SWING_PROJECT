package views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import models.dto.UserDTO;

public class DetailNotice extends JDialog {
	private JPanel titlePanel, createTimePanel, writerPanel;
	private JLabel titleLabel, createTimeLabel, writerLabel, statusLabel;
	private JTextArea contentTextArea;
	private JTextField titleTextField, createTimeTextField, writerTextField;
	private JComboBox<String> statusComboBox; // 상태를 선택하기 위한 콤보박스
	private UserDTO user;
	private JButton jButton;

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
					System.out.println("클릭");
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

		createTimePanel = new JPanel();
		createTimeLabel = new JLabel("작성일:");
		createTimeTextField = new JTextField(20);
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
