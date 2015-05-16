package com.weimingtom.iteye.simplerpg.script;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.XmlReader.Element;

public class LoadMapScriptEvent extends ScriptEvent {
	public String file;
	public String path;
	
	public LoadMapScriptEvent(Element eventElement) {
		Element element = eventElement.getChildByName(TAG_STORAGE);
		if (element != null) {
			file = element.getAttribute(TAG_FILE);
			path = element.getAttribute(TAG_PATH);
		}
		Gdx.app.log("LoadMapScriptEvent", 
				"[load_map file=\"" + file + "\" path=\"" + path + "\"]");
	}

	@Override
	public boolean isContinue() {
		return false;
	}
}
