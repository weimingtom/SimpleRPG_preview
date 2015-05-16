package com.weimingtom.iteye.simplerpg.script;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.XmlReader.Element;

public class ProgressScriptEvent extends ScriptEvent {
	public String visible;
	
	public ProgressScriptEvent(Element eventElement) {
		Element element = eventElement.getChildByName(TAG_VISIBLE);
		if (element != null) {
			visible = element.getAttribute(TAG_VALUE);
		}
		Gdx.app.log("ProgressScriptEvent", 
				"[progress visible=\"" + visible + "\"]");
	}

	@Override
	public boolean isContinue() {
		return true;
	}
}
