package views;

import java.awt.Dimension;
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

public class Notice extends JPanel {
	private JTable noticeTable;
	private JDialog dialog;
	private JFrame jFrame;
	private UserDTO user;

	public Notice(JDialog dialog, JFrame jframe, UserDTO user) {
		this.jFrame = jframe;
		this.user = user;
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
			
			
			noticeTable.getTableHeader().setPreferredSize(new Dimension(30,30));
			noticeTable.setModel(tableModel);
			
			noticeTable.setRowHeight(25);
			noticeTable.getTableHeader().setReorderingAllowed(false);
			noticeTable.getTableHeader().setResizingAllowed(false);
			
			DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
			dtcr.setHorizontalAlignment(SwingConstants.CENTER);
			TableColumnModel tcm = noticeTable.getColumnModel();
			
			for(int i=0; i<tcm.getColumnCount(); i++) {
				tcm.getColumn(i).setCellRenderer(dtcr);
			}
			//noticeTable.getColumn(dtcr);
			noticeTable.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					DetailNotice detailNotice = new DetailNotice(jFrame, user);
					detailNotice.setVisible(true);
				}
			});
		}
		return noticeTable;
	}

}