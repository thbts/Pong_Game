import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.geom.*;
import java.awt.image.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


public class Menu extends JFrame  {

	// attributs
	private Image papierPeint;
	private final int LARGEUR_FENETRE = 1024;
    private final int HAUTEUR_FENETRE= 768;
	
	public Menu () {
		
		this.setSize(LARGEUR_FENETRE,HAUTEUR_FENETRE);
		this.setTitle("PONG 2K16");
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setContentPane(new ImagePanel(new ImageIcon("pong2.jpg").getImage()));
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
		jouerSon("EASports2.wav");
	
				
	}
	public void jouerSon (String s) { // Pour un petit son des familles
		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(s).getAbsoluteFile());
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		} catch(Exception ex) {
			System.out.println("Error with playing sound.");
			ex.printStackTrace();
		}
	}
		
}

