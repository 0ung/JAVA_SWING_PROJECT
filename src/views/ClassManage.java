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
import models.dao.AttendStatusDAOImpl;
import models.dao.AttendStautsDAO;
import models.dao.ClassDAO;
import models.dto.AvailableDayDTO;
import models.dto.UserDTO;

public class ClassManage extends JPanel {

	private JButton create, delete;
	private JComboBox<String> comboBox, comboBox2, yearCombo, monthCombo;
	private JPanel createClass, deleteClass, updateClass;
	private UserDTO user;
	private AttendStautsDAO attendStatusDAO = new AttendStatusDAOImpl();
	private Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	private int width = (int) screen.getWidth() / 3;
	private int height = (int) screen.getHeight() / 2;

	public ClassManage(UserDTO user) {
		this.user = user;
		setLayout(new GridLayout(4, 2, 50, 60));
		setSize(new Dimension(width/10, height/8));
		initializeComponents();
		updateClassComboBox();
	}

	private void initializeComponents() {
		comboBox = new JComboBox<>(); // 초기화 위치 변경
		comboBox.setPreferredSize(new Dimension(width/10, height/8));
		comboBox.setFont(new Font("맑은고딕", Font.PLAIN, 30));

		comboBox2 = new JComboBox<>(); // 초기화 위치 변경
		comboBox2.setPreferredSize(new Dimension(width/10, height/8));
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
			create.setBorder(new RoundedBorder(30));
			create.setPreferredSize(new Dimension(width/10, height/8));
			create.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
			create.addActionListener(e -> {
				ClassForm classForm = new ClassForm(Editable.CREATE);
				classForm.setVisible(true);
				updateClassComboBox();
			});

			createClass.setLayout(new GridLayout(1, 2, 50, 50));
			JLabel classCreate = new JLabel("반 생성", JLabel.CENTER);
			classCreate.setFont(new Font("맑은 고딕", Font.PLAIN, 40));
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
			delete.setBorder(new RoundedBorder(30));
			delete.setPreferredSize(new Dimension(width/10, height/8));
			delete.setFont(new Font("맑은 고딕", Font.PLAIN, 20));

			JLabel classDelete = new JLabel("반 삭제", JLabel.CENTER);
			classDelete.setFont(new Font("맑은 고딕", Font.PLAIN, 40));
			deleteClass.add(classDelete);
			// 이미 초기화된 comboBox를 추가
			deleteClass.add(comboBox2);
			deleteClass.add(delete);

			delete.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String selectedClass = (String) comboBox.getSelectedItem();
					ClassDAO classDAO = new ClassDAO();
					classDAO.deleteClass(selectedClass);
					JOptionPane.showMessageDialog(ClassManage.this, "반 정보가 삭제되었습니다.");
					updateClassComboBox(); // 삭제 후 comboBox 항목 갱신
				}
			});
		}
		return deleteClass;
	}

	private JPanel getUpdateClass() {

		if (updateClass == null) {
			updateClass = new JPanel(new GridLayout(1, 3, 50, 50));
			create = new JButton("수정");
			create.setPreferredSize(new Dimension(width/10, height/8));
			create.setBorder(new RoundedBorder(30));
			create.setBackground(Color.WHITE);
			create.setFont(new Font("맑은 고딕", Font.PLAIN, 20));

			updateClass.setLayout(new GridLayout(1, 2, 50, 50));

			JLabel update = new JLabel("반 수정", JLabel.CENTER);
			update.setFont(new Font("맑은 고딕", Font.PLAIN, 40));
			updateClass.add(update);

			updateClass.add(comboBox);
			updateClass.add(create);

			create.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// 선택된 반의 이름 가져오기
					String selectedClassName = (String) comboBox.getSelectedItem();

					// 선택된 반의 정보 수정 다이얼로그 생성 및 표시
					ClassForm classForm = new ClassForm(Editable.UPDATE);
					classForm.setClassInfo(selectedClassName); // 선택된 반의 이름 전달
					classForm.setVisible(true);
				}
			});
		}
		return updateClass;
	}

	private JPanel getAvailableday() {
		yearCombo = new JComboBox<String>();
		monthCombo = new JComboBox<String>();
		JPanel availableday = new JPanel(new GridLayout(1, 6, 10, 10)); // 그리드 레이아웃 업데이트
		JTextField txtAvailableday = new JTextField();
		JButton submit = new JButton("저장");

		// 콤보 박스 초기화
		Calendar now = Calendar.getInstance();
		int currentYear = now.get(Calendar.YEAR);
		int currentMonth = now.get(Calendar.MONTH); // Calendar.MONTH는 0부터 시작합니다.
		yearCombo.addItem(String.valueOf(currentYear - 1));
		yearCombo.addItem(String.valueOf(currentYear));

		for (int month = 1; 12 >= month; month++) {
			monthCombo.addItem(String.valueOf(month));
		}

		// 라벨 추가
		JLabel lblYear = new JLabel("년도");
		JLabel lblMonth = new JLabel("월");
		JLabel lblDay = new JLabel("일자"); // 추가된 라벨

		submit.setFont(new Font("맑은고딕", Font.BOLD, 10));
		submit.setBackground(Color.WHITE);
		submit.setPreferredSize(new Dimension(width/10, height/8));
		submit.setBorder(new RoundedBorder(10)); // RoundedBorder는 사용자 정의 보더 클래스로 가정

		// 저장버튼실행
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
			// 사용자가 '예'를 선택한 경우
			if (confirm == JOptionPane.YES_OPTION) {
				attendStatusDAO.insertDay(dayDTO); // 데이터 저장
				JOptionPane.showMessageDialog(this, "날짜가 저장되었습니다."); // 저장 완료 메시지 표시
			}
		});

		// 컴포넌트를 패널에 추가
		availableday.add(lblYear);
		availableday.add(yearCombo); // 가정: yearCombo는 선언되고 초기화된 JComboBox
		availableday.add(lblMonth);
		availableday.add(monthCombo); // 가정: monthCombo는 선언되고 초기화된 JComboBox
		availableday.add(lblDay);
		availableday.add(txtAvailableday);
		availableday.add(submit);
		return availableday;
	}

}
