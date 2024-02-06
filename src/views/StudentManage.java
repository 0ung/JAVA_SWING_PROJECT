package views;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class StudentManage extends JPanel {

	private JTable student;

	public StudentManage() {
		drawUI();
	}

	private void drawUI() {
		this.setSize(new Dimension(400, 500));
		add(new JScrollPane(getStudent()));
		//CommonSetting.locationCenter(this);

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
			
			student.getTableHeader().setPreferredSize(new Dimension(30,30));
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

	public static void main(String[] args) {
        JFrame frame = new JFrame("학생 관리 시스템");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);

        // 메인 패널에 GridLayout 설정
        JPanel mainPanel = new JPanel(new GridLayout(1, 3)); // 1행 3열
        
        mainPanel.setPreferredSize(new Dimension(10,10));

        // 각 컴포넌트(패널) 생성 및 메인 패널에 추가
        mainPanel.add(new StudentManage());
        mainPanel.add(new StudentAttendanceManagement());
        mainPanel.add(new RegistrationApprovalPanel());

        // 프레임에 메인 패널 추가
        frame.add(mainPanel);

        frame.setVisible(true);
	}
}
