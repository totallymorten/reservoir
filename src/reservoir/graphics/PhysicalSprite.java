package reservoir.graphics;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import reservoir.entities.PhysicalEntity;

public abstract class PhysicalSprite extends PhysicalEntity
{
	public BufferedImage img = null;
	
	
	public PhysicalSprite(double x, double y, String imagePath)
	{
		super(x, y, 0, 0);
		
		try
		{
			img = ImageIO.read(getClass().getResourceAsStream(imagePath));
			width = img.getWidth(null);
			height = img.getHeight(null);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	
	@Override
	public void draw(Graphics2D g, int x, int y)
	{
		g.drawImage(img, x, y, null);	
	}



	public PhysicalSprite(double x, double y)
	{
		super(x, y, 0, 0);
	}

	@Override
	public RenderPriority getRenderPriority()
	{
		return RenderPriority.ACTOR;
	}

	
}
