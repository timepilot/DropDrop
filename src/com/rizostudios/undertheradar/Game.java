package com.rizostudios.undertheradar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class Game extends com.badlogic.gdx.Game implements com.badlogic.gdx.Screen
{
	public static final int width = 640;
	public static final int height = 480;
	public static final String title = "DropDrop";
	
	static Screen screen;
	
	private OrthographicCamera cam;
	
	public static void main(String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = width;
		config.height = height;
		config.useGL20 = true;
		config.resizable = false;
		config.title = title;
		new LwjglApplication(new Game(), config);
	}
	
	@Override
	public void create() { 
		setScreen(this);
		screen = new ScreenMenu();
		
		this.cam = new OrthographicCamera(width, height);
		
		this.cam.position.set(width / 2, height / 2, 0F);
		this.cam.update();
	}

	@Override
	public void render(float delta) {
		InputKeyboard.update();
		InputMouse.update();
		
		Gdx.gl.glClear(GL11.GL_COLOR_BUFFER_BIT);
		
		screen.update(delta);
		screen.render();
	}
	
	@Override
	public void resize(int x, int y) {
		
	}
	
	@Override
	public void pause() {
		
	}

	@Override
	public void show() {
		
	}

	@Override
	public void hide() {
		
	}
}
