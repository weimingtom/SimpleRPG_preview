package com.weimingtom.iteye.simplerpg.screen;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.weimingtom.iteye.simplerpg.SimpleRPGGame;
import com.weimingtom.iteye.simplerpg.ui.Hanzi;
import com.weimingtom.iteye.simplerpg.ui.LoadItem;
import com.weimingtom.iteye.simplerpg.ui.Player;
import com.weimingtom.iteye.simplerpg.ui.Script;
import com.weimingtom.iteye.simplerpg.ui.SimpleButton;
import com.weimingtom.iteye.simplerpg.ui.SimpleLayer;
import com.weimingtom.iteye.simplerpg.ui.TextDialog;
import com.weimingtom.iteye.simplerpg.ui.TmxMap;

public class BaseScreen implements Screen, InputProcessor{
	public final static int WIDTH = 640;
	public final static int HEIGHT = 480;
	public final static int FPS = 100;
	public final static long FRAME_TIME = 1000 / FPS;
	
	public final static int SCENE_LOADING = 1;
	public final static int SCENE_TITLE = 2;
	public final static int SCENE_MAP = 3;
	
	private final static int STATE_LOAD = 1;
	private final static int STATE_NORMAL = 2;

	private OrthographicCamera cam;
	protected Rectangle glViewport;
	protected SpriteBatch spriteBatch;
	
	private SimpleRPGGame game;
	private BitmapFont font;
	
	protected AssetManager assetManager;
	private String progressText = "";
	private ShapeRenderer shapeRenderer;

	private boolean isShowProgress = false;
	private boolean isLoadingDone = false;
	
	private List<LoadItem> loadItemList = new ArrayList<LoadItem>();
	
	public BaseScreen(SimpleRPGGame game) {
		this.game = game;
	}
	
	/**
	 * @see https://code.google.com/p/libgdx/wiki/GraphicsScissors
	 */
	@Override
	public void render(float delta) {
		if (assetManager != null) {
			if (!isLoadingDone) {
				isLoadingDone = assetManager.update();
//				if (isLoadingDone == false) {
//					Gdx.app.log("BaseScreen", "render this.isLoadingDone = false");
//				}
				if (isLoadingDone) {
					checkLoadItemList();
					progressText = "Loading done";
					onLoadFinish(this.assetManager);
				} else {
					progressText = String.format("Loading %.2f%%", 
						assetManager.getProgress() * 100);
				}
			}
		} else {
			progressText = "Loading error";
		}
		onUpdate(delta);	
		
		GL10 gl = Gdx.gl10;
		if (glViewport != null) {
			gl.glViewport((int)glViewport.x, (int)glViewport.y, (int)glViewport.width, (int)glViewport.height);
		}
		gl.glClearColor(0f, 1f, 0f, 1f);
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

//		gl.glMatrixMode(GL10.GL_PROJECTION);
//		gl.glLoadIdentity();
//		gl.glOrthof(0, 640, 0, 480, 1, -1);
//		gl.glMatrixMode(GL10.GL_MODELVIEW);
		
		if (cam != null) {
			cam.update();
			cam.apply(gl);
			if (spriteBatch != null) {
				spriteBatch.setProjectionMatrix(cam.combined);
			}
			if (shapeRenderer != null) {
				shapeRenderer.setProjectionMatrix(cam.combined);
			}

			//HEIGHT / 200
			drawFilledRect(Color.WHITE, 0, 0, WIDTH, HEIGHT);

			gl.glEnable(GL10.GL_SCISSOR_TEST);
			gl.glScissor((int)glViewport.x, (int)glViewport.y, (int)glViewport.width, (int)glViewport.height);
			if (isShowProgress) {
				drawStringCenter(progressText, Color.BLACK, WIDTH / 2, HEIGHT / 2);
				Gdx.app.log("BaseScreen", "progressText: " + progressText);
			} else {
				onDraw(delta);
			}
			gl.glDisable(GL10.GL_SCISSOR_TEST);
//			gl.glFlush();
		}
	}

