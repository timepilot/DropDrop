package com.rizostudios.undertheradar;

import static org.lwjgl.input.Mouse.*;

import org.lwjgl.input.Mouse;

import com.badlogic.gdx.math.Vector2;

public class InputMouse {
	public static Vector2 mouseDelta;
	private static Vector2 lastCursor = new Vector2();
	private static Vector2 mpos;
	
	private static boolean M_LEFT = false;
	
	public static void update() {
		mouseDelta = mouseDelta();
		mpos = new Vector2(getX(), getY());
		get();
		rem();
	}
	
	private static void get() {
		if (isButtonDown(0) && !M_LEFT) {
			M_LEFT = true;
			buttonLeft();
		}
	}
	
	private static void rem() {
		if (!isButtonDown(0) && M_LEFT) {
			M_LEFT = false;
			buttonLeftRelease();
		}
	}
	
	private static Vector2 mouseDelta() {
		Vector2 currentCursor = new Vector2(Mouse.getX(), Mouse.getY());
		Vector2 delta = currentCursor.cpy().sub(lastCursor);
		lastCursor = currentCursor;
		return delta;
	}
	
	private static void buttonLeft() {
		if (Game.screen instanceof ScreenGame) {
			platformClick();
		}
		buttonClick();
	}
	
	private static void buttonLeftRelease() {
		if (Game.screen instanceof ScreenGame) {
			platformRelease();
		}
	}
	
	private static void buttonClick() {
		if (Game.screen instanceof ScreenGame) {
			ScreenGame screen = (ScreenGame) Game.screen;
			for (Button button : screen.buttons) {
				boolean pointInRect = Bounds.pointInRect(mpos, new Vector2(button.x, button.y), button.w, button.h);
				if (pointInRect)
					button.run(); 
			}
		} else if (Game.screen instanceof ScreenMenu) {
			ScreenMenu screen = (ScreenMenu) Game.screen;
			for (Button button : screen.buttons) {
				boolean pointInRect = Bounds.pointInRect(mpos, new Vector2(button.x, button.y), button.w, button.h);
				if (pointInRect)
					button.run(); 
			}
		}
	}
	
	private static void platformClick() {
		if (Game.screen instanceof ScreenGame) {
			ScreenGame screen = (ScreenGame) Game.screen;
			for (EntityPlatform p : screen.world.platforms) {
				boolean pointInRect = Bounds.pointInRect(mpos, new Vector2(p.pos.x, 0), p.w, Game.height);
				if (pointInRect && p.ball != null)
					p.selected = true; 
			}
		}
	}
	
	private static void platformRelease() {
		if (Game.screen instanceof ScreenGame) {
			ScreenGame screen = (ScreenGame) Game.screen;
			for (EntityPlatform p : screen.world.platforms)
				if (p.selected)
					screen.world.kill(p);
		}
	}
}
