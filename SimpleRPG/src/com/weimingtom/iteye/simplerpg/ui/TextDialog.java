package com.weimingtom.iteye.simplerpg.ui;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TextDialog {
	private final static float LINE_WIDTH = 5f;
	
	private Color dlgFillColor = new Color(0.0f, 0.0f, 0.0f, 0.5f);
	private Color dlgLineColor = new Color(1.0f, 1.0f, 1.0f, 1.0f);
	private Color dlgTextColor = Color.WHITE;
	
	private float x;
	private float y;
	private float width;
	private float height;
	private float lineWidth = LINE_WIDTH;
	private String text;
	private boolean isVisible = true;
	
	private NinePatch background;
	private boolean isSimple = false;
	
	public TextDialog(AssetManager assetManager, String filename, String name, int left, int right, int top, int bottom, boolean isSimple) {
		if (!isSimple) {
			TextureAtlas atlas = assetManager.get(filename, TextureAtlas.class);
			if (atlas != null) {
				TextureRegion texture = atlas.findRegion(name);
				background = new NinePatch(texture, left, right, top, bottom);	
			}
		}
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public String getText() {
		return this.text;
	}
	
	public Color getDlgFillColor() {
		return dlgFillColor;
	}
	
	public Color getDlgLineColor() {
		return dlgLineColor;
	}
	
	public Color getDlgTextColor() {
		return dlgTextColor;
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

	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

	public NinePatch getBackground() {
		return background;
	}

	public void setBackground(NinePatch background) {
		this.background = background;
	}

	public boolean isSimple() {
		return isSimple;
	}

	public void setSimple(boolean isSimple) {
		this.isSimple = isSimple;
	}
}
