package com.weimingtom.iteye.simplerpg.script;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.XmlReader.Element;

public class MapScriptEvent extends ScriptEvent {
	public String file;
	
	public MapScriptEvent(Element eventElement) {
		Element element = eventElement.getChildByName(TAG_STORAGE);
		if (element != null) {
			file = element.getAttribute(TAG_FILE);
		}
		Gdx.app.log("MapScriptEvent", 
				"[map file=\"" + file + "\"]");
	}

	@Override
	public boolean isContinue() {
		if (file != null) {
			return false;
		} else {
			return true;
		}
	}
}
