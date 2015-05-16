package com.weimingtom.iteye.simplerpg.script;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.XmlReader.Element;

public class CharacterScriptEvent extends ScriptEvent {
	public String centerFile;
	public String centerTransition;
	public String leftCenterFile;
	public String leftCenterTransition;
	public String rightCenterFile;
	public String rightCenterTransition;
	
	public CharacterScriptEvent(Element eventElement) {
		Element element;
		element = eventElement.getChildByName(TAG_CENTER);
		if (element != null) {
			centerFile = element.getAttribute(TAG_FILE);
			centerTransition = element.getAttribute(TAG_TRANSITION);
		}
		element = eventElement.getChildByName(TAG_LEFT_CENTER);
		if (element != null) {
			leftCenterFile = element.getAttribute(TAG_FILE);
			leftCenterTransition = element.getAttribute(TAG_TRANSITION);
		}
		element = eventElement.getChildByName(TAG_RIGHT_CENTER);
		if (element != null) {
			rightCenterFile = element.getAttribute(TAG_FILE);
			rightCenterTransition = element.getAttribute(TAG_TRANSITION);
		}
		Gdx.app.log("CharacterScriptEvent", 
				"[character " + 
				"centerFile=\"" + centerFile + "\" " + 
				"centerTransition=\"" + centerTransition + "\" " + 
				"leftCenterFile=\"" + leftCenterFile + "\" " + 
				"leftCenterTransition=\"" + leftCenterTransition + "\" " + 
				"rightCenterFile=\"" + rightCenterFile + "\" " + 
				"rightCenterTransition=\"" + rightCenterTransition + "\" " + 
				"]");
	}

	@Override
	public boolean isContinue() {
		return true;
	}
}
