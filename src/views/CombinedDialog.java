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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import models.dto.UserDTO;

public class CombinedDialog extends JDialog {
	private UserDTO user;

	public CombinedDialog(UserDTO user) {
//		super(, "학생 출석 현황", true);
		this.user = user;
		this.setSize(800, 600);
		this.setLayout(new BorderLayout());

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point centerPoint = ge.getCenterPoint();
		int X = centerPoint.x - this.getWidth() / 2;
		int Y = centerPoint.y - this.getWidth() / 2;
		this.setLocation(X, Y);

		MonthlyAttendanceLog mal = new MonthlyAttendanceLog(user);

		JButton excel = new JButton("Excel");

		AttendStatus as = new AttendStatus(user);

		JPanel pnl = new JPanel();
		pnl.setLayout(new BoxLayout(pnl, BoxLayout.Y_AXIS));
		pnl.add(mal);
		pnl.add(excel);
		pnl.add(as);

		mal.setPreferredSize(new Dimension(300, 225));
		excel.setPreferredSize(new Dimension(30, 20));
		as.setPreferredSize(new Dimension(300, 200));

		excel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
//				createExcelFile("테스트");
			}
		});

		this.add(pnl);
	}

//	private void createExcelFile(String name) {
//	    String filePath = System.getProperty("user.dir") + "/excel/" + name + ".xlsx"; // 경로 구분자 추가
//
//	    try {
//	        Workbook workbook = new XSSFWorkbook();
//	        Sheet sheet = workbook.createSheet("Attendance");
//
//	        // Get table model data (가정: jTable는 외부에서 생성 및 데이터로 채워진 상태)
//	        JTable jTable = new JTable();
//	        DefaultTableModel model = (DefaultTableModel) jTable.getModel();
//
//	        // Write table data to Excel
//	        for (int i = 0; i < model.getRowCount(); i++) {
//	            Row row = sheet.createRow(i); // 행 생성
//	            for (int j = 0; j < model.getColumnCount(); j++) {
//	                Object value = model.getValueAt(i, j);
//	                Cell cell = row.createCell(j);
//	                cell.setCellValue(value != null ? value.toString() : "");
//	            }
//	        }
//
//	        // Write workbook to file
//	        FileOutputStream outputStream = new FileOutputStream(filePath);
//	        workbook.write(outputStream);
//	        workbook.close();
//	        outputStream.close();
//
//	        JOptionPane.showMessageDialog(this, "엑셀 파일이 생성되었습니다.", "Success", JOptionPane.INFORMATION_MESSAGE);
//	    } catch (IOException e) {
//	        JOptionPane.showMessageDialog(this, "엑셀 파일 생성 중 오류가 발생했습니다.", "Error", JOptionPane.ERROR_MESSAGE);
//	        e.printStackTrace();
//	    }
//	}


	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {

			CombinedDialog dialog = new CombinedDialog(null);
			dialog.setVisible(true);

		});
	}

}
