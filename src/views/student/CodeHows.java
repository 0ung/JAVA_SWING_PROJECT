package views.student;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import models.dto.AttendanceStatusDTO;
import models.dto.UserDTO;
import models.service.AttendService;
import views.RoundedBorder;

public class CodeHows extends JPanel {
	private Image image;
	private JPanel startCheck, endCheck, monthlyPanel;
	private JButton start, end;
	private UserDTO user;
	private AttendService attendService = new AttendService();
	private JPanel test;
	private JPanel jPanel;
	private JTable jTable;
	private JButton before, next;
	EtchedBorder eborder = new EtchedBorder();

	public CodeHows(UserDTO user) {
		this.user = user;
		this.setLayout(new GridLayout(6, 1));
		// 이미지 로드
		loadImage();
		JLabel blank = new JLabel("");
		JLabel codehowsLink = new JLabel("1. 코드하우스!!!");
		loadURI(codehowsLink, "https://www.codehows.com/");
		JLabel githubLink = new JLabel("2. 깃허브!!!");
		loadURI(githubLink, "https://github.com/");
		JLabel naverLink = new JLabel("3. 네이버!!!");
		loadURI(naverLink, "https://www.naver.com/");
		JLabel googleLink = new JLabel("4. 구글!!!");
		loadURI(googleLink, "https://www.google.co.kr/?hl=ko");
		add(blank);
		add(codehowsLink);
		add(githubLink);
		add(naverLink);
		add(googleLink);
		add(getCheckButton());
		add(MonthlyAttendanceLog());

		// 패널 크기 설정
		setPreferredSize(new Dimension(500, 700));

	}

	public JPanel getCheckButton() {
		if (startCheck == null) {
			startCheck = new JPanel();
			start = new JButton();
			start.setPreferredSize(new Dimension(80, 80));
			// start.setBackground(Color.LIGHT_GRAY);

			JLabel txtStart = new JLabel("출근");
			start.add(txtStart);
			start.setBorder(new RoundedBorder(20));
			txtStart.setHorizontalAlignment(JLabel.CENTER);
			txtStart.setFont(new Font("맑은 고딕", getFont().BOLD, 15));
			startCheck.add(start);

			end = new JButton();
			end.setPreferredSize(new Dimension(80, 80));
			end.setBorder(new RoundedBorder(20));
			// end.setBackground(Color.RED);
			JLabel txtEnd = new JLabel("퇴근");
			txtEnd.setHorizontalAlignment(SwingConstants.CENTER);
			txtEnd.setFont(new Font("맑은 고딕", getFont().BOLD, 15));

			end.add(txtEnd);
			startCheck.add(end);

			start.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if (e.getSource() == start) {
						start.setEnabled(false);
						end.setEnabled(true);
				
						attendService.insertStartTime(user.getUserId());
						updateToTable((DefaultTableModel) getJTable().getModel());
					} else if (e.getSource() == end) {
						end.setEnabled(false);
						start.setEnabled(true);
					}
				}
			});

			end.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// attendService.endTime();
					attendService.updateEndTime(user.getUserId());
					updateToTable((DefaultTableModel) getJTable().getModel());
					
					end.setEnabled(false);
					start.setEnabled(true);

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

	public JPanel MonthlyAttendanceLog() {
		monthlyPanel = new JPanel();
		monthlyPanel.setSize(600, 500);
		monthlyPanel.setLayout(new BorderLayout());
		// 테이블을 JScrollPane에 넣은 후, 패널에 추가
		JPanel tablePanel = new JPanel(new BorderLayout());
		tablePanel.add(new JScrollPane(getJTable()), BorderLayout.CENTER);

		// 버튼을 담은 패널 생성
		JPanel buttonPanel = new JPanel();
		before = new JButton("이전");
		before.setBorder(new RoundedBorder(20));
		before.setBackground(Color.WHITE);
		before.setPreferredSize(new Dimension(80, 50));
		next = new JButton("다음");
		next.setBorder(new RoundedBorder(20));
		next.setBackground(Color.WHITE);
		next.setPreferredSize(new Dimension(80, 50));
		buttonPanel.add(before);
		buttonPanel.add(next);

		// 패널을 Frame에 추가
		monthlyPanel.add(tablePanel, BorderLayout.NORTH);
		monthlyPanel.add(buttonPanel, BorderLayout.SOUTH);

		return monthlyPanel;
	}

	public JTable getJTable() {
		if (jTable == null) {
			jTable = new JTable();
			DefaultTableModel tableModel = (DefaultTableModel) jTable.getModel();
			tableModel.addColumn("일자");
			tableModel.addColumn("출석시간");
			tableModel.addColumn("퇴근시간");
			tableModel.addColumn("결과");
			List<AttendanceStatusDTO> attendBoards = attendService.getAttendTime(user.getUserId());
			for (AttendanceStatusDTO board : attendBoards) {
				Object[] row = new Object[] { board.getYearMonthDay(), board.getStartTime(), board.getEndTime(), "결과" };
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

			((DefaultTableCellRenderer) jTable.getTableHeader().getDefaultRenderer())
					.setHorizontalAlignment(JLabel.CENTER);
		}

		return jTable;
	}

	public void updateToTable(DefaultTableModel tableModel) {
		List<AttendanceStatusDTO> attendBoards = attendService.getAttendTime(user.getUserId());
		DefaultTableModel tableModel2 = (DefaultTableModel) this.getJTable().getModel();
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

}