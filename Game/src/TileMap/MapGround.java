package TileMap;
import Main.GameWindow;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import Main.GameWindow;

public class MapGround {
	
	private BufferedImage image;
	
	private double x;
    private double y;
    private double dx;
    private double dy;
    
    public Wall[][] walls = new Wall[17][22];
    public Shoot[] shoots = new Shoot[2]; // a bo narazie tylko dwoch graczy
    
    private double moveScale;
    
    public MapGround(String s, double ms)
    {
    	try 
    	{
    		image = ImageIO.read(getClass().getResourceAsStream(s));	
    		moveScale =  ms;
    		int tmpX= 148;
    		int tmpY=2;
    		for(int i = 0; i < 6; i++)
    		{
    			for(int j = 0; j < 22; j++)
    			{
    				walls[i][j] = new Wall(false,tmpX,tmpY);
    				tmpX+=29;
    			}
    			tmpX=148;
    			tmpY+=29;
    		}
    		for(int i = 6; i < 11; i++)
    		{
    			for(int j = 0; j < 22; j++)
    			{
    				walls[i][j] = new Wall(true,tmpX,tmpY);
    				tmpX+=29;
    			}
    			tmpX=148;
    			tmpY+=29;
    		}
    		for(int i = 11; i < 17; i++)
    		{
    			for(int j = 0; j < 22; j++)
    			{
    				walls[i][j] = new Wall(false,tmpX,tmpY);
    				tmpX+=29;
    			}
    			tmpX=148;
    			tmpY+=29;
    		}
    		
    		// inicjalizacja strzalow
    		int plus = 0;
    		for(int i = 0; i < shoots.length; i++)
    		{
    			shoots[i]= new Shoot(plus);
    			plus+=5;
    		}
    		
    		
    	    
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	
    }
    
    public void setPosition(double x , double y)
    {
    	this.x = (x* moveScale)% GameWindow.WIDTH;
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
    
    public void drawWalls(Graphics2D g)
    {
    	g.setColor(Color.GREEN);
        
		for(int i = 0; i < 17; i++)
		{
			for(int j = 0; j < 22; j++)
			{
				if(walls[i][j].state == true)
				{
					g.fillRect(walls[i][j].x,walls[i][j].y, 30, 30);	
				}
				
			}
		}    
    }
    
    public boolean initMyShoot(int x, int y, int currentChoice)
    {
    	if(!shoots[0].checkIfUse())
    	{
    		shoots[0].Shoting(x, y, currentChoice);	
    		return true;
    	}
    	return false;
    	
    }
    
    public boolean initFriendShoot(int x, int y, int currentChoice)
    {
    	if(!shoots[1].checkIfUse())
    	{
    		shoots[1].Shoting(x, y, currentChoice);	
    		return true;
    	}
    	return false;
    	
    }
    
    
    public void updateAllShoot()
    {
    	if(shoots[0].updatePosition(0) == true);
    	if(shoots[1].updatePosition(1) == true);
    }
    
    public void checkShootsInWalls()
    {
    	for(int a = 0; a < shoots.length; a++)
    	{		
    		for(int i = 0; i < 17; i++)
    		{
    			for(int j = 0; j < 22; j++)
    			{
    				if(walls[i][j].state == true)
    				{
    					if(shoots[a].x > walls[i][j].x && shoots[a].x < walls[i][j].x+30 &&
    					   shoots[a].y > walls[i][j].y && shoots[a].y < walls[i][j].y+30)
    					{
    						walls[i][j].state = false;
    						shoots[a].resetCheckedShot(a);
    					}
    				}
    			}
    		}
    	}
    }
    
    public void drawAllShoots(Graphics2D g)
    {
    	g.setColor(Color.RED);
    	for(int i = 0; i < shoots.length; i++)
    	{
    		g.fillRect(shoots[i].x, shoots[i].y, 3, 3);
    	}
    }

    public int checkLeftSideOfWalls(int x, int y) 
    {
		for(int i = 0; i < 17; i++)
		{
			for(int j = 0; j < 22; j++)
			{
				if(walls[i][j].state == true &&
						(
								(y+41>walls[i][j].y &&y+41 < walls[i][j].y+30)
								||(y<walls[i][j].y+30 && y > walls[i][j].y)
								||(y>walls[i][j].y &&y < walls[i][j].y+30)
						)
						
				)
				{
					if(x + 41 >= walls[i][j].x && x+41 <= walls[i][j].x +30 )	

					
					{
					  return walls[i][j].x-41;
					}
				}
			}
			
		}
		return x;
    }
    
    public int checkRightSideOfWalls(int x, int y) 
    {
		for(int i = 0; i < 17; i++)
		{
			for(int j = 0; j < 22; j++)
			{
				if(walls[i][j].state == true &&
					(
								(y+41>walls[i][j].y &&y+41 < walls[i][j].y+30)
								||(y<walls[i][j].y+30 && y > walls[i][j].y)
								||(y>walls[i][j].y &&y < walls[i][j].y+30)
					)
				)
				{
					if(x  <= walls[i][j].x + 30 && x >= walls[i][j].x)		

					{
					  return walls[i][j].x+30;
					}
				}
			}
			
		}
		return x;
    }
  
    public int checkDownSideOfWalls(int x, int y) 
    {
		for(int i = 0; i < 17; i++)
		{
			for(int j = 0; j < 22; j++)
			{
				if(walls[i][j].state == true  &&
					(
						(x+41 > walls[i][j].x && x+41 < walls[i][j].x +30 )
						||(x < walls[i][j].x && x+41 > walls[i][j].x+30)
						||(x > walls[i][j].x && x < walls[i][j].x +30)
					)
			    
						
			    )
				{
					if(y  <= walls[i][j].y + 30 && y >= walls[i][j].y) 			
					{
					  return walls[i][j].y+30;
					}
				}
			}
			
		}
		return y;
    }
    
    public int checkUpSideOfWalls(int x, int y) 
    {
		for(int i = 0; i < 17; i++)
		{
			for(int j = 0; j < 22; j++)
			{
				if(walls[i][j].state == true  &&
					(
						(x+41 > walls[i][j].x && x+41 < walls[i][j].x +30 )
						||(x < walls[i][j].x && x+41 > walls[i][j].x+30)
						||(x > walls[i][j].x && x < walls[i][j].x +30)
					)
			    
						
			    )
				{
					if(y + 41 >= walls[i][j].y && y+41 <= walls[i][j].y + 30) 			
					{
					  return walls[i][j].y-41;
					}
				}
			}
			
		}
		return y;
    }


}
