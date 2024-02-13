package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
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

	private Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	private int width = (int) screen.getWidth() / 3;
	private int height = (int) screen.getHeight() / 2;

	public Calendars(JFrame jFrame, UserDTO user) {
		this.user = user;
		this.frame = jFrame;
		setLayout(new BorderLayout());
		pNorth = new JPanel();
		btPrev = new JButton("<");
		btPrev.setBorder(new RoundedBorder(8));
		btPrev.setBackground(Color.WHITE);
		lbTittle = new JLabel("년도올예정", SwingConstants.CENTER);
		btNext = new JButton(">");
		btNext.setBorder(new RoundedBorder(8));
		btNext.setBackground(Color.WHITE);
		pCenter = new JPanel(new GridLayout(7, 7, 5, 5));

		lbTittle.setPreferredSize(new Dimension(300, 30));
		pNorth.add(btPrev);
		pNorth.add(lbTittle);
		pNorth.add(btNext);
		add(pNorth, BorderLayout.NORTH);
		add(pCenter, BorderLayout.CENTER);

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
		setPreferredSize(new Dimension(width, height));
		CommonSetting.locationCenter(this);
	}

	public void getDateInfo() {
		keyYear = cal.get(Calendar.YEAR);
		keyMonth = cal.get(Calendar.MONTH);
		startDay = getFirstDayOfMonth(keyYear, keyMonth);
		lastDay = getLastDate(keyYear, keyMonth);
	}

	public void createDay() {
		for (int i = 0; i < 7; i++) {
			DateBox dayBox = new DateBox(dayAr[i], Color.gray, 100, 70, "", keyYear, keyMonth, 0);
			pCenter.add(dayBox);
		}
	}

	public void createDate() {
		Calendar tempCal = (Calendar) cal.clone();
		tempCal.set(Calendar.DAY_OF_MONTH, 1);
		int firstDayOfWeek = tempCal.get(Calendar.DAY_OF_WEEK) - 1;

		for (int i = 0; i < dateBoxAr.length; i++) {
			int dayOfMonth = i - firstDayOfWeek + 1;
			if (dayOfMonth > 0 && dayOfMonth <= lastDay) {
				dateBoxAr[i] = new DateBox(String.valueOf(dayOfMonth), Color.gray, 100, 100, "", keyYear, keyMonth,
						dayOfMonth);
			} else {
				dateBoxAr[i] = new DateBox("", Color.gray, 100, 100, "", keyYear, keyMonth, 0);
			}
			pCenter.add(dateBoxAr[i]);
		}
	}

	public int getFirstDayOfMonth(int yy, int mm) {
		Calendar cal = Calendar.getInstance();
		cal.set(yy, mm, 1);
		return cal.get(Calendar.DAY_OF_WEEK) - 1;
	}

	public int getLastDate(int yy, int mm) {
		Calendar cal = Calendar.getInstance();
		cal.set(yy, mm + 1, 0);
		return cal.get(Calendar.DATE);
	}

	public void printDate(int year, int month) {
		for (int i = 0; i < dateBoxAr.length; i++) {
			DateBox db = dateBoxAr[i];
			int dayOfMonth = db.getDayInt();
			if (dayOfMonth > 0) {
				db.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						Notice notice = new Notice(user);
						notice.createNoticeDialog(db.getYear(), db.getMonth(), db.getDayInt(), 0).setVisible(true);
					}
				});
			} else {
				db.setDay("");
				db.setShowNotice("");
			}
			db.repaint();
		}
	}

	public void setDateTitle() {
		lbTittle.setText(keyYear + "-" + DateBox.getZeroString(keyMonth + 1));
		lbTittle.updateUI();
	}

	public void updateMonth(int delta) {
		cal.add(Calendar.MONTH, delta);
		keyYear = cal.get(Calendar.YEAR);
		keyMonth = cal.get(Calendar.MONTH);
		getDateInfo();
		setDateTitle();

		pCenter.removeAll();
		pCenter.revalidate();
		pCenter.repaint();
		createDay();
		createDate();
		printDate(keyYear, keyMonth);
	}

}