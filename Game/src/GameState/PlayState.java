package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.imageio.ImageIO;

import TileMap.BackGround;
import TileMap.MapGround;


import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;

public class PlayState extends GameState {

	private MapGround mg;
	private int currentChoice ;
	
	private Font font;
	private Color color;
	
	int xPosition;
	int yPosition;
	int xFriend;
	int yFriend;
	int friendChoice;
	
	private BufferedImage imageUp;
	private BufferedImage imageDown;
	private BufferedImage imageLeft;
	private BufferedImage imageRight;
	
	private BufferedImage image2Up;
	private BufferedImage image2Down;
	private BufferedImage image2Left;
	private BufferedImage image2Right;
	
	boolean shooted;
	int shootX;
	int shootY;
	int shootV;
	
	public Socket socket;
	
	String rodzaj;
	int liczba1;
	int liczba2;
	int kier;
	
	DataOutputStream out;
	DataInputStream  in;
	String outMess;
	String inMess;
	
    boolean iWantToExit;
    String key;
    
    String encrypt;
	
	
	

	
	public PlayState(GameStateManager gsm) throws UnknownHostException, IOException
	{
		SocketFactory sf = SSLSocketFactory.getDefault();
		socket = sf.createSocket("localhost",6666);
		out = new DataOutputStream(socket.getOutputStream());
		in = new DataInputStream(socket.getInputStream());
		
		key = in.readUTF();	
		
		encrypt = AES.decrypt(in.readUTF(), key);
		checkMessage(encrypt);		
		xPosition = liczba1;
		yPosition = liczba2;
		currentChoice= kier;
		
		encrypt = AES.decrypt(in.readUTF(), key);
		checkMessage(encrypt);	
		xFriend = liczba1;
		yFriend = liczba2;
		friendChoice = kier;
		
		shooted = false;
		iWantToExit = false;
	
		
		this.gsm = gsm;
		try
		{
			mg = new MapGround("/Backgrounds/plansza.gif", 1);
			imageUp = ImageIO.read(getClass().getResourceAsStream("/Backgrounds/tankUp.gif"));	
			imageDown = ImageIO.read(getClass().getResourceAsStream("/Backgrounds/tankDown.gif"));
			imageLeft = ImageIO.read(getClass().getResourceAsStream("/Backgrounds/tankLeft.gif"));
			imageRight = ImageIO.read(getClass().getResourceAsStream("/Backgrounds/tankRight.gif"));
			image2Up = ImageIO.read(getClass().getResourceAsStream("/Backgrounds/friendUp.gif"));	
			image2Down = ImageIO.read(getClass().getResourceAsStream("/Backgrounds/friendDown.gif"));
			image2Left = ImageIO.read(getClass().getResourceAsStream("/Backgrounds/friendLeft.gif"));
			image2Right = ImageIO.read(getClass().getResourceAsStream("/Backgrounds/friendRight.gif"));
			
			
			font = new Font("Century Gothic", Font.PLAIN,20);
			color = new Color(0,255,0);
			mg.setVector(0, 0);
			
		}
		catch(Exception e )
		{
			e.printStackTrace();
		}
		
		
	}
	
	public void checkMessage(String mess)
	{
		String[] parts = new String[4];
		parts = mess.split(":");
		this.rodzaj = parts[0];
		this.liczba1 = Integer.parseInt(parts[1]);
		this.liczba2 = Integer.parseInt(parts[2]);
		this.kier = Integer.parseInt(parts[3]);
		
	}
	
	
	
