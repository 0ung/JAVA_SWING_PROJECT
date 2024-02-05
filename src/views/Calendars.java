package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Calendars extends JFrame {
	private static final Calendar cal = Calendar.getInstance();
	String[] dayAr = { "일", "월", "화", "수", "목", "금", "토" };
	DateBox[] dateBoxAr = new DateBox[dayAr.length * 6];
	JPanel pNorth, pCenter;
	JButton btNext, btPrev;
	JLabel lbTittle;

	private int keyYear;
	private int keyMonth;
	private int startDay;
	private int lastDay;

	public Calendars() {
		pNorth = new JPanel();
		btPrev = new JButton("이전");
		lbTittle = new JLabel("년도올예정", SwingConstants.CENTER);
		btNext = new JButton("다음");
		pCenter = new JPanel();

		lbTittle.setPreferredSize(new Dimension(300, 30));

		pNorth.add(btPrev);
		pNorth.add(lbTittle);
		pNorth.add(btNext);
		add(pNorth, BorderLayout.NORTH);
		add(pCenter);

		btPrev.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateMonth(-1);
			}
		});
		btNext.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateMonth(1);
			}
		});
		getDateInfo();
		setDateTitle();
		createDay();
		createDate();
		printDate();
		setBounds(0, 0, 750, 780);
		CommonSetting.locationCenter(this);
	}

	// 시작 요일, 끝 날 등 구하기
	public void getDateInfo() {
		keyYear = cal.get(Calendar.YEAR);
		keyMonth = cal.get(Calendar.MONTH);
		startDay = getFirstDayOfMonth(keyYear, keyMonth);
		lastDay = getLastDate(keyYear, keyMonth);
	}

	// 요일 생성
	public void createDay() {
		for (int i = 0; i < 7; i++) {
			DateBox dayBox = new DateBox(dayAr[i], Color.gray, 100, 70, "");
			pCenter.add(dayBox);
		}
	}

	// 날짜 생성
	public void createDate() {
		for (int i = 0; i < dayAr.length * 6; i++) {
			DateBox dateBox = new DateBox("", Color.gray, 100, 100, "");
			pCenter.add(dateBox);
			dateBoxAr[i] = dateBox;
		}
	}

	public int getFirstDayOfMonth(int yy, int mm) {
		Calendar cal = Calendar.getInstance(); // 날짜 객체 생성
		cal.set(yy, mm, 1);
		return cal.get(Calendar.DAY_OF_WEEK) - 1;// 요일은 1부터 시작으로 배열과 쌍을 맞추기 위해 -1
	}

	public int getLastDate(int yy, int mm) {
		Calendar cal = Calendar.getInstance();
		cal.set(yy, mm + 1, 0);
		// 마지막 날을 의미한다.
		return cal.get(Calendar.DATE);
	}

	// 날짜 박스에 날짜 출력하기
	public void printDate() {
		int n = 1;
		for (int i = 0; i < dateBoxAr.length; i++) {
			if (i >= startDay && n <= lastDay) {
				dateBoxAr[i].setDay(Integer.toString(n));
				dateBoxAr[i].setShowNotice(getNoticeList(n));
				dateBoxAr[i].repaint();
				n++;
				for (MouseListener listener : dateBoxAr[i].getMouseListeners()) {
					dateBoxAr[i].removeMouseListener(listener);
				}
				dateBoxAr[i].addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						Notice notice = new Notice("공지사항");
						notice.setVisible(true);
					}
				});
			} else {
				dateBoxAr[i].setDay("");
				dateBoxAr[i].setShowNotice("");
				dateBoxAr[i].repaint();
			}
		}
	}

	// 몇년도 몇월인지를 보여주는 타이틀 라벨의 값을 변경
	public void setDateTitle() {
		lbTittle.setText(keyYear + "-" + DateBox.getZeroString(keyMonth + 1));
		lbTittle.updateUI();
	}

	// 달력을 넘기거나 전으로 이동할 때 날짜 객체에 대한 정보도 변경
	public void updateMonth(int data) {
		// 캘린더 객체에 들어있는 날짜를 기준으로 월 정보를 바꿔준다.
		cal.set(Calendar.MONTH, keyMonth + data);
		getDateInfo();
		printDate();
		setDateTitle();
	}

	// notice 정보 가져오기
	public String getNoticeList(int n) {
		if (n == 2) {
			return "2일";
		} else {
			return "2일 아님";
		}
	}

	public static void main(String[] args) {
		Calendars calendars = new Calendars();
		calendars.setVisible(true);
	}
}
