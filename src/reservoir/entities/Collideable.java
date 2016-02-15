package reservoir.entities;

import java.awt.Shape;

import reservoir.game.GameView;

public interface Collideable
{
	//public Shape getCollisionBox(Vector2D pos);
	public Shape getCollisionBox(GameView view);
	//public Rectangle2D getSpecialCollisionBox(Vector2D pos);
	//public Rectangle2D getStdCollisionBox(Vector2D pos);
	public boolean isColliding(Collideable other, GameView view);
	public void collision(Collideable other);
}
