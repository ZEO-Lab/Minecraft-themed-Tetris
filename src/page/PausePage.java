package page;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.io.IOException;
import java.util.Arrays;

import basicElement.Button;
import basicElement.RectObject;

/**
 * PausePage Class
 * 
 * @author ZEO_Lab
 * @version 1.2
 */

public class PausePage extends page {
	/**
	 * @param buttonStr  按鈕文字
	 */
	private String buttonStr[] = { "Continue", "Restart", "Setting", "MainPage" };

	/**
	 * Initialization
	 * 
	 * @param width  window_width
	 * @param height window_hight
	 * @throws IOException reply input image error
	 */
	public PausePage(int width, int height) throws IOException {
		super(width, height);
		for (int i = 0; i < buttonlist.length; i++) {
			buttonlist[i] = new Button(new RectObject(width / 2, height / 2 - 50 + 100 * (i - 1), 300, 80),
					buttonStr[i]);
			buttonlist[i].setButtonImage("pic/button/button.png");
		}
	}

	/**
	 * draw screen
	 */
	@Override
	public void draw(Graphics2D myBuffer) {
		myBuffer.setColor(new Color(255, 255, 255, 200));
		myBuffer.fillRect(0, 0, w, h);
		myBuffer.setColor(new Color(255, 255, 255, 200));
		int str_x = (int) (w / 2 - myBuffer.getFontMetrics(new Font("Serif", Font.BOLD, 60))
				.getStringBounds("Pause", myBuffer).getWidth() / 2);
		myBuffer.drawString("Pause", str_x, h / 2 - 20);
		Arrays.stream(buttonlist).forEach(button -> button.draw(myBuffer));
	}
}
