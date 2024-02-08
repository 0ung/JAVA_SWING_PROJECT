package views;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import models.dto.UserDTO;

public class NoticeFactory {

	private static UserDTO user;

	public static JPanel createNoticePanel(UserDTO user, JFrame jframe) {
		NoticeFactory.user = user;
		System.out.println(user);

		Notice notice = new Notice(new JDialog(), jframe, user);
		JPanel jPanel = new JPanel(new BorderLayout());
		JButton jButton = new JButton("생성");
		jButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				DetailNotice detailDialog = new DetailNotice(jframe, user);
				// 텍스트 필드를 편집 가능하게 설정합니다.
				detailDialog.setFieldsEditable(true);
				detailDialog.setVisible(true);

			}
		});

		// 버튼을 추가할 새로운 패널을 생성합니다.
		JPanel buttonPanel = new JPanel();
		// 버튼 패널의 선호하는 크기를 설정합니다. 이것은 버튼의 크기를 간접적으로 조절합니다.
		buttonPanel.setPreferredSize(new Dimension(800, 50)); // 예를 들어, 너비는 800, 높이는 50으로 설정합니다.
		// 버튼을 버튼 패널에 추가합니다.
		buttonPanel.add(jButton);
		// 기존 jPanel에 Notice 컴포넌트와 버튼 패널을 추가합니다.
		jPanel.add(notice, BorderLayout.CENTER);
		jPanel.add(buttonPanel, BorderLayout.SOUTH); // buttonPanel을 SOUTH에 추가합니다.

		if (user.getAuthority() == 1) {
			jButton.setVisible(false);
		}
		if (user.getAuthority() == 2) {
			jButton.setVisible(true);
		}

		return jPanel;

	}

	public static JDialog createNoticeDialog(JFrame jframe) {
		JDialog dialog = new JDialog(jframe, "notice", true);
		Notice notice = new Notice(dialog, jframe, user);
		dialog.getContentPane().add(notice, BorderLayout.CENTER);
		dialog.pack();
		return dialog;
	}
}
