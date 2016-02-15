package reservoir.entities;

import java.util.ArrayList;

import reservoir.game.Game;
import reservoir.math.Vector2D;


public abstract class PhysicalEntity extends Entity
{

	public PhysicalEntity(double x, double y, int width, int height)
	{
		super(x, y, width, height, false);

		acc = new Vector2D(0, 0);
	}

	@Override
	public void update(double ms)
	{
		acc.x = Game.game.currentScene.gravityX;
		acc.y = Game.game.currentScene.gravityY;
		
		super.update(ms);
		
		ArrayList<Collideable> collides;
		
		collides = Game.game.checkCollision(this, newPos);

		
		for (Collideable c : collides)
		{
			if (c instanceof Solid)
			{
				Vector2D newPosX = new Vector2D(newPos.x, position.y);
				Vector2D newPosY = new Vector2D(position.x, newPos.y);
				
				if (!Game.game.checkCollision(this, newPosX).contains(c))
				{
					newPos = newPosX;
					speed.y = 0;
				}
				else if (!Game.game.checkCollision(this, newPosY).contains(c))
				{
					newPos = newPosY;
					speed.x = 0;
				}
				else
					newPos = position;
			}
		}
	}


}
