package com.weimingtom.iteye.simplerpg.screen;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.weimingtom.iteye.simplerpg.SimpleRPGGame;
import com.weimingtom.iteye.simplerpg.script.BackgroundScriptEvent;
import com.weimingtom.iteye.simplerpg.script.ButtonScriptEvent;
import com.weimingtom.iteye.simplerpg.script.CharacterScriptEvent;
import com.weimingtom.iteye.simplerpg.script.JumpScriptEvent;
import com.weimingtom.iteye.simplerpg.script.LoadFontScriptEvent;
import com.weimingtom.iteye.simplerpg.script.LoadPixmapScriptEvent;
import com.weimingtom.iteye.simplerpg.script.LoadMapScriptEvent;
import com.weimingtom.iteye.simplerpg.script.LoadSpritePackScriptEvent;
import com.weimingtom.iteye.simplerpg.script.PlayerScriptEvent;
import com.weimingtom.iteye.simplerpg.script.TextDialogScriptEvent;
import com.weimingtom.iteye.simplerpg.script.MapScriptEvent;
import com.weimingtom.iteye.simplerpg.script.ProgressScriptEvent;
import com.weimingtom.iteye.simplerpg.script.ScriptEvent;
import com.weimingtom.iteye.simplerpg.script.MenuScriptEvent;
import com.weimingtom.iteye.simplerpg.script.MessageScriptEvent;
import com.weimingtom.iteye.simplerpg.ui.Hanzi;
import com.weimingtom.iteye.simplerpg.ui.LoadItem;
import com.weimingtom.iteye.simplerpg.ui.Pathfinder;
import com.weimingtom.iteye.simplerpg.ui.Pathfinder.Path;
import com.weimingtom.iteye.simplerpg.ui.Player;
import com.weimingtom.iteye.simplerpg.ui.Position;
import com.weimingtom.iteye.simplerpg.ui.Script;
import com.weimingtom.iteye.simplerpg.ui.SimpleButton;
import com.weimingtom.iteye.simplerpg.ui.SimpleLayer;
import com.weimingtom.iteye.simplerpg.ui.TextDialog;
import com.weimingtom.iteye.simplerpg.ui.TmxMap;

/**
 * @see https://code.google.com/p/libgdx-users/wiki/Tiles
 * @see com.badlogic.gdx.math.Interpolation
 * @author Administrator
 *
 */
public class MapScreen extends BaseScreen {
	private final static int[] tileMapLayersList = {0, 1, 2, 3, 4, 5};
	private TmxMap tmxMap;
	private Player player;
	private Hanzi hanzi;
	private SimpleLayer chLayer;
	private SimpleLayer bgLayer;
	private TextDialog dlg;
	private SimpleButton btnMenu0;
	private SimpleButton btnMenu1;
	private SimpleButton btnMenu2;
	private SimpleButton btnMenu3;
	private Script script;
	
	private float cameraX = 0;
	private float cameraY = 0;
	
	private boolean isShowMenu = false;
	private ScriptEvent currentEvent;

//	private LoadItem scriptLoadItem, hanziLoadItem;
//	private LoadItem mapLoadItem, playerLoadItem;
//	private LoadItem bgLayerLoadItem, chLayerLoadItem;
	private LoadItem currentLoadItem;
	
