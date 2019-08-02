package GameState;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class GameStateManager {

	private ArrayList<GameState> gameStates;
	private int currentState;
	
	public static final int MENUSTATE = 0;
	public static final int PLAYSTATE = 1; 
	
	public GameStateManager() throws UnknownHostException, IOException
	{
		gameStates = new ArrayList<GameState>();
		
		currentState = MENUSTATE;
		gameStates.add(new MenuState(this));
		
	}
	
	public void setState(int state) throws UnknownHostException, IOException 
	{	
		gameStates.add(new PlayState(this)); // na krotko, topornie nowy stan dodaje przy wyborze przycisku w menu bo i tak mam tylko dwa stany w grze
		currentState = state;				
		gameStates.get(currentState).init();
	}
	public void delState(int state)throws UnknownHostException, IOException 
	{
		gameStates.remove(state);
		currentState = MENUSTATE;
		gameStates.get(currentState).init();
		
	}
	public void update()
	{
		gameStates.get(currentState).update();
		
	}
	public void draw(java.awt.Graphics2D g)
	{
		gameStates.get(currentState).draw(g);
	}
	
	public void keyPressed(int k)
	{
		gameStates.get(currentState).keyPressed(k);
	}
	public void keyReleased(int k)
	{
		gameStates.get(currentState).keyReleased(k);
	}
	
}
