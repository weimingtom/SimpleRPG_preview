package com.weimingtom.iteye.simplerpg.script;

public abstract class ScriptEvent {
	public final static String TAG_MESSAGE = "message";
	public final static String TAG_BACKGROUND = "background";
	public final static String TAG_CHARACTER = "character";
	public final static String TAG_MENU = "menu";
	public final static String TAG_MAP = "map";
	public final static String TAG_LOAD_FONT = "load_font";
	public final static String TAG_LOAD_MAP = "load_map";
	public final static String TAG_LOAD_SPRITE_PACK = "load_sprite_pack";
	public final static String TAG_LOAD_PIXMAP = "load_pixmap";
	public final static String TAG_JUMP = "jump";
	public final static String TAG_PROGRESS = "progress";
	public final static String TAG_TEXT_DIALOG = "text_dialog";
	public final static String TAG_PLAYER = "player";
	public final static String TAG_BUTTON = "button";
	
	//message
	public final static String TAG_TALKER = "talker";
	public final static String TAG_BODY = "body";
	
	//background / character / map
	//load_layer / load_map / load_player
	//load_text_dialog
	public final static String TAG_FILE = "file";
	
	//menu / jump
	public final static String TAG_REF = "ref";
	
	//background / load_text_dialog
	public final static String TAG_STORAGE = "storage";
	public final static String TAG_ITEM = "item";
	
	//character
	public final static String TAG_TRANSITION = "transition";
	public final static String TAG_CENTER = "center";
	public final static String TAG_LEFT_CENTER = "left_center";
	public final static String TAG_RIGHT_CENTER = "right_center";
	
	//menu
	public final static String TAG_ITEM0 = "item0";
	public final static String TAG_ITEM1 = "item1";
	public final static String TAG_ITEM2 = "item2";
	public final static String TAG_ITEM3 = "item3";
	
	//load_font
	public final static String TAG_PRENAME = "prename";
	
	//load_map
	public final static String TAG_PATH = "path";
	
	//jump
	public final static String TAG_GOTO = "goto";
	
	//progress
	public final static String TAG_VALUE = "value";
	public final static String TAG_VISIBLE = "visible";
	
	//player / text_dialog
	public final static String TAG_NAME = "name";
	
	//button
	public final static String TAG_DOWN = "down";
	public final static String TAG_UP = "up";
	public final static String TAG_ID = "id";
	
	public abstract boolean isContinue();
}
