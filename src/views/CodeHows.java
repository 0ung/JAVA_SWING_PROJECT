package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class CodeHows extends JPanel {
	private Image image;

	public CodeHows() {
		this.setLayout(new GridLayout(5,1)); 
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

		// 패널 크기 설정
		setPreferredSize(new Dimension(500, 500));
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
		jLabel.setFont(new Font("맑은 고딕",Font.BOLD,20));
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