	protected void onUpdate(float delta) {
		
	}
	
	protected void onDraw(float delta) {

	}
	
	protected void onLoadFinish(AssetManager assetManager) {
		
	}
	
	@Override
	public void resize(int width, int height) {
		calculateGLViewport(glViewport, WIDTH, HEIGHT);
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(this);
		assetManager = this.getAssetManager();
		font = new BitmapFont();
		font.setScale(2);
		glViewport = new Rectangle();
		cam = new OrthographicCamera(WIDTH, HEIGHT);
		cam.position.set(WIDTH / 2, HEIGHT / 2, 0);
		calculateGLViewport(glViewport, WIDTH, HEIGHT);
		spriteBatch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
	}

	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void dispose() {
		if (shapeRenderer != null) {
			shapeRenderer.dispose();
			shapeRenderer = null;
		}
		if (spriteBatch != null) {
			spriteBatch.dispose();
			spriteBatch = null;
		}
		this.game = null;
		if (font != null) {
			font.dispose();
			font = null;
		}
	}
	
	protected void calculateGLViewport(Rectangle viewport, int desiredWidth, int desiredHeight) {
		if (viewport != null) {
			float screenW = Gdx.graphics.getWidth();
			float screenH = Gdx.graphics.getHeight();
			if (screenH != 0 && desiredHeight != 0 &&
				screenW / screenH > (float)desiredWidth / desiredHeight) {
				float aspect = screenH / desiredHeight;
				viewport.width = desiredWidth * aspect;
				viewport.height = screenH;
				viewport.x = screenW / 2 - viewport.width / 2;
				viewport.y = 0;
			} else {
				float aspect = screenW / desiredWidth;
				viewport.width = screenW;
				viewport.height = desiredHeight * aspect;
				viewport.x = 0;
				viewport.y = screenH / 2 - viewport.height / 2;
			}
		}
	}

	public void drawPixmap(Pixmap pixmap, float x, float y) {
		if (pixmap != null) {
			spriteBatch.begin();
			TextureRegion region = new TextureRegion(new Texture(pixmap));
			spriteBatch.draw(region, x, y);
			spriteBatch.end();
		}
	}
	
	public void drawSprite(Sprite sprite, float x, float y) {
		if (sprite != null) {
			spriteBatch.begin();
			spriteBatch.draw(sprite, x, y);
			spriteBatch.end();
		}
	}

	public void drawTextureRegion(TextureRegion textureRegion, float x, float y) {
		if (textureRegion != null) {
			spriteBatch.begin();
			spriteBatch.draw(textureRegion, x, y);
			spriteBatch.end();
		}
	}

	
	public void drawTexture(Texture texture, float x, float y) {
		if (texture != null) {
			spriteBatch.begin();
			spriteBatch.draw(texture, x, y);
			spriteBatch.end();
		}
	}
	
	public LoadItem loadPixmap(String filename) {
		LoadItem loadItem = null;
		if (assetManager != null && filename != null) {
			assetManager.load(filename, Pixmap.class);
			loadItem = new LoadItem(LoadItem.TYPE_PIXMAP, new String[]{filename}, filename, null);
			addLoadItem(loadItem);
		}
		return loadItem;
	}
	
	public void drawSimpleLayer(SimpleLayer layer, float x, float y) {
		if (layer != null && layer.isVisible()) {
			drawTextureRegion(layer.getTextureRegion(), x, y);
		}
	}
	
	public LoadItem loadSpritePack(String filename) {
		LoadItem loadItem = null;
		if (assetManager != null) {
			assetManager.load(filename, TextureAtlas.class);
			loadItem = new LoadItem(LoadItem.TYPE_SPRITE_PACK, new String[]{filename}, filename, null);
			addLoadItem(loadItem);
		}		
		return loadItem;
	}

