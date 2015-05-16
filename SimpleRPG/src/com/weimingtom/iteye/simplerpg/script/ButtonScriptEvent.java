package com.weimingtom.iteye.simplerpg.script;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.XmlReader.Element;

public class ButtonScriptEvent extends ScriptEvent {
	public String file;
	public String down;
	public String up;
	public String id;
	
	public ButtonScriptEvent(Element eventElement) {
		Element element = eventElement.getChildByName(TAG_ITEM);
		if (element != null) {
			file = element.getAttribute(TAG_FILE);
			down = element.getAttribute(TAG_DOWN);
			up = element.getAttribute(TAG_UP);
			id = element.getAttribute(TAG_ID);
		}
		Gdx.app.log("ButtonScriptEvent", 
				"[button " + 
				"file=\"" + file + "\" " + 
				"down=\"" + down + "\" " + 
				"up=\"" + up + "\" " + 
				"id=\"" + id + "\" " + 
				"]");
	}

	@Override
	public boolean isContinue() {
		return true;
	}
}
