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

public class Balle extends Rectangle{
public int hauteur, largeur;
public double x,y;
public BufferedImage image;
public double vitesse, direction;
public int lf, hf;
public int vitmini;
public int vitmax;
public boolean bug = false;

public Balle(int lfe, int hfe, int modevitesse){
	    try {
             image=ImageIO.read(new File("balle2.jpg"));
        } catch(Exception err) {
            System.out.println("balle.jpg introuvable");            
            System.exit(0);    
        }
        x=0;
        y=0;   
        hauteur=image.getHeight(null);
        largeur=image.getWidth(null);
        vitesse=10+modevitesse;
        direction=0;
        lf=lfe;
        hf=hfe;
        vitmini=8+modevitesse;
        vitmax=12+modevitesse;
        }
       
      public void dessine(Graphics g, JFrame jf){
		g.drawImage(image,(int)x,(int)y,jf);
	  }
       
       public int move(){
			int res=0;
			if(this.y<=20 || this.y+this.hauteur>=this.hf) direction=-direction;
			if(this.y<=20) y=25;
			
			x+=vitesse*Math.cos(direction);
			y+=vitesse*Math.sin(direction);
			if(this.x<=0) res=1;
			if(this.x+this.largeur>=lf) res=2;
			return res;
		}
       
       public void setXYMil(){
			vitesse = vitmini;
			double p = Math.random();
			if (p>0.5) {
				direction = 0;
			}else {
				direction = Math.PI;
			}
		   x=lf/2-(largeur-12); // Ligne blanche n'a pas été tracée précisément au centre de l'écran... Ici pour avoir balle sur ligne
		   y=hf/2-hauteur/2;
	 }
      
      public void collision(Joueur j1, Joueur j2){
			int coeff=0;
			double coeffangle=0;
			Rectangle rectj1=new Rectangle(j1.x,j1.y,j1.largeur,j1.hauteur);
			Rectangle rectj2=new Rectangle(j2.x,j2.y,j2.largeur,j2.hauteur);
			Rectangle rectballe=new Rectangle((int)this.x,(int)this.y,this.largeur,this.hauteur);
			
			if(rectj1.intersects(rectballe)){
				jouerSon("sonpingpong.wav");
				coeff=(j1.y+j1.hauteur/2)-((int)this.y+this.hauteur/2);    //coeff est alors entre -(hauteur(raq)/2+haut(bal)/2) de la raquette et +hauteur(raq)/2+haut(bal)/2
				
				//Modification de la vitesse de la balle après la collision : ralentissement ou accélération
				if(Math.abs(coeff)<j1.hauteur/4 && vitesse-0.1*Math.abs(coeff-j1.hauteur/4)>=vitmini) vitesse=vitesse-0.1*Math.abs(coeff-j1.hauteur/4);
				if(Math.abs(coeff)<j1.hauteur/4 && vitesse-0.1*Math.abs(coeff-j1.hauteur/4)<vitmini) vitesse=vitmini;
				if(Math.abs(coeff)>=j1.hauteur/4 && vitesse+Math.abs(coeff)<=vitmax) vitesse=vitesse+Math.abs(coeff);
				if(Math.abs(coeff)>=j1.hauteur/4 && vitesse+Math.abs(coeff)>vitmax) vitesse=vitmax;

					
				
				//Modif de la direction
				coeffangle=2*Math.PI*coeff/((j1.hauteur+this.hauteur)*3);   //coeffangle est alors entre -pi/3 et pi/
				direction=-coeffangle;
			 }
			 
			if (x<0 && y+this.hauteur>j1.y && y<j1.y+j1.hauteur) {
				bug=true;
			}
			
			if(rectj2.intersects(rectballe)){
				jouerSon("sonpingpong.wav");
				coeff=(j2.y+j2.hauteur/2)-((int)this.y+this.hauteur/2);     //coeff est alors entre -(hauteur(raq)/2+haut(bal)/2) de la raquette et +hauteur(raq)/2+haut(bal)/2
				
				//Modification de la vitesse de la balle après la collision : ralentissement ou accélération
				if(Math.abs(coeff)<j2.hauteur/4 && vitesse-0.1*Math.abs(coeff-j1.hauteur/4)>=vitmini) vitesse=vitesse-0.1*Math.abs(coeff-j1.hauteur/4);
				if(Math.abs(coeff)<j2.hauteur/4 && vitesse-0.1*Math.abs(coeff-j1.hauteur/4)<vitmini) vitesse=vitmini;
				if(Math.abs(coeff)>=j2.hauteur/4 && vitesse+Math.abs(coeff)<=vitmax) vitesse=vitesse+Math.abs(coeff);
				if(Math.abs(coeff)>=j2.hauteur/4 && vitesse+Math.abs(coeff)>vitmax) vitesse=vitmax;
				
				//Modif de la direction
				coeffangle=2*Math.PI*coeff/((j2.hauteur+this.hauteur)*3);   //coeffangle est alors entre -pi/3 et pi/3
				direction=coeffangle-Math.PI;
		    }
		    
		    if (x+this.largeur>lf && y+this.hauteur>j2.y && y<j2.y+j2.hauteur) {
				bug=true;
			}
		 }
	public void jouerSon (String s) { // Pour un petit son de balle
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
 
		 