	public void drawPlayerAnimation(Player player, float cameraX, float cameraY) {
		if (spriteBatch != null && player != null) {
	        player.addDrawDuration();
			//TextureRegion textureRegion = player.getTile(col, row);
			TextureRegion region = player.updateAndGetKeyFrame(player.getRow(), true);
			if (region != null) {
				spriteBatch.begin();
				spriteBatch.draw(region, player.getX() + cameraX, player.getY() + cameraY);
				spriteBatch.end();
			}
		}
	}
	
	public LoadItem loadHanzi(String prename) {
		LoadItem loadItem = Hanzi.load(assetManager, prename);
		addLoadItem(loadItem);
		return loadItem;
	}
	
	public void drawHanziSingleLineCenter(Hanzi hanzi, String text, Color color, float x, float y, float maxWidth, float maxHeight) {
		if (spriteBatch != null && hanzi != null && text != null && text.length() > 0) {
			BitmapFont[] fonts = hanzi.getFonts();
			if (fonts != null) {
				float spaceWidth = 32;
				float lineHeight = 32;
				if (fonts[0] != null) {
					spaceWidth = fonts[0].getSpaceWidth();
					lineHeight = fonts[0].getLineHeight();
				}
				float totalWidth = text.length() * spaceWidth * 2;
				float totalHeight = 1 * spaceWidth * 2;
				drawHanziLT(hanzi, text, color, x + (maxWidth / 2 - totalWidth / 2), y - (maxHeight / 2 - totalHeight / 2) + (lineHeight - totalHeight), maxWidth, maxHeight);
			}
		}
	}
	
	public void drawHanziLT(Hanzi hanzi, String text, Color color, float x, float y, float maxWidth, float maxHeight) {
		if (spriteBatch != null && hanzi != null && text != null && text.length() > 0) {
			BitmapFont[] fonts = hanzi.getFonts();
			if (fonts != null) {
				float lineHeight = 32;
				float spaceWidth = 32;
				if (fonts[0] != null) {
					lineHeight = fonts[0].getLineHeight();
					spaceWidth = fonts[0].getSpaceWidth();
				}
				spriteBatch.begin();
				int len = text.length();
				float posX = x;
				float posY = y;
				for (int i = 0; i < len; i++) {
					boolean found = false;
					char ch = text.charAt(i);
					if (ch == '\n') {
						posX = x;
						posY -= lineHeight;
					} else {
						for (int j = 0; j < fonts.length; j++) {
							if (fonts[j] != null && fonts[j].containsCharacter(ch)) {
								fonts[j].setColor(color);
								TextBounds bounds = fonts[j].getBounds(text, i, i + 1);
								float bw = spaceWidth;
								if (bounds != null) {
									bw = bounds.width;
								}
								if (maxWidth > 0 && posX - x + bw > maxWidth) {
									posX = x;
									posY -= lineHeight;
								}
								if (maxHeight <= 0 || y - posY + lineHeight < maxHeight) {
									fonts[j].draw(spriteBatch, text, posX, posY, i, i + 1);
								}
								if (bounds != null) {
									posX += bw;
								}
								found = true;
								break;
							}
						}
						if (!found && fonts[0] != null) {
							posX += spaceWidth;
						}
					}
				}
				spriteBatch.end();
			}
		}
	}
	
	public void drawStringCenter(String text, Color color, float x, float y) {
		if (spriteBatch != null && font != null && text != null) {
			TextBounds bounds = font.getBounds(text);
			spriteBatch.begin();
			font.setColor(color);
			font.draw(spriteBatch, text, x - bounds.width / 2, y + bounds.height / 2);
			spriteBatch.end();
		}
	}
	
	public void drawStringLT(String text, Color color, float x, float y) {
		if (spriteBatch != null && font != null && text != null) {
			spriteBatch.begin();
			font.setColor(color);
			font.draw(spriteBatch, text, x, y);
			spriteBatch.end();
		}
	}

