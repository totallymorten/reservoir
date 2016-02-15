package reservoir.entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Rectangle2D;

import reservoir.game.Game;
import reservoir.game.GameView;
import reservoir.math.Vector2D;
import tools.Numbers;

public abstract class Entity implements Updateable, Renderable, Collideable
{
	public Vector2D position, newPos;
	public Vector2D speed = new Vector2D(0, 0);
	public Vector2D acc = new Vector2D(0,0);
	public double rotation = 0;
	public double rotSpeed = 0;
	public boolean fixed = false;
	
	public int width, height;

	public int entityId = this.hashCode();
	
	public Entity(double x, double y, int width, int height, boolean fixed)
	{
		this.position = new Vector2D(x,y);
		this.newPos = this.position;
		this.width = width;
		this.height = height;
		this.fixed = fixed;
		
	}
	
	public void render(Graphics2D g, GameView view)
	{
		this.render(g, view.getViewCoords(this.position));
	}
	
	public void render(Graphics2D g, Vector2D viewPos)
	{
		
		if (rotation != 0)
		{
			int x = -width / 2;
			int y = -height / 2;
			
			double transX = viewPos.x + width/2;
			double transY = viewPos.y + height/2; 
			
			g.translate(transX, transY);
			g.rotate(rotation);
			
			draw(g,x,y);
			
			// drawing collision box
			Shape collision = getCollisionBox(viewPos);
			
			g.rotate(-rotation);
			g.translate(-transX, -transY);
			
			g.setColor(Color.red);
			g.draw(collision);
		}
		else
		{
			draw(g,(int)viewPos.x,(int)viewPos.y);

			g.setColor(Color.red);
			g.draw(getCollisionBox(viewPos));
			
		}
	}

	
	@Override
	public void update(double ms)
	{
		if (fixed)
			return;
		
		this.position = this.newPos;
		
		double secs = ms / Numbers.tenE3;
		
		// acceleration
		speed.x += acc.x * secs;
		speed.y += acc.y * secs;
		
		
		if (speed.x == 0 && speed.y == 0 && rotSpeed == 0)
			return;

		// scene friction X
		if (Game.game.currentScene.frictionX > Math.abs(speed.x))
			speed.x = 0;
		else if (speed.x < 0)
			speed.x += Game.game.currentScene.frictionX;
		else // speed.x > 0
			speed.x -= Game.game.currentScene.frictionX;
		
		// scene friction Y
		if (Game.game.currentScene.frictionY > Math.abs(speed.y))
			speed.y = 0;
		else if (speed.y < 0)
			speed.y += Game.game.currentScene.frictionY;
		else // speed.x > 0
			speed.y -= Game.game.currentScene.frictionY;

		// enforcing max speed x
		if (speed.x > Game.game.currentScene.maxSpeedX)
			speed.x = Game.game.currentScene.maxSpeedX;
		else if (speed.x < -Game.game.currentScene.maxSpeedX)
			speed.x = -Game.game.currentScene.maxSpeedX;
		
		// enforcing max speed y
		if (speed.y > Game.game.currentScene.maxSpeedY)
			speed.y = Game.game.currentScene.maxSpeedY;
		else if (speed.y < -Game.game.currentScene.maxSpeedY)
			speed.y = -Game.game.currentScene.maxSpeedY;
		

		// scene friction rot
		if (Game.game.currentScene.rotFriction > Math.abs(rotSpeed))
			rotSpeed = 0;
		else if (rotSpeed < 0)
			rotSpeed += Game.game.currentScene.rotFriction;
		else // speed.x > 0
			rotSpeed -= Game.game.currentScene.rotFriction;

		// enforcing max speed rot
		if (rotSpeed > Game.game.currentScene.maxRot)
			rotSpeed = Game.game.currentScene.maxRot;
		else if (rotSpeed < -Game.game.currentScene.maxRot)
			rotSpeed = -Game.game.currentScene.maxRot;

		
		
		// rotation
		rotation += rotSpeed;
		
		newPos = new Vector2D(position.x + speed.x * secs, position.y + speed.y * secs);
	}

	@Override
	public Shape getCollisionBox(GameView view)
	{
		return getCollisionBox(view.getViewCoords(position));
	}
	
	//@Override
	protected Shape getCollisionBox(Vector2D viewPos)
	{
		Rectangle2D box;

		box = getSpecialCollisionBox(viewPos);
		
		if (box == null)
			box = getStdCollisionBox(viewPos);

		if (rotation != 0)
			return getRotatedCollisionBox(box, viewPos);
		
		return box;
	}

	public boolean wouldCollideAtPos(Collideable other, GameView view, Vector2D pos)
	{
		return isColliding(other, view, this.getCollisionBox(view.getViewCoords(pos)));
	}

	@Override
	public boolean isColliding(Collideable other, GameView view)
	{
		return isColliding(other, view, getCollisionBox(view));
	}
	
	private boolean isColliding(Collideable other, GameView view, Shape thisBox)
	{
		Shape otherBox;
		
		otherBox = other.getCollisionBox(view);
		
		if (thisBox instanceof Rectangle2D)
		{
			return otherBox.intersects((Rectangle2D)thisBox);
		}
		else if (otherBox instanceof Rectangle2D)
		{
			return thisBox.intersects((Rectangle2D)otherBox);
		}
		else // both are polygons
		{
			return (pContainsP2((Polygon)thisBox, (Polygon)otherBox) 
				 || pContainsP2((Polygon)otherBox, (Polygon)thisBox));
		}
	}

	public boolean pContainsP2(Polygon p, Polygon p2)
	{
		for (int i = 0; i < p2.npoints; i++)
		{
			if (p.contains(p2.xpoints[i], p2.ypoints[i]))
				return true;
		}
		
		return false;
	}
	
	//@Override
	protected Rectangle2D getSpecialCollisionBox(Vector2D viewPos)
	{
		return null;
	}

	public Polygon getRotatedCollisionBox(Rectangle2D r, Vector2D viewPos)
	{       
		AffineTransform at = AffineTransform.getRotateInstance(rotation, viewPos.x + width/2, viewPos.y + height/2);
	
	    PathIterator i = r.getPathIterator(at);
	    
	    int[] xs = new int[50];
	    int[] ys = new int[50];
	    
	    int n = 0;
	    while (!i.isDone()) 
	    {
	        double[] xy = new double[2];
	        i.currentSegment(xy);
	        
	        xs[n] = (int) xy[0];
	        ys[n] = (int) xy[1];
	        
	        i.next();
	        n++;
	    }
	    
	    return new Polygon(xs, ys, n-1);
	}

	//@Override
	protected Rectangle2D getStdCollisionBox(Vector2D viewPos)
	{
		return new Rectangle2D.Double(viewPos.x, viewPos.y, width, height);
	}


}
