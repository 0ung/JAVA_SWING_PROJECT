package views;

import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Notice extends JDialog {
	private JTable noticeTable;

	public Notice() {
		setTitle("공지사항");
		add(new JScrollPane(getTable()));
		setSize(new Dimension(800,800));
	}

	public JTable getTable() {
		if (noticeTable == null) {
			noticeTable = new JTable();
			DefaultTableModel tableModel = (DefaultTableModel) noticeTable.getModel();
			tableModel.addColumn("번호");
			tableModel.addColumn("제목");
			tableModel.addColumn("글쓴이");
			tableModel.addColumn("등록일자");
			
		}
		return noticeTable;
	}

	public static void main(String[] args) {
		Notice notice = new Notice();
		notice.setVisible(true);
	}
}
