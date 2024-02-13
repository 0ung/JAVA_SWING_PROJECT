package views;

import constant.Editable;
import models.dao.ClassDAO;
import models.dao.ClassDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClassForm extends JDialog {

	private JTextField teacher, roomNum, progress, classNum;
	private JPanel classPanel, roomPanel, progressPanel, teacherPanel, btnPanel;

	public ClassForm(Editable editable) {
		super();
		setTitle("클래스 폼");
		setModal(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		switch (editable) {
		case CREATE:
			add(getClassNum());
			add(getTeacherPanel());
			add(getRoomPanel());
			add(getProgressPanel());
			add(getBtnPanel());
			break;
		case UPDATE:
			add(getClassNum());
			add(getTeacherPanel());
			add(getRoomPanel());
			add(getProgressPanel());
			add(getUpdateBtnPanel());
			break;
		default:
			break;
		}

		setLayout(new GridLayout(5, 1));
		setSize(new Dimension(500, 600));

		setLocationRelativeTo(null);

		placeholder(classNum, "?반 형식으로 입력하세요");
		placeholder(roomNum, "?호실 형식으로 입력하세요");
		placeholder(teacher, "강사님 이름을 입력하세요");
		placeholder(progress, "진도 상태를 입력하세요");
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

	public void validationClass(String input) throws RuntimeException {
		Pattern pattern = Pattern.compile("\\d+반");
		Matcher matcher = pattern.matcher(input);
		if (!matcher.matches()) {
			throw new RuntimeException();
		}
	}

	public void validationRoom(String input) throws RuntimeException {
		Pattern pattern = Pattern.compile("\\d+호");
		Matcher matcher = pattern.matcher(input);
		if (!matcher.matches()) {
			throw new RuntimeException();
		}
	}

	public void validationTeacher(String input) throws RuntimeException {
		Pattern pattern = Pattern.compile("[가-힣]{2,5}");
		Matcher matcher = pattern.matcher(input);
		if (!matcher.matches()) {
			throw new RuntimeException();
		}
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
	};

	private JPanel getBtnPanel() {
		ClassDAO classDAO = new ClassDAO();

		if (btnPanel == null) {
			btnPanel = new JPanel();

			JButton okButton = new JButton("확인");
			okButton.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
			okButton.setPreferredSize(new Dimension(80, 50));
			okButton.setBackground(new Color(237, 248, 221));

			okButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String classNumText = classNum.getText();
					String teacherText = teacher.getText();
					String roomNumText = roomNum.getText();
					String progressText = progress.getText();
					try {
						validationClass(classNumText);
						validationRoom(roomNumText);
						validationTeacher(teacherText);
						classDAO.insertClass(classNumText, teacherText, roomNumText, progressText);
						JOptionPane.showMessageDialog(okButton, "생성이 완료되었습니다.");
						dispose();
					} catch (RuntimeException e2) {
						JOptionPane.showMessageDialog(okButton, "입력이 올바르지 않습니다.");
					}
					dispose();
				}
			});

			JButton cancelButton = new JButton("취소");

			cancelButton.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
			cancelButton.setPreferredSize(new Dimension(80, 50));
			cancelButton.setBackground(new Color(237, 248, 221));
			cancelButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});

			btnPanel.add(okButton);
			btnPanel.add(cancelButton);
		}
		return btnPanel;
	}

	private JPanel getUpdateBtnPanel() {
		ClassDAO classDAO = new ClassDAO();
		JPanel updateBtnPanel = new JPanel();

		JButton updateButton = new JButton("수정");
		updateButton.setBackground(new Color(237, 248, 221));
		updateButton.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		updateButton.setPreferredSize(new Dimension(100, 50));
		updateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String classNumText = classNum.getText();
				String roomNumText = roomNum.getText();
				String progressText = progress.getText();
				String teacherText = teacher.getText();
				try {
					validationClass(classNumText);
					validationRoom(roomNumText);
					validationTeacher(teacherText);
					classDAO.updateClass(classNumText, roomNumText, progressText, teacherText);
					JOptionPane.showMessageDialog(updateBtnPanel, "수정이 완료되었습니다.");
					dispose();
				} catch (RuntimeException e2) {
					JOptionPane.showMessageDialog(updateButton, "입력이 올바르지 않습니다.");
				}

			}
		});

		JButton cancelButton = new JButton("취소");
		cancelButton.setBackground(new Color(237, 248, 221));
		cancelButton.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		cancelButton.setPreferredSize(new Dimension(100, 50));
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		updateBtnPanel.add(updateButton);
		updateBtnPanel.add(cancelButton);
		return updateBtnPanel;
	}

	public void setClassInfo(String className) {
		ClassDAO classDAO = new ClassDAO();
		String[] classInfo = classDAO.getClassInfo(className);

		if (classInfo != null) {
			classNum.setText(classInfo[0]);
			teacher.setText(classInfo[1]);
			roomNum.setText(classInfo[2]);
			progress.setText(classInfo[3]);
		} else {
			JOptionPane.showMessageDialog(null, "해당 반의 정보를 가져올 수 없습니다.");
		}
	}

	private void placeholder(JTextField textField, String placeholder) {
		textField.setForeground(Color.GRAY);
		textField.setText(placeholder);
		textField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (textField.getText().equals(placeholder)) {
					textField.setText("");
					textField.setForeground(Color.BLACK);
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (textField.getText().isEmpty()) {
					textField.setForeground(Color.GRAY);
					textField.setText(placeholder);
				}
			}
		});
	}
}
