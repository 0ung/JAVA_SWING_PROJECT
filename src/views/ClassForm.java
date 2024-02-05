package views;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import constant.Editable;

public class ClassForm extends JDialog {

	private JButton confirm;
	private JTextField teacher, roomNum, progress, classNum;
	private JPanel classPanel, roomPanel, progressPanel, teacherPanel, btnPanel;

	public ClassForm(Editable editable) {
		super();
		setTitle("클래스 폼");
		setModal(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		// 패널 추가
		switch (editable) {
		case CREATE:
			add(getClassNum());
			add(getRoomPanel());
			add(getTeacherPanel());
			add(getProgressPanel());
			add(getBtnPanel());
			break;
		case UPDATE:
			add(getClassNum());
			add(getRoomPanel());
			add(getTeacherPanel());
			add(getProgressPanel());
			add(getBtnPanel());
			break;
		default:
			break;
		}

		setLayout(new GridLayout(5, 1));
		setSize(new Dimension(500, 600));

		setLocationRelativeTo(null); // 화면 중앙에 위치

	}

	private JPanel getClassNum() {
		classPanel = new JPanel();
		classNum = new JTextField();
		classNum.setPreferredSize(new Dimension(300, 50));
		JLabel label = new JLabel("반 :");
		label.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		label.setSize(30, 20);
		label.setPreferredSize(new Dimension(100, 50));
		classPanel.add(label);
		classPanel.add(classNum);
		return classPanel;
	}

	private JPanel getRoomPanel() {
		roomPanel = new JPanel();
		roomNum = new JTextField();
		roomNum.setPreferredSize(new Dimension(300, 50));
		JLabel label = new JLabel("반 호실 :");
		label.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		label.setSize(30, 20);
		label.setPreferredSize(new Dimension(100, 50));
		roomPanel.add(label);
		roomPanel.add(roomNum);
		return roomPanel;
	}

	private JPanel getProgressPanel() {
		progressPanel = new JPanel();
		progress = new JTextField();
		progress.setPreferredSize(new Dimension(300, 50));
		JLabel label = new JLabel("진도 :");
		label.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		label.setSize(30, 20);
		label.setPreferredSize(new Dimension(100, 50));
		progressPanel.add(label);
		progressPanel.add(progress);
		return progressPanel;
	}

	private JPanel getTeacherPanel() {
		teacherPanel = new JPanel();
		teacher = new JTextField();
		teacher.setPreferredSize(new Dimension(300, 50));
		JLabel label = new JLabel("담당 교사 :");
		label.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		label.setSize(30, 20);
		label.setPreferredSize(new Dimension(100, 50));
		teacherPanel.add(label);
		teacherPanel.add(teacher);
		return teacherPanel;
	}

	private JPanel getBtnPanel() {
		if (btnPanel == null) {
			btnPanel = new JPanel();
			btnPanel.add(getConfirm());

			confirm.setPreferredSize(new Dimension(200, 50));
			JLabel label = new JLabel("확인");
			label.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
			label.setSize(30, 20);
			label.setPreferredSize(new Dimension(100, 50));
			btnPanel.add(label);
			btnPanel.add(confirm);

		}
		return btnPanel;
	}

	private JButton getConfirm() {
		if (confirm == null) {
			confirm = new JButton();
			confirm.setText("Ok");
//			confirm.setLayout(new GridLayout(1, 1));
			confirm.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					System.out.println("클릭");
				}
			});
		}
		return confirm;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			ClassForm form = new ClassForm(Editable.UPDATE);
			form.setVisible(true);
		});
	}
}
