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

import models.dto.UserDTO;

public class Calendars extends JPanel {
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
	private JFrame frame;
	private UserDTO user;

	public Calendars(JFrame jFrame, UserDTO user) {
		this.user = user;
		this.frame = jFrame;
		setLayout(new BorderLayout()); // 패널의 레이아웃을 BorderLayout으로 설정
		pNorth = new JPanel();
		btPrev = new JButton("<");
		btPrev.setBorder(new RoundedBorder(8));
		btPrev.setBackground(Color.WHITE);
		lbTittle = new JLabel("년도올예정", SwingConstants.CENTER);
		btNext = new JButton(">");
		btNext.setBorder(new RoundedBorder(8));
		btNext.setBackground(Color.WHITE);
		pCenter = new JPanel(new GridLayout(7, 7, 5, 5)); // pCenter에 GridLayout 설정

		lbTittle.setPreferredSize(new Dimension(300, 30));
		pNorth.add(btPrev);
		pNorth.add(lbTittle);
		pNorth.add(btNext);
		add(pNorth, BorderLayout.NORTH); // pNorth를 상단에 배치
		add(pCenter, BorderLayout.CENTER); // pCenter를 중앙에 배치

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
		printDate(keyYear, keyMonth);
		setPreferredSize(new Dimension(600, 600)); // JFrame에서 pack()을 호출할 경우를 대비해 선호 크기 설정
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
			DateBox dayBox = new DateBox(dayAr[i], Color.gray, 100, 70, "", keyYear, keyMonth, 0);
			pCenter.add(dayBox);
		}
	}

	public void createDate() {
		Calendar tempCal = (Calendar) cal.clone(); // 현재 캘린더 상태 복제
		tempCal.set(Calendar.DAY_OF_MONTH, 1);
		int firstDayOfWeek = tempCal.get(Calendar.DAY_OF_WEEK) - 1;

		for (int i = 0; i < dateBoxAr.length; i++) {
			int dayOfMonth = i - firstDayOfWeek + 1;
			// 조건 수정: 현재 월의 일자에 해당하는 DateBox만 생성
			if (dayOfMonth > 0 && dayOfMonth <= lastDay) {
				dateBoxAr[i] = new DateBox(String.valueOf(dayOfMonth), Color.gray, 100, 100, "", keyYear, keyMonth,
						dayOfMonth);
			} else {
				// 현재 월의 일자가 아닌 경우 비워진 DateBox 생성
				dateBoxAr[i] = new DateBox("", Color.gray, 100, 100, "", keyYear, keyMonth, 0);
			}
			pCenter.add(dateBoxAr[i]);
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
	public void printDate(int year, int month) {
		for (int i = 0; i < dateBoxAr.length; i++) {
			DateBox db = dateBoxAr[i];
			int dayOfMonth = db.getDayInt(); // 수정: DateBox에서 일자 정보 직접 가져오기
			if (dayOfMonth > 0) {
				db.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						Notice notice = new Notice(user);
						notice.createNoticeDialog(db.getYear(), db.getMonth(), db.getDayInt(), 0).setVisible(true);
					}
				});
			} else {
				db.setDay(""); // 날짜가 없는 경우 빈 문자열로 설정
				db.setShowNotice("");
			}
			db.repaint();
		}
	}

	// 몇년도 몇월인지를 보여주는 타이틀 라벨의 값을 변경
	public void setDateTitle() {
		lbTittle.setText(keyYear + "-" + DateBox.getZeroString(keyMonth + 1));
		lbTittle.updateUI();
	}

	public void updateMonth(int delta) {
		// 캘린더 객체에 들어있는 날짜를 기준으로 월 정보를 바꿔준다.
		cal.add(Calendar.MONTH, delta);
		keyYear = cal.get(Calendar.YEAR);
		keyMonth = cal.get(Calendar.MONTH);
		getDateInfo(); // 새로운 keyYear, keyMonth에 대한 정보를 다시 가져온다.
		setDateTitle(); // 타이틀 라벨 업데이트

		// pCenter 패널의 모든 컴포넌트를 제거하고 다시 그리기
		pCenter.removeAll(); // 기존 날짜 박스 제거
		pCenter.revalidate(); // 레이아웃 매니저에게 패널의 레이아웃을 다시 계산하도록 지시
		pCenter.repaint(); // 패널을 다시 그리도록 지시
		createDay();
		createDate(); // 변경된 달에 대한 새로운 날짜 박스 생성
		printDate(keyYear, keyMonth); // 변경된 달에 대한 날짜 박스에 날짜 출력
	}

}