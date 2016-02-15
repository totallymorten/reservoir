package reservoir.game;

import java.util.ArrayList;

import reservoir.entities.Collideable;
import reservoir.entities.Entity;
import reservoir.entities.Player;
import reservoir.entities.Renderable;
import reservoir.entities.Updateable;
import reservoir.game.Scenes.SceneName;

public abstract class Scene
{
	public SceneName name;
	public ArrayList<Renderable> renderables = new ArrayList<Renderable>();
	public ArrayList<Updateable> updateables = new ArrayList<Updateable>();
	public ArrayList<Collideable> collideables = new ArrayList<Collideable>();

	// gravity / attraction
	public double gravityX = 0, gravityY = 0;
	
	// max allowed pixels/s
	public double maxSpeedX = 10000, maxSpeedY = 10000;
	
	// defined as the pixels/s you lose every second
	public double frictionX = 0, frictionY = 0;
	
	// rotation friction
	public double rotFriction = 0;
	
	// max rotation
	public double maxRot = 0;
	
	private PlayerKeyHandler playerKeyHandler;
	
	public Scene(SceneName name, PlayerKeyHandler keyHandler)
	{
		this.name = name;
		this.playerKeyHandler = keyHandler;
	}
	
	public void handleKeys(Player p, double ms)
	{
		playerKeyHandler.handleKeys(p, ms);
	}
	
	public void addEntity(Entity e)
	{
		updateables.add(e);
		renderables.add(e);
		collideables.add(e);
	}
	
	public void removeEntity(Entity e)
	{
		updateables.remove(e);
		renderables.remove(e);
		collideables.remove(e);
	}

	public abstract void initScene();
}
