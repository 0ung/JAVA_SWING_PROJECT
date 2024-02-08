package views;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import models.dao.NoticeDAOImpl;
import models.dto.NoticeDto;
import models.dto.UserDTO;

public class Notice extends JPanel {
	private JTable noticeTable;
	private JDialog dialog;
	private JFrame jFrame;
	private UserDTO user;
	private NoticeDto noticeDto;

	public Notice(JDialog dialog, JFrame jframe, UserDTO user, int important) {
		this.jFrame = jframe;
		this.user = user;
		add(new JScrollPane(getTable(important)));
		setSize(new Dimension(800, 800));
		CommonSetting.locationCenter(this);
		this.dialog = dialog;
	}

	public JTable getTable(int important) {
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

			NoticeDAOImpl daoImpl = new NoticeDAOImpl();
			List<NoticeDto> list = daoImpl.readID(important); // 가정: readID가 List<NoticeDto>를 반환

			for (NoticeDto notice : list) {
				Object[] arr = { String.valueOf(notice.getNoticeId()), // 가정: getNoticeId()가 long 또는 int 타입
						notice.getTitle(), notice.getUserName(), // UserDTO에 getUserName()이 정의되어 있어야 함
						notice.getCreateTime().toString() // SimpleDateFormat을 사용하여 포매팅할 수 있음
				};
				tableModel.addRow(arr);
			}

			noticeTable.setModel(tableModel);
			customizeTable();
		}
		return noticeTable;
	}

	private void customizeTable() {
		noticeTable.getTableHeader().setPreferredSize(new Dimension(30, 30));
		noticeTable.setRowHeight(25);
		noticeTable.getTableHeader().setReorderingAllowed(false);
		noticeTable.getTableHeader().setResizingAllowed(false);

		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(SwingConstants.CENTER);
		TableColumnModel tcm = noticeTable.getColumnModel();

		for (int i = 0; i < tcm.getColumnCount(); i++) {
			tcm.getColumn(i).setCellRenderer(dtcr);
		}

		noticeTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int a = noticeTable.getSelectedRow();
				System.out.println(noticeTable.getValueAt(a, 0));
				System.out.println(noticeTable.getValueAt(a, 2));
				DetailNotice detailNotice = new DetailNotice(jFrame, user, false);
				detailNotice.setVisible(true);
				detailNotice.setStatusComboBoxEditable(false);
			}
		});

	}

}