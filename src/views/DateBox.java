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
	private String day; // 일자 표시용 문자열
	@Setter
	private String showNotice; // 공지사항 표시용 문자열
	private Color color; // 박스의 배경색
	private int width; // 박스의 너비
	private int height; // 박스의 높이

	// 추가된 날짜 관련 필드
	@Getter
	private int year; // 년
	@Getter
	private int month; // 월
	@Getter
	private int dayInt; // 일 (정수형)

	// 생성자 수정: 년, 월, 일 정보를 받아 저장
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
	// 날짜 관련 정보에 대한 Getter 메서드 추가

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g); // 기본 배경 그리기 처리
		g.setColor(color);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.white);
		g.drawString(day, 10, 20);
		g.drawString(showNotice, 20, 40);
	}

	// 날짜 숫자를 문자열로 변환할 때 0을 추가하는 메서드는 그대로 유지
	public static String getZeroString(int n) {
		return (n < 10) ? "0" + n : Integer.toString(n);
	}
}
