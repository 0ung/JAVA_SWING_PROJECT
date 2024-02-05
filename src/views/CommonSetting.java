package views;

import java.awt.Component;
import java.awt.GraphicsEnvironment;
import java.awt.Point;

public class CommonSetting {
	public static void locationCenter(Component component) {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point centerPoint = ge.getCenterPoint();
		int leftTopX = centerPoint.x - component.getWidth() / 2;
		int leftTopY = centerPoint.y - component.getHeight() / 2;
		component.setLocation(leftTopX, leftTopY);
	}
}
