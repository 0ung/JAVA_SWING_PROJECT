package views;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

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

import models.dto.UserDTO;
import models.service.UserService;

public class RegistrationApprovalPanel extends JPanel {
	private UserService service = new UserService();
	private JTable student;
	private String[] selectOptions = { "학생", "강사", "승인거부" };
	private JButton approval;
	private Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	private int width = (int) screen.getWidth() / 3;
	private int height = (int) screen.getHeight() / 2;
	
	public RegistrationApprovalPanel() {
		drawUI();
	}

	private void drawUI() {
		this.setSize(new Dimension(width, height));
		this.setLayout(new BorderLayout());
		JPanel tablePanel = new JPanel();
		JScrollPane js = new JScrollPane(getStudent());
		js.setPreferredSize(new Dimension(width-100, height-100));
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
			model.addColumn("이름");
			model.addColumn("반");
			model.addColumn("권한");

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

			ArrayList<UserDTO> list = service.getAuthMember();
			for (int i = 0; i < list.size(); i++) {
				String[] arr = { String.valueOf(i + 1), list.get(i).getUserId(), list.get(i).getUserName(),
						list.get(i).getClassName(), "미승인" };
				model.addRow(arr);
			}

			student.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					// 상세정보 추가
				}
			});
		}
		return student;
	}

	private void updatePanelContents(DefaultTableModel model) {
		ArrayList<UserDTO> list = service.getAuthMember(); // 가정: 서비스 메서드를 통해 최신 목록을 가져옴
		model.setRowCount(0); // 테이블의 기존 데이터를 모두 제거

		// 최신 데이터로 테이블 모델을 업데이트
		for (UserDTO user : list) {
			String[] arr = { String.valueOf(model.getRowCount() + 1), user.getUserId(), user.getUserName(),
					user.getClassName(), "미승인" };
			model.addRow(arr);
		}
		this.revalidate();
		this.repaint();
	}

	private JButton getApproval() {
		if (approval == null) {
			JPanel jp = new JPanel();
			approval = new JButton("전체확인");
			approval.setPreferredSize(new Dimension(100, 40));
			approval.setBorder(new RoundedBorder(20));
			jp.setOpaque(false);

			jp.add(approval);
			approval.setBackground(Color.WHITE);
			approval.addActionListener(e -> {
				DefaultTableModel model = (DefaultTableModel) student.getModel();
				int rowCount = model.getRowCount(); // 테이블의 행 수를 가져옴
				for (int i = 0; i < rowCount; i++) {
					UserDTO userDTO = new UserDTO();
					String userId = model.getValueAt(i, 1).toString(); // '아이디' 열이 1번 인덱스라고 가정
					int authority = service.checkAuth(model.getValueAt(i, 4).toString()); // '권한' 열이 4번 인덱스라고 가정
					userDTO.setUserId(userId);
					userDTO.setAuthority(authority);
					service.updateAuth(userDTO);
				}
				JOptionPane.showMessageDialog(this, "승인이 완료되었습니다.");
				updatePanelContents(model);
			});
		}
		return approval;
	}
}
