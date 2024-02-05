package views;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import constant.Editable;

public class ClassManage extends JPanel {
	private JButton create, delete;
	private String[] selectClass = { "1반", "2반", "3반" };
	
	private JComboBox<String> comboBox;
	private JPanel createClass, deleteClass, updateClass;

	public ClassManage() {
		setLayout(new GridLayout(3, 2, 50, 60));
		setSize(new Dimension(100, 200));
		add(getCreateClass());
		add(getDeleteClass());
		add(getUpdateClass());
	}
	
	

	private JPanel getCreateClass() {

		if (createClass == null) {
			createClass = new JPanel();
			create = new JButton("생성");
			create.setBackground(Color.WHITE);
			create.setBorder(new RoundedBorder(20));
			create.setPreferredSize(new Dimension(80,60));
			create.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
			create.addActionListener(e->{
				ClassForm classForm = new ClassForm(Editable.CREATE);
				classForm.setVisible(true);
			});

			createClass.setLayout(new GridLayout(1,2, 50, 50));
			JLabel classCreate = new JLabel("반 생성", JLabel.CENTER);
			classCreate.setFont(new Font("맑은 고딕", Font.PLAIN, 40));
			createClass.add(classCreate);
			
			createClass.add(create);

		}
		return createClass;
	}

	private JPanel getDeleteClass() {

		if (deleteClass == null) {
			deleteClass = new JPanel();
			delete = new JButton("삭제");
			delete.setBackground(Color.WHITE);
			delete.setPreferredSize(new Dimension(80,60));
			delete.setBorder(new RoundedBorder(20));
			delete.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
			comboBox = new JComboBox<String>(selectClass);
			comboBox.setPreferredSize(new Dimension(80,60));
			comboBox.setFont(new Font("맑은고딕", Font.PLAIN, 30));
			deleteClass.setLayout(new GridLayout(1,3, 50, 50));
			deleteClass.add(comboBox);
			deleteClass.add(delete);

		}
		return deleteClass;
	}

	private JPanel getUpdateClass() {

		if (updateClass == null) {
			updateClass = new JPanel();
			create = new JButton("수정");
			create.setPreferredSize(new Dimension(80,60));
			create.setBorder(new RoundedBorder(20));
			create.setBackground(Color.WHITE);
			create.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
			comboBox = new JComboBox<String>(selectClass);
			comboBox.setPreferredSize(new Dimension(80,60));
			comboBox.setFont(new Font("맑은고딕", Font.PLAIN, 30));
			updateClass.setLayout(new GridLayout(1,2,50,50));
			JLabel update = new JLabel("반 수정", JLabel.CENTER);
			update.setFont(new Font("맑은 고딕", Font.PLAIN, 40));
			updateClass.add(update);
			
			
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
