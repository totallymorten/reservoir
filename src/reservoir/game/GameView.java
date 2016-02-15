package reservoir.game;

import java.awt.Graphics2D;

import reservoir.entities.Collideable;
import reservoir.entities.Entity;
import reservoir.graphics.RenderPriority;
import reservoir.math.Vector2D;

public class GameView extends Entity
{
	
	public GameView(int width, int height)
	{
		super(0,0, width, height, false);
	}
	
	double playerEdgeThreshold = 380;
	double speedFactor = 3;
	
	@Override
	public void update(double ms)
	{
		super.update(ms);
		
		Vector2D playerPos = Game.game.view.getViewCoords(Game.game.player.position);
		double diffx=0,diffy=0;
		
		if (playerPos.x < playerEdgeThreshold)
			diffx = playerPos.x - playerEdgeThreshold;
		else if (playerPos.x > (width - playerEdgeThreshold))
			diffx = playerEdgeThreshold - (width - playerPos.x);
		
		if (playerPos.y < playerEdgeThreshold)
			diffy = playerPos.y - playerEdgeThreshold;
		else if (playerPos.y > (height - playerEdgeThreshold))
			diffy = playerEdgeThreshold - (height - playerPos.y);
		
		
		speed = new Vector2D(diffx * speedFactor, diffy * speedFactor);
		
	}

	public boolean isVisible(Entity e)
	{
		return ((Collideable)e).isColliding(this, this);
	}
	
	public Vector2D getViewCoords(Vector2D pos)
	{
		return new Vector2D(pos.x - position.x, pos.y - position.y);
	}

	@Override
	public RenderPriority getRenderPriority()
	{
		return RenderPriority.BACKGROUND;
	}

	@Override
	public void collision(Collideable other)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Graphics2D g, int x, int y)
	{
		// TODO Auto-generated method stub
		
	}

}
