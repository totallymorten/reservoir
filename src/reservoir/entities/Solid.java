package reservoir.entities;

import java.awt.Color;
import java.awt.Graphics2D;

import reservoir.graphics.RenderPriority;

public class Solid extends Entity
{

	boolean visible;
	
	public Solid(double x, double y, int width, int height, boolean visible)
	{
		super(x, y, width, height, true);
	}

	public Solid(double x, double y, int width, int height, boolean visible, double rotation)
	{
		super(x, y, width, height, true);
		
		this.rotation = rotation;
	}

	@Override
	public RenderPriority getRenderPriority()
	{
		return RenderPriority.ACTOR;
	}

	@Override
	public void collision(Collideable other)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void draw(Graphics2D g, int x, int y)
	{
		if (visible)
		{
			g.setColor(Color.yellow);
			g.fillRect(x, y, width, height);			
		}
	}

}
