package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.List;

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

import models.dao.AttendDAO;
import models.dao.AttendDAOImpl;
import models.dto.AttendanceStatusDTO;
import models.dto.UserDTO;

import models.dao.AttendDAO;
import models.dao.AttendDAOImpl;
import models.dto.AttendanceStatusDTO;

public class MonthlyAttendanceLog extends JPanel {

	private JPanel jPanel;
	private JTable jTable;
	private JButton before, next;
	private UserDTO dto;
	EtchedBorder eborder = new EtchedBorder();

	public MonthlyAttendanceLog(UserDTO user) {
		this.dto = user;
		this.setSize(600, 500);
		this.setLayout(new BorderLayout());
		// 테이블을 JScrollPane에 넣은 후, 패널에 추가
		JPanel tablePanel = new JPanel(new BorderLayout());
		tablePanel.add(new JScrollPane(getJTable()), BorderLayout.CENTER);

		// 버튼을 담은 패널 생성
		JPanel buttonPanel = new JPanel();
		JButton before = new JButton("이전");
		before.setBorder(new RoundedBorder(20));
		before.setBackground(Color.WHITE);
		before.setPreferredSize(new Dimension(80, 50));
		JButton next = new JButton("다음");
		next.setBorder(new RoundedBorder(20));
		next.setBackground(Color.WHITE);
		next.setPreferredSize(new Dimension(80, 50));
		buttonPanel.add(before);
		buttonPanel.add(next);

		// 패널을 Frame에 추가
		this.add(tablePanel, BorderLayout.NORTH);
		this.add(buttonPanel, BorderLayout.SOUTH);
	}

	public JTable getJTable() {
		if (jTable == null) {
			jTable = new JTable();
			DefaultTableModel tableModel = (DefaultTableModel) jTable.getModel();
			tableModel.addColumn("일자");
			tableModel.addColumn("출석시간");
			tableModel.addColumn("퇴근시간");
			tableModel.addColumn("결과");
			
			 AttendDAO attend = new AttendDAOImpl(); 
			 List<AttendanceStatusDTO>attendBoards = attend.getAttendBoards(dto.getUserID()); 			 
			for (AttendanceStatusDTO board : attendBoards) {
			    Object[] row = new Object[]{
			        board.getYearMonthDay(),
			        board.getStartTime(),
			        board.getEndTime(),
			        "결과" // '결과'는 해당 출근 데이터에 기반한 상태를 나타냅니다 (예: 정상, 지각 등). 필요에 따라 계산 로직 추가
			    };
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
	
//	public void main(String[] args) {
//		SwingUtilities.invokeLater(()->{
//			MonthlyAttendanceLog jframe = new MonthlyAttendanceLog();
//			jframe.setVisible(true);
//		});
//	}

}
