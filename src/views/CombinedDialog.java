package views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CombinedDialog extends JDialog {

	public CombinedDialog(JFrame parentFrame) {
		super(parentFrame, "학생 출석 현황", true);
		this.setSize(800, 600);
		this.setLayout(new BorderLayout());

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point centerPoint = ge.getCenterPoint();
		int X = centerPoint.x - this.getWidth() / 2;
		int Y = centerPoint.y - this.getWidth() / 2;
		this.setLocation(X, Y);

		MonthlyAttendanceLog mal = new MonthlyAttendanceLog();
		
		JButton excel = new JButton("Excel");
        
        AttendStatus as = new AttendStatus();
        
		JPanel pnl = new JPanel();
		pnl.setLayout(new BoxLayout(pnl, BoxLayout.Y_AXIS));
		pnl.add(mal);
		pnl.add(excel);
		pnl.add(as);
		
		mal.setPreferredSize(new Dimension(300,225));
		excel.setPreferredSize(new Dimension(30,20));
		as.setPreferredSize(new Dimension(300,200));
		
		excel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
            }
        });
		
		this.add(pnl);
	}
	
 
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {

			CombinedDialog dialog = new CombinedDialog(null);
			dialog.setVisible(true);

		});
	}

}
