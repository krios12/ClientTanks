package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.JFrame;

import com.sun.glass.ui.Menu;

import TileMap.BackGround;
public class MenuState extends GameState {

	private BackGround bg;
	private int currentChoice = 0;
	public String[] options={"Start","Config[In progress]","Exit"};
	
	private Color titleColor;
	private Font titleFont;
	
	private Font font;
	
	
	public MenuState(GameStateManager gsm)
	{
		this.gsm = gsm;
		try
		{
			bg = new BackGround("/Backgrounds/bg2.gif", 0);
			bg.setVector(-0.1, 0);
			titleColor = new Color(100,100,100);
			titleFont = new Font("Century Gothic", Font.PLAIN,50); 
			font = new Font("Arial", Font.PLAIN,20);
		}
		catch(Exception e )
		{
			e.printStackTrace();
		}
		
		
	}
	
	public void init() 
	{
	      options[0]="Start";
	}
	public void update() 
	{
		bg.update();
	}
	public void draw(Graphics2D g)
	{
		// draw bg
		bg.draw(g);
		
		// draw title
		g.setColor(titleColor);
		g.setFont(titleFont);
		g.drawString("Tanks", 80, 70);
		
		// draw menu option
		g.setFont(font);
		for(int i = 0; i < options.length; i++)
		{
			if(i == currentChoice)
			{
				g.setColor(Color.RED);
			}
			else
			{
				g.setColor(Color.BLACK);
			}
			g.drawString(options[i], 145, 140 + i *30);
		}
	}
	
	
	private void select() throws UnknownHostException, IOException 
	{
		if(currentChoice == 0)
		{
			// start
			options[0]= options[0]+ "                             Wait for other Player";
			gsm.setState(1); // o tu dodaje dopiero stan PlayState
			
		}
		if(currentChoice == 1)
		{		
			// help
		}
		if(currentChoice == 2)
		{
			// exit
			System.exit(0);
		}
	}
	public void keyPressed(int k)
	{
		if(k == KeyEvent.VK_ENTER)
		{
				try {
					select();
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

		}
		if(k == KeyEvent.VK_UP)
		{
			currentChoice--;
			if(currentChoice == -1)
			{
				currentChoice = options.length - 1;
			}
		}
		if(k == KeyEvent.VK_DOWN)
		{
			currentChoice++;
			if(currentChoice == options.length)
			{
				currentChoice = 0;
			}
		}
		if(k == KeyEvent.VK_ESCAPE)
		{
			options[0]= "Start";
		}
		
	}
	public void keyReleased(int k)
	{
		
	}


}