	@Override
	public void onDraw(float delta) {
		if (tmxMap != null && tmxMap.isVisible()) {
			if (player != null) {
				player.updateAnime();
			}
			cameraX = TmxMap.WIDTH / 2 - player.getX();
			cameraY = TmxMap.HEIGHT / 2 - player.getY();
			if (cameraX < TmxMap.WIDTH - tmxMap.getWidth()) {
				cameraX = TmxMap.WIDTH - tmxMap.getWidth();
			}
			if (cameraX > 0) {
				cameraX = 0;
			}
			if (cameraY < TmxMap.HEIGHT - tmxMap.getHeight()) {
				cameraY = TmxMap.HEIGHT - tmxMap.getHeight();
			}
			if (cameraY > 0) {
				cameraY = 0;
			}
			
			drawTmxMap(tmxMap, tileMapLayersList, cameraX, cameraY);
			drawPlayerAnimation(player, cameraX, cameraY);
			drawStringLT("Location: " + tmxMap.getX() + "," + tmxMap.getY() + "," + player.getX() + "," + player.getY() + "," + player.getAnimeSize(), 
				Color.BLUE, 0, getLineHeight() * 2);
			drawStringLT("FPS: " + Gdx.graphics.getFramesPerSecond(), 
				Color.BLUE, 0, getLineHeight() * 1);
		}	
		
		drawSimpleLayer(bgLayer, 0, 0);
		drawSimpleLayer(chLayer, 0, 0);
		drawDialog(dlg, hanzi);
		if (isShowMenu) {
			drawButton(btnMenu0, hanzi);
			drawButton(btnMenu1, hanzi);
			drawButton(btnMenu2, hanzi);
			drawButton(btnMenu3, hanzi);
		}
	}
	
	public MapScreen(SimpleRPGGame game) {
		super(game);
	}

	@Override
	public void show() {
		super.show();
		init();
	}
	
	@Override
	public void resume() {
		super.resume();
		/**
		 * @see https://code.google.com/p/libgdx/issues/detail?id=427
		 */
		init();
	}
	
	private void init() {
		//FIXME:
		script = null;
		hanzi = null;
		tmxMap = null;
		player = null;
		dlg = null;
		
		currentLoadItem = loadScript("data/script/game.xml");
//		setShowProgress(true);
	}
	
	@Override
	protected void onLoadFinish(AssetManager assetManager) {
		if (currentLoadItem != null && currentLoadItem.isLoaded) {
			switch (currentLoadItem.type) {
			case LoadItem.TYPE_SCRIPT:
				if (script == null) {
					script = new Script(assetManager);
					script.loadFile(currentLoadItem.param1);
					
//					currentLoadItem = loadHanzi("data/Fonts/hanzi");
				}
				break;
				
			case LoadItem.TYPE_HANZI:
				if (hanzi == null) {
					hanzi = new Hanzi(assetManager, currentLoadItem.param1);

//					currentLoadItem = loadTmxMap("data/maps/test2.tmx", "data/maps");
				}
				break;
				
			case LoadItem.TYPE_TMXMAP:
				if (tmxMap == null) {
					tmxMap = new TmxMap(assetManager, currentLoadItem.param1);			
					tmxMap.setXY(0, 0);
					tmxMap.setTileWidth(WIDTH / 10);
					tmxMap.setVisible(false);
					
//					currentLoadItem = loadPlayer("data/sprites/spritepack.atlas");
		
		//			bgLayerLoadItem = loadSimpleLayer("data/pictures/bg004.jpg");
		//			chLayerLoadItem = loadSimpleLayer("data/pictures/Charctor.png");
//					setShowProgress(true);
				}
				break;
				
			case LoadItem.TYPE_SPRITE_PACK:
				break;
								
			case LoadItem.TYPE_PIXMAP:
				break;
			}
			
			stepScript();
		}
	}
	
	private void resetScript(String scenario) {
		if (script != null) {
			script.reset(scenario);
		}
	}
	
