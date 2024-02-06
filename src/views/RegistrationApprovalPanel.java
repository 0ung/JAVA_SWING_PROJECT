package views;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class RegistrationApprovalPanel extends JPanel {
	private JTable student;
	private String[] selectOptions = { "학생", "강사", "승인거부" };
	private JButton approval;

	public RegistrationApprovalPanel() {
		drawUI();
	}

	private void drawUI() {
		this.setSize(new Dimension(400, 500));
		this.setLayout(new BorderLayout());
		//this.add(new JScrollPane(getStudent()));
		JPanel tablePanel = new JPanel();
		tablePanel.add(new JScrollPane(getStudent()));
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(getApproval());
		
		this.add(tablePanel,BorderLayout.NORTH);
		this.add(buttonPanel,BorderLayout.SOUTH);
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
			model.addColumn("아이디");
			model.addColumn("이름");
			model.addColumn("반");
			model.addColumn("권한");

			String[] studentdumpData = { "1", "asd", "123:123123", "123:123", "asd" };
			model.addRow(studentdumpData);
			student.setModel(model);
			student.setRowHeight(25);

			JComboBox<String> comboBox = new JComboBox<>(selectOptions);
			TableColumn statusColumn = student.getColumnModel().getColumn(4); // "상태" 열 인덱스
			statusColumn.setCellEditor(new DefaultCellEditor(comboBox));

			DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
			dtcr.setHorizontalAlignment(SwingConstants.CENTER);
			TableColumnModel tcm = student.getColumnModel();
			
			for(int i=0; i<tcm.getColumnCount(); i++) {
				tcm.getColumn(i).setCellRenderer(dtcr);
			}
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
		if (approval == null) {
			JPanel jp = new JPanel();
			approval = new JButton("전체승인");
			approval.setPreferredSize(new Dimension(100,50));
			approval.setBorder(new RoundedBorder(20));
			jp.setOpaque(false);
			
			jp.add(approval);
			//jp.add(approval, BorderLayout.);
			approval.setBackground(Color.WHITE);
			approval.addActionListener(e -> {
				JOptionPane.showConfirmDialog(this, "승인");
			});
		}
		return approval;
	}
}