	public void drawFilledRect(Color color, float x, float y, float width, float height) {
		GL10 gl = Gdx.gl10;
		if (shapeRenderer != null) {
			gl.glEnable(GL10.GL_BLEND);
            gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
			shapeRenderer.begin(ShapeType.Filled);
            //shapeRenderer.begin(ShapeType.FilledRectangle);
			shapeRenderer.setColor(color);
			shapeRenderer.rect(x, y, width, height);
			//shapeRenderer.filledRect(x, y, width, height);
			shapeRenderer.end();
			gl.glDisable(GL10.GL_BLEND);
		}
	}
	
	public void drawRect(Color color, float x, float y, float width, float height, float lineWidth) {
		drawFilledRect(color, x, y, width, lineWidth);
		drawFilledRect(color, x, y, lineWidth, height);
		drawFilledRect(color, x + width - lineWidth, y, lineWidth, height);
		drawFilledRect(color, x, y + height - lineWidth, width, lineWidth);
	}
	
	public LoadItem loadTmxMap(String filename, String path) {
		LoadItem loadItem = TmxMap.load(assetManager, filename, path);
		addLoadItem(loadItem);
		return loadItem;
	}
	
	public void drawTmxMap(TmxMap tmxMap, int[] tileMapLayersList, float cameraX, float cameraY) {
		if (tmxMap != null && tileMapLayersList != null) {
			tmxMap.drawMap(tileMapLayersList, cameraX, cameraY);
		}
	}
	
	public void drawDialog(TextDialog dlg, Hanzi hanzi) {
		if (dlg != null && dlg.isVisible()) {
			float x = dlg.getX();
			float y = dlg.getY();
			float w = dlg.getWidth();
			float h = dlg.getHeight();
			NinePatch ninePatch = dlg.getBackground();
			if (dlg.isSimple() || ninePatch == null) {
				float lineWidth = dlg.getLineWidth();
				drawFilledRect(dlg.getDlgFillColor(), x, y, w, h);
				drawRect(dlg.getDlgLineColor(), x, y, w, h, lineWidth);
				if (hanzi != null) {
					drawHanziLT(hanzi, dlg.getText(), 
						dlg.getDlgTextColor(), x + lineWidth, y + h - lineWidth,
						w - lineWidth * 2, h - lineWidth * 2);
				}
			} else {
				drawNinePatch(ninePatch, x, y, w, h);
				if (hanzi != null) {
					drawHanziLT(hanzi, dlg.getText(), 
						dlg.getDlgTextColor(),
						x + ninePatch.getLeftWidth(), 
						y + h - ninePatch.getBottomHeight(),
						w - ninePatch.getLeftWidth() - ninePatch.getRightWidth(), 
						h - ninePatch.getTopHeight() - ninePatch.getBottomHeight());
				}
			}
		}
	}
	
	public void drawButton(SimpleButton button, Hanzi hanzi) {
		if (button != null && button.isVisible() && hanzi != null) {
			float x = button.getX();
			float y = button.getY();
			float width = button.getWidth();
			float height = button.getHeight();
			NinePatch backgroundDown, backgroundUp;
			backgroundDown = button.getBackgroundDown();
			backgroundUp = button.getBackgroundUp();
			if (button.isSimple() || backgroundUp == null) {
				float lineWidth = button.getLineWidth();
				if (button.isButtonDown()) {
					drawFilledRect(button.getButtonDownColor(), x, y, width, height);
				} else {
					drawFilledRect(button.getButtonUpColor(), x, y, width, height);
				}
				drawRect(button.getButtonLineColor(), x, y, width, height, lineWidth);
				if (hanzi != null) {
					drawHanziSingleLineCenter(hanzi, button.getText(), 
						button.getButtonTextColor(), x + lineWidth, y + height - lineWidth,
						width - lineWidth * 2, height - lineWidth);
				}
			} else {
				if (button.isButtonDown()) {
					drawNinePatch(backgroundDown, x, y, width, height);
				} else {
					drawNinePatch(backgroundUp, x, y, width, height);
				}
				if (hanzi != null) {
					drawHanziSingleLineCenter(hanzi, button.getText(), button.getButtonTextColor(), 
						x + backgroundUp.getLeftWidth(), 
						y + height - backgroundUp.getBottomHeight(),
						width - backgroundUp.getLeftWidth() - backgroundUp.getRightWidth(), 
						height - backgroundUp.getTopHeight() - backgroundUp.getBottomHeight());
				}				
			}
		}
	}
	
