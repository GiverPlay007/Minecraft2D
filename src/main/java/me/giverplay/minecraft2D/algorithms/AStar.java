package me.giverplay.minecraft2D.algorithms;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import me.giverplay.minecraft2D.world.Tile;
import me.giverplay.minecraft2D.world.World;

public class AStar
{
	public static double lastTime = System.currentTimeMillis();
	
	private static final Comparator<Node> nodeSorter = Comparator.comparingDouble(Node::getfCost);
	
	public static boolean clear()
	{
		return System.currentTimeMillis() - lastTime >= 1000;
	}
	
	public static void updateLastTime()
	{
		lastTime = System.currentTimeMillis();
	}
	
	public static List<Node> findPath(World world, Vector2i start, Vector2i end)
	{
		updateLastTime();
		
		List<Node> openList = new ArrayList<>();
		List<Node> closedList = new ArrayList<>();
		
		Node current = new Node(start, null, 0, getDistance(start, end));
		openList.add(current);
		
		while(openList.size() > 0)
		{
			openList.sort(nodeSorter);
			current = openList.get(0);
			
			if(current.tile.equals(end))
			{
				List<Node> path = new ArrayList<>();
				
				while(current.parent != null)
				{
					path.add(current);
					current = current.parent;
				}
				
				openList.clear();
				closedList.clear();
				return path;
			}
			
			openList.remove(current);
			closedList.add(current);
			
			for(int i = 0; i < 9; i++)
			{
				if(i == 4)
					continue;
				
				int x = current.tile.x;
				int y = current.tile.y;
				int xi = (i % 3) - 1;
				int yi = (i / 3) - 1;
				
				Tile tile = world.getTiles()[x + xi + ((y + yi) * world.getWidth())];
				
				if(tile == null)
					continue;
				
				if(tile.isRigid())
					continue;
				
				if(i == 0)
				{
					Tile test = world.getTiles()[x + xi +1 +((y + yi) * world.getWidth())];
					Tile test2 = world.getTiles()[x + xi +((y + yi +1) * world.getWidth())];
					
					if(test.isRigid() || test2.isRigid());
						continue;
					
				}
				else if(i == 2)
				{
					Tile test = world.getTiles()[x + xi -1 + ((y + yi) * world.getWidth())];
					Tile test2 = world.getTiles()[x + xi + ((y + yi + 1) * world.getWidth())];
					
					if(test.isRigid() || test2.isRigid());
						continue;
				}
				else if(i == 6)
				{
					Tile test = world.getTiles()[x + xi + ((y + yi -1) * world.getWidth())];
					Tile test2 = world.getTiles()[x + xi + 1 +((y + yi) * world.getWidth())];
					
					if(test.isRigid() || test2.isRigid());
						continue;
				}
				else if(i == 8)
				{
					Tile test = world.getTiles()[x + xi + ((y + yi -1) * world.getWidth())];
					Tile test2 = world.getTiles()[x + xi -1 + ((y + yi) * world.getWidth())];
					
					if(test.isRigid() || test2.isRigid())
						continue;
				}
				
				Vector2i a = new Vector2i(x + xi, y + yi);
				
				double gCost = current.gCost + getDistance(current.tile, a);
				double hCost = getDistance(a, end);
				
				Node node = new Node(a, current, gCost, hCost);
				
				if(vecInList(closedList, a) && gCost >= current.gCost)
					continue;
				
				if(!vecInList(openList, a))
				{
					openList.add(node);
				}
				else if(gCost < current.gCost)
				{
					openList.remove(current);
					openList.add(node);
				}
			}
		}
		
		closedList.clear();
		return null;
	}
	
	private static double getDistance(Vector2i tile, Vector2i goal)
	{
		double dx = tile.x - goal.y;
		double dy = tile.x - goal.y;
		
		return Math.sqrt(dx * dx + dy * dy);
	}
	
	private static boolean vecInList(List<Node> list, Vector2i vector)
	{
		for(int i = 0; i < list.size(); i++)
		{
			if(list.get(i).tile.equals(vector))
			{
				return true;
			}
		}
		
		return false;
	}
}
