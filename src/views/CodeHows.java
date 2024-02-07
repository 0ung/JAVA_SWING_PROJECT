package views;

import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import models.dto.UserDTO;
import models.service.AttendService;

public class CodeHows extends JPanel {
	private Image image;
	private JPanel startCheck, endCheck;
	private JButton start, end;
	private UserDTO user;
	private AttendService attendService = new AttendService();

	public CodeHows(UserDTO user) {
		this.user = user;
		this.setLayout(new GridLayout(6, 1));
		// 이미지 로드
		loadImage();
		JLabel blank = new JLabel("");
		JLabel codehowsLink = new JLabel("1. 코드하우스!!!");
		loadURI(codehowsLink, "https://www.codehows.com/");
		JLabel githubLink = new JLabel("2. 깃허브!!!");
		loadURI(githubLink, "https://github.com/");
		JLabel naverLink = new JLabel("3. 네이버!!!");
		loadURI(naverLink, "https://www.naver.com/");
		JLabel googleLink = new JLabel("4. 구글!!!");
		loadURI(googleLink, "https://www.google.co.kr/?hl=ko");
		add(blank);
		add(codehowsLink);
		add(githubLink);
		add(naverLink);
		add(googleLink);
		add(getCheckButton());

		// 패널 크기 설정
		setPreferredSize(new Dimension(500, 700));

	}

	public JPanel getCheckButton() {
		if (startCheck == null) {
			startCheck = new JPanel();
			JButton start = new JButton();
			start.setPreferredSize(new Dimension(80, 80));
			// start.setBackground(Color.LIGHT_GRAY);

			JLabel txtStart = new JLabel("출근");
			start.add(txtStart);
			start.setBorder(new RoundedBorder(20));
			txtStart.setHorizontalAlignment(JLabel.CENTER);
			txtStart.setFont(new Font("맑은 고딕", getFont().BOLD, 15));
			startCheck.add(start);

			JButton end = new JButton();
			end.setPreferredSize(new Dimension(80, 80));
			end.setBorder(new RoundedBorder(20));
			// end.setBackground(Color.RED);
			JLabel txtEnd = new JLabel("퇴근");
			txtEnd.setHorizontalAlignment(SwingConstants.CENTER);
			txtEnd.setFont(new Font("맑은 고딕", getFont().BOLD, 15));

			end.add(txtEnd);
			startCheck.add(end);

			start.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if (e.getSource() == start) {
						start.setEnabled(false);
						// LocalDateTime.now();
						attendService.insertStartTime(user.getUserId());
						end.setEnabled(true);

					} else if (e.getSource() == end) {
						end.setEnabled(false);
						start.setEnabled(true);
					}

				}
			});

			end.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// attendService.endTime();
					end.setEnabled(false);
					start.setEnabled(true);
				}
			});

		}
		return startCheck;

	}

	private void loadImage() {
		try {
			String imagePath = System.getProperty("user.dir") + File.separator + "image" + File.separator + "logo.png";
			ImageIcon imageIcon = new ImageIcon(imagePath);
			image = imageIcon.getImage();

			// 이미지 로딩 검증 (선택적)
			if (imageIcon.getIconWidth() == -1) {
				System.err.println("Image load failed: " + imagePath);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Image loading error: " + e.getMessage());
		}
	}

	public void loadURI(JLabel jLabel, String url) {
		jLabel.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		jLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (Desktop.isDesktopSupported()) {
					Desktop desktop = Desktop.getDesktop();
					try {
						URI uri = new URI(url);
						desktop.browse(uri);
					} catch (IOException | URISyntaxException e1) {
						e1.printStackTrace();
					}
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// 이미지가 있을 경우에만 그리기
		if (image != null) {
			g.drawImage(image, 0, 0, this);
		}
	}

}