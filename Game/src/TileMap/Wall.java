package TileMap;

public class Wall {
	boolean state;
	int x;
	int y;
	int h;
	int w;
	
	
	
	public Wall(boolean state, int x, int y)
	{
		this.state = state;
		this.x = x;
		this.y = y;

	}
	public Wall(boolean state, int x, int y, int h, int w)
	{
		this.state = state;
		this.x = x;
		this.y = y;
		this.h = h;
		this.w = w;
	}
	
}
