package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import models.dto.AttendanceStatusDTO;
import models.dto.UserDTO;
import models.service.AttendService;
import models.service.UserService;

public class StudentAttendanceManagement extends JPanel {
	private JTable student;
	private String[] selectOptions = { "출석", "결석", "지각", "조퇴", "외출" };
	private JButton approval;
	private UserDTO user;
	private UserService service = new UserService();
	private AttendService attendService = new AttendService();
	private Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	private int width = (int) screen.getWidth() / 3;
	private int height = (int) screen.getHeight() / 2;

	public StudentAttendanceManagement(UserDTO user) {
		this.user = user;
		drawUI();
	}

	private void drawUI() {
		this.setSize(new Dimension(400, 500));
		this.setLayout(new BorderLayout());
		JPanel tablePanel = new JPanel();
		JScrollPane js = new JScrollPane(getStudent());
		js.setPreferredSize(new Dimension(width-50, height-90));
		tablePanel.add(js);
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(getApproval());

		this.add(tablePanel, BorderLayout.NORTH);
		this.add(buttonPanel, BorderLayout.SOUTH);
		// CommonSetting.locationCenter(this);
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
			student.getTableHeader().setPreferredSize(new Dimension(30, 30));

			model.addColumn("번호");
			model.addColumn("아이디");
			model.addColumn("출석");
			model.addColumn("퇴근");
			model.addColumn("결과");

			ArrayList<AttendanceStatusDTO> list = attendService.getList(user.getUserId());
			for (int i = 0; i < list.size(); i++) {
				String[] arr = new String[5]; // 배열 크기를 5로 변경
				arr[0] = String.valueOf(i + 1);
				arr[1] = list.get(i).getUserId();
				arr[2] = list.get(i).getStartTime();
				arr[3] = list.get(i).getEndTime();
				String result = ""; // "결과" 컬럼에 들어갈 문자열
				try {
					int a = attendService.attendAlgorithm(list.get(i));
					switch (a) {
					case 0:
						result = "결석";
						break;
					case 1:
						result = "지각";
						break;
					case 2:
						result = "조퇴";
						break;
					case 3:
						result = "출석";
						break;
					default:
						result = "결석"; // 기타 경우
					}
				} catch (ParseException e) {
					e.printStackTrace();
					result = "오류"; // 오류 발생 시
				}
				arr[4] = result;
				model.addRow(arr);
			}
			student.setModel(model);
			student.setRowHeight(25);

			JComboBox<String> comboBox = new JComboBox<>(selectOptions);
			TableColumn statusColumn = student.getColumnModel().getColumn(4); // "상태" 열 인덱스
			statusColumn.setCellEditor(new DefaultCellEditor(comboBox));

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

				}
			});
		}
		return student;
	}

	private JButton getApproval() {
		if (approval == null) {
			approval = new JButton("출결 승인");
			approval.setPreferredSize(new Dimension(100, 40));
			approval.setBorder(new RoundedBorder(20));
			approval.setBackground(Color.WHITE);
			approval.addActionListener(e -> {
				DefaultTableModel model = (DefaultTableModel) student.getModel();
				int rowCount = model.getRowCount(); // 테이블의 행 수를 가져옵니다.

				for (int i = 0; i < rowCount; i++) {
					AttendanceStatusDTO dto = new AttendanceStatusDTO();
					dto.setYearMonthDay(LocalDate.now().toString());
					String key = String.valueOf(model.getValueAt(i, 4));
					String userId = String.valueOf(model.getValueAt(i, 1));
					switch (key) {
					case "결석":
						dto.setUserId(userId);
						dto.setAbsentCnt(1);
						break;
					case "지각":
						dto.setUserId(userId);
						dto.setLateCnt(1);
						break;
					case "외출":
						dto.setUserId(userId);
						dto.setOutingCnt(1);
						break;
					case "조퇴":
						dto.setUserId(userId);
						dto.setEarlyleaveCnt(1);
						break;
					default:
						break;
					}
					attendService.updateAttend(user.getUserId(), dto);
				}
				JOptionPane.showMessageDialog(approval, "승인이 완료되었습니다.");
			});
		}
		return approval;
	}

}
