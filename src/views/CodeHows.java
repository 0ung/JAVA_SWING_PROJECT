package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import models.dto.AttendanceStatusDTO;
import models.dto.UserDTO;
import models.service.AttendService;
import models.service.UserService;

public class CodeHows extends JPanel {
	private Image image;
	private JPanel startCheck, monthlyPanel, monthlyPanel1, studentManage;
	private JButton start, end;
	private UserDTO user;
	private AttendService attendService = new AttendService();
	private JPanel jPanel;
	private JTable jTable;
	private JButton before, next;
	private JDialog combined;
	private JTable student;
	private UserService service = new UserService();
	EtchedBorder eborder = new EtchedBorder();
	private LocalDate thisMonth = LocalDate.now();

	public CodeHows(UserDTO user) {
		this.user = user;

		this.setLayout(new FlowLayout());

		// 이미지 로드
		loadImage();
		JLabel blank = new JLabel("");
		blank.setPreferredSize(new Dimension(400, 120));
		JLabel codehowsLink = new JLabel("CODE HOWS");
		codehowsLink.setPreferredSize(new Dimension(400, 100));
		loadURI(codehowsLink, "https://www.codehows.com/");
		JLabel githubLink = new JLabel("GitHub");
		githubLink.setPreferredSize(new Dimension(400, 100));
		loadURI(githubLink, "https://github.com/");
		JLabel naverLink = new JLabel("NAVER");
		naverLink.setPreferredSize(new Dimension(400, 100));
		loadURI(naverLink, "https://www.naver.com/");
		JLabel googleLink = new JLabel("GOOGLE");
		googleLink.setPreferredSize(new Dimension(400, 100));
		loadURI(googleLink, "https://www.google.co.kr/?hl=ko");
		add(blank);
		add(codehowsLink);
		add(githubLink);
		add(naverLink);
		add(googleLink);
		JLabel blank2 = new JLabel("");
		blank2.setPreferredSize(new Dimension(400, 120));
		add(getCheckButton());
		add(MonthlyAttendanceLog(true));

		// 패널 크기 설정
		setPreferredSize(new Dimension(500, 1200));

	}

