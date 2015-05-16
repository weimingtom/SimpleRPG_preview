package com.weimingtom.iteye.simplerpg.script;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.XmlReader.Element;

public class LoadFontScriptEvent extends ScriptEvent {
	public String prename;
	
	public LoadFontScriptEvent(Element eventElement) {
		Element element = eventElement.getChildByName(TAG_STORAGE);
		if (element != null) {
			prename = element.getAttribute(TAG_PRENAME);
		}
		Gdx.app.log("LoadFontScriptEvent", 
				"[load_font prename=\"" + prename + "\"]");
	}

	@Override
	public boolean isContinue() {
		return false;
	}
}
