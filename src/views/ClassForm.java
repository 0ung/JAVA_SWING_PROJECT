package views;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import constant.Editable;

public class ClassForm extends JDialog {

	private JButton confirm;
	private JTextField teacher, roomNum, progress, classNum;
	private JPanel classPanel, roomPanel, progressPanel, teacherPanel;

	public ClassForm(Editable editable) {
		setLayout(new GridLayout(1, 1));
		setSize(new Dimension(100, 200));
		add(getClassNum());
		add(getRoomPanel());
		add(getProgressPanel());
		add(getTeacherPanel());
	}

	private JPanel getClassNum() {
		if (classPanel == null) {
			classPanel = new JPanel();
			classNum = new JTextField();
			classNum.setColumns(10);
			classPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
			classPanel.add(new JLabel("반"));
			classPanel.add(classNum);
		}
		return classPanel;
	}

	private JPanel getRoomPanel() {
		if (roomPanel == null) {
			roomPanel = new JPanel();
			roomNum = new JTextField();
			roomNum.setColumns(10);
			roomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
			roomPanel.add(new JLabel("반"));
			roomPanel.add(classNum);
		}
		return classPanel;
	}

	private JPanel getProgressPanel() {
		if (progressPanel == null) {
			progressPanel = new JPanel();
			progress = new JTextField();
			progress.setColumns(10);
			progressPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
			progressPanel.add(new JLabel("반"));
			progressPanel.add(classNum);
		}
		return classPanel;
	}

	private JPanel getTeacherPanel() {
		if (teacherPanel == null) {
			teacherPanel = new JPanel();
			teacher = new JTextField();
			teacher.setColumns(10);
			teacherPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
			teacherPanel.add(new JLabel("반"));
			teacherPanel.add(classNum);
		}
		return classPanel;
	}

}
