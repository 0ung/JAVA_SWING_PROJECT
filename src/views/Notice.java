package views;

import java.awt.Dimension;
import java.awt.event.MouseEvent;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Notice extends JDialog {
	private JTable noticeTable;

	public Notice() {
		setTitle("공지사항");
		add(new JScrollPane(getTable()));
		setSize(new Dimension(800, 800));
	}

	public JTable getTable() {
		if (noticeTable == null) {
			noticeTable = new JTable();
			DefaultTableModel tableModel = (DefaultTableModel) noticeTable.getModel();
			tableModel.addColumn("번호");
			tableModel.addColumn("제목");
			tableModel.addColumn("글쓴이");
			tableModel.addColumn("등록일자");
			String[] arr = { "테스트1", "테스트", "김영웅", "0204" };
			tableModel.addRow(arr);
			noticeTable.addMouseListener(null);;
		}
		return noticeTable;
	}

	public static void main(String[] args) {
		Notice notice = new Notice();
		notice.setVisible(true);
	}
}