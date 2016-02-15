package reservoir.graphics;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import reservoir.math.Vector2D;

public abstract class AnimatedSprite extends PhysicalSprite
{
	BufferedImage[][] sprites;
	
	double msDelay = 20; // delay between sprite change
	double msDelayCountDown = msDelay;
	int wCount = 0;
	int hCount = 0;	
	
	int currentW = 0;
	
	protected int[] animPath = null;
	protected int animPathCount = 0;
	
	public enum AnimState {ANIMATING, STOPPED};
	
	public AnimState state = AnimState.STOPPED;
	
	public AnimatedSprite(double x, double y, String imagePath, int tileWidth, int tileHeight)
	{
		super(x, y, imagePath);

		
		sprites = new BufferedImage[height / tileHeight][width / tileHeight];
		
		for (int h = 0; h < height; h += tileHeight)
		{
			for (int w = 0; w < width; w += tileWidth)
			{
				sprites[hCount][wCount] = img.getSubimage(w, h, tileWidth, tileHeight);
				wCount++;
			}
			hCount++;
		}
			
		width = tileWidth;
		height = tileHeight;
		
		img = sprites[0][0];
	}

	public AnimatedSprite(double x, double y, String imagePath, String imageName, String imageExt, String prefix, int count)
	{
		super(x, y);
		
		sprites = new BufferedImage[1][count];
		
		String fullImageName;
		for (int w = 0; w < count; w++)
		{
			try
			{
				fullImageName = imagePath + "/" + imageName + prefix + String.format("%05d", w) + "." + imageExt;
				sprites[0][w] = ImageIO.read(getClass().getResourceAsStream(fullImageName));
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		hCount = 1;
		wCount = 40;
		
		img = sprites[0][0];

		width = img.getWidth();
		height = img.getHeight();
	}

	@Override
	public RenderPriority getRenderPriority()
	{
		return RenderPriority.ACTOR;
	}

	@Override
	public void render(Graphics2D g, Vector2D viewPos)
	{
		img = sprites[0][currentW];
		super.render(g, viewPos);
	}

	@Override
	public void update(double ms)
	{
		super.update(ms);
		
		if (state == AnimState.STOPPED)
			return;
		
		msDelayCountDown -= ms;
		
		if (msDelayCountDown < 0)
		{
			if (animPath == null)
				currentW = ++currentW % wCount;
			else
			{
				currentW = animPath[++animPathCount % animPath.length];
			}
			msDelayCountDown = msDelay;
		}
	}
	

}
