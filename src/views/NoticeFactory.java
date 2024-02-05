package views;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class NoticeFactory {
	public static JPanel createNoticePanel() {
		Notice notice = new Notice(new JDialog());
		JPanel jPanel = new JPanel(new BorderLayout());
		jPanel.add(notice, BorderLayout.CENTER);
		return jPanel;
	}

	public static JDialog createNoticeDialog(JFrame jframe) {
		JDialog dialog = new JDialog(jframe, "notice", true);
		Notice notice = new Notice(dialog);
		dialog.getContentPane().add(notice, BorderLayout.CENTER);
		dialog.pack();
		return dialog;
	}
}
