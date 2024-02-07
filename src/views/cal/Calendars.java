package views.cal;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Calendar;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import models.dto.UserDTO;
import views.RoundedBorder;

public class Calendars {
	private static final Calendar cal = Calendar.getInstance();
	private String[] dayAr = { "일", "월", "화", "수", "목", "금", "토" };
	private DateBox[] dateBoxAr = new DateBox[dayAr.length * 6];
	private JPanel pNorth, pCenter, Calendars;
	private JButton btNext, btPrev;
	private JLabel lbTittle;
	private JTable noticeTable;
	private JPanel titlePanel, createTimePanel, writerPanel;
	private JLabel titleLabel, createTimeLabel, writerLabel, statusLabel;
	private JTextArea contentTextArea;
	private JTextField titleTextField, createTimeTextField, writerTextField;
	private JComboBox<String> statusComboBox; // 상태를 선택하기 위한 콤보박스
	private UserDTO user;
	private JButton jButton;
	private int keyYear;
	private int keyMonth;
	private int startDay;
	private int lastDay;

	public JPanel getCalendars() {
		Calendars = new JPanel();

		Calendars.setLayout(new BorderLayout()); // 패널의 레이아웃을 BorderLayout으로 설정
		pNorth = new JPanel();
		btPrev = new JButton("이전");
		btPrev.setBorder(new RoundedBorder(8));
		btPrev.setBackground(Color.WHITE);
		lbTittle = new JLabel("년도올예정", SwingConstants.CENTER);
		btNext = new JButton("다음");
		btNext.setBorder(new RoundedBorder(8));
		btNext.setBackground(Color.WHITE);
		pCenter = new JPanel(new GridLayout(7, 7, 5, 5)); // pCenter에 GridLayout 설정

		lbTittle.setPreferredSize(new Dimension(300, 30));
		pNorth.add(btPrev);
		pNorth.add(lbTittle);
		pNorth.add(btNext);
		Calendars.add(pNorth, BorderLayout.NORTH); // pNorth를 상단에 배치
		Calendars.add(pCenter, BorderLayout.CENTER); // pCenter를 중앙에 배치

		btPrev.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateMonth(-1);
			}
		});
		btNext.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateMonth(1);
			}
		});
		getDateInfo();
		setDateTitle();
		createDay();
		createDate();
		printDate();
		Calendars.setPreferredSize(new Dimension(600, 600)); // JFrame에서 pack()을 호출할 경우를 대비해 선호 크기 설정

		return Calendars;
	}

	// 시작 요일, 끝 날 등 구하기
	public void getDateInfo() {
		keyYear = cal.get(Calendar.YEAR);
		keyMonth = cal.get(Calendar.MONTH);
		startDay = getFirstDayOfMonth(keyYear, keyMonth);
		lastDay = getLastDate(keyYear, keyMonth);
	}

	// 요일 생성
	public void createDay() {
		for (int i = 0; i < 7; i++) {
			DateBox dayBox = new DateBox(dayAr[i], Color.gray, 100, 70, "");
			pCenter.add(dayBox);
		}
	}

	// 날짜 생성
	public void createDate() {
		for (int i = 0; i < dayAr.length * 6; i++) {
			DateBox dateBox = new DateBox("", Color.gray, 100, 100, "");
			pCenter.add(dateBox);
			dateBoxAr[i] = dateBox;
		}
	}

	public int getFirstDayOfMonth(int yy, int mm) {
		Calendar cal = Calendar.getInstance(); // 날짜 객체 생성
		cal.set(yy, mm, 1);
		return cal.get(Calendar.DAY_OF_WEEK) - 1;// 요일은 1부터 시작으로 배열과 쌍을 맞추기 위해 -1
	}

	public int getLastDate(int yy, int mm) {
		Calendar cal = Calendar.getInstance();
		cal.set(yy, mm + 1, 0);
		// 마지막 날을 의미한다.
		return cal.get(Calendar.DATE);
	}

	// 날짜 박스에 날짜 출력하기
	public void printDate() {
		int n = 1;
		for (int i = 0; i < dateBoxAr.length; i++) {
			if (i >= startDay && n <= lastDay) {
				dateBoxAr[i].setDay(Integer.toString(n));
				dateBoxAr[i].setShowNotice(getNoticeList(n));
				dateBoxAr[i].repaint();
				n++;
				for (MouseListener listener : dateBoxAr[i].getMouseListeners()) {
					dateBoxAr[i].removeMouseListener(listener);
				}
				dateBoxAr[i].addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						JDialog dialog = new JDialog();
						dialog.add(getNotice(user));
						dialog.setVisible(true);
					}
				});
			} else {
				dateBoxAr[i].setDay("");
				dateBoxAr[i].setShowNotice("");
				dateBoxAr[i].repaint();
			}
		}
	}

	// 몇년도 몇월인지를 보여주는 타이틀 라벨의 값을 변경
	public void setDateTitle() {
		lbTittle.setText(keyYear + "-" + DateBox.getZeroString(keyMonth + 1));
		lbTittle.updateUI();
	}

	// 달력을 넘기거나 전으로 이동할 때 날짜 객체에 대한 정보도 변경
	public void updateMonth(int data) {
		// 캘린더 객체에 들어있는 날짜를 기준으로 월 정보를 바꿔준다.
		cal.set(Calendar.MONTH, keyMonth + data);
		getDateInfo();
		printDate();
		setDateTitle();
	}

	// notice 정보 가져오기
	public String getNoticeList(int n) {
		if (n == 2) {
			return "2일";
		} else {
			return "2일 아님";
		}
	}

	public JDialog getDetailNotice(UserDTO user) {
		JDialog detailNotice = new JDialog();
		detailNotice.setSize(new Dimension(700, 800));
		detailNotice.setLayout(new BorderLayout(5, 5));
		detailNotice.setAlwaysOnTop(true);
		detailNotice.add(getTitlePanel(), BorderLayout.NORTH);
		detailNotice.add(getContentPanel(), BorderLayout.CENTER);
		detailNotice.add(getInfoPanel(), BorderLayout.SOUTH);
		detailNotice.setTitle("상세보기");
		detailNotice.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		detailNotice.setLocationRelativeTo(null); // Center on screen

		return detailNotice;

	}

	public JPanel getNotice(UserDTO user) {
		JPanel notice = new JPanel();
		notice.add(new JScrollPane(getNoticeTable(user)));
		notice.setSize(new Dimension(800, 800));
		return notice;
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

	public JTable getNoticeTable(UserDTO user) {
		if (noticeTable == null) {
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

			String[] arr = { "테스트1", "테스트", "김영웅", "0204" };
			tableModel.addRow(arr);

			noticeTable.getTableHeader().setPreferredSize(new Dimension(30, 30));
			noticeTable.setModel(tableModel);

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
					getDetailNotice(user).setVisible(true);
				}
			});
		}
		return noticeTable;
	}

	public static void main(String[] args) {
		Calendars calendar = new Calendars();
		JFrame frame = new JFrame();
		frame.getContentPane().add(calendar.getCalendars());
		frame.setVisible(true);
	}

}
