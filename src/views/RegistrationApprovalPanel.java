package views;

import java.awt.Dimension;
import java.awt.FlowLayout;
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

public class RegistrationApprovalPanel extends JPanel {
	private JTable student;
	private String[] selectOptions = { "학생", "강사", "승인거부" };
	private JButton approval;

	public RegistrationApprovalPanel() {
		drawUI();
	}

	private void drawUI() {
		this.setSize(new Dimension(400, 500));
		this.setLayout(new FlowLayout());
		add(new JScrollPane(getStudent()));
		add(getApproval());
		CommonSetting.locationCenter(this);
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
			model.addColumn("번호");
			model.addColumn("아이디");
			model.addColumn("이름");
			model.addColumn("반");
			model.addColumn("권한");

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
		if (approval == null) {
			approval = new JButton("전체승인");
			approval.addActionListener(e -> {
				JOptionPane.showConfirmDialog(this, "승인");
			});
		}
		return approval;
	}
}
