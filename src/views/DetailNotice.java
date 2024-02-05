package views;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JPanel;

public class DetailNotice extends JDialog {
	private JPanel title, createTime, writer;
	private JTextArea content;

	public DetailNotice() {
		setSize(new Dimension(700, 800));
		this.add(title());
		this.add(getCreateTime());
		this.add(getContent());
		this.add(getWriter());
	}

	public JPanel title() {
		if (title == null) {
			title = new JPanel();
			title.setLayout(new FlowLayout(FlowLayout.RIGHT));
			title.add(new JLabel("제목"));
		}
		return title;
	}

	public JPanel getCreateTime() {
		if (createTime == null) {
			createTime = new JPanel();
			createTime.setLayout(new FlowLayout(FlowLayout.RIGHT));
			createTime.add(new JLabel("작성일"));
			createTime.add(new JTextField());
		}
		return createTime;
	}

	public JPanel getWriter() {
		if (writer == null) {
			writer = new JPanel();
			writer.setLayout(new FlowLayout(FlowLayout.RIGHT));
			writer.add(new JLabel("작성자"));
			writer.add(new JTextField());
		}
		return writer;
	}

	public JTextArea getContent() {
		if (content == null) {
			content = new JTextArea();
			content.setLayout(new FlowLayout(FlowLayout.RIGHT));
			content.add(new JLabel("내용"));
			content.add(new JTextArea());
		}
		return content;
	}

	public static void main(String[] args) {
		DetailNotice detailNotice = new DetailNotice();
		detailNotice.setVisible(true);
	}
}
