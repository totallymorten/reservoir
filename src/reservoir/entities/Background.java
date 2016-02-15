package reservoir.entities;

import reservoir.graphics.RenderPriority;
import reservoir.graphics.Sprite;

public class Background extends Sprite
{

	public Background(double x, double y, String imagePath)
	{
		super(x, y, imagePath, true);
	}
	
	
/*
	@Override
	public void render(Graphics2D g, GameView view)
	{
		g.drawImage(img.getSubimage((int)view.position.x, (int)view.position.y, view.width, view.height),0,0, null);
	}
*/


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

}
