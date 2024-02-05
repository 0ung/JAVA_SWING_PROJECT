package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import constant.Editable;

public class ClassManage extends JPanel {
	private JButton create, delete;
	private String[] selectClass = { "1반", "2반", "3반" };
	private JComboBox<String> comboBox;
	private JPanel createClass, deleteClass, updateClass;

	public ClassManage() {
		setLayout(new GridLayout(3, 1));
		setSize(new Dimension(100, 200));
		add(getCreateClass());
		add(getDeleteClass());
		add(getUpdateClass());
	}

	private JPanel getCreateClass() {

		if (createClass == null) {
			createClass = new JPanel();
			create = new JButton("생성");
			create.addActionListener(e -> {
				ClassForm classForm = new ClassForm(Editable.CREATE);
				classForm.setVisible(true);
			});

			createClass.setLayout(new FlowLayout(FlowLayout.CENTER));
			createClass.add(new JLabel("반 생성"));
			createClass.add(create);

		}
		return createClass;
	}

	private JPanel getDeleteClass() {

		if (deleteClass == null) {
			deleteClass = new JPanel();
			delete = new JButton("삭제");
			comboBox = new JComboBox<String>(selectClass);
			deleteClass.setLayout(new FlowLayout(FlowLayout.CENTER));
			deleteClass.add(comboBox);
			deleteClass.add(delete);

		}
		return deleteClass;
	}

	private JPanel getUpdateClass() {

		if (updateClass == null) {
			updateClass = new JPanel();
			create = new JButton("수정");
			comboBox = new JComboBox<String>(selectClass);
			updateClass.setLayout(new FlowLayout(FlowLayout.CENTER));
			updateClass.add(new JLabel("반 수정"));
			updateClass.add(comboBox);
			updateClass.add(create);

		}
		return updateClass;
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.getContentPane().add(new ClassManage());
		frame.setVisible(true);
		frame.setSize(new Dimension(500, 500));
	}

}
