package com.weimingtom.iteye.simplerpg.ui;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class SimpleButton {
	private Color buttonUpColor = new Color(1.0f, 1.0f, 1.0f, 0.5f);
	private Color buttonDownColor = new Color(1.0f, 0.5f, 0.1f, 0.5f); // f58220
	private Color buttonLineColor = new Color(0.0f, 0.0f, 0.0f, 0.5f);
	private Color buttonTextColor = new Color(0.0f, 0.0f, 0.0f, 1.0f);
	
	private float x, y, width, height;
	private float lineWidth = 5;
	private String text;
	private boolean buttonDown = false;
	private boolean isVisible = true;
	
	private NinePatch backgroundDown, backgroundUp;
	private boolean isSimple = false;
	
	public SimpleButton(AssetManager assetManager, String filename, String nameDown, String nameUp, int left, int right, int top, int bottom, boolean isSimple) {
		this.isSimple = isSimple;
		if (!isSimple) {
			TextureAtlas atlas = assetManager.get(filename, TextureAtlas.class);
			if (atlas != null) {
				TextureRegion textureDown = atlas.findRegion(nameDown);
				backgroundDown = new NinePatch(textureDown, left, right, top, bottom);	
				TextureRegion textureUp = atlas.findRegion(nameUp);
				backgroundUp = new NinePatch(textureUp, left, right, top, bottom);	
			}
		}
	}
	
	public Color getButtonUpColor() {
		return buttonUpColor;
	}
	
	public void setButtonUpColor(Color buttonUpColor) {
		this.buttonUpColor = buttonUpColor;
	}
	
	public Color getButtonDownColor() {
		return buttonDownColor;
	}

	public void setButtonDownColor(Color buttonDownColor) {
		this.buttonDownColor = buttonDownColor;
	}

	public Color getButtonLineColor() {
		return buttonLineColor;
	}
	
	public void setButtonLineColor(Color buttonLineColor) {
		this.buttonLineColor = buttonLineColor;
	}
	
	public Color getButtonTextColor() {
		return buttonTextColor;
	}
	
	public void setButtonTextColor(Color buttonTextColor) {
		this.buttonTextColor = buttonTextColor;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public void setRect(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public float getLineWidth() {
		return lineWidth;
	}

	public void setLineWidth(float lineWidth) {
		this.lineWidth = lineWidth;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isButtonDown() {
		return buttonDown;
	}

	public void setButtonDown(boolean buttonDown) {
		this.buttonDown = buttonDown;
//		Gdx.app.log("SimpleButton", "text == " + text + " setButtonDown = " + buttonDown);
	}
	
	
	public boolean isHitRegion(Vector2 pt) {
		if (pt != null && isVisible) {
			Rectangle rect = new Rectangle(x, y, width, height);
			if (rect.contains(pt.x, pt.y)) {
				return true;
			}
		}
		return false;
	}

	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

	public NinePatch getBackgroundDown() {
		return backgroundDown;
	}

	public void setBackgroundDown(NinePatch backgroundDown) {
		this.backgroundDown = backgroundDown;
	}

	public NinePatch getBackgroundUp() {
		return backgroundUp;
	}

	public void setBackgroundUp(NinePatch backgroundUp) {
		this.backgroundUp = backgroundUp;
	}

	public boolean isSimple() {
		return isSimple;
	}

	public void setSimple(boolean isSimple) {
		this.isSimple = isSimple;
	}
	
	
}
