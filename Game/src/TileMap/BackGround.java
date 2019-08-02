package TileMap;
import Main.GameWindow;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import Main.GameWindow;

public class BackGround {
	
	private BufferedImage image;
	
	private double x;
    private double y;
    private double dx;
    private double dy;
    
    private double moveScale;
    
    public BackGround(String s, double ms)
    {
    	try 
    	{
    		image = ImageIO.read(getClass().getResourceAsStream(s));	
    		moveScale =  ms;
    		
    	    
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	
    }
    
    public void setPosition(double x , double y)
    {
    	this.x = x;
    	this.y = (y* moveScale)% GameWindow.HEIGHT;
    }
    public void setVector(double dx, double dy)
    {
    	this.dx = dx;
    	this.dy = dy;
    }
    public void update()
    {
    	x += dx;
    	y += dy;
    }
    
    public void draw(Graphics2D g)
    {
     g.drawImage(image,(int)x,(int)y, null);	
     if(x < 0)
     {
          g.drawImage(image,(int)x + GameWindow.WIDTH,(int)y,null);
     }
     if(x>0)
     {
    	 g.drawImage(image,(int)x - GameWindow.WIDTH,(int)y,null);
     }
     
    }

}
