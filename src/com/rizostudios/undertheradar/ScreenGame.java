package com.rizostudios.undertheradar;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public class ScreenGame extends Screen {
	public static final int DIFFICULTY_EASY = 0;
	public static final int DIFFICULTY_MEDIUM = 1;
	public static final int DIFFICULTY_HARD = 2;
	
	private boolean paused;
	private boolean gameOver;
	public World world;
	
	private Vector2 pause_loc = new Vector2(20, 20);
	private float pausebar_w = 15;
	private float pausebar_h = 50;
	private float pause_gap = 15;
	
	public Button[] buttons = new Button[2];
	private int button_w = 100;
	private int button_h = 40;
	
	public int score;
	public int difficulty;
	
	public ScreenGame(int difficulty) {
		this.difficulty = difficulty;
		
		world = new World(ren, difficulty);
		bf = new BitmapFont(Gdx.files.internal("font/Mecha.fnt"), false);
		buttons[0] = new TextButtonRestart(Game.width / 2 - button_w / 2, Game.height / 2 + 5, 
				button_w, button_h, "Restart", floatColor(150, 150, 150));
		buttons[1] = new TextButtonMenu(Game.width / 2 - button_w / 2, Game.height / 2 - button_h - 5, 
				button_w, button_h, "Menu", floatColor(150, 150, 150));
	}
	
	@Override
	public void update(float delta) {
		if (!Display.isActive())
			setPaused(true);
		
		if (paused || gameOver)
			return;
		
		world.update(delta);
		score = (int) ((world.pV - world.startingPV) * 100);
	}
	
	@Override
	public void render() {
		world.render();
		
		if (paused) {
			renderPauseScreen();
			return;
		}
		
		if (gameOver) {
			renderGameOverScreen();
			return;
		}
		
		ren.sb.begin();
		bf.setColor(floatColor(255, 255, 255));
		bf.draw(ren.sb, String.valueOf(score), 5, 5 + bf.getBounds(String.valueOf(score)).height);
		ren.sb.end();
	}
	
	private void renderPauseScreen() {
		Gdx.gl.glEnable(GL11.GL_BLEND);
	    Gdx.gl.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		ren.sr.begin(ShapeType.FilledRectangle);
		ren.sr.setColor(floatColor(150, 150, 150, 100));
		ren.sr.filledRect(0, 0, Game.width, Game.height);
		ren.sr.end();
		Gdx.gl.glDisable(GL11.GL_BLEND);
		
		ren.sr.begin(ShapeType.FilledRectangle);
		ren.sr.setColor(floatColor(255, 255, 255));
		ren.sr.filledRect(pause_loc.x, pause_loc.y, pausebar_w, pausebar_h);
		ren.sr.filledRect(pause_loc.x + pausebar_w + pause_gap, pause_loc.y, pausebar_w, pausebar_h);
		ren.sr.end();
		
		buttons[0].draw();
		buttons[1].draw();
		
		ren.sb.begin();
		bf.setColor(floatColor(255, 255, 255));
		bf.draw(ren.sb, "Score: " + score, 5, Game.height - 5);
		ren.sb.end();
	}
	
	private void renderGameOverScreen() {
		Gdx.gl.glEnable(GL11.GL_BLEND);
	    Gdx.gl.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		ren.sr.begin(ShapeType.FilledRectangle);
		ren.sr.setColor(floatColor(150, 150, 150, 100));
		ren.sr.filledRect(0, 0, Game.width, Game.height);
		ren.sr.end();
		Gdx.gl.glDisable(GL11.GL_BLEND);
		
		ren.sr.begin(ShapeType.FilledRectangle);
		ren.sr.setColor(floatColor(150, 0, 0));
		ren.sr.filledRect(pause_loc.x, pause_loc.y, pausebar_h, pausebar_h);
		ren.sr.end();
		
		buttons[0].draw();
		buttons[1].draw();

		ren.sb.begin();
		bf.setColor(floatColor(255, 255, 255));
		bf.draw(ren.sb, "Score: " + score, 5, Game.height - 5);
		ren.sb.end();
	}
	
	public static void screenshot(final String path) {
		GL11.glReadBuffer(GL11.GL_FRONT);
		final int width = Display.getDisplayMode().getWidth();
		final int height= Display.getDisplayMode().getHeight();
		final int bpp = 4; // Assuming a 32-bit display with a byte each for red, green, blue, and alpha.
		final ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * bpp);
		GL11.glReadPixels(0, 0, width, height, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer );
		
		new Thread(new Runnable() {
			public void run() {
				File file = new File(path); // The file to save to.
				String format = "png"; // Example: "PNG" or "JPG"
				BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
				 
				for(int x = 0; x < width; x++) {
					for(int y = 0; y < height; y++) {
						int i = (x + (width * y)) * bpp;
						int r = buffer.get(i) & 0xFF;
						int g = buffer.get(i + 1) & 0xFF;
						int b = buffer.get(i + 2) & 0xFF;
						image.setRGB(x, height - (y + 1), (0xFF << 24) | (r << 16) | (g << 8) | b);
					}
				}
				 
				try {
					ImageIO.write(image, format, file);
					System.out.println("Saved screenshot to: \""+ file.getAbsolutePath() + "\"");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	public void setPaused(boolean state) {
		if (gameOver)
			return;
		paused = state;
	}
	
	public boolean getPaused() {
		return paused;
	}
	
	public void setGameOver(boolean state) {
		paused = !state;
		gameOver = state;
	}
	
	public boolean getGameOver() {
		return gameOver;
	}
	
	private class TextButtonRestart extends TextButton {
		public TextButtonRestart(int x, int y, int w, int h, String s, Color c) {
			super(x, y, w, h, s, c);
		}
		
		@Override
		public void run() {
			if (paused || gameOver)
				Game.screen = new ScreenGame(difficulty);
		}
	}
	
	private class TextButtonMenu extends TextButton {
		public TextButtonMenu(int x, int y, int w, int h, String s, Color c) {
			super(x, y, w, h, s, c);
		}
		
		@Override
		public void run() {
			if (paused || gameOver) {
				Game.screen = new ScreenMenu();
			}
		}
	}
}
