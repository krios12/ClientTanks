package Main;


import javax.swing.JFrame;

public class Game {
	
	public static void main(String[] args)
	{
		JFrame menu  = new JFrame("Game");
		menu.setContentPane(new GameWindow());
		menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menu.setResizable(false);
		menu.pack();
		menu.setVisible(true);
		
		
	}
	

}
