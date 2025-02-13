package de.me.dalliklick.menu;

import de.me.dalliklick.Main;
import de.me.dalliklick.game.DalliClick;
import de.me.dalliklick.game.GameDifficulty;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainMenu extends JFrame {

	private static final long serialVersionUID = -8685212216647376730L;

	private BufferedImage icon;

	/**
	 * Create the frame.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public MainMenu() {
		setResizable(false);
		try {
			this.icon = ImageIO.read(new File("resources/icon.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle(Main.getName() + " v" + Main.getVersion() + " by " + Main.getAuthor());
		setBounds(100, 100, 450, 417);
		this.setIconImage(this.icon);
		JPanel contentPane = new JPanel();
		contentPane.setBackground(SystemColor.menu);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblDalliClick = new JLabel(Main.getName(), SwingConstants.CENTER);
		lblDalliClick.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
		lblDalliClick.setBounds(5, 0, 424, 51);
		contentPane.add(lblDalliClick);

		JComboBox difficultyBox = new JComboBox(GameDifficulty.values());
		difficultyBox.setBounds(5, 284, 424, 36);
		difficultyBox.setSelectedIndex(1);
		difficultyBox.setEditable(false);

		contentPane.add(difficultyBox);

		JButton btnStartGame = new JButton("Spiel starten");
		btnStartGame.setToolTipText("Startet das Spiel");
		btnStartGame.setBounds(5, 331, 424, 36);
		btnStartGame.addActionListener(e -> {
			new DalliClick((GameDifficulty) difficultyBox.getSelectedItem());
			dispose();
		});

		contentPane.add(btnStartGame);

		JLabel lblDifficulty = new JLabel("Schwierigkeitsgrad", SwingConstants.CENTER);
		lblDifficulty.setBounds(5, 259, 424, 14);
		contentPane.add(lblDifficulty);
		
		JLabel lblIcon = new JLabel(new ImageIcon(this.icon));
		lblIcon.setBounds(120, 51, 200, 200);
		contentPane.add(lblIcon);
		
		JLabel lblVersion = new JLabel("v" + Main.getVersion(), SwingConstants.CENTER);
		lblVersion.setBounds(5, 37, 424, 14);
		contentPane.add(lblVersion);
	}
}
