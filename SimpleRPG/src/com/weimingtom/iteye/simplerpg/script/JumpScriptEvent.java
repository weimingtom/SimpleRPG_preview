package com.weimingtom.iteye.simplerpg.script;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.XmlReader.Element;

public class JumpScriptEvent extends ScriptEvent {
	public String ref;
	
	public JumpScriptEvent(Element eventElement) {
		Element element = eventElement.getChildByName(TAG_GOTO);
		if (element != null) {
			ref = element.getAttribute(TAG_REF);
		}
		Gdx.app.log("JumpScriptEvent", 
				"[jump ref=\"" + ref + "\"]");
	}

	@Override
	public boolean isContinue() {
		return true;
	}
}
