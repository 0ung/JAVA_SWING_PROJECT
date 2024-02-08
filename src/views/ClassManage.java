package views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import constant.Editable;
import models.dao.ClassDAO;
import models.dto.UserDTO;

public class ClassManage extends JPanel {
	
	private JButton create, delete;
	private JComboBox<String> comboBox, comboBox2;
	private JPanel createClass, deleteClass, updateClass;

	private UserDTO user;

	public ClassManage(UserDTO user) {
		this.user = user;
		setLayout(new GridLayout(3, 2, 50, 60));
		setSize(new Dimension(100, 200));
		initializeComponents();
		updateClassComboBox();
	}
	
	private void initializeComponents() {
        comboBox = new JComboBox<>(); // 초기화 위치 변경
        comboBox.setPreferredSize(new Dimension(80, 60));
        comboBox.setFont(new Font("맑은고딕", Font.PLAIN, 30));
        
        comboBox2 = new JComboBox<>(); // 초기화 위치 변경
        comboBox2.setPreferredSize(new Dimension(80, 60));
        comboBox2.setFont(new Font("맑은고딕", Font.PLAIN, 30));
        
        add(getCreateClass());
        add(getDeleteClass());
        add(getUpdateClass());
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
			create = new JButton("생성");
			create.setBackground(Color.WHITE);
			create.setBorder(new RoundedBorder(20));
			create.setPreferredSize(new Dimension(80, 60));
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
            delete.setPreferredSize(new Dimension(80, 60));
            delete.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
            
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
			updateClass = new JPanel();
			create = new JButton("수정");
			create.setPreferredSize(new Dimension(80, 60));
			create.setBorder(new RoundedBorder(20));
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
	

}
