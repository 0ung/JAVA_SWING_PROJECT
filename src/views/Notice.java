package views;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Notice extends JPanel {
	private JTable noticeTable;
	private JDialog dialog;

	public Notice(JDialog dialog) {
		add(new JScrollPane(getTable()));
		setSize(new Dimension(800, 800));
		CommonSetting.locationCenter(this);
		this.dialog = dialog;
	}

	public JTable getTable() {
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

			noticeTable.setModel(tableModel);
			noticeTable.getTableHeader().setReorderingAllowed(false);
			noticeTable.getTableHeader().setResizingAllowed(false);
			noticeTable.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					DetailNotice detailNotice = new DetailNotice(dialog);
					detailNotice.setVisible(true);
				}
			});
		}
		return noticeTable;
	}

}