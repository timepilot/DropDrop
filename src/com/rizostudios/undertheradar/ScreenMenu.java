package com.rizostudios.undertheradar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class ScreenMenu extends Screen {
	Button[] buttons = new Button[3];
	
	public ScreenMenu() {
		bf = new BitmapFont(Gdx.files.internal("font/Mecha.fnt"), false);
		
		buttons[0] = new TextButtonStart(5, 5, 100, 40, "Hard", floatColor(50, 50, 50), 2);
		buttons[1] = new TextButtonStart(5, 50, 100, 40, "Medium", floatColor(100, 100, 100), 1);
		buttons[2] = new TextButtonStart(5, 95, 100, 40, "Easy", floatColor(150, 150, 150), 0);
	}
	
	@Override
	public void update(float delta) {
	
	}

	@Override
	public void render() {
		renderTitle();
		
		for (Button button : buttons)
			button.draw();
	}
	
	private void renderTitle() {
		TextBounds tb = bf.getBounds(Game.title);
		float titleX = Game.width / 2 - tb.width / 2;
		float titleY = Game.height * 0.95F;
		
		int scalar = 5;
		
		float ballR = World.bR * scalar;
		float ballX = titleX + tb.width - tb.width / 5 - ballR;
		float ballY = titleY - tb.height + tb.height / 5;
		
		float platW = World.pW * scalar;
		float platH = World.pH * scalar;
		float platX = ballX - platW / 2;
		float platY = ballY - ballR - platH;
		
		ren.sr.begin(ShapeType.FilledCircle);
		ren.sr.setColor(World.bC);
		ren.sr.filledCircle(ballX, ballY, ballR, (int) ballR);
		ren.sr.end();
		
		ren.sr.begin(ShapeType.FilledRectangle);
		ren.sr.setColor(World.pC);
		ren.sr.filledRect(platX, platY, platW, platH);
		ren.sr.end();
		
		ren.sb.begin();
		bf.setScale(5);
		bf.setColor(floatColor(255, 255, 255));
		bf.draw(ren.sb, Game.title, titleX, titleY);
		ren.sb.end();
	}
	
	private class TextButtonStart extends TextButton {
		private int difficulty;
		
		public TextButtonStart(int x, int y, int w, int h, String s, Color c, int difficulty) {
			super(x, y, w, h, s, c);
			this.difficulty = difficulty;
		}
		
		@Override
		public void run() {
			Game.screen = new ScreenGame(difficulty);
		}
	}
}
