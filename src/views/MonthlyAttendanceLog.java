package views;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableCellRenderer;

public class MonthlyAttendanceLog extends JFrame {

    private JPanel jPanel;
    private JTable jTable;
    private JButton before, next;

    EtchedBorder eborder = new EtchedBorder();

    public MonthlyAttendanceLog() {
        this.setSize(600, 500);
        this.setTitle("한달 출석 로그");

        // 테이블을 JScrollPane에 넣은 후, 패널에 추가
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(new JScrollPane(getJTable()), BorderLayout.CENTER);

        // 버튼을 담은 패널 생성
        JPanel buttonPanel = new JPanel();
        JButton before = new JButton("이전");
        JButton next = new JButton("다음");
        buttonPanel.add(before);
        buttonPanel.add(next);

        // 패널을 Frame에 추가
        this.add(tablePanel, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);
    }

    public JTable getJTable() {
        if (jTable == null) {
            String[] columnNames = { "일자", "출석시간", "퇴근시간", "결과" };
            Object[][] rowData = { };

            jTable = new JTable(rowData, columnNames);

            // 각 셀의 레이아웃을 설정하는 Renderer
            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                        boolean hasFocus, int row, int column) {
                    Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    ((JComponent) c).setBorder(eborder); // 각 셀에 테두리 설정
                    
                    return c;
                }
            };

            // 각 셀에 Renderer 설정
            for (int i = 0; i < jTable.getColumnCount(); i++) {
                jTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }

            // 헤더의 높낮이 조절
            jTable.getTableHeader().setPreferredSize(new java.awt.Dimension(30, 30));

            // 헤더에 테두리 추가
            jTable.getTableHeader().setBorder(eborder);
            jTable.setBorder(eborder);
            
            ((DefaultTableCellRenderer) jTable.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
        }

        return jTable;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MonthlyAttendanceLog monthlyAttendanceLog = new MonthlyAttendanceLog();
                monthlyAttendanceLog.setVisible(true);
            }
        });
    }
}