	private void stepScript() {
		while (true) {
			if (script != null && script.hasNextEvent()) {
				currentEvent = script.nextEvent();
				if (currentEvent != null) {
					Gdx.app.log("MapScreen", "stepScript " + currentEvent.getClass().toString()); 
					if (currentEvent instanceof MessageScriptEvent) {
						onMessageEvent((MessageScriptEvent)currentEvent);
					} else if (currentEvent instanceof BackgroundScriptEvent) {
						onBackgroundEvent((BackgroundScriptEvent)currentEvent);
					} else if (currentEvent instanceof CharacterScriptEvent) {
						onCharacterEvent((CharacterScriptEvent)currentEvent);
					} else if (currentEvent instanceof MenuScriptEvent) {
						onMenuEvent((MenuScriptEvent)currentEvent);
					} else if (currentEvent instanceof MapScriptEvent) {
						onMapEvent((MapScriptEvent)currentEvent);
					} else if (currentEvent instanceof LoadFontScriptEvent) {
						onLoadFontEvent((LoadFontScriptEvent)currentEvent);
					} else if (currentEvent instanceof LoadMapScriptEvent) {
						onLoadMapEvent((LoadMapScriptEvent)currentEvent);
					} else if (currentEvent instanceof LoadSpritePackScriptEvent) {
						onLoadSpritePackEvent((LoadSpritePackScriptEvent)currentEvent);
					} else if (currentEvent instanceof LoadPixmapScriptEvent) {
						onLoadPixmapEvent((LoadPixmapScriptEvent)currentEvent);
					} else if (currentEvent instanceof JumpScriptEvent) {
						onJumpEvent((JumpScriptEvent)currentEvent);
					} else if (currentEvent instanceof ProgressScriptEvent) {
						onProgressEvent((ProgressScriptEvent)currentEvent);
					} else if (currentEvent instanceof TextDialogScriptEvent) {
						onTextDialogEvent((TextDialogScriptEvent)currentEvent);
					} else if (currentEvent instanceof PlayerScriptEvent) {
						onPlayerEvent((PlayerScriptEvent)currentEvent);
					} else if (currentEvent instanceof ButtonScriptEvent) {
						onButtonEvent((ButtonScriptEvent)currentEvent);
					}
					if (currentEvent.isContinue()) {
						continue;
					} else {
						break;
					}
				}
			} else {
				break;
			}
		}
	}
	
	private void onMessageEvent(MessageScriptEvent event) {
		if (dlg != null) {
			if (event.talker == null && event.body == null) {
				dlg.setVisible(false);				
			} else {
				dlg.setVisible(true);
				dlg.setText(event.talker + "\n    " + event.body);
			}
		}
	}

	private void onBackgroundEvent(BackgroundScriptEvent event) {
		if (bgLayer != null) {
			if (event.file != null) {
				bgLayer.setVisible(true);
				bgLayer.loadFile(event.file);
			} else {
				bgLayer.setVisible(false);
				bgLayer.loadFile(null);
			}
		}
	}

	private void onCharacterEvent(CharacterScriptEvent event) {
		if (chLayer != null) {
			if (event.centerFile != null) {
				chLayer.setVisible(true);
				chLayer.loadFile(event.centerFile);
			} else {
				chLayer.setVisible(false);
				chLayer.loadFile(null);				
			}
		}
	}
	
	private void onMenuEvent(MenuScriptEvent event) {
		Gdx.app.log("MapScreen", "onMenuEvent item == " + event.item1 + ", " + event.item2);
		if (event.item0 == null && 
			event.item1 == null && 
			event.item2 == null &&
			event.item3 == null) {
			isShowMenu = false;
		} else {
			if (btnMenu0 != null) {
				if (event.item0 != null) {
					btnMenu0.setVisible(true);
					btnMenu0.setText(event.item0);
					btnMenu0.setButtonDown(false);
				} else {
					btnMenu0.setVisible(false);
					btnMenu0.setText(null);
					btnMenu0.setButtonDown(false);
				}
			}
			if (btnMenu1 != null) {
				if (event.item1 != null) {
					btnMenu1.setVisible(true);
					btnMenu1.setText(event.item1);
					btnMenu1.setButtonDown(false);
				} else {
					btnMenu1.setVisible(false);
					btnMenu1.setText(null);
					btnMenu1.setButtonDown(false);
				}
			}
			if (btnMenu2 != null) {
				if (event.item2 != null) {
					btnMenu2.setVisible(true);
					btnMenu2.setText(event.item2);
					btnMenu2.setButtonDown(false);
				} else {
					btnMenu2.setVisible(false);
					btnMenu2.setText(null);
					btnMenu2.setButtonDown(false);
				}
			}
			if (btnMenu3 != null) {
				if (event.item3 != null) {
					btnMenu3.setVisible(true);
					btnMenu3.setText(event.item3);
					btnMenu3.setButtonDown(false);
				} else {
					btnMenu3.setVisible(false);
					btnMenu3.setText(null);
					btnMenu3.setButtonDown(false);
				}
			}
			isShowMenu = true;
		}
	}
	
