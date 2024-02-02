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
	private Color color;
	private int width;
	private int height;

	public DateBox(String day, Color color, int width, int height) {
		this.day = day;
		this.width = width;
		this.height = height;
		this.color = color;
		setPreferredSize(new Dimension(width, height));
	}

	public void paint(Graphics g) {
		g.fillRect(0, 0, width, height);
		g.setColor(Color.yellow);
		g.drawString(day, 10, 20);
	}

	public static String getZeroString(int n) {
		return (n < 10) ? "0" + n : Integer.toString(n);
	}
}
