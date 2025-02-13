package de.me.dalliklick.game.window;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class DalliKeyListener implements KeyListener {

	private DalliClickWindow window;

	public DalliKeyListener(DalliClickWindow window) {
		this.window = window;
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == 27) {
			System.exit(0);
			return;
		}

		if (e.getKeyCode() != 32)
			return;

		this.window.onKeyClick();
	}
}