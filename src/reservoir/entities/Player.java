package reservoir.entities;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import reservoir.game.Game;
import reservoir.graphics.AnimatedSprite;
import reservoir.graphics.RenderPriority;
import reservoir.math.Vector2D;
import reservoir.types.Direction;

public class Player extends AnimatedSprite
{
	Direction moveDirection = null;

	Rectangle2D collisionBox;
	
	
	
	public enum JumpState {JUMPING, STANDING};
	
	public JumpState jumpState = JumpState.STANDING;
	
	public Player(int x, int y)
	{
		super(x, y, "/img/animation", "animation", "png", "_", 40);

		//animPath = new int[] {0,1,2,1,0,3,4,3};
	}

	boolean lastUP = false, lastDOWN = false, lastLEFT = false, lastRIGHT = false;
	
	@Override
	public void update(double ms)
	{
		Game.game.currentScene.handleKeys(this,ms);
		super.update(ms);
	}

	@Override
	public void render(Graphics2D g, Vector2D viewPos)
	{
		super.render(g, viewPos);
	}

	@Override
	public RenderPriority getRenderPriority()
	{
		return RenderPriority.ACTOR;
	}

	int collisionOffsetX = 5;
	int collisionOffsetY = 25;
	double collisionW = 40;
	double collisionH = 25;
	
	@Override
	public Rectangle2D getSpecialCollisionBox(Vector2D viewPos)
	{
		return null;
		//return new Rectangle2D.Double(viewPos.x + collisionOffsetX, viewPos.y + collisionOffsetY, collisionW, collisionH);
	}

	@Override
	public void collision(Collideable other)
	{
		// TODO Auto-generated method stub
		
	}
}
