package views;

import models.dto.AttendanceStatusDTO;
import models.dto.UserDTO;
import models.service.AttendService;
import models.service.UserService;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
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

public class CodeHows extends JPanel {

	private Image image1, image2;
	private JPanel startCheck, monthlyPanel, monthlyPanel1, studentManage, imagePane1, ptitle;
	private JButton start, end;
	private UserDTO user;
	private AttendService attendService = new AttendService();
	private JLabel label1, label2, titleLabel;
	private JTable jTable;
	private JButton before, next;
	private JDialog combined;
	private JTable student;
	private UserService service = new UserService();
	EtchedBorder eborder = new EtchedBorder();
	private LocalDate thisMonth = LocalDate.now();
	private Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	private int width = (int) screen.getWidth() / 3;
	private int height = (int) screen.getHeight() / 2;

	public CodeHows(UserDTO user) {
		this.user = user;
		this.setLayout(new BorderLayout());

		loadImage();
		add(imagePane1, BorderLayout.NORTH);
		add(getCheckButton(), BorderLayout.CENTER);
		add(MonthlyAttendanceLog(), BorderLayout.SOUTH);
	}

	public JPanel getCheckButton() {
		if (startCheck == null) {
			startCheck = new JPanel();
			start = new JButton("출근");
			start.setPreferredSize(new Dimension(width / 3, height / 7));

			start.setBorder(new RoundedBorder(20));
			start.setFont(new Font("맑은 고딕", getFont().BOLD, 20));
			start.setForeground(Color.DARK_GRAY);
			start.setBackground(new Color(248, 240, 198));
			startCheck.add(start);

			end = new JButton("퇴근");
			end.setPreferredSize(new Dimension(width / 3, height / 7));
			end.setBorder(new RoundedBorder(20));
			end.setFont(new Font("맑은 고딕", getFont().BOLD, 20));
			end.setForeground(Color.DARK_GRAY);
			end.setBackground(new Color(248, 240, 198));

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
							start.setEnabled(false);
							end.setEnabled(false);
						}
						updateToTable((DefaultTableModel) jTable.getModel());
					}
				}
			});

			end.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					attendService.updateEndTime(user.getUserId());
					updateToTable((DefaultTableModel) jTable.getModel());
					end.setEnabled(false);
					start.setEnabled(false);

				}
			});

		}
		return startCheck;

	}

	public JPanel getTitleLabel() {
		if (ptitle == null) {
			ptitle = new JPanel(new FlowLayout());

			titleLabel = new JLabel();
			titleLabel.setText(user.getUserName() + "의 한달 기록");
			titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 20));
			ptitle.add(titleLabel);

		}
		return ptitle;
	}

	private void loadImage() {
		try {

			String imagePath1 = System.getProperty("user.dir") + File.separator + "image" + File.separator
					+ "logo1.png";
			ImageIcon imageIcon1 = new ImageIcon(imagePath1);
			image1 = imageIcon1.getImage();

			String imagePath2 = System.getProperty("user.dir") + File.separator + "image" + File.separator
					+ "logo2.png";
			ImageIcon imageIcon2 = new ImageIcon(imagePath2);
			image2 = imageIcon2.getImage();

			label1 = new JLabel(imageIcon1);
			label2 = new JLabel(imageIcon2);

			loadURI(label1, "https:www.codehows.com/");
			loadURI(label2, "https:www.codehows.com/");

			imagePane1 = new JPanel();
			imagePane1.add(label1);
			imagePane1.add(label2);

			if (imageIcon1.getIconWidth() == -1 || imageIcon2.getIconWidth() == -1) {
				System.err.println("Image load failed: " + imagePath1 + imagePath2);
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
		if (image1 != null) {
			g.drawImage(image1, width / 3, height / 4, this);
		}
		if (image2 != null) {
			g.drawImage(image2, width / 3, height / 4, this);
		}
	}

	public JPanel MonthlyAttendanceLog() {

		monthlyPanel = new JPanel(new BorderLayout());

		monthlyPanel.add(new JScrollPane(getJTable()));
		JPanel buttonPanel = new JPanel(new FlowLayout());
		before = new JButton("<");
		before.setFont(new Font("Arial", Font.PLAIN, 24));
		before.setPreferredSize(new Dimension(80, 60));
		before.setOpaque(false);
		before.setContentAreaFilled(false);
		before.setBorderPainted(false);

		before.addActionListener(e -> {
			AttendanceStatusDTO attendanceStatusDTO = new AttendanceStatusDTO();
			attendanceStatusDTO.setUserId(user.getUserId());
			thisMonth = thisMonth.minusMonths(1);
			attendanceStatusDTO.setYearMonthDay(thisMonth.toString());
			updateToTable((DefaultTableModel) jTable.getModel());
		});

		next = new JButton(">");
		next.setFont(new Font("Arial", Font.PLAIN, 24));
		next.setPreferredSize(new Dimension(80, 60));

		next.setOpaque(false);
		next.setContentAreaFilled(false);
		next.setBorderPainted(false);
		next.addActionListener(e -> {
			AttendanceStatusDTO attendanceStatusDTO = new AttendanceStatusDTO();
			attendanceStatusDTO.setUserId(user.getUserId());
			thisMonth = thisMonth.plusMonths(1);
			attendanceStatusDTO.setYearMonthDay(thisMonth.toString());
			updateToTable((DefaultTableModel) jTable.getModel());
		});
		buttonPanel.add(before);
		buttonPanel.add(next);

		monthlyPanel.add(buttonPanel, BorderLayout.SOUTH);
		monthlyPanel.add(buttonPanel, BorderLayout.SOUTH);

		return monthlyPanel;
	}

	public JTable getJTable(String userId) {
		List<AttendanceStatusDTO> attendBoards;
		jTable = new JTable();
		DefaultTableModel tableModel = (DefaultTableModel) jTable.getModel();
		tableModel.addColumn("일자");
		tableModel.addColumn("출석시간");
		tableModel.addColumn("퇴근시간");
		tableModel.addColumn("결과");
		AttendanceStatusDTO attendanceStatusDTO = new AttendanceStatusDTO();
		attendanceStatusDTO.setUserId(userId);
		attendanceStatusDTO.setYearMonthDay(thisMonth.toString());
		attendBoards = attendService.getAttendTime(attendanceStatusDTO);
		for (AttendanceStatusDTO board : attendBoards) {
			Object[] row = new Object[] { board.getYearMonthDay(), board.getStartTime(), board.getEndTime(),
					attendService.validaiton(board) };
			tableModel.addRow(row);
		}

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				((JComponent) c).setBorder(eborder);
				return c;
			}
		};
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

		for (int i = 0; i < jTable.getColumnCount(); i++) {
			jTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}

		jTable.getTableHeader().setPreferredSize(new java.awt.Dimension(30, 30));
		jTable.getTableHeader().setBorder(eborder);
		jTable.setRowHeight(25);

		((DefaultTableCellRenderer) jTable.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
		return jTable;
	}

	public JTable getJTable() {
		List<AttendanceStatusDTO> attendBoards;
		jTable = new JTable();
		DefaultTableModel tableModel = (DefaultTableModel) jTable.getModel();
		tableModel.addColumn("일자");
		tableModel.addColumn("출석시간");
		tableModel.addColumn("퇴근시간");
		tableModel.addColumn("결과");
		AttendanceStatusDTO attendanceStatusDTO = new AttendanceStatusDTO();
		attendanceStatusDTO.setUserId(user.getUserId());
		attendanceStatusDTO.setYearMonthDay(thisMonth.toString());
		attendBoards = attendService.getAttendTime(attendanceStatusDTO);
		for (AttendanceStatusDTO board : attendBoards) {
			Object[] row = new Object[] { board.getYearMonthDay(), board.getStartTime(), board.getEndTime(),
					attendService.validaiton(board) };
			tableModel.addRow(row);
		}

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				((JComponent) c).setBorder(eborder);
				return c;
			}
		};
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

		for (int i = 0; i < jTable.getColumnCount(); i++) {
			jTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}
		jTable.getTableHeader().setPreferredSize(new java.awt.Dimension(30, 30));
		jTable.getTableHeader().setBackground(new Color(237, 248, 221));

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
		tableModel.setRowCount(0);

		for (AttendanceStatusDTO board : attendBoards) {
			Object[] row = new String[4];
			row[0] = board.getYearMonthDay();
			row[1] = board.getStartTime();
			row[2] = board.getEndTime();
			row[3] = attendService.validaiton(board);
			tableModel.addRow(row);
		}
		this.revalidate();
		this.repaint();
	}

	public JPanel getStudentMange() {
		studentManage = new JPanel();
		JScrollPane js = new JScrollPane(getStudent());
		js.setPreferredSize(new Dimension(width - 50, height - 80));
		studentManage.add(js);
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
			student.getTableHeader().setBackground(new Color(237, 248, 221));
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
						int row = student.getSelectedRow();
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
		combined.setSize(width, height + 300);
		combined.setLayout(new BorderLayout());

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point centerPoint = ge.getCenterPoint();
		int X = centerPoint.x - combined.getWidth() / 2;
		int Y = centerPoint.y - combined.getWidth() / 2;
		combined.setLocation(X, Y);

		JButton excel = new JButton("Excel");
		excel.setBackground(Color.DARK_GRAY);
		excel.setForeground(Color.white);
		monthlyPanel1 = new JPanel(new BorderLayout());
		monthlyPanel1.add(new JScrollPane(getJTable(userId)));

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