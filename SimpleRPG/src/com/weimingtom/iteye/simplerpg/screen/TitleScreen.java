package com.weimingtom.iteye.simplerpg.screen;

import com.badlogic.gdx.Input;
import com.weimingtom.iteye.simplerpg.SimpleRPGGame;

public class TitleScreen extends BaseScreen {
	public TitleScreen(SimpleRPGGame game) {
		super(game);
	}
	
	@Override
	public void onDraw(float delta) {
		
	}
	
	@Override
	public void show() {

	}
	
	@Override
	public void dispose() {

	}
	
	@Override
	public boolean keyUp(int keycode) {
		switch (keycode) {
		case Input.Keys.BACK:
		case Input.Keys.ESCAPE:
			this.setScene(SCENE_MAP);
			return true;
		}
		return false;
	}
}
