package com.weimingtom.iteye.simplerpg.script;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.XmlReader.Element;

public class LoadSpritePackScriptEvent extends ScriptEvent {
	public String file;

	public LoadSpritePackScriptEvent(Element eventElement) {
		Element element = eventElement.getChildByName(TAG_STORAGE);
		if (element != null) {
			file = element.getAttribute(TAG_FILE);
		}
		Gdx.app.log("LoadSpritePackScriptEvent", 
				"[load_sprite_pack file=\"" + file + "\"]");
	}

	@Override
	public boolean isContinue() {
		return false;
	}
}
