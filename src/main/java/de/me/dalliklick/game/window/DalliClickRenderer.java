package de.me.dalliklick.game.window;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.function.Supplier;

public class DalliClickRenderer extends Canvas {

	private static final long serialVersionUID = -9030167991064749382L;

	private Supplier<Drawable> toDraw;
	private boolean clear = true;

	public DalliClickRenderer(Supplier<Drawable> toDraw) {
		this.toDraw = toDraw;
		this.setBackground(Color.BLACK);
	}

	private int calculateCoord(float c, float adjust) {
		return (int) Math.ceil(c * adjust);
	}

	private int[] adjustCoords(float w1, float w2, float h1, float h2) {
		final float adjustTo = w1 > h1 ? w2 / w1 : h2 / h1;
		return new int[] { this.calculateCoord(w1, adjustTo), this.calculateCoord(h1, adjustTo) };
	}

	@Override
	public void update(Graphics g) {
		this.paint(g);
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setBackground(Color.BLACK);
		g2d.setColor(Color.BLACK);

		if (this.clear) {
			g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
			this.clear = !this.clear;
		}

		Drawable draw = this.toDraw.get();
		if (draw != null) {
			BufferedImage image = draw.getImage();
			final int[] coords = this.adjustCoords(image.getWidth(), this.getWidth(), image.getHeight(), this.getHeight());
			final int width = coords[0], height = coords[1];
			g2d.drawImage(image, (this.getWidth() - width) / 2, (this.getHeight() - height) / 2, width, height, null);
		}
	}

	public void setClear(boolean clear) {
		this.clear = clear;
	}
}
