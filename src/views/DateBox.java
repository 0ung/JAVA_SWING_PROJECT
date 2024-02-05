package views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import lombok.Getter;
import lombok.Setter;

public class DateBox extends JPanel {
	@Setter
	private String day;
	@Setter
	private String showNotice;
	private Color color;
	private int width;
	private int height;

	public DateBox(String day, Color color, int width, int height, String showNotice) {
		this.day = day;
		this.showNotice = showNotice;
		this.width = width;
		this.height = height;
		this.color = color;

		setPreferredSize(new Dimension(width, height));
	}

	public void paint(Graphics g) {
		g.setColor(color);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.white);
		g.drawString(day, 10, 20);
		g.drawString(showNotice, 20, 40);
	}

	public static String getZeroString(int n) {
		return (n < 10) ? "0" + n : Integer.toString(n);
	}
}
