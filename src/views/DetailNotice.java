package views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class DetailNotice extends JDialog {
    private JPanel titlePanel, createTimePanel, writerPanel;
    private JLabel titleLabel, createTimeLabel, writerLabel;
    private JTextArea contentTextArea;
    private JTextField titleTextField, createTimeTextField, writerTextField;

    public DetailNotice() {
        initializeUI();
    }

    private void initializeUI() {
        setSize(new Dimension(700, 800));
        setLayout(new BorderLayout(5, 5));

        add(getTitlePanel(), BorderLayout.NORTH);
        add(getContentPanel(), BorderLayout.CENTER);
        add(getInfoPanel(), BorderLayout.SOUTH);

        // Set the dialog properties
        setTitle("상세보기");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center on screen
    }

    private JPanel getTitlePanel() {
        if (titlePanel == null) {
            titlePanel = new JPanel();
            titleLabel = new JLabel("제목:");
            titleTextField = new JTextField(20);
            titleTextField.setEditable(false); // Make it non-editable
            titlePanel.add(titleLabel);
            titlePanel.add(titleTextField);
        }
        return titlePanel;
    }

    private JPanel getInfoPanel() {
        JPanel infoPanel = new JPanel();

        createTimePanel = new JPanel();
        createTimeLabel = new JLabel("작성일:");
        createTimeTextField = new JTextField(20);
        createTimeTextField.setEditable(false);
        createTimePanel.add(createTimeLabel);
        createTimePanel.add(createTimeTextField);

        writerPanel = new JPanel();
        writerLabel = new JLabel("작성자:");
        writerTextField = new JTextField(20);
        writerTextField.setEditable(false);
        writerPanel.add(writerLabel);
        writerPanel.add(writerTextField);

        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.add(createTimePanel);
        infoPanel.add(writerPanel);

        return infoPanel;
    }

    private JScrollPane getContentPanel() {
        contentTextArea = new JTextArea(10, 20);
        contentTextArea.setEditable(false);
        JScrollPane contentScrollPane = new JScrollPane(contentTextArea);
        return contentScrollPane;
    }

    public void setData(String title, String createTime, String writer, String content) {
        titleTextField.setText(title);
        createTimeTextField.setText(createTime);
        writerTextField.setText(writer);
        contentTextArea.setText(content);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DetailNotice detailNotice = new DetailNotice();
            detailNotice.setData("테스트 제목", "2024-02-05", "김영웅", "이것은 상세 내용의 예시입니다...");
            detailNotice.setVisible(true);
        });
    }
}