	private void onMapEvent(MapScriptEvent event) {
		if (tmxMap != null) {
			if (event.file != null) {
				tmxMap.setVisible(true);
			} else {
				tmxMap.setVisible(false);
			}
		}
	}
	
	private void onLoadFontEvent(LoadFontScriptEvent event) {
		if (script != null) {
			if (event.prename != null) {
				currentLoadItem = loadHanzi(event.prename);
			}
		}
	}
	
	private void onLoadMapEvent(LoadMapScriptEvent event) {
		if (event.file != null && event.path != null) {
			currentLoadItem = loadTmxMap(event.file, event.path);
		}
	}
	
	private void onLoadSpritePackEvent(LoadSpritePackScriptEvent event) {
		if (event.file != null) {
			currentLoadItem = loadSpritePack(event.file);
		}
	}
	
	private void onLoadPixmapEvent(LoadPixmapScriptEvent event) {
		if (event.file != null) {
			currentLoadItem = loadPixmap(event.file);
		}
	}
	
	private void onJumpEvent(JumpScriptEvent event) {
		if (event.ref != null) {
			if (script != null) {
				script.reset(event.ref);
			}
		}
	}
	
	private void onProgressEvent(ProgressScriptEvent event) {
		if (script != null) {
			if (event.visible != null && event.visible.equals("true")) {
				setShowProgress(true);
			} else {
				setShowProgress(false);
			}
		}
	}
	
	private void onTextDialogEvent(TextDialogScriptEvent event) {
		if (event.file != null && event.name != null) {
			dlg = new TextDialog(assetManager, 
				event.file, 
				event.name, //"menuskin", 
				8, 8, 8, 8, false);
			//dlg.setRect(0, HEIGHT * 2 / 3, WIDTH, HEIGHT / 3);
			dlg.setRect(0, 0, WIDTH, HEIGHT / 3);
			//dlg.setText("你好，我是中国人。\n你好，我是中国人。你好，我是中国人。你好，我是中国人。你好，我是中国人。你好，我是中国人。你好，我是中国人。你好，我是中国人。");
			dlg.setVisible(false);

			
			
			
			bgLayer = new SimpleLayer(assetManager);
			//bgLayer.loadFile("data/pictures/bg004.jpg", true);
			
			chLayer = new SimpleLayer(assetManager);
			//chLayer.loadFile("data/pictures/Charctor.png", true);
		}
	}
	
	private void onPlayerEvent(PlayerScriptEvent event) {
		if (event.file != null && event.name != null) {
			player = new Player(assetManager, 
				currentLoadItem.param1, "ghost", 
				40, 46, 0, 0, 3, 4);
			player.setFrameDuration(0.5f);
			player.setRow(2);
			Vector2 loc = tmxMap.posToLoc(new Position(0, 1));
			player.setXY(loc.x, loc.y);
		}
	}
	
