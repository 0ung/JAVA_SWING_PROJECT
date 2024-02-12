package views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

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
import models.dao.NoticeDAOImpl;
import models.dto.NoticeDto;
import models.dto.UserDTO;

public class Notice {
	private JDialog detailNotice, createNotice;
	private JPanel titlePanel, createTimePanel, writerPanel, notice, editPanel;
	private JLabel titleLabel, createTimeLabel, writerLabel, statusLabel;
	private JTextArea contentTextArea;
	private JTextField titleTextField, createTimeTextField, writerTextField;
	private JComboBox<String> statusComboBox; // 상태를 선택하기 위한 콤보박스
	private JButton jButton, editBtn, deleteBtn;
	private JTable noticeTable;
	private UserDTO user;
	private NoticeDto noticeDto;
	private HashMap<Integer, Long> map = new HashMap<>();
	private Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	private int width = (int) screen.getWidth() / 3;
	private int height = (int) screen.getHeight() / 2;
	private NoticeDAO noticeDAO = new NoticeDAOImpl();

	public Notice(UserDTO user) {
		this.user = user;
	}

	public JPanel getNotice(int important) {
		notice = new JPanel();
		notice.add(new JScrollPane(getTable(important)));
		notice.setPreferredSize(new Dimension(width , height-100));
		notice.add(createNoticePanel());
		CommonSetting.locationCenter(notice);
		return notice;
	}

	public JPanel getNotice(int important, int year, int month, int day) {
		notice = new JPanel();
		notice.add(new JScrollPane(getTable(important, year, month, day)));
		notice.setSize(new Dimension(width , height/2));
		notice.add(createNoticePanel());
		CommonSetting.locationCenter(notice);
		return notice;
	}

	public JPanel createNoticePanel() {
		JPanel jPanel1 = new JPanel(new BorderLayout());
		JButton addButton = new JButton("생성");
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getCreateNotice().setVisible(true);
				setFieldsEditable(true);
			}
		});
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(addButton);
		if (user.getAuthority() == 1) {
			addButton.setVisible(false);
		}
		if (user.getAuthority() == 2) {
			addButton.setVisible(true);
		}
		jPanel1.add(buttonPanel, BorderLayout.SOUTH);
		return jPanel1;
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

		NoticeDAOImpl daoImpl = new NoticeDAOImpl();
		List<NoticeDto> list = daoImpl.readID(important); // 가정: readID가 List<NoticeDto>를 반환
		for (int i = 0; i < list.size(); i++) {
			map.put(i + 1, list.get(i).getNoticeId());
			Object[] arr = { String.valueOf(i + 1), // 가정: getNoticeId()가 long 또는 int 타입
					list.get(i).getTitle(), list.get(i).getUserName(), // UserDTO에 getUserName()이 정의되어 있어야 함
					list.get(i).getCreateTime().toString() };// SimpleDateFormat을 사용하여 포매팅할 수 있음
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

		NoticeDAOImpl daoImpl = new NoticeDAOImpl();
		// 날짜 포매팅
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.set(year, month, day);
		String formattedDate = sdf.format(cal.getTime());

		List<NoticeDto> list = daoImpl.readbyday(formattedDate); // 가정: readID가 List<NoticeDto>를 반환
		for (int i = 0; i < list.size(); i++) {
			map.put(i + 1, list.get(i).getNoticeId());
			Object[] arr = { String.valueOf(i + 1), // 가정: getNoticeId()가 long 또는 int 타입
					list.get(i).getTitle(), list.get(i).getUserName(), // UserDTO에 getUserName()이 정의되어 있어야 함
					list.get(i).getCreateTime().toString() };// SimpleDateFormat을 사용하여 포매팅할 수 있음
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
				NoticeDAOImpl impl = new NoticeDAOImpl();
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
		createNotice.setLocationRelativeTo(null); // Center on screen
		return createNotice;
	}

	public JDialog getDetailNotice(NoticeDto dto, boolean edit) {
		detailNotice = new JDialog();
		detailNotice.setSize(new Dimension(700, 800));
		detailNotice.setLayout(new BorderLayout(5, 5));
		detailNotice.setAlwaysOnTop(true);
		detailNotice.add(getTitlePanel(), BorderLayout.NORTH);
		detailNotice.add(getContentPanel(), BorderLayout.CENTER);

		JPanel bottomPanel = new JPanel(new BorderLayout()); // 새로운 컨테이너 패널 생성
		bottomPanel.add(getInfoPanel(), BorderLayout.CENTER); // infoPanel을 bottomPanel 중앙에 추가

		if (edit) {
			editBtn = new JButton("수정");
			deleteBtn = new JButton("삭제");
			editPanel = new JPanel(new FlowLayout()); // 수정 및 삭제 버튼을 우측에 배치
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
			bottomPanel.add(editPanel, BorderLayout.SOUTH); // editPanel을 bottomPanel 하단에 추가
		}

		detailNotice.add(bottomPanel, BorderLayout.SOUTH); // 수정: bottomPanel을 대화상자 남쪽에 추가

		// Set the dialog properties
		detailNotice.setTitle("상세보기");
		detailNotice.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		detailNotice.setLocationRelativeTo(null); // Center on screen
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

	// 콤보박스 띄우는지 여부 확인
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
				noticeDto = new NoticeDto();
				// 사용자 입력값을 NoticeDto 객체에 설정
				noticeDto.setUserId(user.getUserId());
				noticeDto.setTitle(titleTextField.getText());
				noticeDto.setContent(contentTextArea.getText());
				// 선택된 콤보박스 항목에 따라 important 값 설정
				noticeDto.setImportant(statusComboBox.getSelectedIndex() == 0);
				// 예를 들어 "중요한 게시물"이 첫 번째 항목일 경우 1로저장됨
				NoticeDAOImpl dao = new NoticeDAOImpl();
				dao.insertNotice(noticeDto);
				// 저장 후 알림
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
		tableModel.setRowCount(0); // 기존 데이터를 모두 삭제

		// 새로운 데이터로 테이블 채우기
		NoticeDAOImpl daoImpl = new NoticeDAOImpl();
		List<NoticeDto> list = daoImpl.readID(important);
		for (int i = 0; i < list.size(); i++) {
			map.put(i + 1, list.get(i).getNoticeId());
			Object[] arr = { String.valueOf(i + 1), // 가정: getNoticeId()가 long 또는 int 타입
					list.get(i).getTitle(), list.get(i).getUserName(), // UserDTO에 getUserName()이 정의되어 있어야 함
					list.get(i).getCreateTime().toString() };// SimpleDateFormat을 사용하여 포매팅할 수 있음
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