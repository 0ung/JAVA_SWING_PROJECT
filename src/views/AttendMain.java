package views;

import javax.swing.SwingUtilities;

public class AttendMain {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			LoginScreen loginScreen = new LoginScreen();
			loginScreen.setVisible(true);
		});
	}
}
