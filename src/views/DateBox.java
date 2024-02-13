package views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import lombok.Getter;
import lombok.Setter;

public class DateBox extends JPanel {
	@Getter
	@Setter
	private String day;
	@Setter
	private String showNotice;
	private Color color;
	private int width;
	private int height;

	@Getter
	private int year;
	@Getter
	private int month;
	@Getter
	private int dayInt;

	public DateBox(String day, Color color, int width, int height, String showNotice, int year, int month, int dayInt) {
		this.day = day;
		this.showNotice = showNotice;
		this.color = color;
		this.width = width;
		this.height = height;
		this.year = year;
		this.month = month;
		this.dayInt = dayInt;

		setPreferredSize(new Dimension(width, height));
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
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
