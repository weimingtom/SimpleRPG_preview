package com.weimingtom.iteye.simplerpg.script;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.XmlReader.Element;

public class LoadPixmapScriptEvent extends ScriptEvent {
	public String file;
	
	public LoadPixmapScriptEvent(Element eventElement) {
		Element element = eventElement.getChildByName(TAG_STORAGE);
		if (element != null) {
			file = element.getAttribute(TAG_FILE);
		}
		Gdx.app.log("LoadPixmapScriptEvent", 
				"[load_pixmap file=\"" + file + "\"]");
	}

	@Override
	public boolean isContinue() {
		return false;
	}
}