	public OrthographicCamera getCam() {
		return cam;
	}
	
	public AssetManager getAssetManager() {
		return this.game.getAssetManager();
	}
	
	public void setScene(int scene) {
		this.game.setScene(scene);
	}
	
	/**
	 * FIXME:
	 * 
	 * @param x Left top of screen
	 * @param y Left top of screen
	 * @return Left bottom of scene
	 */
	public Vector2 screenToScene(Rectangle glViewport, float x, float y) {
		float xt = (x - glViewport.x) * WIDTH/*tileCamera.viewportWidth*/ / glViewport.width;
		float yt = (y - glViewport.y) * HEIGHT/*tileCamera.viewportHeight*/ / glViewport.height;
		return new Vector2(xt, HEIGHT/*tileCamera.viewportHeight*/ - yt);
	}

	@Override
	public boolean keyDown(int keycode) {		
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		Vector2 pt = screenToScene(glViewport, x, y);
		onPointerDown(pt);
		return false;
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		Vector2 pt = screenToScene(glViewport, x, y);
		onPointerUp(pt);
		return false;
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		Vector2 pt = screenToScene(glViewport, x, y);
		onPointerDrag(pt);
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
	
	public float getLineHeight() {
		if (font != null) {
			return font.getLineHeight();
		} else {
			return 0;
		}
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}
	
	protected void onPointerDown(Vector2 pt) {
		
	}
	
	protected void onPointerUp(Vector2 pt) {
		
	}

	protected void onPointerDrag(Vector2 pt) {
		
	}
	
	public void checkButtonDown(SimpleButton button, Vector2 pt) {
		if (button != null && pt != null && button.isHitRegion(pt)) {
			button.setButtonDown(true);
		}
	}
	
	public void checkButtonUp(SimpleButton button, Vector2 pt) {
//		Gdx.app.log("BaseScreen", "checkButtonUp");
		if (button != null && pt != null && button.isHitRegion(pt)) {
			button.setButtonDown(false);
		}
	}
	
	public void checkButtonDrag(SimpleButton button, Vector2 pt) {
		if (button != null && pt != null) {
			if (button.isHitRegion(pt)) {
				button.setButtonDown(true);
			} else {
				button.setButtonDown(false);
			}
		}
	}
	
	public LoadItem loadScript(String filename) {
		LoadItem loadItem = Script.load(assetManager, filename);
		addLoadItem(loadItem);
		return loadItem;
	}
	
	public void drawNinePatch(NinePatch ninePatch, float x, float y, float w, float h) {
		if (spriteBatch != null && ninePatch != null) {
			spriteBatch.begin();
			ninePatch.draw(spriteBatch, x, y, w, h);
			spriteBatch.end();
		}
	}
	
	private void addLoadItem(LoadItem loadItem) {
		if (loadItem != null) {
			loadItemList.add(loadItem);
			this.isLoadingDone = false;
			Gdx.app.log("BaseScreen", "addLoadItem this.isLoadingDone = false");
		}
	}
	
	private void checkLoadItemList() {
		for (LoadItem item : this.loadItemList) {
			if (item != null) {
				if (this.assetManager != null) {
					item.isLoaded = true;
					for (int i = 0; i < item.filenames.length; i++) {
						if (!this.assetManager.isLoaded(item.filenames[i])) {
							item.isLoaded = false;
							break;
						}
					}
				} else {
					item.isLoaded = false;
				}
			}
		}
	}

	public boolean isShowProgress() {
		return isShowProgress;
	}

	public void setShowProgress(boolean isShowProgress) {
		this.isShowProgress = isShowProgress;
	}
}

