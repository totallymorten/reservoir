package reservoir.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import reservoir.entities.Collideable;
import reservoir.entities.Entity;
import reservoir.entities.Player;
import reservoir.entities.Renderable;
import reservoir.entities.Updateable;
import reservoir.game.Scenes.SceneName;
import reservoir.graphics.RenderPriority;
import reservoir.math.Vector2D;

public class Game
{
	public static final Game game = new Game();
	
	public static final boolean DEBUG = true;
	
	private Game()
	{
		
	}
	
	public GameView view;
	public Player player;
	public Scene currentScene; 
	
	public HashMap<SceneName,Scene> scenes = new HashMap<SceneName, Scene>();
	
	public ArrayList<Renderable> removeRenderables = new ArrayList<Renderable>();
	public ArrayList<Renderable> addRenderables = new ArrayList<Renderable>();

	public ArrayList<Updateable> removeUpdateables = new ArrayList<Updateable>();
	public ArrayList<Updateable> addUpdateables = new ArrayList<Updateable>();

	public ArrayList<Collideable> removeCollideables = new ArrayList<Collideable>();
	public ArrayList<Collideable> addCollideables = new ArrayList<Collideable>();

	public HashMap<Integer, Entity> entities = new HashMap<Integer, Entity>();
	
	public synchronized void removeRenderable(Renderable r)
	{
		removeRenderables.add(r);
	}

	public synchronized void addRenderable(Renderable r)
	{
		addRenderables.add(r);
	}

	public synchronized void clearRenderableArrays()
	{
		removeRenderables.clear();
		addRenderables.clear();
	}

	public synchronized void removeCollideable(Collideable c)
	{
		removeCollideables.add(c);
	}

	public synchronized void addCollideable(Collideable c)
	{
		addCollideables.add(c);
	}

	public synchronized void clearCollideableArrays()
	{
		removeCollideables.clear();
		addCollideables.clear();
	}

	public synchronized void removeUpdateable(Updateable u)
	{
		removeUpdateables.add(u);
	}

	public synchronized void addUpdateable(Updateable u)
	{
		addUpdateables.add(u);
	}
	
	public synchronized void removeEntity(Entity e)
	{
		removeEntity(e.entityId);
	}
	
	public synchronized void removeEntity(int entityId)
	{
		Entity e = entities.remove(new Integer(entityId));
		
		if (e instanceof Renderable)
			removeRenderable(e);
		if (e instanceof Updateable)
			removeUpdateable(e);
		if (e instanceof Collideable)
			removeCollideable(e);
	}

	public synchronized void addView(GameView view)
	{
		addUpdateable(view);
		
		this.view = view;
	}
	
	public synchronized void addEntity(Entity e)
	{
		if (e instanceof Updateable)
			addUpdateable(e);
		if (e instanceof Renderable)
			addRenderable(e);
		if (e instanceof Collideable)
			addCollideable(e);
			
		entities.put(new Integer(e.entityId), e);
	}
	
	public synchronized Entity getEntity(int entityId)
	{
		return entities.get(new Integer(entityId));
	}
	
	public synchronized List<Entity> getEntities()
	{
		ArrayList<Entity> entityList = new ArrayList<Entity>();
		
		for (Entity e : entities.values())
		{
			entityList.add(e);
		}
		
		return entityList;
	}

	private synchronized void clearUpdateableArrays()
	{
		removeUpdateables.clear();
		addUpdateables.clear();
	}	
	
	public synchronized void handleUpdateables()
	{
		currentScene.updateables.addAll(addUpdateables);
		currentScene.updateables.removeAll(removeUpdateables);

		clearUpdateableArrays();
	}
	
	public synchronized void handleCollideables()
	{
		currentScene.collideables.addAll(addCollideables);
		currentScene.collideables.removeAll(removeCollideables);

		clearCollideableArrays();
	}

	public void handleRenderables()
	{
		for (Renderable r : addRenderables)
		{
			searchAndAddRenderable(r);
		}

		currentScene.renderables.removeAll(removeRenderables);

		clearRenderableArrays();
	}
	
	
	private void searchAndAddRenderable(Renderable r)
	{
		int i = 0;
		for (Renderable r1 : currentScene.renderables)
		{
			if (RenderPriority.leq(r1.getRenderPriority(), r.getRenderPriority()))
			{
				i++;
			}
			else
			{
				// insert renderables in front of current element
				break;
			}
			
		}
		currentScene.renderables.add(i,r);
	}

	public void checkAllCollisions()
	{
		for (Collideable c1 : currentScene.collideables)
		{
			for (Collideable c2 : currentScene.collideables)
			{
				if (c1 != c2 && c1.isColliding(c2, view))
				{
					c1.collision(c2);
					c2.collision(c1);
				}
			}							
		}
	}
	
	public ArrayList<Collideable> checkCollision(Entity e, Vector2D pos)
	{
		ArrayList<Collideable> collisionObjects = new ArrayList<Collideable>();
		
		for (Collideable c1 : currentScene.collideables)
		{
			if (c1 != e && e.wouldCollideAtPos(c1, view, pos))
			{
				collisionObjects.add(c1);
			}
		}
		
		return collisionObjects;
	}
	
	public void addScene(Scene scene)
	{
		scenes.put(scene.name, scene);
		
		if (scenes.size() == 1)
			currentScene = scene;
	}
	
	public void changeScene(SceneName name)
	{
		Scene newScene = scenes.get(name);
		newScene.initScene();
		currentScene = scenes.get(name);
	}

}
