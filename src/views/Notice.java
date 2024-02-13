package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import models.dao.NoticeDAO;
import models.dao.NoticeDAO;
import models.dto.NoticeDto;
import models.dto.UserDTO;

public class Notice {
	private JDialog detailNotice, createNotice;
	private JPanel titlePanel, createTimePanel, writerPanel, notice, editPanel, ptitle;
	private JLabel titleLabel, createTimeLabel, writerLabel, statusLabel, titleLabel2;
	private JTextArea contentTextArea;
	private JTextField titleTextField, createTimeTextField, writerTextField;
	private JComboBox<String> statusComboBox;
	private JButton jButton, editBtn, deleteBtn;
	private JTable noticeTable;
	private UserDTO user;
	private NoticeDto noticeDto;
	private HashMap<Integer, Long> map = new HashMap<>();
	private Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	private int width = (int) screen.getWidth() / 3;
	private int height = (int) screen.getHeight() / 2;
	private NoticeDAO noticeDAO = new NoticeDAO();

	public Notice(UserDTO user) {
		this.user = user;
	}

	public JPanel getNotice(int important) {
		notice = new JPanel(new BorderLayout());
		JScrollPane js = new JScrollPane(getTable(important));

		js.setPreferredSize(new Dimension(width - 50, height - 100));
		JButton addButton = new JButton("공지사항 생성");
		addButton.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		addButton.setBackground(new Color(237, 248, 221));
		addButton.setPreferredSize(new Dimension(100, 40));
		addButton.setBorder(BorderFactory.createLineBorder(new Color(198, 232, 149)));
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getCreateNotice().setVisible(true);
				setFieldsEditable(true);
			}
		});
		if (user.getAuthority() == 1) {
			addButton.setVisible(false);
		}
		if (user.getAuthority() == 2) {
			addButton.setVisible(true);
		}

		notice.add(js);
		CommonSetting.locationCenter(notice);
		notice.add(addButton, BorderLayout.SOUTH);
		return notice;
	}

	public JPanel getNotice(int important, int year, int month, int day) {
		notice = new JPanel();
		notice.add(getTitleLabel());
		JScrollPane js = new JScrollPane();
		notice.add(new JScrollPane(getTable(important, year, month, day)));

		notice.setSize(new Dimension(width, height - 150));

		CommonSetting.locationCenter(notice);

		return notice;
	}

	public JPanel getTitleLabel() {
		if (ptitle == null) {
			ptitle = new JPanel(new FlowLayout());

			titleLabel2 = new JLabel();
			titleLabel2.setText("중요 공지 사항");
			titleLabel2.setFont(new Font("맑은 고딕", Font.BOLD, 20));
			ptitle.add(titleLabel);

		}
		return ptitle;
	}

	public JTable getTable(int important) {
		noticeTable = new JTable();
		DefaultTableModel tableModel = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tableModel.addColumn("번호");
		tableModel.addColumn("제목");
		tableModel.addColumn("글쓴이");
		tableModel.addColumn("등록일자");

		DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
		cellRenderer.setBackground(new Color(237, 248, 221));

		NoticeDAO daoImpl = new NoticeDAO();
		List<NoticeDto> list = daoImpl.readID(important);
		for (int i = 0; i < list.size(); i++) {
			map.put(i + 1, list.get(i).getNoticeId());
			Object[] arr = { String.valueOf(i + 1), list.get(i).getTitle(), list.get(i).getUserName(),
					list.get(i).getCreateTime().toString() };
			tableModel.addRow(arr);
		}
		noticeTable.setModel(tableModel);
		customizeTable();
		return noticeTable;
	}

	public JTable getTable(int important, int year, int month, int day) {
		noticeTable = new JTable();

		DefaultTableModel tableModel = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tableModel.addColumn("번호");
		tableModel.addColumn("제목");
		tableModel.addColumn("글쓴이");
		tableModel.addColumn("등록일자");

		NoticeDAO daoImpl = new NoticeDAO();
		// 날짜 포매팅
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.set(year, month, day);
		String formattedDate = sdf.format(cal.getTime());

		List<NoticeDto> list = daoImpl.readbyday(formattedDate);
		for (int i = 0; i < list.size(); i++) {
			map.put(i + 1, list.get(i).getNoticeId());
			Object[] arr = { String.valueOf(i + 1), list.get(i).getTitle(), list.get(i).getUserName(),
					list.get(i).getCreateTime().toString() };
			tableModel.addRow(arr);
		}
		noticeTable.setModel(tableModel);
		customizeTable();
		return noticeTable;
	}

	private void customizeTable() {
		noticeTable.getTableHeader().setPreferredSize(new Dimension(30, 30));
		noticeTable.setRowHeight(25);
		noticeTable.getTableHeader().setReorderingAllowed(false);
		noticeTable.getTableHeader().setResizingAllowed(false);
		noticeTable.getTableHeader().setBackground(new Color(237, 248, 221));

		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(SwingConstants.CENTER);
		TableColumnModel tcm = noticeTable.getColumnModel();

		for (int i = 0; i < tcm.getColumnCount(); i++) {
			tcm.getColumn(i).setCellRenderer(dtcr);
		}

		noticeTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int a = noticeTable.getSelectedRow();
				String noticeIdStr = (String) noticeTable.getValueAt(a, 0);
				int noticeId = Integer.parseInt(noticeIdStr);
				NoticeDAO impl = new NoticeDAO();
				NoticeDto noticeDetail = new NoticeDto();
				noticeDetail = impl.getNoticeDetailById(map.get(noticeId));
				String userName = (String) noticeTable.getValueAt(a, 2);
				noticeDetail.setUserName(userName);
				if (noticeDetail.getUserName().equals(userName)) {
					getDetailNotice(noticeDetail, true).setVisible(true);
				} else {
					getDetailNotice(noticeDetail, false).setVisible(true);
				}
				setStatusComboBoxEditable(false);
			}
		});

	}

	public JDialog getCreateNotice() {
		createNotice = new JDialog();
		createNotice.setSize(new Dimension(700, 800));
		createNotice.setLayout(new BorderLayout(5, 5));
		createNotice.setAlwaysOnTop(true);
		createNotice.add(getTitlePanel(), BorderLayout.NORTH);
		createNotice.add(getContentPanel(), BorderLayout.CENTER);
		createNotice.add(getInfoPanel(), BorderLayout.SOUTH);
		createTimeTextField.setText(LocalDate.now() + "");
		writerTextField.setText(user.getUserName());
		createNotice.setDefaultCloseOperation(createNotice.DISPOSE_ON_CLOSE);
		createNotice.setLocationRelativeTo(null);
		return createNotice;
	}

	public JDialog getDetailNotice(NoticeDto dto, boolean edit) {
		detailNotice = new JDialog();
		detailNotice.setSize(new Dimension(700, 800));
		detailNotice.setLayout(new BorderLayout(5, 5));
		detailNotice.setAlwaysOnTop(true);
		detailNotice.add(getTitlePanel(), BorderLayout.NORTH);
		detailNotice.add(getContentPanel(), BorderLayout.CENTER);

		JPanel bottomPanel = new JPanel(new BorderLayout());
		bottomPanel.add(getInfoPanel(), BorderLayout.CENTER);

		if (edit && user.getAuthority() == 2 && dto.getUserId().equals(user.getUserId())) {
			editBtn = new JButton("수정");
			deleteBtn = new JButton("삭제");
			editBtn.setBackground(new Color(237, 248, 221));
			deleteBtn.setBackground(new Color(237, 248, 221));
			editPanel = new JPanel(new FlowLayout());
			editBtn.addActionListener(e -> {
				NoticeDto notice = new NoticeDto();
				notice.setTitle(titleTextField.getText());
				notice.setContent(contentTextArea.getText());
				notice.setNoticeId(dto.getNoticeId());
				noticeDAO.updateNoticeById(notice);
				JOptionPane.showMessageDialog(detailNotice, "수정완료");
				updateNoticeTable(1);
				detailNotice.dispose();
			});
			deleteBtn.addActionListener(e -> {
				NoticeDto notice = new NoticeDto();
				notice.setTitle(writerTextField.getText());
				notice.setNoticeId(dto.getNoticeId());
				noticeDAO.deleteNoticeById(notice);
				JOptionPane.showMessageDialog(detailNotice, "삭제완료");
				updateNoticeTable(1);
				detailNotice.dispose();
			});
			editPanel.add(editBtn);
			editPanel.add(deleteBtn);
			bottomPanel.add(editPanel, BorderLayout.SOUTH);
		}

		detailNotice.add(bottomPanel, BorderLayout.SOUTH);

		detailNotice.setTitle("상세보기");
		detailNotice.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		detailNotice.setLocationRelativeTo(null);
		titleTextField.setText(dto.getTitle());
		contentTextArea.setText(dto.getContent());
		createTimeTextField.setText(dto.getCreateTime() + "");
		writerTextField.setText(dto.getUserName());

		setStatusComboBoxEditable(edit);
		setFieldsEditable(edit);
		return detailNotice;
	}

	public JDialog createNoticeDialog(int year, int month, int day, int important) {
		JDialog dialog = new JDialog();
		JPanel jPanel = getNotice(important, year, month, day);
		dialog.getContentPane().add(jPanel, BorderLayout.CENTER);
		dialog.pack();
		return dialog;
	}

	public void setStatusComboBoxEditable(boolean editable) {
		statusComboBox.setVisible(editable);
		jButton.setVisible(editable);
		statusLabel.setVisible(editable);
	}

	public void setFieldsEditable(boolean editable) {
		titleTextField.setEditable(editable);
		createTimeTextField.setEditable(editable);
		writerTextField.setEditable(editable);
		contentTextArea.setEditable(editable);
	}

	private JPanel getTitlePanel() {
		titlePanel = new JPanel();
		titleLabel = new JLabel("제목:");
		titleTextField = new JTextField(20);
		titleTextField.setEditable(false);
		titlePanel.add(titleLabel);
		titlePanel.add(titleTextField);

		statusLabel = new JLabel("선택:");
		statusComboBox = new JComboBox<>(new String[] { "중요한 게시물", "켈린더 게시물" });
		titlePanel.add(statusLabel);
		titlePanel.add(statusComboBox);

		jButton = new JButton();
		jButton.setText("저장");
		jButton.setBackground(new Color(237, 248, 221));
		titlePanel.add(jButton);
		jButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				noticeDto = new NoticeDto();
				noticeDto.setUserId(user.getUserId());
				noticeDto.setTitle(titleTextField.getText());
				noticeDto.setContent(contentTextArea.getText());
				noticeDto.setImportant(statusComboBox.getSelectedIndex() == 0);
				NoticeDAO dao = new NoticeDAO();
				dao.insertNotice(noticeDto);
				JOptionPane.showMessageDialog(createNotice, "공지가 성공적으로 저장되었습니다.");
				updateNoticeTable(1);
				createNotice.dispose();
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

		return titlePanel;

	}

	public void updateNoticeTable(int important) {
		DefaultTableModel tableModel = (DefaultTableModel) noticeTable.getModel();
		tableModel.setRowCount(0); 
		NoticeDAO daoImpl = new NoticeDAO();
		List<NoticeDto> list = daoImpl.readID(important);
		for (int i = 0; i < list.size(); i++) {
			map.put(i + 1, list.get(i).getNoticeId());
			Object[] arr = { String.valueOf(i + 1), 
					list.get(i).getTitle(), list.get(i).getUserName(), 
					list.get(i).getCreateTime().toString() };
			tableModel.addRow(arr);
		}
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