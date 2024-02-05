package views;

import java.awt.BorderLayout;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class LogsDialog extends JDialog {
    private JTextArea attendanceStatus;
    private JTextArea attendanceLog;

    public LogsDialog(JFrame parent, String studentName) {
        super(parent, "Attendance for " + studentName, true);
        setSize(400, 300);

        attendanceStatus = new JTextArea();
        attendanceStatus.setEditable(false);

        attendanceLog = new JTextArea();
        attendanceLog.setEditable(false);

        JScrollPane attendanceStatusScrollPane = new JScrollPane(attendanceStatus);
        JScrollPane attendanceLogScrollPane = new JScrollPane(attendanceLog);

        setLayout(new BorderLayout());
        add(attendanceStatusScrollPane, BorderLayout.CENTER);
        add(attendanceLogScrollPane, BorderLayout.SOUTH);

        setLocationRelativeTo(parent);

        // TODO: 학생 이름을 기반으로 해당 학생의 출결 데이터 조회 및 표시
        // 여기서는 간단한 예제로 고정된 데이터를 사용하겠습니다.
        String statusText = "Attendance Status for " + studentName + ":\n" +
                "Date 1: Present\n" +
                "Date 2: Absent\n" +
                "Date 3: Present";
        attendanceStatus.setText(statusText);

        String logText = "Attendance Log for " + studentName + ":\n" +
                "Date 1: Checked in at 9:00 AM\n" +
                "Date 2: Checked out at 5:00 PM\n" +
                "Date 3: Checked in at 9:15 AM";
        attendanceLog.setText(logText);
    }
}