	private void onButtonEvent(ButtonScriptEvent event) {
		if (event.file != null && 
			event.up != null && 
			event.down != null && 
			event.id != null) {
			
			int buttonWidth = 600;
			int buttonHeight = 50;
			int buttonSpace = 10;
			String filename = event.file;
			String buttonDown = event.down; //"Button_BabyBlue_Down"; // "Button_White_Down"; //
			String buttonUp = event.up; //"Button_BabyBlue_Normal"; // "Button_White_Normal"; //
			int buttonInset = 4;
			
			if ("btnMenu0".equals(event.id)) {
				btnMenu0 = new SimpleButton(assetManager, 
						filename, 
						buttonDown, 
						buttonUp, 
						buttonInset, buttonInset, buttonInset, buttonInset, false);
				btnMenu0.setRect(WIDTH / 2 - buttonWidth / 2, 
						HEIGHT / 2 - buttonHeight / 2 + (buttonHeight + buttonSpace) * 3, 
						buttonWidth, buttonHeight);
			} else if ("btnMenu1".equals(event.id)) {
				btnMenu1 = new SimpleButton(assetManager, 
						filename, 
						buttonDown, 
						buttonUp, 
						buttonInset, buttonInset, buttonInset, buttonInset, false);
				btnMenu1.setRect(WIDTH / 2 - buttonWidth / 2, 
						HEIGHT / 2 - buttonHeight / 2 + (buttonHeight + buttonSpace) * 2, 
						buttonWidth, buttonHeight);
			} else if ("btnMenu2".equals(event.id)) {
				btnMenu2 = new SimpleButton(assetManager, 
						filename, 
						buttonDown, 
						buttonUp, 
						buttonInset, buttonInset, buttonInset, buttonInset, false);
				btnMenu2.setRect(WIDTH / 2 - buttonWidth / 2, 
						HEIGHT / 2 - buttonHeight / 2 + (buttonHeight + buttonSpace) * 1, 
						buttonWidth, buttonHeight);
			} else if ("btnMenu3".equals(event.id)) {
				btnMenu3 = new SimpleButton(assetManager, 
						filename, 
						buttonDown, 
						buttonUp, 
						buttonInset, buttonInset, buttonInset, buttonInset, false);
				btnMenu3.setRect(WIDTH / 2 - buttonWidth / 2, 
						HEIGHT / 2 - buttonHeight / 2 + (buttonHeight + buttonSpace) * 0, 
						buttonWidth, buttonHeight);
			}
		}
	}
	
	@Override
	protected void onPointerDown(Vector2 pt) {
		checkButtonDown(btnMenu0, pt);
		checkButtonDown(btnMenu1, pt);
		checkButtonDown(btnMenu2, pt);
		checkButtonDown(btnMenu3, pt);
	}

	@Override
	protected void onPointerDrag(Vector2 pt) {
		checkButtonDrag(btnMenu0, pt);
		checkButtonDrag(btnMenu1, pt);
		checkButtonDrag(btnMenu2, pt);
		checkButtonDrag(btnMenu3, pt);
	}

	//FIXME:
	private void testTMXMap(Vector2 pt) {
		if (false) {
			if (tmxMap != null) {
				tmxMap.setXY(pt.x, pt.y);
			}
		} else {
			if (tmxMap != null) {
				if (pt.x < WIDTH / 3) {
					tmxMap.moveMapLeft();
				} else if (pt.x > WIDTH * 2 / 3){
					tmxMap.moveMapRight();
				} else if (pt.y < HEIGHT / 3) {
					tmxMap.moveMapDown();
				} else if (pt.y > HEIGHT * 2 / 3) {
					tmxMap.moveMapUp();
				}
			}
		}
	}
	
