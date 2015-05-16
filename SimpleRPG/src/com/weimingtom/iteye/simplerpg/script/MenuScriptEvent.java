package com.weimingtom.iteye.simplerpg.script;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.XmlReader.Element;

public class MenuScriptEvent extends ScriptEvent {
	public final static String VALUE_EXIT = "exit";
	public final static String VALUE_EXTRA = "extra";
	
	public String item0;
	public String item1;
	public String item2;
	public String item3;
	
	public String ref0;
	public String ref1;
	public String ref2;
	public String ref3;
	
	public MenuScriptEvent(Element eventElement) {
		Element element;
		element = eventElement.getChildByName(TAG_ITEM0);
		if (element != null) {
			this.item0 = element.getText();
			this.ref0 = element.getAttribute(TAG_REF, null);
		}
		element = eventElement.getChildByName(TAG_ITEM1);
		if (element != null) {
			this.item1 = element.getText();
			this.ref1 = element.getAttribute(TAG_REF, null);
		}
		element = eventElement.getChildByName(TAG_ITEM2);
		if (element != null) {
			this.item2 = element.getText();
			this.ref2 = element.getAttribute(TAG_REF, null);
		}
		element = eventElement.getChildByName(TAG_ITEM3);
		if (element != null) {
			this.item3 = element.getText();
			this.ref3 = element.getAttribute(TAG_REF, null);
		}
		Gdx.app.log("MenuScriptEvent", 
			"[menu " + 
			"item0=\"" + item0 + "\" ref0=\"" + ref0 + "\" " + 
			"item1=\"" + item1 + "\" ref1=\"" + ref1 + "\" " + 
			"item2=\"" + item2 + "\" ref2=\"" + ref2 + "\" " + 
			"item3=\"" + item3 + "\" ref3=\"" + ref3 + "\" " + 
			"]");
	}

	@Override
	public boolean isContinue() {
		if (item0 == null && 
			item1 == null &&
			item2 == null &&
			item3 == null) {
			return true;
		} else {
			return false;
		}
	}
}
