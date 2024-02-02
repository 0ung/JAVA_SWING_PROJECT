package views;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class AttendStatus extends JFrame{
	
	private JPanel lateCnt, absentCnt , earlyLeaveCnt, outStandingCnt, cnt1, cnt2, cnt3, cnt4;
	private JLabel late, absent, earlyLeave, outStanding; 
	
	public AttendStatus() {
		
		this.setSize(600,500);
		this.setTitle("현재 출결 상황");
		
		this.add(getLateCnt(),BorderLayout.CENTER);
		this.add(getAbsentCnt(),BorderLayout.CENTER);
		this.add(getEarlyLeaveCnt(),BorderLayout.CENTER);
		this.add(getOutStandingCnt(),BorderLayout.CENTER);
		this.add(getCnt1(),BorderLayout.CENTER);
		this.add(getCnt2(),BorderLayout.CENTER);
		this.add(getCnt3(),BorderLayout.CENTER);
		this.add(getCnt4(),BorderLayout.CENTER);
		
	}
	
	public JPanel getLateCnt() {
		if(lateCnt == null) {
			lateCnt = new JPanel();
			late = new JLabel();
			late.setText("지각");
		}
		return lateCnt;
	}
	public JPanel getAbsentCnt() {
		if(absentCnt == null) {
			absentCnt = new JPanel();
			absent = new JLabel();
			absent.setText("지각");
		}
		return lateCnt;
	}
	public JPanel getEarlyLeaveCnt() {
		if(earlyLeaveCnt == null) {
			earlyLeaveCnt = new JPanel();
			earlyLeave = new JLabel();
			earlyLeave.setText("지각");
		}
		return lateCnt;
	}
	public JPanel getOutStandingCnt() {
		if(outStandingCnt == null) {
			outStandingCnt = new JPanel();
			outStanding = new JLabel();
			outStanding.setText("지각");
		}
		return lateCnt;
	}
	
	public JPanel getCnt1() {
		if(cnt1 == null) {
			cnt1 = new JPanel();
		}
		return cnt1;
		
	}
	
	public JPanel getCnt2() {
		if(cnt2 == null) {
			cnt2 = new JPanel();
		}
		return cnt2;
		
	}
	
	public JPanel getCnt3() {
		if(cnt3 == null) {
			cnt3 = new JPanel();
		}
		return cnt3;
		
	}
	
	public JPanel getCnt4() {
		if(cnt4 == null) {
			cnt4 = new JPanel();
			
		}
		return cnt4;
	}

	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				AttendStatus attendStatus = new AttendStatus();
				attendStatus.setVisible(true);
			}
		});

	}

}
