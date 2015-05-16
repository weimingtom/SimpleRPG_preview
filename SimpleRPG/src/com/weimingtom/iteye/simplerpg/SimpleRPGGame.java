package com.weimingtom.iteye.simplerpg;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.weimingtom.iteye.simplerpg.screen.BaseScreen;
import com.weimingtom.iteye.simplerpg.screen.LoadingScreen;
import com.weimingtom.iteye.simplerpg.screen.MapScreen;
import com.weimingtom.iteye.simplerpg.screen.TitleScreen;
import com.weimingtom.iteye.simplerpg.tiled.TileMapRenderer;
import com.weimingtom.iteye.simplerpg.tiled.TileMapRendererLoader;
import com.weimingtom.iteye.simplerpg.ui.Script;

public class SimpleRPGGame extends Game {
	private TitleScreen titleScreen;
	private MapScreen mapScreen;
	private LoadingScreen loadingScreen;
	
	private FileHandleResolver resolver;
	private AssetManager assetManager;
	
	@Override
	public void create() {		
		//Gdx.input.setCatchBackKey(true);
		resolver = new InternalFileHandleResolver();
		assetManager = new AssetManager(resolver);
		assetManager.setLoader(Script.XMLScript.class, new Script.XMLScriptLoader(resolver));
		assetManager.setLoader(TileMapRenderer.class, new TileMapRendererLoader(resolver));
		loadingScreen = new LoadingScreen(this);
		titleScreen = new TitleScreen(this);
		mapScreen = new MapScreen(this);
		setScene(BaseScreen.SCENE_MAP);
	}

	public void setScene(int scene) {
		switch (scene) {
		case BaseScreen.SCENE_LOADING:
			setScreen(loadingScreen);
			break;
			
		case BaseScreen.SCENE_TITLE:
			setScreen(titleScreen);
			break;
			
		case BaseScreen.SCENE_MAP:
			setScreen(mapScreen);
			break;
		}
	}
	
	@Override
	public void dispose() {
		super.dispose();
		if (assetManager != null) {
			assetManager.dispose();
			assetManager = null;
		}
	}
	
	public AssetManager getAssetManager() {
		return this.assetManager;
	}
}
