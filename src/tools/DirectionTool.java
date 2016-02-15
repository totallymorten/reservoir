package tools;

import java.awt.Point;

import reservoir.types.Direction;


public abstract class DirectionTool
{
	public static Point getPointInDirection(Point fromPoint, Direction dir, int speed)
	{
		if (dir == Direction.NORTH)
			return new Point(fromPoint.x, fromPoint.y-speed);
		else if (dir == Direction.SOUTH)
			return new Point(fromPoint.x,fromPoint.y+speed);
		else if (dir == Direction.EAST)
			return new Point(fromPoint.x+speed,fromPoint.y);
		else
			return new Point(fromPoint.x-1,fromPoint.y);		
	}
}
