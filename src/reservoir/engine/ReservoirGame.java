package reservoir.engine;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import reservoir.entities.Background;
import reservoir.entities.Coin;
import reservoir.entities.Player;
import reservoir.entities.Renderable;
import reservoir.entities.Solid;
import reservoir.entities.Updateable;
import reservoir.game.Game;
import reservoir.game.GameView;
import reservoir.game.Scene;
import reservoir.game.Scenes;
import reservoir.game.Scenes.SceneName;
import tools.Numbers;
import engine.JavaEngine;
import engine.Keys;


public class ReservoirGame extends JavaEngine
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3841844401025841597L;
	
	public ReservoirGame (int w, int h, double fps)
	{
		super(w,h,fps);
		
		initGame();
	}
	
	private void changeScene()
	{
		state = GameState.PAUSED;
		try
		{
			// wait for update loops to finish and pause to have manifested
			Thread.sleep(1000);
			Game.game.changeScene(SceneName.Water);
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		state = GameState.RUNNING;
	}
	
	private void updateUpdateables(double period)
	{
		for (Updateable u : Game.game.currentScene.updateables)
		{
			u.update(period);
		}
	}
	
	private void renderRenderables(Graphics2D g)
	{
		for (Renderable r : Game.game.currentScene.renderables)
		{
			r.render(g, Game.game.view);
		}
	}

	
	public void initGame()
	{
		Scene main,water;
		main = Scenes.mainScene();
		water = Scenes.waterScene();
		
		Game.game.addScene(main);
		Game.game.addScene(water);
		
		Game.game.player = new Player(1200, 1000);
		Game.game.addEntity(Game.game.player);
		water.addEntity(Game.game.player);
		
		Game.game.addEntity(new Coin(1350,1100));
		Game.game.addView(new GameView(WIDTH, HEIGHT));
		Game.game.addRenderable(new Background(1000, 1000, "/img/tank/room.png"));
		Game.game.addEntity(new Solid(1000,1335,1000,30,false));
	}
	
	public void render(Graphics2D g)
	{
		
		renderRenderables(g);

		int y = 20;
		int xLeft = 10;
		int xRight = WIDTH - 200;
		
		g.setColor(Color.white);
		g.setFont(font);
		g.drawString("[ESC]", xLeft, y);
		g.drawString(getFormattedCurrentFPS(), xRight, y);
		g.drawString(getFormattedAvgUpdateTime(), xRight, y + 20);
		g.drawString(getFormattedAvgRenderTime(), xRight, y + 40);

		/** PLAYING WITH LIGHTING
		g.setColor(Color.black);
		g.fillRect(80, 80, 20, 20);
		
		
		
		int trans = 0;
		int offSet = 30;
		for (int i = 0; i < 30; i++)
		{
			g.setColor(new Color(255,255,255,trans));
			g.fillRect((int)player.position.x-offSet, (int)player.position.y-offSet, 50 + offSet * 2, 50 + offSet * 2);
			trans += 1;
			offSet -= 1;
		}
		*/
	}
	
	public void update(double ns)
	{
		double ms = ns / Numbers.tenE6;

		updateUpdateables(ms);
		Game.game.checkAllCollisions();
	}
	
	Font font = new Font("Courier New",Font.BOLD,20);
	
	@Override
	public void handlePreCycle()
	{
		// removing and adding game elements
		Game.game.handleRenderables();
		Game.game.handleUpdateables();
		Game.game.handleCollideables();
		
		if (Keys.pull(KeyEvent.VK_N))
		{
			changeScene();
		}
	}

	public static void main(String[] args)
	{
		(new Thread(new ReservoirGame(1000,700,100))).start();
	}

	@Override
	public void preExit()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init()
	{
		// TODO Auto-generated method stub
		
	}
}
