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
		js.setPreferredSize(new Dimension(width - 100, height - 140));
		tablePanel.add(js);

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(getApproval());

		this.add(tablePanel, BorderLayout.NORTH);
		this.add(buttonPanel, BorderLayout.SOUTH);
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
			student.getTableHeader().setBackground(new Color(237, 248, 221));
			model.addColumn("번호");
			model.addColumn("아이디");
			model.addColumn("이름");
			model.addColumn("반");
			model.addColumn("권한");

			student.setModel(model);
			student.setRowHeight(25);

			JComboBox<String> comboBox = new JComboBox<>(selectOptions);
			TableColumn statusColumn = student.getColumnModel().getColumn(4);
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
		}
		return student;
	}

	private void updatePanelContents(DefaultTableModel model) {
		ArrayList<UserDTO> list = service.getAuthMember();
		model.setRowCount(0);

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
			approval.setBorder(new RoundedBorder(10));
			approval.setBackground(new Color(237, 248, 221));
			jp.setOpaque(false);

			jp.add(approval);
			approval.addActionListener(e -> {
				DefaultTableModel model = (DefaultTableModel) student.getModel();
				int rowCount = model.getRowCount();
				for (int i = 0; i < rowCount; i++) {
					UserDTO userDTO = new UserDTO();
					String userId = model.getValueAt(i, 1).toString();
					int authority = service.checkAuth(model.getValueAt(i, 4).toString());
					userDTO.setUserId(userId);
					userDTO.setAuthority(authority);
					if (authority == 3) {
						service.deleteUser(userDTO);
					}
					service.updateAuth(userDTO);
				}
				JOptionPane.showMessageDialog(this, "승인이 완료되었습니다.");
				updatePanelContents(model);
			});
		}
		return approval;
	}
}
