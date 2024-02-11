package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import models.dao.AttendStatusDAO;
import models.dao.AttendanceCheckDAO;
import models.dao.AttendanceCheckDAOImpl;
import models.dao.AvailableDayDAO;
import models.dto.AttendanceStatusDTO;
import models.dto.AvailableDayDTO;
import models.dto.UserDTO;

public class AttendStatus extends JPanel {

	private JPanel upperPanel, lowerPanel;
	private JPanel lateCnt, absentCnt, earlyLeaveCnt, outStandingCnt, cnt1, cnt2, cnt3, cnt4;
	private JLabel late, absent, earlyLeave, outStanding, cnt1Label, cnt2Label, cnt3Label, cnt4Label, titleLabel,
			attendanceRateLabel;;
	EtchedBorder eborder = new EtchedBorder();
	private AttendStatusDAO attendStatusDAO = new AttendStatusDAO();
	private AttendanceCheckDAO checkDAO = new AttendanceCheckDAOImpl();
	private AvailableDayDAO dayDAO = new AvailableDayDAO();

	private UserDTO user;

	public AttendStatus(UserDTO user) {
		this.user = user;
		this.setSize(600, 500);
		this.setLayout(new BorderLayout());
		// this.setLayout(new PaddedFlowLayout(FlowLayout.CENTER, 20, 20, 20));

		JPanel topSpacer = new JPanel();
		topSpacer.setPreferredSize(new Dimension(this.getWidth(), 100)); // 상단 공간의 높이 설정
		topSpacer.setOpaque(false);
		titleLabel = new JLabel();
		topSpacer.add(titleLabel);

		upperPanel = new JPanel(new GridLayout(1, 4, 20, 10));
		upperPanel.setBorder(BorderFactory.createEmptyBorder(10, 80, 10, 80));

		upperPanel.add(getLateCnt());
		upperPanel.add(getAbsentCnt());
		upperPanel.add(getEarlyLeaveCnt());
		upperPanel.add(getOutStandingCnt());

		lowerPanel = new JPanel(new GridLayout(2, 4, 20, 10));
		lowerPanel.setBorder(BorderFactory.createEmptyBorder(0, 80, 10, 80));

		lowerPanel.add(getCnt1());
		lowerPanel.add(getCnt2());
		lowerPanel.add(getCnt3());
		lowerPanel.add(getCnt4());

		attendanceRateLabel = new JLabel("전체 출석률: ");
		lowerPanel.add(attendanceRateLabel);

		lowerPanel.add(new JLabel("")); // 빈 라벨 추가
		lowerPanel.add(new JLabel("")); // 빈 라벨 추가
		lowerPanel.add(new JLabel("")); // 빈 라벨 추가

		// this.add(upperPanel2,BorderLayout.NORTH);
		this.add(topSpacer, BorderLayout.NORTH);
		this.add(upperPanel, BorderLayout.CENTER);
		this.add(lowerPanel, BorderLayout.SOUTH);

		// this.add(lowerPanel2,BorderLayout.SOUTH);

		totalAttendance(user.getUserId()); // 영웅님 꺼랑 연결
	}

