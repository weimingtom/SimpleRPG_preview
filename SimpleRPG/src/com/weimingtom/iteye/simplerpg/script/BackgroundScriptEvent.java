package com.weimingtom.iteye.simplerpg.script;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.XmlReader.Element;

public class BackgroundScriptEvent extends ScriptEvent {
	public String file;
	
	public BackgroundScriptEvent(Element eventElement) {
		Element element = eventElement.getChildByName(TAG_ITEM);
		if (element != null) {
			file = element.getAttribute(TAG_FILE);
		}
		Gdx.app.log("BackgroundScriptEvent", 
				"[background file=\"" + file + "\"]");
	}

	@Override
	public boolean isContinue() {
		return true;
	}
}
