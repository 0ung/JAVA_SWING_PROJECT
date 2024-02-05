package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EtchedBorder;

/*class PaddedFlowLayout extends FlowLayout{
	private int hotizontalPadding;
	
	public PaddedFlowLayout(int align, int hgap, int vgap, int horizontalPadding) {
		super(align, hgap, vgap);
		this.hotizontalPadding = horizontalPadding;
	}
	
	@Override
	public int getHgap() {
		return super.getHgap() + hotizontalPadding;
	}
}*/

public class AttendStatus extends JFrame{
	
	private JPanel upperPanel, lowerPanel;
	private JPanel lateCnt, absentCnt , earlyLeaveCnt, outStandingCnt, cnt1, cnt2, cnt3, cnt4;
	private JLabel late, absent, earlyLeave, outStanding; 
	EtchedBorder eborder = new EtchedBorder();
	
	public AttendStatus() {
		
		this.setSize(600,500);
		this.setTitle("현재 출결 상황");
		this.setLayout(new BorderLayout());
		//this.setLayout(new PaddedFlowLayout(FlowLayout.CENTER, 20, 20, 20));
		
		
		upperPanel = new JPanel(new GridLayout(1,4,20,10));
		upperPanel.setBorder(BorderFactory.createEmptyBorder(80,80,130,80));
		
		upperPanel.add(getLateCnt());
		upperPanel.add(getAbsentCnt());
		upperPanel.add(getEarlyLeaveCnt());
		upperPanel.add(getOutStandingCnt());
		
		lowerPanel = new JPanel(new GridLayout(1,4,20,10));
		lowerPanel.setBorder(BorderFactory.createEmptyBorder(0,80,120,80));
		
		lowerPanel.add(getCnt1());
		lowerPanel.add(getCnt2());
		lowerPanel.add(getCnt3());
		lowerPanel.add(getCnt4());
		
		//this.add(upperPanel2,BorderLayout.NORTH);
		this.add(upperPanel, BorderLayout.CENTER);
		this.add(lowerPanel,BorderLayout.SOUTH);
		//this.add(lowerPanel2,BorderLayout.SOUTH);
		
	}
	
	public JPanel getLateCnt() {
		if(lateCnt == null) {
			lateCnt = new JPanel();
			late = new JLabel("지각", JLabel.CENTER);
			late.setPreferredSize(new Dimension(70,60));
			late.setBorder(eborder);
			late.setBackground(Color.pink);
			lateCnt.add(late);
			
		}
		return lateCnt;
	}
	public JPanel getAbsentCnt() {
		if(absentCnt == null) {
			absentCnt = new JPanel();
			absent = new JLabel("결석",JLabel.CENTER);
			absent.setPreferredSize(new Dimension(70,60));
			absent.setBorder(eborder);
			absent.setBackground(Color.pink);
			absentCnt.add(absent);
			
		}
		return absentCnt;
	}
	public JPanel getEarlyLeaveCnt() {
		if(earlyLeaveCnt == null) {
			earlyLeaveCnt = new JPanel();
			earlyLeave = new JLabel("조퇴",JLabel.CENTER);
			earlyLeave.setPreferredSize(new Dimension(70,60));
			earlyLeave.setBorder(eborder);
			earlyLeave.setBackground(Color.pink);
			earlyLeaveCnt.add(earlyLeave);
		}
		return earlyLeaveCnt;
	}
	public JPanel getOutStandingCnt() {
		if(outStandingCnt == null) {
			outStandingCnt = new JPanel();
			outStanding = new JLabel("외출",JLabel.CENTER);
			outStanding.setPreferredSize(new Dimension(70,60));
			outStanding.setBorder(eborder);
			outStanding.setBackground(Color.pink);
			outStandingCnt.add(outStanding);
			
			
		}
		return outStandingCnt;
	}
	
	public JPanel getCnt1() {
		if(cnt1 == null) {
			cnt1 = new JPanel();
			cnt1.setPreferredSize(new Dimension(70,60));
			cnt1.setBackground(Color.pink);
		}
		return cnt1;
		
	}
	
	public JPanel getCnt2() {
		if(cnt2 == null) {
			cnt2 = new JPanel();
			cnt2.setPreferredSize(new Dimension(70,60));
			cnt2.setBackground(Color.pink);
		}
		return cnt2;
		
	}
	
	public JPanel getCnt3() {
		if(cnt3 == null) {
			cnt3 = new JPanel();
			cnt3.setPreferredSize(new Dimension(70,60));
			cnt3.setBackground(Color.pink);
		}
		return cnt3;
		
	}
	
	public JPanel getCnt4() {
		if(cnt4 == null) {
			cnt4 = new JPanel();
			cnt4.setPreferredSize(new Dimension(70,60));
			cnt4.setBackground(Color.pink);
			
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