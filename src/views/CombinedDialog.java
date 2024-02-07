package views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import models.dto.UserDTO;
import views.student.AttendStatus;

public class CombinedDialog extends JDialog {
	private String userId;

	public CombinedDialog(String userId) {
		this.userId = userId;
		this.setSize(800, 600);
		this.setLayout(new BorderLayout());

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point centerPoint = ge.getCenterPoint();
		int X = centerPoint.x - this.getWidth() / 2;
		int Y = centerPoint.y - this.getWidth() / 2;
		this.setLocation(X, Y);

		MonthlyAttendanceLog mal = new MonthlyAttendanceLog(userId);

		JButton excel = new JButton("Excel");
		AttendStatus as = new AttendStatus(userId);


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



	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {

			CombinedDialog dialog = new CombinedDialog(null);
			dialog.setVisible(true);

		});
	}

}
