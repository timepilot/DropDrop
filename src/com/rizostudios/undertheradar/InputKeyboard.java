package com.rizostudios.undertheradar;

import static org.lwjgl.input.Keyboard.*;

public class InputKeyboard {
	private static boolean K_ESC = false;
	private static boolean K_P = false;
	
	public static void update() {
		get();
		rem();
	}
	
	private static void get() {
		if (isKeyDown(KEY_ESCAPE) && !K_ESC) {
			K_ESC = true;
			keyEscape();
		}
		
		if (isKeyDown(KEY_P) && !K_P) {
			K_P = true;
			keyP();
		}
	}
	
	private static void rem() {
		if (!isKeyDown(KEY_ESCAPE) && K_ESC)
			K_ESC = false;
		
		if (!isKeyDown(KEY_P) && K_P)
			K_P = false;
	}
	
	private static void keyEscape() {
		if (Game.screen instanceof ScreenGame) {
			ScreenGame screen = (ScreenGame) Game.screen;
			screen.setPaused(screen.getPaused() ^ true);
			if (screen.getGameOver())
				Game.screen = new ScreenMenu();
		}
	}
	
	private static void keyP() {
		if (Game.screen instanceof ScreenGame)
			ScreenGame.screenshot(System.nanoTime() / 1000000 + ".png");
	}
}
