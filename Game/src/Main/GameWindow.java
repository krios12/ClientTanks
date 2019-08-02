package Main;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.UnknownHostException;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.*;
import javax.swing.JPanel;

import GameState.GameStateManager;


public class GameWindow extends JPanel implements Runnable, KeyListener {

	// rozmiary okna
	public static final int WIDTH =	1920;
	public static final int HEIGHT = 1080;
	public static final int SCALE = 2;

	// gra
	private Thread thread;
	private boolean running;
	private int FPS = 30;
	private long targetTime = 1000 / FPS;

	// obraz
	private BufferedImage image;
	
	private Graphics2D g;
	
	
	// game state manager
	private GameStateManager gsm;
	
	
	

	public GameWindow() {
		super();
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setFocusable(true);
		requestFocus();
	}

	public void addNotify() {
		super.addNotify();
		if (thread == null) {
			thread = new Thread(this);
			addKeyListener(this);
			thread.start();
		}

	}

	private void init() throws UnknownHostException, IOException {
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);	
		g = (Graphics2D) image.getGraphics();
		
		running = true;
		
		gsm =new GameStateManager();

	}

	public void run() {
		try {
			init();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		long start;
		long elapsed;
		long wait;

		// main loop
		while (running) {
			start = System.nanoTime();

			update();
			draw();
			drawToScreen();

			elapsed = System.nanoTime() - start;

			wait = targetTime - elapsed / 1000;

			try {
				//Thread.sleep(wait);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void update()
	{
		
		gsm.update();
	}

	private void draw()
	{
		
		gsm.draw(g);
	}

	private void drawToScreen() {
		Graphics g2 = getGraphics();
		g2.drawImage(image, 0, 0,WIDTH * SCALE, HEIGHT * SCALE, null);
		g2.dispose();

	}

	public void keyTyped(KeyEvent key)
	{
			
	} 

	public void keyPressed(KeyEvent key)
	{
		gsm.keyPressed(key.getKeyCode());
	}

	public void keyReleased(KeyEvent key)
	{
		gsm.keyReleased(key.getKeyCode());
	}

}
