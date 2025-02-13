package de.me.dalliklick.game;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import de.me.dalliklick.Main;
import de.me.dalliklick.game.window.DalliClickWindow;
import de.me.dalliklick.util.Pair;

@SuppressWarnings("restriction")
public class DalliClick {

	private final GameDifficulty difficulty;
	private final DalliClickWindow window;
	private final ScheduledExecutorService threadPool = Executors.newSingleThreadScheduledExecutor();
	private final List<File> used = new ArrayList<>();

	private DalliClickImage currentImage;
	private ScheduledFuture<?> revealThread;
	private GameState state;

	public DalliClick(GameDifficulty difficulty) {
		this.difficulty = difficulty;
		this.window = new DalliClickWindow(this, () -> DalliClick.this.currentImage);
		this.showNewPicture();
		this.window.setVisible(true);
	}

	private List<File> loadPictureFiles() {
		File file = new File("pictures/");
		if (!file.isDirectory())
			return new ArrayList<>();

		return Arrays.stream(Objects.requireNonNull(file.listFiles())).collect(Collectors.toList());
	}

	private Pair<BufferedImage, File> getRandomImage(List<File> loaded) {
		if (loaded.size() == 0)
			return null;

		File pic = loaded.get(loaded.size() == 1 ? 0 : new Random().nextInt(loaded.size() - 1));
		if (!used.contains(pic)) {
			try {
				return new Pair<>(ImageIO.read(pic), pic);
			} catch (IOException e) {
				Main.LOGGER.info("Bild " + pic.getPath() + " konnte nicht geladen werden! Falsches Format?");
				Main.LOGGER.error("Konnte Bild nicht laden!", e);
			}
		}

		loaded.remove(pic);
		return this.getRandomImage(loaded);
	}

	private BufferedImage getRandomImage() {
		Pair<BufferedImage, File> imagePair = this.getRandomImage(this.loadPictureFiles());
		BufferedImage image = null;
		if (imagePair != null) {
			this.used.add(imagePair.getValue());
			image = imagePair.getKey();
		}

		return image;
	}

	private void end() {
		this.state = GameState.END;
		this.threadPool.shutdown();
		JOptionPane.showMessageDialog(null, "Alle Bilder wurden nun genutzt!\n" + "Vielen Dank für's Spielen!\n" + "\n" + "Grüße an die Jungschar in Firrel ;)\n" + "~ Mattis Kaiser");
		this.window.dispose();
		Main.LOGGER.info("Spiel vorrüber!");
	}

	private void startRevealTimer() {
		this.state = GameState.REVEALING;
		this.revealThread = this.threadPool.scheduleAtFixedRate(this::revealPart, 3, 3, TimeUnit.SECONDS);
	}

	public void onContinuePress() {
		switch (this.state) {
		case IDLE:
			this.revealPart();
			this.startRevealTimer();
			break;
		case REVEALING:
			this.showAll();
			break;
		case SHOWING:
			this.showNewPicture();
			break;
		case END:
			System.exit(0);
			break;
		}
	}

	public void showAll() {
		this.revealThread.cancel(true);
		this.state = GameState.SHOWING;
		this.currentImage.revealAll();
		this.window.refresh();
	}

	public void revealPart() {
		if (!this.currentImage.revealPart())
			this.showAll();
		else
			this.window.refresh();
	}

	public void showNewPicture() {
		BufferedImage image = this.getRandomImage();
		this.currentImage = image == null ? null : new DalliClickImage(image, this.difficulty.getPercentage());
		this.state = GameState.IDLE;

		if (image == null)
			end();
		else
			this.window.repaintBackground();
	}
}