package com.weimingtom.iteye.simplerpg.ui;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.weimingtom.iteye.simplerpg.tween.EaseOutCubic;
import com.weimingtom.iteye.simplerpg.tween.SimpleTweener;

public class Player {
	public static final int NORMAL = 0;
	public static final int REVERSED = 1;
	public static final int LOOP = 2;
	public static final int LOOP_REVERSED = 3;
	public static final int LOOP_PINGPONG = 4;
	public static final int LOOP_RANDOM = 5;
	private float frameDuration = 0.15f;
	private int playMode = LOOP_PINGPONG;
	private float animTime;
	
	private TextureRegion[][] tiles;
	private int numX;
	private int numY;
	private int tileWidth;
	private int tileHeight;
	private int margin;
	private int spacing;
	
	private float playerZoom = 1.0f;
	private float playerX = 0;
	private float playerY = 0;
	private int playerRow = 0;
	private int playerCol = 0;
	private SimpleTweener tween = new EaseOutCubic();
	private static final double TWEEN_TIME = 0.3;
	private Queue<MoveAnime> moveAnimeQueue = new LinkedList<MoveAnime>();
	private MoveAnime currentMoveAnime;
	private Object moveAnimeQueueLock = new Object();
	private Runnable moveAnimeIdleRunnable;
		
	public Player(AssetManager assetManager, String filename, String name, int tileWidth, int tileHeight, int margin, int spacing, int numX, int numY) {
		TextureAtlas atlas = assetManager.get(filename, TextureAtlas.class);
		if (atlas != null) {
			TextureRegion texture = atlas.findRegion(name);
			if (texture != null) {
				this.tileWidth = tileWidth;
				this.tileHeight = tileHeight;
				this.margin = margin;
				this.spacing = spacing;
				if (numX != 0) {
					this.numX = numX;
				} else {
					this.numX = (texture.getRegionWidth() - margin) / (tileWidth + spacing);
				}
				if (numY != 0) {
					this.numY = numY;
				} else {
					this.numY = (texture.getRegionHeight() - margin) / (tileHeight + spacing);
				}
				this.tiles = new TextureRegion[numY][numX];
				for (int y = 0; y < numY; ++y) {
					this.tiles[y] = new TextureRegion[numX];
					for (int x = 0; x < numX; ++x) {
						int tileX = x * (tileWidth + spacing) + margin;
						int tileY = y * (tileHeight + spacing) + margin;
						this.tiles[y][x] = new TextureRegion(texture, tileX, tileY, tileWidth, tileHeight);
					}
				}
			}
		}
	}

	public int getWidth() {
		return numX;
	}

	public int getHeight() {
		return numY;
	}

	public int getTileWidth() {
		return tileWidth;
	}

	public int getTileHeight() {
		return tileHeight;
	}

	public int getMargin() {
		return margin;
	}

	public int getSpacing() {
		return spacing;
	}
	
	public TextureRegion getTile(int x, int y) {
		if (tiles != null && x >= 0 && x < numX && y >= 0 && y < numY) {
			return tiles[y][x];
		} else {
			return null;
		}
	}
	
	public TextureRegion updateAndGetKeyFrame(int row, boolean looping) {
		if (looping && (playMode == NORMAL || playMode == REVERSED)) {
			if (playMode == NORMAL) {
				playMode = LOOP;
			} else {
				playMode = LOOP_REVERSED;
			}
		} else if (!looping && !(playMode == NORMAL || playMode == REVERSED)) {
			if (playMode == LOOP_REVERSED) {
				playMode = REVERSED;
			} else {
				playMode = LOOP;
			}
		}
		return updateAndGetKeyFrame(row);
	}
	
