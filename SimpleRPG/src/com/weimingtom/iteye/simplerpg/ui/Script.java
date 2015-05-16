package com.weimingtom.iteye.simplerpg.ui;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.weimingtom.iteye.simplerpg.script.BackgroundScriptEvent;
import com.weimingtom.iteye.simplerpg.script.ButtonScriptEvent;
import com.weimingtom.iteye.simplerpg.script.CharacterScriptEvent;
import com.weimingtom.iteye.simplerpg.script.JumpScriptEvent;
import com.weimingtom.iteye.simplerpg.script.LoadFontScriptEvent;
import com.weimingtom.iteye.simplerpg.script.LoadPixmapScriptEvent;
import com.weimingtom.iteye.simplerpg.script.LoadMapScriptEvent;
import com.weimingtom.iteye.simplerpg.script.LoadSpritePackScriptEvent;
import com.weimingtom.iteye.simplerpg.script.PlayerScriptEvent;
import com.weimingtom.iteye.simplerpg.script.TextDialogScriptEvent;
import com.weimingtom.iteye.simplerpg.script.MapScriptEvent;
import com.weimingtom.iteye.simplerpg.script.ProgressScriptEvent;
import com.weimingtom.iteye.simplerpg.script.ScriptEvent;
import com.weimingtom.iteye.simplerpg.script.MenuScriptEvent;
import com.weimingtom.iteye.simplerpg.script.MessageScriptEvent;

public class Script implements Disposable {
	//FIXME:
	public final static String INIT_SCENARIO = "init0";
	public final static String LOAD_SCENARIO = "load";
	
	private AssetManager assetManager;
	private XMLScript xmlScript;
	private int currentPosition = 0;
	private String currentScenario;
	private List<ScriptEvent> currentEvents;
	
	public static LoadItem load(AssetManager assetManager, String filename) {
		if (assetManager != null && filename != null) {
			assetManager.load(filename, XMLScript.class);
			return new LoadItem(LoadItem.TYPE_SCRIPT, new String[]{filename}, filename, null);
		}
		return null;
	}
	
	public Script(AssetManager assetManager) {
		this.assetManager = assetManager;
	}
	
	public void loadFile(String filename) {
		if (assetManager != null) {
			xmlScript = assetManager.get(filename, XMLScript.class);
		}
		//reset(INIT_SCENARIO);
		reset(LOAD_SCENARIO);
	}
	
	public void reset(String scenario) {
		currentPosition = 0;
		currentScenario = scenario;
		if (xmlScript != null && xmlScript.eventsMap != null) {
			currentEvents = xmlScript.eventsMap.get(currentScenario);
		} else {
			currentEvents = null;
		}
	}
	
	public boolean hasNextEvent() {
		if (xmlScript != null && currentEvents != null) {
			if (currentPosition >= 0 && currentPosition < currentEvents.size()) {
				return true;
			}
		}
		return false;
	}
	
	public ScriptEvent nextEvent() {
		if (xmlScript != null && xmlScript.eventsMap != null) {
			if (currentPosition >= 0 && currentPosition < currentEvents.size()) {
				ScriptEvent event = currentEvents.get(currentPosition);
				currentPosition++;
				return event;
			}
		}
		return null;
	}
	
	@Override
	public void dispose() {

	}
	
	/**
	 * @see TiledLoader
	 * @author Administrator
	 *
	 */
	public final static class XMLScript implements Disposable {
		private Map<String, List<ScriptEvent>> eventsMap;
		
