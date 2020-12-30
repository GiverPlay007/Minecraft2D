package me.giverplay.minecraft2D.algorithms;

public class Node
{
	public Vector2i tile;

	public Node parent;
	public double fCost;
	public double gCost;
	public double hCost;

	public Node(Vector2i tile, Node parent, double gCost, double hCost)
	{
		this.tile = tile;
		this.parent = parent;
		this.gCost = gCost;
		this.hCost = hCost;
		this.fCost = gCost + hCost;
	}

	public double getfCost()
	{
		return fCost;
	}
}