	private TextureRegion updateAndGetKeyFrame(int row) {
		if (this.tiles != null && row >= 0 && row < this.tiles.length) {
			TextureRegion[] keyFrames = this.tiles[row];
			if (keyFrames != null) {
				this.playerRow = row;
				int frameNumber = (int)(this.animTime / this.frameDuration);
				switch (playMode) {
				case NORMAL:
					frameNumber = Math.min(keyFrames.length - 1, frameNumber);
					break;
					
				case LOOP:
					frameNumber = frameNumber % keyFrames.length;
					break;
				
				case LOOP_PINGPONG:
					frameNumber = frameNumber % (keyFrames.length * 2);
					if (frameNumber >= keyFrames.length) { 
						frameNumber = keyFrames.length - 1 - (frameNumber - keyFrames.length);
					}
					break;
				
				case LOOP_RANDOM:
					frameNumber = MathUtils.random(keyFrames.length - 1);
					break;
				
				case REVERSED:
					frameNumber = Math.max(keyFrames.length - frameNumber - 1, 0);
					break;
				
				case LOOP_REVERSED:
					frameNumber = frameNumber % keyFrames.length;
					frameNumber = keyFrames.length - frameNumber - 1;
					break;
	
				default:
					frameNumber = Math.min(keyFrames.length - 1, frameNumber);
					break;
				}
				this.playerCol = frameNumber;
				return keyFrames[frameNumber];
			}
		}
		return null;
	}
	
	public void setFrameDuration(float duration) {
		this.frameDuration = duration;
	}
	
	public void addDrawDuration() {
		animTime += Gdx.graphics.getDeltaTime();
	}
	
	public void setX(float x) {
		playerX = x;
	}
	
	public void setY(float y) {
		playerY = y;
	}

	/**
	 * @return left bottom of player
	 */
	public void setXY(float x, float y) {
		playerX = x;
		playerY = y;
		//Gdx.app.log("MapScreen", "player setXY " + x + "," + y);
	}
	
	/**
	 * @return left bottom of player
	 */
	public float getX() {
		return playerX;
	}
	
	/**
	 * @return left bottom of player
	 */
	public float getY() {
		return playerY;
	}
	
	public Vector2 getXY() {
		return new Vector2(playerX, playerY);
	}
	
	public float getZoom() {
		return playerZoom;
	}

	public int getRow() {
		return playerRow;
	}

	public void setRow(int playerRow) {
		this.playerRow = playerRow;
	}

	public int getCol() {
		return playerCol;
	}

	public void setCol(int playerCol) {
		this.playerCol = playerCol;
	}
	
	public void tweenToXY(Vector2 pt) {
        if (!isAnimeRunning()) {
        	synchronized (moveAnimeQueueLock) {
	        	moveAnimeQueue.offer(new MoveAnime(pt));
        	}
        } else {
        	Gdx.app.log("Player", "moveAnimeQueue.size() == " + moveAnimeQueue.size());
        }
    }
	
	public void tweenPath(List<Vector2> pts) {
		clearMoveAnime();
		if (!isAnimeRunning()) {
        	synchronized (moveAnimeQueueLock) {
	        	for (Vector2 pt : pts) {
	        		moveAnimeQueue.offer(new MoveAnime(pt));
	        	}
        	}
        } else {
        	//Gdx.app.log("Player", "moveAnimeQueue.size() == " + moveAnimeQueue.size());
        }
    }
	
	public boolean isAnimeRunning() {
		return tween.isTweening() || 
			currentMoveAnime != null ||
			getAnimeSize() > 0;
	}
	
	public void updateAnime() {
		if (tween.update()) {
	        setXY(tween.currentX(), tween.currentY());
	    } else {
	    	if (this.moveAnimeIdleRunnable != null) {
	    		this.moveAnimeIdleRunnable.run();
	    		this.moveAnimeIdleRunnable = null;
	    	}
	    	pollMoveAnime();
	    }
	}
	
	public void pollMoveAnime() {
		synchronized (moveAnimeQueueLock) {
        	currentMoveAnime = moveAnimeQueue.poll();
		}	
		if (currentMoveAnime != null) {
			Vector2 pt = currentMoveAnime.to;
		    double distance = Math.hypot(pt.x - getX(), pt.y - getY());
	        tween.startTween(getX(), pt.x, getY(), pt.y, (long)(distance * TWEEN_TIME));
		}
	}
	
	public int getAnimeSize() {
		return moveAnimeQueue.size();
	}
	
	public void clearMoveAnime() {
		moveAnimeQueue.clear();
		currentMoveAnime = null;
	}
	
	public void runOnIdle(Runnable runnable) {
		this.moveAnimeIdleRunnable = runnable;
	}
}
