package com.weimingtom.iteye.simplerpg.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Disposable;

public class SimpleLayer implements Disposable {
	private final static boolean isManaged = true;
	
	private Texture texture;
	private TextureRegion textureRegion;
	private AssetManager assetManager;
	private boolean isVisible = true;
		
	public SimpleLayer(AssetManager assetManager) {
		this.assetManager = assetManager;
	}

	public void loadFile(String filename) {
		if (textureRegion != null) {
			textureRegion = null;
		}
		if (texture != null) {
			texture.dispose();
			texture = null;
		}
		if (assetManager != null && filename != null) {
			Pixmap pixmap;
			if (isManaged) {
				pixmap = assetManager.get(filename, Pixmap.class);
			} else {
				pixmap = new Pixmap(Gdx.files.internal(filename));
			}
			if (pixmap != null) {
				int originalWidth = pixmap.getWidth();
				int originalHeight = pixmap.getHeight();
				if (!MathUtils.isPowerOfTwo(originalWidth) || !MathUtils.isPowerOfTwo(originalHeight)) {
					final int width = MathUtils.nextPowerOfTwo(originalWidth);
					final int height = MathUtils.nextPowerOfTwo(originalHeight);
					Pixmap potPixmap = new Pixmap(width, height, pixmap.getFormat());
					potPixmap.drawPixmap(pixmap, 0, 0, 0, 0, width, height);
					texture = new Texture(potPixmap);
					potPixmap.dispose();
				} else {
					texture = new Texture(pixmap);
				}
				textureRegion = new TextureRegion(texture, originalWidth, originalHeight);
			}
		}
	}
	
	
	public TextureRegion getTextureRegion() {
		return textureRegion;
	}
	
	@Override
	public void dispose() {
		if (textureRegion != null) {
			textureRegion = null;
		}
		if (texture != null) {
			texture.dispose();
			texture = null;
		}
	}

	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}
}
