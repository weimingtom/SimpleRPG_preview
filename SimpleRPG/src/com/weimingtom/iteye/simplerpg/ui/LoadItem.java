package com.weimingtom.iteye.simplerpg.ui;

public class LoadItem {
	public boolean isLoaded = false;
	
	public static final int TYPE_NONE = 0;
	public static final int TYPE_HANZI = 1;
	public static final int TYPE_SPRITE_PACK = 2;
	public static final int TYPE_SCRIPT = 3;
	public static final int TYPE_PIXMAP = 4;
	public static final int TYPE_TMXMAP = 5;

	public int type = TYPE_NONE;
	public String[] filenames;
	public String param1;
	public String param2;
	
	public LoadItem(int type, String[] filenames, String param1, String param2) {
		this.type = type;
		this.filenames = filenames;
		this.param1 = param1;
		this.param2 = param2;
	}
}
