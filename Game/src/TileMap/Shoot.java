package TileMap;

public class Shoot {
	boolean inUse;
	int currentChoice;
	int x;
	int y;
	
	public Shoot(int z)
	{
		this.x = 60;
		this.y = 90+ z;
		this.inUse = false;
	}
	public int getX()
	{
		return x;
	}
	public int getY()
	{
		return y;
	}
	
	public boolean checkIfUse()
	{
		return inUse;
	}
	
	public void Shoting(int x, int y,int currentChoice)
	{
		this.x = x+20;
		this.y = y+20;
		this.inUse = true;
		this.currentChoice  = currentChoice;
	}
	public void resetMyShot()
	{
		inUse = false;
		this.x = 60;
		this.y = 90;
	}
	public void resetFriendShot()
	{
		inUse = false;
		this.x = 60;
		this.y = 95;
	}
	public void resetCheckedShot(int i)
	{
		if(i == 0)
		{
			resetMyShot();
		}
		if(i == 1)
		{
			resetFriendShot();
		}
	}
	
	
	
	public boolean updatePosition(int i)
	{
	  if(inUse == true)
	  {
		if(this.x < 0 || this.x > 1000 || this.y < 0 || this.y > 482) 
		{
			resetCheckedShot(i);
			return true;
		}
			
		
		if(currentChoice == 0)
		{
			this.y = this.y - 7;
		}
		if(currentChoice == 1)
		{
			this.y = this.y + 7;
		}
		if(currentChoice == 2)
		{
			this.x = this.x - 7;
		}
		if(currentChoice == 3)
		{
			this.x = this.x + 7;
		}
	  }
	  return false;
	}
	public boolean updateFriendPosition()
	{
	  if(inUse == true)
	  {
		if(this.x < 0 || this.x > 1000 || this.y < 0 || this.y > 482) 
		{
			resetFriendShot();
			return true;
		}
			
		
		if(currentChoice == 0)
		{
			this.y = this.y - 7;
		}
		if(currentChoice == 1)
		{
			this.y = this.y + 7;
		}
		if(currentChoice == 2)
		{
			this.x = this.x - 7;
		}
		if(currentChoice == 3)
		{
			this.x = this.x + 7;
		}
	  }
	  return false;
	}
	
}
