package com.weimingtom.iteye.simplerpg.script;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.XmlReader.Element;

public class PlayerScriptEvent extends ScriptEvent {
	public String file;
	public String name;
	
	public PlayerScriptEvent(Element eventElement) {
		Element element = eventElement.getChildByName(TAG_ITEM);
		if (element != null) {
			file = element.getAttribute(TAG_FILE);
			name = element.getAttribute(TAG_NAME);
		}
		Gdx.app.log("PlayerScriptEvent", 
				"[player file=\"" + file + "\" name=\"" + name + "\"]");
	}

	@Override
	public boolean isContinue() {
		return true;
	}
}
