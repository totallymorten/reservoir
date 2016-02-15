package reservoir.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import engine.Keys;

import reservoir.entities.Collideable;
import reservoir.entities.Entity;
import reservoir.entities.Player;
import reservoir.entities.Player.JumpState;
import reservoir.graphics.RenderPriority;
import reservoir.graphics.AnimatedSprite.AnimState;



public abstract class Scenes
{
	public enum SceneName {Main, Water};
	
	public static PlayerKeyHandler mainHandler = new PlayerKeyHandler()
	{
		// player configuration
		public int walkAcc = 5; // pixels pr. sec
		public int jumpSpeed = 500;

		
		@Override
		public void handleKeys(Player p, double ms)
		{
			if (p.speed.y == 0)
				p.jumpState = JumpState.STANDING;
			
			//speed.x = 0;
			
			p.state = AnimState.STOPPED;
			
			if (Keys.check(KeyEvent.VK_UP))
			{
				if (p.jumpState == JumpState.STANDING)
				{
					p.speed.y -= jumpSpeed;
					p.jumpState = JumpState.JUMPING;
				}
			}
			else
			{
			}
			
			if (Keys.check(KeyEvent.VK_DOWN))
			{
			}
			else
			{
			}
			
			if (Keys.check(KeyEvent.VK_RIGHT))
			{
				p.speed.x += walkAcc;

				p.state = AnimState.ANIMATING;
			}
			else
			{
			}
			
			if (Keys.check(KeyEvent.VK_LEFT))
			{
				p.speed.x -= walkAcc;
				
				p.state = AnimState.ANIMATING;
			}
			else
			{
			}


			/*
			if (Keys.Q)
			{
				p.rotation -= rotSpeed;			
			}
			
			if (Keys.E)
			{
				p.rotation += rotSpeed;			
			}
			*/

		}
	};
	
	public static PlayerKeyHandler waterHandler = new PlayerKeyHandler()
	{
		// player configuration
		public double swimAcc = 0.5; // pixels pr. sec
		public double rotAcc = 0.00005;

		
		@Override
		public void handleKeys(Player p, double ms)
		{
			//speed.x = 0;
			
			p.state = AnimState.STOPPED;
			
			if (Keys.check(KeyEvent.VK_UP))
			{
				p.speed.y -= swimAcc;
				p.state = AnimState.ANIMATING;
			}
			
			if (Keys.check(KeyEvent.VK_DOWN))
			{
				p.speed.y += swimAcc;
				p.state = AnimState.ANIMATING;
			}
			
			if (Keys.check(KeyEvent.VK_RIGHT))
			{
				p.speed.x += swimAcc;
				p.state = AnimState.ANIMATING;
			}
			
			if (Keys.check(KeyEvent.VK_LEFT))
			{
				p.speed.x -= swimAcc;
				p.state = AnimState.ANIMATING;
			}


			
			if (Keys.check(KeyEvent.VK_Q))
			{
				p.rotSpeed -= rotAcc;			
				p.state = AnimState.ANIMATING;
			}
			
			if (Keys.check(KeyEvent.VK_E))
			{
				p.rotSpeed += rotAcc;			
				p.state = AnimState.ANIMATING;
			}


		}
	};

	public static Scene mainScene()
	{
		Scene main = new Scene(SceneName.Main, mainHandler)
		{
			@Override
			public void initScene()
			{
				Game.game.player.rotation = 0;
			}
		};
		main.gravityY = 100 * 10;
		main.maxSpeedY = 1000;
		main.maxSpeedX = 200;
		main.frictionX = 3;

		Entity background =  new Entity(0,0,3000,3000,true)
		{
			@Override
			public void collision(Collideable other)
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public RenderPriority getRenderPriority()
			{
				return RenderPriority.BACKGROUND;
			}
			
			@Override
			public void draw(Graphics2D g, int x, int y)
			{
				g.setColor(Color.black);
				g.fillRect(x, y, width, height);	
			}
		};
		
		main.addEntity(background);

		return main;
	}
	
	public static Scene waterScene()
	{
		Scene water = new Scene(SceneName.Water, waterHandler)
		{
			@Override
			public void initScene()
			{
				Game.game.player.rotation = Math.PI / 2;
			}
		};
		
		
		water.maxSpeedY = 200;
		water.maxSpeedX = 200;
		water.frictionX = 0.3;
		water.frictionY = 0.3;
		water.rotFriction = 0.00003;
		water.maxRot = 0.006;

		Entity background =  new Entity(0,0,3000,3000,true)
		{
			@Override
			public void collision(Collideable other)
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public RenderPriority getRenderPriority()
			{
				return RenderPriority.BACKGROUND;
			}
			
			@Override
			public void draw(Graphics2D g, int x, int y)
			{
				g.setColor(Color.gray);
				g.fillRect(x, y, width, height);	
			}
		};
		
		water.addEntity(background);
		
		return water;		
	}
}
