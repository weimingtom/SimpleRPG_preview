/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.weimingtom.iteye.simplerpg;

import java.io.File;
import java.io.IOException;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.tools.imagepacker.TexturePacker;
import com.badlogic.gdx.tools.imagepacker.TexturePacker2;
import com.weimingtom.iteye.simplerpg.tiled.TiledMapPacker;

public class SimpleRPGDesktop {
	public static void main (String[] argv) {
		if (false) {
			processTexture();
			//processMap();
		}
		
		new LwjglApplication(new SimpleRPGGame(), "Simple RPG", 640, 480, false);
	}
	
	private static void processTexture() {
		/**
		 * @see https://code.google.com/p/libgdx/wiki/TexturePacker
		 */
		TexturePacker2.Settings texSettings = new TexturePacker2.Settings();
		TexturePacker2.process(texSettings, "data/Graphics/Titles", "data/Graphics", "Titles.atlas");
		TexturePacker2.process(texSettings, "data/sprites/spritepack", "data/sprites", "spritepack.atlas");
	}
	
	private static void processMap() {
		//TexturePacker2.Settings tileSettings = new TexturePacker2.Settings();
		TexturePacker.Settings tileSettings = new TexturePacker.Settings();
		TiledMapPacker packer = new TiledMapPacker();
        File inputDir = new File("data/maps");
        File outputDir = new File("data/maps/");
        try {
			packer.processMap(inputDir, outputDir, tileSettings);
			//packer.processMaps(inputDir, outputDir, tileSettings);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
