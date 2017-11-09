import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.awt.geom.*;
import java.awt.image.*;
import javax.imageio.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Jeu1 extends JFrame implements ActionListener,KeyListener {
    
    // AFFICHAGE
    private final int LARGEUR_FENETRE = 1024;
    private final int HAUTEUR_FENETRE=728;
    private BufferedImage monBuf;
    private Image papierPeint;
    private Image Score1;
    private Image Score2;
    private Image VictoireJ1;
    private Image VictoireJ2;
    private Image pause;
    public Menu newMenuPrincipal;
    private static Jeu1 partie;    
    
    
    // TIMER
	final private int TPS_TIMER_MS =10;
	private Timer t;
	private int n=0;
	
	//Joueur
	public Joueur joueur1;
	private int points1=0;
	public Joueur joueur2;
	private int points2=0;
	private static int pointMax;
	//Balle
	private Balle balle;
	
	//Bonus
	private Bonus bonus;
	private int qui;
	private int actif;
	public int victime;
	public static boolean bonusallowed;
	
	//Interaction
    boolean toucheBas;
    boolean toucheHaut;
    boolean toucheA;
    boolean toucheQ;
    boolean toucheEspace;
    
	//Intelligence artificielle
	private static boolean iA;
	
    //Mode vitesse
    int modevit;
	
	public Jeu1(boolean ia, int pMax, boolean bonusallowed, int modevitesse){
        // Initialisation de la fenêtre
		setLayout(null); // pas de LayoutManager
		setSize(LARGEUR_FENETRE,HAUTEUR_FENETRE); // dimension de la fenêtre
		setLocationRelativeTo(null); // centrer la fenêtre sur l'écran
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // fin du programme lors de la fermeture de la fenêtre
		setResizable(false); // fenêtre non redimensionnable
		setTitle("Un jeu 2.0 By Pereira, Issenhuth, Carles and Gauly");
		
		//On récupère les paramètres de jeu décidés par l'utilisateur
		pointMax = pMax;
		iA=ia;
		this.bonusallowed = bonusallowed;
		
		
		
		newMenuPrincipal = ImagePanel.menuPrincipal; // Variable menu principal définie dans classe ImagePanel est visible ici aussi, éviter création second objet de type Menu
		
		// Initialisation du buffer
		monBuf = new BufferedImage(LARGEUR_FENETRE,HAUTEUR_FENETRE,BufferedImage.TYPE_INT_RGB);
		
		//Chargement fond d'écran
		Toolkit T = Toolkit.getDefaultToolkit();
		papierPeint = T.getImage("terrain.jpg");
		
		//Ajout de l'écouter Key Listener sur la fenêtre
		this.addKeyListener(this);
		
		//Apparition d'un bonus
		if (bonusallowed==true) {
		bonus = new Bonus(LARGEUR_FENETRE,HAUTEUR_FENETRE);
		}
	
		//Timer
		t=new Timer(TPS_TIMER_MS,this);
		
		joueur1=new Joueur(0, LARGEUR_FENETRE, HAUTEUR_FENETRE, modevitesse);
		joueur2=new Joueur(0,  LARGEUR_FENETRE, HAUTEUR_FENETRE, modevitesse);   //on l'initialise à gauche
		joueur2.x=LARGEUR_FENETRE-joueur2.largeur;  //puis on la place à droite telle que son extrémité droite corresponde à l'extrémité droite de la fenêtre
		balle=new Balle(LARGEUR_FENETRE,HAUTEUR_FENETRE, modevitesse);
		balle.setXYMil();
		setVisible(true);
		/*t.start();*/ // Jeu est en pause au début
		}

	public void paint(Graphics g){
        // préparation de l'affichage en dessinant dans le buffer
        Graphics buffer = monBuf.getGraphics(); // récupération de l'objet Graphics associé au buffer
		/*buffer.clearRect(0,0,this.LARGEUR_FENETRE,this.HAUTEUR_FENETRE);*/
		buffer.drawImage(papierPeint,0,0,this);
		buffer.drawImage(Score1,250,0,this);
		buffer.drawImage(Score2,700,0,this);
        balle.dessine(buffer,this);
        joueur1.dessine(buffer,this);
		joueur2.dessine(buffer,this);
		
		if (bonusallowed==true) {
			if (bonus.vivant>0) {
				bonus.dessine(buffer,this);
			}
		}
		
		if ((!t.isRunning()) && (points1 != pointMax) && (points2 != pointMax) && (n!=0)){
			Toolkit V = Toolkit.getDefaultToolkit();
			pause = V.getImage("pause.png");
			buffer.drawImage(pause,402,402,this);
		}
		
		if (points1 == pointMax){
			Toolkit U = Toolkit.getDefaultToolkit();
			VictoireJ1 = U.getImage("victoirej1.png");
			buffer.drawImage(VictoireJ1, 212, 320, this);
			/*jouerSon("StandingOvation.wav");*/   //Nous l'avons enlevé car il était déformé à la lecture
		}
		if (points2 == pointMax){
			Toolkit U = Toolkit.getDefaultToolkit();
			VictoireJ2 = U.getImage("victoirej2.png");
			buffer.drawImage(VictoireJ2, 212, 320, this);
			/*jouerSon("StandingOvation.wav");*/     //Nous l'avons enlevé car il était déformé à la lecture
		}

        // affichage du contenu du buffer
        g.drawImage(monBuf,0,0,this);
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
	
	public void gestionbonus(){
	
		actif--;
			
		if (actif==0) { //tout revient à la normal au bout d'un temps donné
			if(victime==1) {
				try {
				joueur1.image=ImageIO.read(new File("Raquette2.png"));
				} catch(Exception err) {
				System.out.println("Raquette2.png introuvable !");            
				System.exit(0);    
				}
				joueur1.hauteur=joueur1.image.getHeight(null);
				joueur1.largeur=joueur1.image.getWidth(null);
				qui=0;
			}
			else {
				try {
				joueur2.image=ImageIO.read(new File("Raquette2.png"));
				} catch(Exception err) {
				System.out.println("Raquette2.png introuvable !");            
				System.exit(0);    
				}
				joueur2.hauteur=joueur2.image.getHeight(null);
				joueur2.largeur=joueur2.image.getWidth(null);
				qui=0;
			}
		}
		
		qui = bonus.collision(balle);		
			
		if(qui==1){//collision + effet(diminution de la raquette)				
				try {
					joueur1.image=ImageIO.read(new File("Raquette3.png"));
				} catch(Exception err) {
					System.out.println("Raquette3.png introuvable !");            
					System.exit(0);    
				}
				joueur1.hauteur=joueur1.image.getHeight(null);
				joueur1.largeur=joueur1.image.getWidth(null);
				actif = 300;
				victime=1;
				}
		if (qui==2){
				try {
					joueur2.image=ImageIO.read(new File("Raquette3.png"));
				} catch(Exception err) {
					System.out.println("Raquette3.png introuvable !");            
					System.exit(0);    
				}
				joueur2.hauteur=joueur2.image.getHeight(null);
				joueur2.largeur=joueur2.image.getWidth(null);;
				actif = 300;
				victime=2;
				}
	}

	public void actionPerformed(ActionEvent e){
		n=n+1;
		double d=(double)n*TPS_TIMER_MS/1000;
	    
	    if (bonusallowed==true) {
	    int essai = (int)(1000*Math.random()); //création aléatoire du bonus
	    if (essai==42) {
			bonus = new Bonus(LARGEUR_FENETRE,HAUTEUR_FENETRE);
		}
			
	    
	    if (bonus.vivant>0){ //Si le bonus existe toujours, test de la collision
			this.gestionbonus();
		}
		}
	    
	     balle.collision(joueur1,joueur2);
	    
	    switch(balle.move()){
			case 1 :
			      balle.setXYMil();
			      joueur1.setXYmilR();
			      joueur2.setXYmilR();
			      points2++;
			      t.stop();
			      break;
			      
			case 2 : 
			      balle.setXYMil();
			      joueur1.setXYmilR();
			      joueur2.setXYmilR();
			      points1++;
			      t.stop();
			      break;
		  }
	    if(iA){
			joueur2.moveIA(balle,HAUTEUR_FENETRE);
	    }else{
			if(toucheHaut) joueur2.move(false,HAUTEUR_FENETRE);
			if(toucheBas) joueur2.move(true,HAUTEUR_FENETRE);
	    }
		if(toucheA) joueur1.move(false,HAUTEUR_FENETRE);
		if(toucheQ) joueur1.move(true,HAUTEUR_FENETRE);
		/*if(d%1==0) this.setTitle("Temps ecoule : " +String.valueOf(d) + " secondes");*/
		
		//Affichage des scores sur le fond d'écran
		Toolkit P = Toolkit.getDefaultToolkit();
		Score1 = P.getImage("score"+points1+".png");
		
		Toolkit R = Toolkit.getDefaultToolkit();
		Score2 = R.getImage("score"+points2+".png");
	   
	    repaint();
	}
	
	public void keyTyped(KeyEvent e) {}
	
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		switch (code) {
			case KeyEvent.VK_A : toucheA=true; break;
			case KeyEvent.VK_Q : toucheQ=true; break;
			case KeyEvent.VK_UP : toucheHaut=true; break;
			case KeyEvent.VK_DOWN : toucheBas=true; break;
			case KeyEvent.VK_P : 
				if (t.isRunning()){
					t.stop();
				}
				newMenuPrincipal.setVisible(true); // Rend l'ancienne fenêtre menu de nouveau visible
				this.dispose(); // Ferme la fenêtre de jeu
				break;
			
			case KeyEvent.VK_ESCAPE : System.exit(0); break;
			case KeyEvent.VK_SPACE:
				if (t.isRunning() || ((points1 == pointMax) || (points2 == pointMax))){
					toucheEspace = true;
					t.stop();
					repaint();
				}else{
					toucheEspace = false;
					t.start();
				}
			break;
			
		}
	}
		
	

	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		switch (code) {
			case KeyEvent.VK_A : toucheA=false; break;
			case KeyEvent.VK_Q : toucheQ=false; break;
			case KeyEvent.VK_UP : toucheHaut=false; break;
			case KeyEvent.VK_DOWN : toucheBas=false; break;
		}
	}
}

