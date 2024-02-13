package views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import constant.Editable;
import models.dao.AttendStatusDAO;
import models.dao.ClassDAO;
import models.dto.AvailableDayDTO;
import models.dto.UserDTO;

public class ClassManage extends JPanel {

	private JButton create, delete;
	private JComboBox<String> comboBox, comboBox2, yearCombo, monthCombo;
	private JPanel createClass, deleteClass, updateClass;
	private UserDTO user;
	private AttendStatusDAO attendStatusDAO = new AttendStatusDAO();
	private Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	private int width = (int) screen.getWidth() / 3;
	private int height = (int) screen.getHeight() / 2;

	public ClassManage(UserDTO user) {
		this.user = user;
		setLayout(new GridLayout(5, 2, 40, 40));
		setSize(new Dimension(width / 10, height / 8));
		initializeComponents();
		updateClassComboBox();
	}

	private void initializeComponents() {
		comboBox = new JComboBox<>();
		comboBox.setPreferredSize(new Dimension(width / 10, height / 8));
		comboBox.setFont(new Font("맑은고딕", Font.PLAIN, 30));

		comboBox2 = new JComboBox<>();
		comboBox2.setPreferredSize(new Dimension(width / 10, height / 8));
		comboBox2.setFont(new Font("맑은고딕", Font.PLAIN, 30));

		yearCombo = new JComboBox<>();
		monthCombo = new JComboBox<>();

		add(getCreateClass());
		add(getDeleteClass());
		add(getUpdateClass());
		add(getAvailableday());
	}

	private void updateClassComboBox() {
		ClassDAO classDAO = new ClassDAO();
		ArrayList<String> classList = classDAO.fetchAllClasses();
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(classList.toArray(new String[0]));
		comboBox.setModel(model);
		comboBox2.setModel(model);
	}

	private JPanel getCreateClass() {

		if (createClass == null) {
			createClass = new JPanel();
			create = new JButton("생성하기");
			create.setBackground(Color.WHITE);
			create.setPreferredSize(new Dimension(width / 10, height / 8));
			create.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
			create.addActionListener(e -> {
				ClassForm classForm = new ClassForm(Editable.CREATE);
				classForm.setVisible(true);
				updateClassComboBox();
			});

			createClass.setLayout(new GridLayout(1, 2, 50, 50));
			JLabel classCreate = new JLabel("반 생성", JLabel.CENTER);
			classCreate.setFont(new Font("맑은 고딕", Font.PLAIN, 30));
			createClass.add(classCreate);

			createClass.add(create);

		}
		return createClass;
	}

	private JPanel getDeleteClass() {
		if (deleteClass == null) {
			deleteClass = new JPanel(new GridLayout(1, 3, 50, 50));
			delete = new JButton("삭제");
			delete.setBackground(Color.WHITE);
			delete.setPreferredSize(new Dimension(width / 10, height / 8));
			delete.setFont(new Font("맑은 고딕", Font.PLAIN, 20));

			JLabel classDelete = new JLabel("반 삭제", JLabel.CENTER);
			classDelete.setFont(new Font("맑은 고딕", Font.PLAIN, 30));
			deleteClass.add(classDelete);
			deleteClass.add(comboBox2);
			deleteClass.add(delete);

			delete.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String selectedClass = (String) comboBox.getSelectedItem();
					ClassDAO classDAO = new ClassDAO();
					classDAO.deleteClass(selectedClass);
					JOptionPane.showMessageDialog(ClassManage.this, "반 정보가 삭제되었습니다.");
					updateClassComboBox();
				}
			});
		}
		return deleteClass;
	}

	private JPanel getUpdateClass() {

		if (updateClass == null) {
			updateClass = new JPanel(new GridLayout(1, 3, 50, 50));
			create = new JButton("수정");
			create.setPreferredSize(new Dimension(width / 10, height / 8));
			create.setBackground(Color.WHITE);
			create.setFont(new Font("맑은 고딕", Font.PLAIN, 20));

			updateClass.setLayout(new GridLayout(1, 2, 50, 50));

			JLabel update = new JLabel("반 수정", JLabel.CENTER);
			update.setFont(new Font("맑은 고딕", Font.PLAIN, 30));
			updateClass.add(update);

			updateClass.add(comboBox);
			updateClass.add(create);

			create.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String selectedClassName = (String) comboBox.getSelectedItem();

					ClassForm classForm = new ClassForm(Editable.UPDATE);
					classForm.setClassInfo(selectedClassName);
					classForm.setVisible(true);
				}
			});
		}
		return updateClass;
	}

	private JPanel getAvailableday() {
		yearCombo = new JComboBox<String>();
		monthCombo = new JComboBox<String>();
		JPanel availableday = new JPanel(new GridLayout(1, 6, 10, 10));
		JTextField txtAvailableday = new JTextField();
		JButton submit = new JButton("저장");

		Calendar now = Calendar.getInstance();
		int currentYear = now.get(Calendar.YEAR);
		yearCombo.addItem(String.valueOf(currentYear - 1));
		yearCombo.addItem(String.valueOf(currentYear));

		for (int month = 1; 12 >= month; month++) {
			monthCombo.addItem(String.valueOf(month));
		}

		JLabel lblYear = new JLabel("년도");
		JLabel lblMonth = new JLabel("월");
		JLabel lblDay = new JLabel("일자");
		lblYear.setHorizontalAlignment(JLabel.CENTER);
		lblYear.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		lblMonth.setHorizontalAlignment(JLabel.CENTER);
		lblMonth.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		lblDay.setHorizontalAlignment(JLabel.CENTER);
		lblDay.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		submit.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		submit.setBackground(Color.WHITE);
		submit.setPreferredSize(new Dimension(width / 10, height / 8));

		submit.addActionListener(e -> {
			String year = String.valueOf(yearCombo.getSelectedItem());
			String month = String.format("%02d", Integer.parseInt((String) monthCombo.getSelectedItem()));
			AvailableDayDTO dayDTO = new AvailableDayDTO();
			dayDTO.setAvailableDay(Integer.parseInt(txtAvailableday.getText()));
			dayDTO.setClassName(user.getClassName());
			StringBuilder builder = new StringBuilder();
			builder.append(year);
			builder.append("-");
			builder.append(month);
			dayDTO.setAvailableYearMonth(builder.toString());
			int confirm = JOptionPane.showConfirmDialog(this, "한번 입력된 날짜는 수정이 불가합니다. 계속하시겠습니까?", "경고!",
					JOptionPane.YES_NO_OPTION);
			if (confirm == JOptionPane.YES_OPTION) {
				try {
					attendStatusDAO.insertDay(dayDTO);
					JOptionPane.showMessageDialog(this, "날짜가 저장되었습니다.");
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(this, "이미 저장되었습니다.");
				}
			}
		});

		availableday.add(lblYear);
		availableday.add(yearCombo);
		availableday.add(lblMonth);
		availableday.add(monthCombo);
		availableday.add(lblDay);
		availableday.add(txtAvailableday);
		availableday.add(submit);
		return availableday;
	}

}
