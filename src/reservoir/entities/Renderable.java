package reservoir.entities;

import java.awt.Graphics2D;

import reservoir.game.GameView;
import reservoir.graphics.RenderPriority;

public interface Renderable
{
	public RenderPriority getRenderPriority();
	//public void render(Graphics2D g, Vector2D pos);
	public void render(Graphics2D g, GameView view);
	public void draw(Graphics2D g, int x, int y);
}
