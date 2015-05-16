package com.weimingtom.iteye.simplerpg.ui;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

//import com.badlogic.gdx.graphics.g2d.tiled.TiledMap;
import com.weimingtom.iteye.simplerpg.tiled.TiledMap;
import com.badlogic.gdx.utils.Pool;

public class Pathfinder {
	public static interface PathfinderStrategy {
		public int getCost(PathNode p);
		public int getHeuristicCost(PathNode start, PathNode end);
		public void getNeighborNodes(ArrayList<PathNode> neighbors, PathNodePool pool, PathNode p, List<Point> ignore);
		public int nodeHash(PathNode node);
	}
	
	private PathfinderStrategy strategy;
	private PathNodePool pathNodePool;
	private PriorityQueue<PathNode> openList;
	private HashMap<Integer, PathNode> closedList;
	private ArrayList<PathNode> neighbors; 
	
	public Pathfinder(TiledMap map) {
		this.strategy = new MapPathfinderStrategy(map);
		this.pathNodePool = new PathNodePool();
		this.openList = new PriorityQueue<PathNode>(50, new PathNodeComparator());
		this.closedList = new HashMap<Integer, PathNode>();
		this.neighbors = new ArrayList<PathNode>();
	}
	
	private final static class PathNodeComparator implements Comparator<PathNode> {
		@Override
		public int compare(PathNode a, PathNode b) {
			return a.f - b.f;
		}
	}
	
	public Path searchPath(int startX, int startY, int endX, int endY, List<Point> ignore) {
		PathNode start = pathNodePool.obtain(startX, startY);
		PathNode end = pathNodePool.obtain(endX, endY);
		openList.add(start);
		start.g = 0;
		start.f = start.g + strategy.getHeuristicCost(start, end);
		while (!openList.isEmpty()) {
			PathNode current = openList.peek();
			if(current.equals(end)) {
				Path path = reconstructPath(current);
				cleanUp();
				return path;
			}
			openList.poll();
			closedList.put(strategy.nodeHash(current), current);
			neighbors.clear();
			strategy.getNeighborNodes(neighbors, pathNodePool, current, ignore);
			for (PathNode neighbor: neighbors) {
				if (closedList.containsKey(strategy.nodeHash(neighbor))) {
					pathNodePool.free(neighbor);
					continue;
				}
				int tentativeG = current.g + strategy.getCost(neighbor);
				if (!openList.contains(current) || 
					tentativeG < neighbor.g) {
					neighbor.g = tentativeG;
					neighbor.f = neighbor.g + strategy.getHeuristicCost(neighbor, end);
					openList.add(neighbor);
					neighbor.parent = current;
				} else {
					pathNodePool.free(neighbor);
				}
			}
		}
		cleanUp();
		return null;
	}
	
	private void cleanUp() {
		pathNodePool.clear();
		openList.clear();
		closedList.clear();
		neighbors.clear();
	}
	
	private Path reconstructPath(PathNode current) {
		if (current.parent != null) {
			Path path = reconstructPath(current.parent);
			path.addPoint(current.x, current.y);
			return path;
		} else {
			Path path = new Path();
			path.addPoint(current.x, current.y);
			return path;
		}
	}
	
	public final static class PathNode {
		public int x;
		public int y;
		public int f;
		public int g;
		public PathNode parent;
		
		public PathNode() {
			this.x = 0;
			this.y = 0;
			this.f = 0;
			this.g = 0;
			this.parent = null;
		}
		
		public void setPos(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public boolean equals(Object obj) {
			PathNode node = (PathNode) obj;
			return (x == node.x) && (y == node.y);
		}
	}
	
	public final static class PathNodePool extends Pool<PathNode> {
		public PathNodePool() {
			super(50);
		}
		
		@Override
		protected PathNode newObject() {
			return new PathNode();
		}
		
		public PathNode obtain(int x, int y) {
			PathNode node = obtain();
			node.setPos(x, y);
			return node;
		}
	}
	
	public final static class Path {
		public ArrayList<Point> points;
		
		public Path() {
			this.points = new ArrayList<Point>();
		}
		
		public void addPoint(int x, int y) {
			points.add(new Point(x, y));
		}
		
		public void addPoint(Point p) {
			points.add(p);
		}
	}

	public final static class Point {
		public int x;
		public int y;
		
		public Point() {
			this.x = 0;
			this.y = 0;
		}
		
		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	
	private final static class MapPathfinderStrategy implements PathfinderStrategy {
		private TiledMap map;
		
		public MapPathfinderStrategy(TiledMap map) {
			this.map = map;
		}
		
		@Override
		public int getCost(PathNode p) {
			return 1;
		}

		@Override
		public int getHeuristicCost(PathNode start, PathNode end) {
			return Math.abs(start.x - end.x) + Math.abs(start.y - end.y); 
		}

		@Override
		public void getNeighborNodes(ArrayList<PathNode> neighbors, PathNodePool pool, PathNode p, List<Point> ignore) {
			checkNeighbor(neighbors, pool, pool.obtain(p.x - 1, p.y), ignore);
			checkNeighbor(neighbors, pool, pool.obtain(p.x + 1, p.y), ignore);
			checkNeighbor(neighbors, pool, pool.obtain(p.x, p.y - 1), ignore);
			checkNeighbor(neighbors, pool, pool.obtain(p.x, p.y + 1), ignore);
		}

		private void checkNeighbor(ArrayList<PathNode> neighbors, PathNodePool pool, PathNode neighbor, List<Point> ignore) {
			if (neighbor.x < 0 || neighbor.x >= map.width || neighbor.y < 0 || neighbor.y >= map.height) {
				pool.free(neighbor);
				return;
			}
			if (checkMapCollision(neighbor.x, neighbor.y)) {
				pool.free(neighbor);
				return;
			}
			if (ignore != null) {
				for (Point p: ignore) {
					if (neighbor.x == p.x && neighbor.y == p.y) {
						return;
					}
				}
			}
			neighbors.add(neighbor);
		}
		
		private boolean checkMapCollision(int x, int y) {
			int tile = map.layers.get(5).tiles[y][x];
			String property = map.getTileProperty(tile, "collidable");
			boolean collidable = false;	
			if (property != null) {
				collidable = property.equals("true") ? true : false;
			}
			return collidable;
		}
		
		@Override
		public int nodeHash(PathNode node) {
			return node.y * map.width + node.x;
		}
	}
}
