package de.me.dalliklick;

import javax.swing.SwingUtilities;

import com.alee.laf.WebLookAndFeel;

import de.me.dalliklick.logger.Logger;
import de.me.dalliklick.menu.MainMenu;

public class Main {

	private static final String NAME = "Dalli Klick", VERSION = "0.1", AUTHOR = "Mattis Kaiser";
	public static final Logger LOGGER = new Logger();

	static {
		LOGGER.setConsole(true);
	}

	public static void main(String[] args) {
		LOGGER.info("Enabling " + NAME + " v" + VERSION + " by " + AUTHOR + "...");

		SwingUtilities.invokeLater(() -> {
			WebLookAndFeel.install();
			new MainMenu().setVisible(true);
		});
	}

	public static String getName() {
		return NAME;
	}

	public static String getVersion() {
		return VERSION;
	}

	public static String getAuthor() {
		return AUTHOR;
	}
}