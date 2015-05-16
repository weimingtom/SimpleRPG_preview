package com.weimingtom.iteye.simplerpg.script;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.XmlReader.Element;

public class MessageScriptEvent extends ScriptEvent {	
	public String talker;
	public String body;
	
	public MessageScriptEvent(Element eventElement) {
		this.talker = eventElement.get(TAG_TALKER, null);
		this.body = eventElement.get(TAG_BODY, null);
		Gdx.app.log("MessageScriptEvent", 
			"[message talker=\"" + talker + "\" body=\"" + body + "\"]");
	}
	
	@Override
	public boolean isContinue() {
		if (this.talker == null && this.body == null) {
			return true;
		} else {
			return false;
		}
	}
}
