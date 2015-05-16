package com.weimingtom.iteye.simplerpg.ui;

//import com.badlogic.gdx.graphics.g2d.tiled.TileMapRenderer;
//import com.badlogic.gdx.graphics.g2d.tiled.TiledMap;
//import com.badlogic.gdx.graphics.g2d.tiled.TiledObject;
//import com.badlogic.gdx.graphics.g2d.tiled.TiledObjectGroup;
//import com.badlogic.gdx.assets.loaders.TileMapRendererLoader;


import com.badlogic.gdx.assets.AssetManager;
import com.weimingtom.iteye.simplerpg.tiled.TileMapRendererLoader;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.weimingtom.iteye.simplerpg.tiled.TileMapRenderer;
import com.weimingtom.iteye.simplerpg.tiled.TiledMap;
import com.weimingtom.iteye.simplerpg.tiled.TiledObject;
import com.weimingtom.iteye.simplerpg.tiled.TiledObjectGroup;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.weimingtom.iteye.simplerpg.screen.BaseScreen;

public class TmxMap implements Disposable {
	public static final boolean IS_INDEX_LEFT_TOP = true;
	public static final int WIDTH = BaseScreen.WIDTH;
	public static final int HEIGHT = BaseScreen.HEIGHT;
	
	private OrthographicCamera tileCamera;
	private TileMapRenderer tileMapRenderer;
	private TiledMap tileMap;
	private float tileMapZoom = 1.0f;
	private float tileMapX = 0;
	private float tileMapY = 0;
	private float cameraX = 0;
	private float cameraY = 0;
	private boolean isVisible = false;
	
	public Pathfinder pathFinder;
	
	public static LoadItem load(AssetManager assetManager, String filename, String path) {
		if (assetManager != null) {
			TileMapRendererLoader.TileMapParameter param = new TileMapRendererLoader.TileMapParameter(
					path, 10, 10);
			assetManager.load(filename, TileMapRenderer.class, param);
			return new LoadItem(LoadItem.TYPE_TMXMAP, new String[]{filename}, filename, path);
		}
		return null;
	}
	
	public TmxMap(AssetManager assetManager, String filename) {
		this.tileCamera = new OrthographicCamera(WIDTH, HEIGHT);
		this.tileMapRenderer = assetManager.get(filename, TileMapRenderer.class);
		if (tileMapRenderer != null) {
			this.tileMap = tileMapRenderer.getMap();;
			
			if (tileMap != null) {
				for (TiledObjectGroup group : tileMap.objectGroups) {
					for (TiledObject object : group.objects) {
						System.out.println("Object " + object.name + " x,y = " + object.x + "," + object.y + " width,height = "
							+ object.width + "," + object.height);
					}
				}
			}
		}
		pathFinder = new Pathfinder(getTiledMap());
	}
	
	@Override
	public void dispose() {
		
	}
	
	//FIXME:
	public void moveMapUp() {
		if (tileMap != null) {
			float y = getY() + getTileHeight();
			if (isInViewport(getX(), y)) {
				setY(y);
			}
		}
	}
	
	//FIXME:
	public void moveMapDown() {
		if (tileMap != null) {
			float y = getY() - getTileHeight();
			if (isInViewport(getX(), y)) { 
				setY(y);
			}
		}
	}
	
	//FIXME:
	public void moveMapLeft() {
		if (tileMap != null) {
			float x = getX() - getTileWidth();
			if (isInViewport(x, getY())) { 
				setX(x);
			}
		}
	}
	
	//FIXME:
	public void moveMapRight() {
		if (tileMap != null) {
			float x = getX() + getTileWidth();
			if (isInViewport(x, getY())) { 
				setX(x);
			}
		}
	}
	
	public void setZoom(float zoom) {
		this.tileMapZoom = zoom;
		updatePosition();
	}
	
	public boolean isInViewport(float x, float y) {
		if (tileMapRenderer != null) {
			float w = getWidth();
			float h = getHeight();
			if (x < WIDTH - w) {
				return false;
			} else if (x > 0) {
				return false;
			}
			if (y < HEIGHT - h) {
				return false;
			} else if (y > 0) {
				return false;
			}
			return true;
		}
		return false;
	}
	
	private void updatePosition() {
		tileCamera.zoom = 1.0f / this.tileMapZoom;
		tileCamera.position.set(
			(WIDTH * 0.5f - (tileMapX + cameraX)) * tileCamera.zoom, 
			(HEIGHT * 0.5f - (tileMapY + cameraY)) * tileCamera.zoom, 
			0);
		//tileCamera.viewportHeight
	}
	
	//FIXME:
	public void drawMap(int[] tileMapLayersList, float cameraX, float cameraY) {
		if (tileMapRenderer != null && tileCamera != null) {
			this.cameraX = cameraX;
			this.cameraY = cameraY;
			updatePosition();
			tileCamera.update();
			tileMapRenderer.render(tileCamera, tileMapLayersList);
		}
	}

	public void setX(float x) {
		tileMapX = x;
		updatePosition();
	}
	
	public void setY(float y) {
		tileMapY = y;
		updatePosition();
	}

	/**
	 * @return left bottom of map
	 */
	public void setXY(float x, float y) {
		tileMapX = x;
		tileMapY = y;
		updatePosition();
	}
	
	/**
	 * @return left bottom of map
	 */
	public float getX() {
		return tileMapX;
	}
	
	/**
	 * @return left bottom of map
	 */
	public float getY() {
		return tileMapY;
	}
	
	public float getZoom() {
		return this.tileMapZoom;
	}
	
	public float getTileWidth() {
		return tileMap.tileWidth * this.tileMapZoom;
	}
	
	public float getTileHeight() {
		return tileMap.tileHeight * this.tileMapZoom;
	}
	
	public float getWidth() {
		return tileMap.tileWidth * tileMap.width * this.tileMapZoom;
	}
	
	public float getHeight() {
		return tileMap.tileHeight * tileMap.height * this.tileMapZoom;
	}
	
	public void setTileWidth(float w) {
		setZoom(w / tileMap.tileWidth);
	}
	
	public void setTileHeight(float h) {
		setZoom(h / tileMap.tileHeight);
	}
	
	public OrthographicCamera getCam() {
		return this.tileCamera;
	}
	
	public Matrix4 getTransformMatrix() {
		return tileMapRenderer.getTransformMatrix();
	}

	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}
	
	public Position locToPos(Vector2 pt) {
		Position pos = new Position();
		pos.x = (int)Math.floor(pt.x / getTileWidth());
		if (IS_INDEX_LEFT_TOP) {
			pos.y = (int)Math.floor((getHeight() - pt.y) / getTileHeight());
		} else {
			pos.y = (int)Math.floor(pt.y / getTileHeight());
		}
		return pos;
	}
	
	public Vector2 posToLoc(Position pos) {
		Vector2 loc = new Vector2();
		loc.x = pos.x * getTileWidth();
		if (IS_INDEX_LEFT_TOP) {
			loc.y = getHeight() - (pos.y + 1) * getTileHeight();
		} else {
			loc.y = pos.y * getTileHeight();
		}
		return loc;
	}
	
	public Vector2 posToGridPos(Vector2 pt) {
		return posToLoc(locToPos(pt));
	}
	
	public TiledMap getTiledMap() {
		return this.tileMap;
	}
}