	@Override
	protected void onPointerUp(final Vector2 pt) {
//		Gdx.app.log("MapScreen", "onPointerUp tmxMap.isVisible() == " + (tmxMap != null ? tmxMap.isVisible() : "null"));
		if (tmxMap != null && tmxMap.isVisible() && player != null) {
			if (false) {
				Vector2 target = tmxMap.posToGridPos(screenLocToMapLoc(pt));
				player.tweenToXY(target);
			} else {
				player.runOnIdle(new Runnable() {
					@Override
					public void run() {
						Position posStart = tmxMap.locToPos(new Vector2(player.getX(), 
							TmxMap.IS_INDEX_LEFT_TOP ? 
							player.getY() + tmxMap.getTileHeight() :
							player.getY()
						));
						Position posEnd = tmxMap.locToPos(screenLocToMapLoc(pt));
						//Gdx.app.log("MapScreen", "posStart == " + posStart.x + "," + posStart.y);
						//Gdx.app.log("MapScreen", "posEnd == " + posEnd.x + "," + posEnd.y);
						Path path = tmxMap.pathFinder.searchPath(posStart.x, posStart.y, posEnd.x, posEnd.y, null);
						if (path != null) {
							ArrayList<Vector2> pts = new ArrayList<Vector2>();
							for (Pathfinder.Point point : path.points) {
								Vector2 loc = tmxMap.posToLoc(new Position(point.x, point.y));
								pts.add(loc);
							}
							player.tweenPath(pts);
						}
					}
				});
			}
		} else {
			checkButtonUp(btnMenu0, pt);
			checkButtonUp(btnMenu1, pt);
			checkButtonUp(btnMenu2, pt);
			checkButtonUp(btnMenu3, pt);
			if (!isShowMenu) {
				stepScript();
			} else {
				if (btnMenu0.isHitRegion(pt)) {
					checkMenu(0);
				} else if (btnMenu1.isHitRegion(pt)) {
					checkMenu(1);
				} else if (btnMenu2.isHitRegion(pt)) {
					checkMenu(2);
				} else if (btnMenu3.isHitRegion(pt)) {
					checkMenu(3);
				}
			}
        }
	}
	
	public Vector2 screenLocToMapLoc(Vector2 pt) {
		return new Vector2(pt.x - cameraX, pt.y - cameraY);
	}
	
	private void checkMenu(int id) {
		//Gdx.app.log("MapScreen", "checkMenu " + id);
		if (currentEvent instanceof MenuScriptEvent) {
			MenuScriptEvent event = (MenuScriptEvent)currentEvent;
			String ref = null;
			switch (id) {
			case 0:
				ref = event.ref0;
				break;
				
			case 1:
				ref = event.ref1;
				break;
				
			case 2:
				ref = event.ref2;
				break;
				
			case 3:
				ref = event.ref3;
				break;
			}
			if (ref == null || ref.length() == 0) {
				isShowMenu = false;
				stepScript();
			} else if (MenuScriptEvent.VALUE_EXIT.equals(ref)){
				isShowMenu = false;
				Gdx.app.exit();
			} else if (MenuScriptEvent.VALUE_EXTRA.equals(ref)) {
				//FIXME:
				//don't leave menu
				isShowMenu = true;
			} else {
				//jump to other scenario
				isShowMenu = false;
				resetScript(ref);
				stepScript();
			}
		}
	}
	
	@Override
	public boolean keyDown(int keycode) {
		switch(keycode) {
		case Input.Keys.DPAD_UP:
			if (tmxMap != null) {
				tmxMap.moveMapUp();
			}
			break;
			
		case Input.Keys.DPAD_DOWN:
			if (tmxMap != null) {
				tmxMap.moveMapDown();
			}
			break;
			
		case Input.Keys.DPAD_LEFT:
			if (tmxMap != null) {
				tmxMap.moveMapLeft();
			}
			break;
			
		case Input.Keys.DPAD_RIGHT:
			if (tmxMap != null) {
				tmxMap.moveMapRight();
			}
			break;
			
		case Input.Keys.ESCAPE:
			resetScript(Script.INIT_SCENARIO);
			stepScript();
			break;
		}
//		System.out.println("keyDown:" + keycode);
		return false;
	}
	
	
	@Override
	public void pause() {
		super.pause();
		if (chLayer != null) {
			chLayer.dispose();
			chLayer = null;
		}
		if (bgLayer != null) {
			bgLayer.dispose();
			bgLayer = null;
		}
	}

	@Override
	public void dispose() {
		super.dispose();
		if (chLayer != null) {
			chLayer.dispose();
			chLayer = null;
		}
		if (bgLayer != null) {
			bgLayer.dispose();
			bgLayer = null;
		}
	}
}
