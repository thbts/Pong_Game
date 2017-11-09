import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.awt.geom.*;
import java.awt.image.*;
import javax.imageio.*;

public class Bonus extends Rectangle{
public int hauteur, largeur;
public int x,y;
public BufferedImage image;
public int vivant;
public int actif;
public int qui=0;


public Bonus(int lfe, int hfe){
	    try {
             image=ImageIO.read(new File("bonuspetit2.jpg"));
        } catch(Exception err) {
            System.out.println("bonuspetit2.jpg introuvable");            
            System.exit(0);    
        }
        x=50+(int)((lfe-100)*Math.random());
        y=50+(int)((hfe-100)*Math.random());   
        hauteur=image.getHeight(null);
        largeur=image.getWidth(null);
        vivant = 400;
        }
       
      public void dessine(Graphics g, JFrame jf){
		g.drawImage(image,x,y,jf);
	  }
       
      
      public int collision(Balle bal){
	
			Rectangle rectball=new Rectangle((int)bal.x,(int)bal.y,bal.largeur,bal.hauteur);
			Rectangle rectbonus=new Rectangle(this.x,this.y,this.largeur,this.hauteur);
			qui=0;
			vivant--;
			
			if(rectball.intersects(rectbonus)){//collision
				
				this.image=null;
				this.hauteur=0;
				this.largeur=0;
				
				
				if(Math.cos(bal.direction)<=0) qui=1;
				else qui=2;
			}
			return qui;
		}

}
