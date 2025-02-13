package de.me.dalliklick.game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import de.me.dalliklick.game.window.Drawable;
import de.me.dalliklick.math.Vec2i;

public class DalliClickImage implements Drawable {

	private final BufferedImage source;
	private final Map<Vec2i, Boolean> imageMap;
	private final float percentage;
	private final BufferedImage draw;

	private boolean all;
	private int blockWidth, blockHeight;

	public DalliClickImage(BufferedImage image, float percentage) {
		this.source = image;
		this.percentage = percentage;
		this.imageMap = this.generateImageMap();
		this.draw = new BufferedImage(this.source.getWidth(), this.source.getHeight(), BufferedImage.TYPE_INT_RGB);
	}

	private int calculateParts(int dimension) {
		return (int) (dimension / (this.percentage * (float) dimension));
	}

	private Vec2i[] filteredPartLocations(boolean enabled) {
		return this.imageMap.keySet().stream().filter(v -> this.imageMap.get(v) == enabled).toArray(Vec2i[]::new);
	}

	private Map<Vec2i, Boolean> generateImageMap() {
		Map<Vec2i, Boolean> map = new HashMap<>();
		int width = this.source.getWidth(), height = this.source.getHeight();
		int xParts = this.calculateParts(width), yParts = this.calculateParts(height);
		this.blockWidth = width / xParts;
		this.blockHeight = height / yParts;
		for (int x = 0; x < xParts; x++) {
			for (int y = 0; y < yParts; y++) {
				map.put(new Vec2i(x * this.blockWidth, y * this.blockHeight), false);
			}
		}

		return map;
	}

	private void draw(Vec2i vec) {
		Graphics g = this.draw.getGraphics();
		BufferedImage on = this.source.getSubimage(vec.getX(), vec.getY(), this.blockWidth, this.blockHeight);
		g.drawImage(on, vec.getX(), vec.getY(), null);
	}

	public void revealAll() {
		this.all = true;
	}

	public boolean revealPart() {
		Vec2i[] disabled = this.filteredPartLocations(false);
		if (disabled.length == 0)
			return false;

		Vec2i chosen = disabled[disabled.length == 1 ? 0 : new Random().nextInt(disabled.length - 1)];
		this.imageMap.put(chosen, true);
		this.draw(chosen);
		return true;
	}

	@Override
	public BufferedImage getImage() {
		return this.all ? this.source : this.draw;
	}
}