		public void load(FileHandle file) {
			XmlReader xmlReader = new XmlReader(); 
	        try { 
	            Element gameElement = xmlReader.parse(new InputStreamReader(file.read(), "UTF8")); 
	            eventsMap = new TreeMap<String, List<ScriptEvent>>();
	            Array<Element> scenarioElements = gameElement.getChildrenByName("scenario"); 
	            for (Element scenarioElement : scenarioElements) {
	            	String name = scenarioElement.getAttribute("name");
	            	List<ScriptEvent> events = new ArrayList<ScriptEvent>();
	            	Array<Element> eventElements = scenarioElement.getChildrenByName("event");
		            for (Element eventElement : eventElements) { 
		            	String type = eventElement.getAttribute("type");
		            	if (type != null) {
		            		if (ScriptEvent.TAG_MESSAGE.equals(type)) {
		            			events.add(new MessageScriptEvent(eventElement));
		            		} else if (ScriptEvent.TAG_BACKGROUND.equals(type)) {
		            			events.add(new BackgroundScriptEvent(eventElement));
			            	} else if (ScriptEvent.TAG_CHARACTER.equals(type)) {
			            		events.add(new CharacterScriptEvent(eventElement));
			            	} else if (ScriptEvent.TAG_MENU.equals(type)) {
			            		events.add(new MenuScriptEvent(eventElement));
			            	} else if (ScriptEvent.TAG_MAP.equals(type)) {
			            		events.add(new MapScriptEvent(eventElement));
			            	} else if (ScriptEvent.TAG_LOAD_FONT.equals(type)) {
			            		events.add(new LoadFontScriptEvent(eventElement));
				            } else if (ScriptEvent.TAG_LOAD_MAP.equals(type)) {
			            		events.add(new LoadMapScriptEvent(eventElement));
				            } else if (ScriptEvent.TAG_LOAD_SPRITE_PACK.equals(type)) {
			            		events.add(new LoadSpritePackScriptEvent(eventElement));
				            } else if (ScriptEvent.TAG_LOAD_PIXMAP.equals(type)) {
			            		events.add(new LoadPixmapScriptEvent(eventElement));
				            } else if (ScriptEvent.TAG_JUMP.equals(type)) {
			            		events.add(new JumpScriptEvent(eventElement));
				            } else if (ScriptEvent.TAG_PROGRESS.equals(type)) {
			            		events.add(new ProgressScriptEvent(eventElement));
				            } else if (ScriptEvent.TAG_TEXT_DIALOG.equals(type)) {
			            		events.add(new TextDialogScriptEvent(eventElement));
				            } else if (ScriptEvent.TAG_PLAYER.equals(type)) {
			            		events.add(new PlayerScriptEvent(eventElement));
				            } else if (ScriptEvent.TAG_BUTTON.equals(type)) {
			            		events.add(new ButtonScriptEvent(eventElement));
				            }
		            	}
		            }
		            eventsMap.put(name, events);
	            }
	        } catch (IOException e) {
	        	throw new GdxRuntimeException("Error Parsing XML file", e);
			}
		}
		
		@Override
		public void dispose() {

		}
	}
	
	public final static class XMLScriptParameter extends AssetLoaderParameters<XMLScript> {
		
	}
	
	public final static class XMLScriptLoader extends AsynchronousAssetLoader<XMLScript, XMLScriptParameter> {
		private XMLScript xmlScript;
		
		public XMLScriptLoader(FileHandleResolver resolver) {
			super(resolver);
		}

//		@Override
//		public void loadAsync(AssetManager manager, String fileName, XMLScriptParameter parameter) {
//			xmlScript = null;
//			xmlScript = new XMLScript();
//			xmlScript.load(resolve(fileName));
//		}

//		@Override
//		public XMLScript loadSync(AssetManager manager, String fileName, XMLScriptParameter parameter) {
//			return xmlScript;
//		}

//		@Override
//		public Array<AssetDescriptor> getDependencies(String fileName, XMLScriptParameter parameter) {
//			return null;
//		}

		@Override
		public void loadAsync(AssetManager manager, String fileName,
				FileHandle file, XMLScriptParameter parameter) {
			xmlScript = null;
			xmlScript = new XMLScript();
			xmlScript.load(resolve(fileName));
		}

		@Override
		public XMLScript loadSync(AssetManager manager, String fileName,
				FileHandle file, XMLScriptParameter parameter) {
			return xmlScript;
		}

		@Override
		public Array<AssetDescriptor> getDependencies(String fileName,
				FileHandle file, XMLScriptParameter parameter) {
			// TODO Auto-generated method stub
			return null;
		}
	}
}
