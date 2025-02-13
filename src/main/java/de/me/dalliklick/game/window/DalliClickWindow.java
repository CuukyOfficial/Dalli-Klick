package de.me.dalliklick.game.window;

import java.util.function.Supplier;

import javax.swing.JFrame;

import de.me.dalliklick.Main;
import de.me.dalliklick.game.DalliClick;

public class DalliClickWindow extends JFrame {

	private static final long serialVersionUID = 7586000477737543854L;

	private DalliClick game;
	private DalliClickRenderer renderer;

	public DalliClickWindow(DalliClick game, Supplier<Drawable> supplier) {
		this.game = game;
		
		this.setTitle(Main.getName() + " v" + Main.getVersion() + " by " + Main.getAuthor());
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setUndecorated(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		this.renderer = new DalliClickRenderer(supplier);
		this.getContentPane().add(renderer);
		this.renderer.setBounds(0, 0, this.getWidth(), this.getHeight());

		this.renderer.addKeyListener(new DalliKeyListener(this));
	}

	public void refresh() {
		this.renderer.repaint();
	}
	
	public void repaintBackground() {
		this.renderer.setClear(true);
		this.renderer.repaint();
	}

	public void onKeyClick() {
		this.game.onContinuePress();
	}
}