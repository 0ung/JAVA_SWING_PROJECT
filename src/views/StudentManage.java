package views;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import models.dto.UserDTO;

public class StudentManage extends JPanel {

	private JTable student;
	private UserDTO user;

	public StudentManage(UserDTO user) {
		this.user = user;
		drawUI();
	}

	private void drawUI() {
		this.setSize(new Dimension(400, 500));
		add(new JScrollPane(getStudent()));
		// CommonSetting.locationCenter(this);

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
			model.addColumn("이름");

			// 데이터 가져올 부분
			String[] studentdumpData = { "1", "asd" };

			model.addRow(studentdumpData);
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
						JDialog dialog = new CombinedDialog(null);
						dialog.setVisible(true);
					}

				}
			});
		}
		return student;
	}

}