	public JPanel getCheckButton() {
		if (startCheck == null) {
			startCheck = new JPanel();
			start = new JButton("출근");
			start.setPreferredSize(new Dimension(100, 80));
			// start.setBackground(Color.LIGHT_GRAY);

			start.setBorder(new RoundedBorder(20));
			start.setFont(new Font("맑은 고딕", getFont().BOLD, 20));
			start.setForeground(Color.DARK_GRAY);
			start.setBackground(new Color(248, 240, 198));
			// start.setOpaque(false);
			startCheck.add(start);

			end = new JButton("퇴근");
			end.setPreferredSize(new Dimension(100, 80));
			end.setBorder(new RoundedBorder(20));
			end.setFont(new Font("맑은 고딕", getFont().BOLD, 20));
			end.setForeground(Color.DARK_GRAY);
			end.setBackground(new Color(248, 240, 198));
			// end.setOpaque(false);

			startCheck.add(end);

			start.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if (e.getSource() == start) {
						start.setEnabled(false);
						end.setEnabled(true);
						if (attendService.insertStartTime(user.getUserId())) {
						} else {
							JOptionPane.showMessageDialog(start, "이미 출근하셨습니다.");
						}
						updateToTable((DefaultTableModel) getJTable(true, null).getModel());
					}
				}
			});

			end.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// attendService.endTime();
					attendService.updateEndTime(user.getUserId());
					updateToTable((DefaultTableModel) getJTable(true, null).getModel());
					end.setEnabled(false);
					start.setEnabled(false);

				}
			});

		}
		return startCheck;

	}

	private void loadImage() {
		try {
			String imagePath = System.getProperty("user.dir") + File.separator + "image" + File.separator + "logo.png";
			ImageIcon imageIcon = new ImageIcon(imagePath);
			image = imageIcon.getImage();

			// 이미지 로딩 검증 (선택적)
			if (imageIcon.getIconWidth() == -1) {
				System.err.println("Image load failed: " + imagePath);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Image loading error: " + e.getMessage());
		}
	}

	public void loadURI(JLabel jLabel, String url) {
		jLabel.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		jLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (Desktop.isDesktopSupported()) {
					Desktop desktop = Desktop.getDesktop();
					try {
						URI uri = new URI(url);
						desktop.browse(uri);
					} catch (IOException | URISyntaxException e1) {
						e1.printStackTrace();
					}
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// 이미지가 있을 경우에만 그리기
		if (image != null) {
			g.drawImage(image, 0, 0, this);
		}
	}

	public JPanel MonthlyAttendanceLog(boolean isBtn) {

		monthlyPanel = new JPanel(new BorderLayout());

		// 테이블을 JScrollPane에 넣은 후, 패널에 추가

		monthlyPanel.add(new JScrollPane(getJTable(true, null)));
		if (isBtn) {
			// 버튼을 담은 패널 생성
			JPanel buttonPanel = new JPanel(new FlowLayout());
			before = new JButton("<");
			before.setFont(new Font("Arial", Font.PLAIN, 24));
			// before.setFocusPainted(false);
			before.setPreferredSize(new Dimension(80, 60));
			before.setOpaque(false);
			before.setContentAreaFilled(false);
			before.setBorderPainted(false);

			before.addActionListener(e -> {
				AttendanceStatusDTO attendanceStatusDTO = new AttendanceStatusDTO();
				attendanceStatusDTO.setUserId(user.getUserId());
				thisMonth = thisMonth.minusMonths(1);
				attendanceStatusDTO.setYearMonthDay(thisMonth.toString());
				System.out.println(attendanceStatusDTO.toString());
				updateToTable((DefaultTableModel) getJTable(true, null).getModel());
			});

			next = new JButton(">");
			next.setFont(new Font("Arial", Font.PLAIN, 24));
			// next.setFocusPainted(false);
			next.setPreferredSize(new Dimension(80, 60));

			next.setOpaque(false);
			next.setContentAreaFilled(false);
			next.setBorderPainted(false);
			next.addActionListener(e -> {
				AttendanceStatusDTO attendanceStatusDTO = new AttendanceStatusDTO();
				attendanceStatusDTO.setUserId(user.getUserId());
				thisMonth = thisMonth.plusMonths(1);
				attendanceStatusDTO.setYearMonthDay(thisMonth.toString());
				System.out.println(attendanceStatusDTO.toString());
				updateToTable((DefaultTableModel) getJTable(true, null).getModel());
			});
			buttonPanel.add(before);
			buttonPanel.add(next);

			// 패널을 Frame에 추가
			monthlyPanel.add(buttonPanel, BorderLayout.SOUTH); // 패널을 Frame에 추가
			monthlyPanel.add(buttonPanel, BorderLayout.SOUTH);
		}

		return monthlyPanel;
	}

	public JTable getJTable(Boolean test, String userId) {
		List<AttendanceStatusDTO> attendBoards;
		jTable = new JTable();
		DefaultTableModel tableModel = (DefaultTableModel) jTable.getModel();
		tableModel.addColumn("일자");
		tableModel.addColumn("출석시간");
		tableModel.addColumn("퇴근시간");
		tableModel.addColumn("결과");
		if (test) {
			AttendanceStatusDTO attendanceStatusDTO = new AttendanceStatusDTO();
			attendanceStatusDTO.setUserId(user.getUserId());
			attendanceStatusDTO.setYearMonthDay(thisMonth.toString());
			attendBoards = attendService.getAttendTime(attendanceStatusDTO);
		} else {
			AttendanceStatusDTO attendanceStatusDTO = new AttendanceStatusDTO();
			attendanceStatusDTO.setUserId(userId);
			attendanceStatusDTO.setYearMonthDay(thisMonth.toString());
			attendBoards = attendService.getAttendTime(attendanceStatusDTO);
		}
		for (AttendanceStatusDTO board : attendBoards) {
			Object[] row = new Object[] { board.getYearMonthDay(), board.getStartTime(), board.getEndTime(),
					attendService.vlidaiton(board) };
			tableModel.addRow(row);
		}

		// 각 셀의 레이아웃을 설정하는 Renderer
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				((JComponent) c).setBorder(eborder); // 각 셀에 테두리 설정
				return c;
			}
		};
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

		// 각 셀에 Renderer 설정
		for (int i = 0; i < jTable.getColumnCount(); i++) {
			jTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}

		// 헤더의 높낮이 조절
		jTable.getTableHeader().setPreferredSize(new java.awt.Dimension(30, 30));

		// 헤더에 테두리 추가
		jTable.getTableHeader().setBorder(eborder);
		jTable.setRowHeight(25);

		((DefaultTableCellRenderer) jTable.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
		return jTable;
	}

	public void updateToTable(DefaultTableModel tableModel) {
		AttendanceStatusDTO attendanceStatusDTO = new AttendanceStatusDTO();
		attendanceStatusDTO.setUserId(user.getUserId());

		attendanceStatusDTO.setYearMonthDay(thisMonth.toString());
		List<AttendanceStatusDTO> attendBoards = attendService.getAttendTime(attendanceStatusDTO);
		DefaultTableModel tableModel2 = (DefaultTableModel) this.getJTable(true, null).getModel();
		tableModel2.setRowCount(0);

		for (AttendanceStatusDTO board : attendBoards) {
			Object[] row = new String[4];
			row[0] = board.getYearMonthDay();
			row[1] = board.getStartTime();
			row[2] = board.getEndTime();
			row[3] = "결과"; // '결과'는 해당 출근 데이터에 기반한 상태를 나타냅니다 (예: 정상, 지각 등). 필요에 따라 계산 로직 추가

			tableModel2.addRow(row);
		}
		this.revalidate();
		this.repaint();
	}

	public JPanel getStudentMange() {
		studentManage = new JPanel();
		studentManage.setSize(new Dimension(400, 500));
		studentManage.add(new JScrollPane(getStudent()));
		return studentManage;

	}

	private JTable getStudent() {
		if (student == null) {
			student = new JTable();
			DefaultTableModel model = new DefaultTableModel() {
				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
				}

			};
			student.getTableHeader().setPreferredSize(new Dimension(30, 30));
			student.setRowHeight(25);
			model.addColumn("번호");
			model.addColumn("아아디");
			model.addColumn("이름");
			ArrayList<UserDTO> list = service.getSameClassMember(user.getUserId());
			for (int i = 0; i < list.size(); i++) {
				String[] arr = new String[3];
				arr[0] = String.valueOf(i + 1);
				arr[1] = list.get(i).getUserId();
				arr[2] = list.get(i).getUserName();
				model.addRow(arr);
			}

			student.setModel(model);
			DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
			dtcr.setHorizontalAlignment(SwingConstants.CENTER);
			TableColumnModel tcm = student.getColumnModel();

			for (int i = 0; i < tcm.getColumnCount(); i++) {
				tcm.getColumn(i).setCellRenderer(dtcr);
			}
			student.getTableHeader().setReorderingAllowed(false);
			student.getTableHeader().setResizingAllowed(false);
			student.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() == 2) {
						int row = student.getSelectedRow(); // 선택된 행의 인덱스
						String asd = student.getModel().getValueAt(row, 1).toString();
						getCombinedDialog(student.getModel().getValueAt(row, 1).toString()).setVisible(true);
					}

				}
			});
		}
		return student;
	}

	public JDialog getCombinedDialog(String userId) {
		combined = new JDialog();
		combined.setSize(800, 600);
		combined.setLayout(new BorderLayout());
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point centerPoint = ge.getCenterPoint();
		int X = centerPoint.x - combined.getWidth() / 2;
		int Y = centerPoint.y - combined.getWidth() / 2;
		combined.setLocation(X, Y);

		JButton excel = new JButton("Excel");
		monthlyPanel1 = new JPanel(new BorderLayout());
		monthlyPanel1.add(new JScrollPane(getJTable(false, userId)));
		
		AttendStatus as = new AttendStatus(service.getUser(userId));
		JPanel pnl = new JPanel();
		pnl.setLayout(new BoxLayout(pnl, BoxLayout.Y_AXIS));
		pnl.add(monthlyPanel1);
		pnl.add(excel);
		pnl.add(as);

		monthlyPanel1.setPreferredSize(new Dimension(300, 225));
		excel.setPreferredSize(new Dimension(30, 20));
		as.setPreferredSize(new Dimension(300, 400));

		excel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				UserDTO user = service.getUser(userId);
				service.createExcel(user.getUserName(), user.getUserId());
				JOptionPane.showMessageDialog(pnl, user.getUserName() + "의 엑셀파일이 생성되었습니다.");
			}
		});

		combined.add(pnl);
		return combined;
	}

}