package reservoir.entities;

import reservoir.game.Game;
import reservoir.graphics.PhysicalSprite;

public class Coin extends PhysicalSprite
{

	public Coin(double x, double y)
	{
		super(x, y, "/img/coin.png");
	}

	@Override
	public void collision(Collideable other)
	{
		if (other instanceof Player)
			Game.game.removeEntity(this);
	}

}