	// dao에 있는 count 값을 가져와서 각 패널에 레이블로 넣어주기
	public void totalAttendance(String userId) {

		LocalDate currentDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
		String yearMonth = currentDate.format(formatter);

//		String yearMonth = "2024-03"; // date 또는 Calendar에서 year, month 가져와서 year-month 형식으로 저장하기
//		AttendanceCheckDAOImpl a = new AttendanceCheckDAOImpl();

		AttendanceStatusDTO dto = checkDAO.calculateMonthlyAttendance(userId, yearMonth);
		AttendanceStatusDTO statusDTO = attendStatusDAO.calculateAttendanceRate(userId, yearMonth);
		AvailableDayDTO dayDTO = dayDAO.readClassName(user.getClassName(), yearMonth);

		cnt1Label.setText(dto.getLateCnt() + "");
		cnt2Label.setText(dto.getAbsentCnt() + "");
		cnt3Label.setText(dto.getEarlyleaveCnt() + "");
		cnt4Label.setText(dto.getOutingCnt() + "");

		titleLabel.setText(user.getUserName() + "의 " + currentDate.getMonthValue() + "월 " + " 출결 상황판");
		titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 25));
		attendanceRateLabel
				.setText("전체 출석률: " + String.format("%.2f%%", calculate(statusDTO, dayDTO.getAvailableDay())));

	}

	private double calculate(AttendanceStatusDTO statusDTO, int availableDay) {
		double cnt = statusDTO.getAbsentCnt()
				+ (statusDTO.getEarlyleaveCnt() + statusDTO.getOutingCnt() + statusDTO.getLateCnt()) / 3.0;
		double result = (availableDay - cnt) / availableDay * 100;
		return result;
	}

	public JPanel getLateCnt() {
		if (lateCnt == null) {
			lateCnt = new JPanel();
			late = new JLabel("지각", JLabel.CENTER);
			late.setPreferredSize(new Dimension(70, 60));
			late.setBorder(eborder);
			late.setBackground(Color.pink);
			lateCnt.add(late);

		}
		return lateCnt;
	}

	public JPanel getAbsentCnt() {
		if (absentCnt == null) {
			absentCnt = new JPanel();
			absent = new JLabel("결석", JLabel.CENTER);
			absent.setPreferredSize(new Dimension(70, 60));
			absent.setBorder(eborder);
			absent.setBackground(Color.pink);
			absentCnt.add(absent);

		}
		return absentCnt;
	}

	public JPanel getEarlyLeaveCnt() {
		if (earlyLeaveCnt == null) {
			earlyLeaveCnt = new JPanel();
			earlyLeave = new JLabel("조퇴", JLabel.CENTER);
			earlyLeave.setPreferredSize(new Dimension(70, 60));
			earlyLeave.setBorder(eborder);
			earlyLeave.setBackground(Color.pink);
			earlyLeaveCnt.add(earlyLeave);
		}
		return earlyLeaveCnt;
	}

	public JPanel getOutStandingCnt() {
		if (outStandingCnt == null) {
			outStandingCnt = new JPanel();
			outStanding = new JLabel("외출", JLabel.CENTER);
			outStanding.setPreferredSize(new Dimension(70, 60));
			outStanding.setBorder(eborder);
			outStanding.setBackground(Color.pink);
			outStandingCnt.add(outStanding);

		}
		return outStandingCnt;
	}

	public JPanel getCnt1() {
		if (cnt1 == null) {
			cnt1 = new JPanel();
			cnt1Label = new JLabel();
			cnt1.setPreferredSize(new Dimension(70, 60));
			cnt1.setBackground(Color.pink);
			cnt1.add(cnt1Label);
		}
		return cnt1;
	}

	public JPanel getCnt2() {
		if (cnt2 == null) {
			cnt2 = new JPanel();
			cnt2Label = new JLabel();
			cnt2.setPreferredSize(new Dimension(70, 60));
			cnt2.setBackground(Color.pink);
			cnt2.add(cnt2Label);

		}
		return cnt2;

	}

	public JPanel getCnt3() {
		if (cnt3 == null) {
			cnt3 = new JPanel();
			cnt3Label = new JLabel();
			cnt3.setPreferredSize(new Dimension(70, 60));
			cnt3.setBackground(Color.pink);
			cnt3.add(cnt3Label);
		}
		return cnt3;

	}

	public JPanel getCnt4() {
		if (cnt4 == null) {
			cnt4 = new JPanel();
			cnt4Label = new JLabel();
			cnt4.setPreferredSize(new Dimension(70, 60));
			cnt4.setBackground(Color.pink);
			cnt4.add(cnt4Label);

		}
		return cnt4;
	}

}