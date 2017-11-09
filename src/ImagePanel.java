import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.geom.*;
import java.awt.image.*;



public class ImagePanel extends JPanel implements ActionListener {
	
    private Image img;
    private JComboBox menuDeroulant1;
    private JComboBox menuDeroulant2;
    private JComboBox menuDeroulant3;
    private JButton bouton1;
    private boolean soloOuMulti;
    private int modeDeJeu; 
    public static Menu menuPrincipal;
    private boolean bonusallowed;
    private int modevitesse;
    
    public ImagePanel(Image img){
        this.img = img;
        
        setLayout(null);
        
        //Conteneur principal
        JPanel panneau= new JPanel ();
        panneau.setBounds (570,70,390,80);       
        panneau.setLayout(null);
        
		JLabel etiquette1 = new JLabel ("Selectionnez un mode de jeu");
		etiquette1.setBounds (5,5,210,20);
		etiquette1.setForeground(Color.red);
		panneau.add(etiquette1);
		
		JLabel etiquette2 = new JLabel ("Souhaitez vous des bonus ?");
		etiquette2.setBounds (5,30,210,20);
		etiquette2.setForeground(Color.red);
		panneau.add(etiquette2);
		
		JLabel etiquette3 = new JLabel ("Choisissez votre adversaire");
		etiquette3.setBounds (5,55,210,20);
		etiquette3.setForeground(Color.red);
		panneau.add(etiquette3);
        
        // Création ComboBox1 pour choix du mode de jeu
		String [] s = new String [2];
		s[0] = "Partie en 5 points";
		s[1] = "Partie en 10 points";
		menuDeroulant1 = new JComboBox(s);
		menuDeroulant1.setBounds (215,5,170,20);
		panneau.add(menuDeroulant1);
		
		// Création ComboBox2 pour choix des bonus
		String [] r = new String [2];
		r[0] = "Oui";
		r[1] = "Non";
		menuDeroulant2 = new JComboBox(r);
		menuDeroulant2.setBounds (215,30,170,20);
		panneau.add(menuDeroulant2);
		
		// Création ComboBox2 pour choix solo ou multi
		String [] p = new String [4];
		p[0] = "Joueur 2 - Facile";
		p[1]="Joueur 2 - Difficile";
		p[2] = "Ordinateur - Facile";
		p[3]="Ordinateur - Difficile";
		menuDeroulant3 = new JComboBox(p);
		menuDeroulant3.setBounds (215,55,170,20);
		panneau.add(menuDeroulant3);
		
		//Création Bouton1 pour lancement partie
		bouton1 = new JButton ("Lancer la partie");
		bouton1.setSize(150,30);
		bouton1.setLocation(panneau.getX() + panneau.getWidth()/2- bouton1.getWidth()/2,panneau.getY() + panneau.getHeight());
		bouton1.addActionListener(this);
		
		
		add (bouton1);
		add(panneau);
		
    }
     
    public void paintComponent(Graphics g) {
        g.drawImage(img, 0, 0, null);
    }
    
    public void actionPerformed (ActionEvent e) {
		if (e.getSource() == bouton1){
			int i = menuDeroulant1.getSelectedIndex();
			switch (i){
				case 0 : 
					modeDeJeu = 5;
					break;
				default : 
					modeDeJeu = 10;
			}	
		
			int j = menuDeroulant2.getSelectedIndex();
			switch (j){
				case 0 : 
					bonusallowed = true;
					break;
				default : 
					bonusallowed = false;
			}
		
			int k = menuDeroulant3.getSelectedIndex();
			switch (k){
				case 0 : 
					soloOuMulti = false;
					modevitesse=-2;
					break;
				case 1 : 
					soloOuMulti = false;
					modevitesse=0;
					break;
				case 2 : 
					soloOuMulti = true;
					modevitesse=-2;
					break;
				case 3 : 
					soloOuMulti = true;
					modevitesse=0;
					break;
				
			}
			new Jeu1(soloOuMulti, modeDeJeu, bonusallowed, modevitesse);
			menuPrincipal.setVisible(false); // fenêtre Menu non visible
		}
	}
	

    public static void main(String[] args) {
        menuPrincipal = new Menu();
    }
}


