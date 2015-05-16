package com.weimingtom.iteye.simplerpg.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.BitmapFontLoader;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.BitmapFontData;

public class Hanzi {
	private final static int FILES_NUM = 12;
	
	private BitmapFont[] fonts;
	
	public static LoadItem load(AssetManager assetManager, String prename) {
		if (prename != null) {
			String[] filenames = new String[FILES_NUM * 2];
			for (int i = 0; i < FILES_NUM; i++) {
				String preName = prename + (i + 1);
				/**
				Texture fontTexture = new Texture(Gdx.files.internal("data/Fonts/hanzi" + (i + 1) + ".png"), true);
				fontTexture.setFilter(TextureFilter.Linear, TextureFilter.MipMapLinearLinear);
				TextureRegion fontRegion = new TextureRegion(fontTexture);
				 */
				TextureLoader.TextureParameter param1 = new TextureLoader.TextureParameter();
				param1.minFilter = TextureFilter.Linear;
				param1.magFilter = TextureFilter.MipMapLinearLinear;
				param1.genMipMaps = true;
				
				/**
				fonts[i] = new BitmapFont(Gdx.files.internal("data/Fonts/hanzi" + (i + 1) + ".fnt"), fontRegion, false);
				fonts[i].setUseIntegerPositions(false);
				 */
				BitmapFontLoader.BitmapFontParameter param2 = new BitmapFontLoader.BitmapFontParameter();
				param2.bitmapFontData = new BitmapFontData(Gdx.files.internal(preName + ".fnt"), false);
				
				assetManager.load(preName + ".png", Texture.class, param1);
				assetManager.load(preName + ".fnt", BitmapFont.class, param2);
				filenames[i * 2] = preName + ".png";
				filenames[i * 2 + 1] = preName + ".fnt";
			}
			return new LoadItem(LoadItem.TYPE_HANZI, filenames, prename, null);
		}
		return null;
	}
	
	public Hanzi(AssetManager assetManager, String prename) {
		fonts = new BitmapFont[12];
		for (int i = 0; i < fonts.length; i++) {
			String preName = "data/Fonts/hanzi" + (i + 1);
			fonts[i] = assetManager.get(preName + ".fnt", BitmapFont.class);
			if (fonts[i] != null) {
				fonts[i].setUseIntegerPositions(false);
			}
		}
	}
	
	public BitmapFont[] getFonts() {
		return fonts;
	}
}
