package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class StudentAttendanceManagement extends JPanel {
	private JTable student;
	private String[] selectOptions = { "출석", "결석", "지각", "조퇴", "외출" };
	private JButton approval;

	public StudentAttendanceManagement() {
		drawUI();
	}

	private void drawUI() {
		this.setSize(new Dimension(400, 500));
		this.setLayout(new BorderLayout());
		JPanel tablePanel = new JPanel();
		tablePanel.add(new JScrollPane(getStudent()));
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(getApproval());
		
		this.add(tablePanel, BorderLayout.NORTH);
		this.add(buttonPanel, BorderLayout.SOUTH);
		//CommonSetting.locationCenter(this);
	}

	private JTable getStudent() {
		if (student == null) {
			student = new JTable();
			DefaultTableModel model = new DefaultTableModel() {
				@Override
				public boolean isCellEditable(int row, int column) {
					return column == 4;
				}
			};
			student.getTableHeader().setPreferredSize(new Dimension(30,30));
			model.addColumn("번호");
			model.addColumn("이름");
			model.addColumn("출석");
			model.addColumn("퇴근");
			model.addColumn("결과");

			String[] studentdumpData = { "1", "asd", "123:123123", "123:123", "asd" };
			model.addRow(studentdumpData);
			student.setModel(model);

			JComboBox<String> comboBox = new JComboBox<>(selectOptions);
			TableColumn statusColumn = student.getColumnModel().getColumn(4); // "상태" 열 인덱스
			statusColumn.setCellEditor(new DefaultCellEditor(comboBox));

			student.getTableHeader().setReorderingAllowed(false);
			student.getTableHeader().setResizingAllowed(false);
			student.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					// 상세정보 추가
				}
			});
		}
		return student;
	}

	private JButton getApproval() {
		if(approval == null) {
			approval = new JButton("출결 승인");
			approval.setPreferredSize(new Dimension(100,50));
			approval.setBorder(new RoundedBorder(20));
			approval.setBackground(Color.WHITE);
			approval.addActionListener(e->{
				JOptionPane.showConfirmDialog(this, "승인");
			});
		}
		return approval;
	}
}
