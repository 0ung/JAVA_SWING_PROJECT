package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Logs extends JFrame {
    private DefaultListModel<String> studentListModel;
    private JList<String> studentList;
    private JTextArea attendanceStatus;
    private JTextArea attendanceLog;

    public Logs() {
        setTitle("Attendance System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        studentListModel = new DefaultListModel<>();
        studentListModel.addElement("Student 1");
        studentListModel.addElement("Student 2");
        studentListModel.addElement("Student 3");

        studentList = new JList<>(studentListModel);
        studentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        studentList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                showStudentAttendanceDialog();
            }
        });

        attendanceStatus = new JTextArea();
        attendanceStatus.setEditable(false);

        attendanceLog = new JTextArea();
        attendanceLog.setEditable(false);

        JScrollPane studentListScrollPane = new JScrollPane(studentList);
        JScrollPane attendanceStatusScrollPane = new JScrollPane(attendanceStatus);
        JScrollPane attendanceLogScrollPane = new JScrollPane(attendanceLog);

        setLayout(new BorderLayout());
        add(studentListScrollPane, BorderLayout.WEST);
        add(attendanceStatusScrollPane, BorderLayout.CENTER);
        add(attendanceLogScrollPane, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
    }

    private void showStudentAttendanceDialog() {
        String selectedStudent = studentList.getSelectedValue();
        if (selectedStudent != null) {
            LogsDialog dialog = new LogsDialog(this, selectedStudent);
            dialog.setVisible(true);
        }
    }

}