	public void init() 
	{
		
	}
	public void update() 
	{
		mg.update();
	}
	public void draw(Graphics2D g)
	{
		if(iWantToExit== true)
		{
			outMess ="EXT:0:0:0";
			encrypt= AES.encrypt(outMess, key);
			try {
				out.writeUTF(encrypt);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			try {
				socket.close();
				gsm.delState(1);// usuwam stan tej rozgrywki
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}		
		else if(shooted == true)
		{
			outMess ="SOT:"+this.shootX+":"+this.shootY+":"+this.shootV;
			shooted = false;
		}
		else
		{
			outMess ="COR:"+this.xPosition+":"+this.yPosition+":"+this.currentChoice;
		}	
		try 
		{
			encrypt= AES.encrypt(outMess, key);
			out.writeUTF(encrypt);
		} catch (IOException e)
		{
			
			e.printStackTrace();
		}
		
		try 
		{
		  encrypt =in.readUTF();
		  inMess =AES.decrypt(encrypt, key);
		} catch (IOException e)
		{
			e.printStackTrace();
		}	
	    checkMessage(inMess);
		if(rodzaj.contains("COR"))
		{
			
			this.xFriend = liczba1;
			this.yFriend = liczba2;
			this.friendChoice = kier;
		}
		else if(rodzaj.contains("EXT"))
		{
			try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				gsm.delState(1);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			System.out.println("Odebrano info o strzale");
		    mg.initFriendShoot(liczba1,liczba2,kier);
		}
		

		// draw mg
		mg.draw(g);	
		mg.updateAllShoot();
		mg.drawAllShoots(g);
		mg.checkShootsInWalls();
		mg.drawWalls(g);
		
		
		g.setColor(color);
		g.setFont(font);
		g.drawString("xCordinate: " +xPosition , 5, 17);
		g.drawString("yCordinate: " +yPosition , 5, 35);
		g.drawString("tankWidth: "  +Integer.toString(imageUp.getHeight()), 5 , 53);
		g.drawString("xFriend: " +xFriend , 5, 71);
		g.drawString("yFriend: " +yFriend , 5, 89);
		
		if(currentChoice == 0)
		{
			g.drawImage(imageUp, xPosition, yPosition,null);
			
		}
		if(currentChoice == 1)
		{
			g.drawImage(imageDown, xPosition, yPosition,null);
		}
		if(currentChoice == 2)
		{
			g.drawImage(imageLeft, xPosition, yPosition,null);
		}
		if(currentChoice == 3)
		{
			g.drawImage(imageRight, xPosition, yPosition,null);
		}
		
		if(friendChoice == 0)
		{
			g.drawImage(image2Up, xFriend, yFriend,null);	
		}
		if(friendChoice == 1)
		{
			g.drawImage(image2Down, xFriend, yFriend,null);
		}
		if(friendChoice == 2)
		{
			g.drawImage(image2Left, xFriend, yFriend,null);
		}
		if(friendChoice == 3)
		{
			g.drawImage(image2Right, xFriend, yFriend,null);
			
		}
	}
	
	
	public void keyPressed(int k)
	{
		if(k == KeyEvent.VK_UP)
		{
			
			currentChoice= 0;
			if(yPosition > 2)
			{
				yPosition-=4;
			}
			yPosition=mg.checkDownSideOfWalls(xPosition, yPosition);	
		}
		if(k == KeyEvent.VK_DOWN)
		{	
			
			currentChoice= 1;
			if(yPosition < 482)
			{
				yPosition+=4;
			}
			yPosition=mg.checkUpSideOfWalls(xPosition, yPosition);
		}
		if(k == KeyEvent.VK_LEFT)
		{
			
			currentChoice= 2;
			if(xPosition > 0)
			{
				xPosition-=4;
			}
			xPosition=mg.checkRightSideOfWalls(xPosition, yPosition);	
		}
		if(k == KeyEvent.VK_RIGHT)
		{	
			
			currentChoice= 3;	
			if(xPosition < 916)
			{
				xPosition+=4;
			}
			xPosition=mg.checkLeftSideOfWalls(xPosition, yPosition);	
		}
		if(k == KeyEvent.VK_SPACE)
		{
			shooted = mg.initMyShoot(xPosition,yPosition,currentChoice);
			shooted = true;
			shootX = xPosition;
			shootY = yPosition;
			shootV = currentChoice;

		}
		if(k == KeyEvent.VK_ESCAPE)
		{		
			iWantToExit = true;
		}
		
	}
	public void keyReleased(int k)
	{
	
	}

	
}
