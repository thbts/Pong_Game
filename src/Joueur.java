import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.awt.geom.*;
import java.awt.image.*;
import javax.imageio.*;

public class Joueur extends Rectangle{

public int hauteur, largeur;
public int lf, hf;
public int x,y;
public int vitesse=5;
public BufferedImage image;
int modevitesse;
  
  public Joueur(int ax, int largeurfenetre, int hauteurfenetre, int modevit){
	    try {
             image=ImageIO.read(new File("Raquette2.png"));
        } catch(Exception err) {
            System.out.println("Raquette2.png introuvable !");            
            System.exit(0);    
        }
        x=ax;
        lf=largeurfenetre;
        hf=hauteurfenetre;       
        hauteur=image.getHeight(null);
        largeur=image.getWidth(null);
        modevit=modevitesse;
        y=(hf-hauteur)/2;
        
    }

	public void dessine(Graphics g, JFrame jf){
		g.drawImage(image,x,y,jf);
	}
	
	public void move(boolean a, int hauteurFe){
		if((a) && ((y+hauteur)<hauteurFe)){
			y=y+vitesse;
		}
		if ((!a) && (y>20)){
			y=y-vitesse;
		}
	 }
	 
	 public void setXYmilR(){
		 y=(hf-hauteur)/2;
	 }
	 
	 public void moveIA(Balle bal, int hauteurFe){
		if(modevitesse==-2){        //mode facile : l'ordinateur tape avec le centre de la raquette
				if(bal.y+bal.hauteur/2>this.y+this.hauteur/2  && ((y+hauteur)<hauteurFe)) y=y+6;     //vitesse de l'ordi doit être plus petite pour la jouabilité
				if(bal.y+bal.hauteur/2<this.y+this.hauteur/2 && (y>20)) y=y-6;
	    }else{
			    if(this.y>bal.y  && (y>20)) y=y-9;     //vitesse de l'ordi plus élevée en mode difficile
				if(this.y+this.hauteur<bal.y+bal.hauteur && ((y+hauteur)<hauteurFe)) y=y+9;
		}
			
	 }
}
