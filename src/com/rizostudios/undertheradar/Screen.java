package com.rizostudios.undertheradar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public abstract class Screen {
	protected Renderers ren = new Renderers();
	protected BitmapFont bf;
	
	public abstract void update(float delta);
	public abstract void render();
	
	public static Color floatColor(int r, int g, int b) {
		return floatColor(r, g, b, 255);
	}
	public static Color floatColor(int r, int g, int b, int a) {
		return new Color(r / 255F, g / 255F, b / 255F, a / 255F);
	}
	
	protected abstract class TextButton extends Button {
		private BitmapFont bf;
		public String s;
		private Color c;
		
		public TextButton(int x, int y, int w, int h, String s, Color c) {
			super(x, y, w, h);
			
			this.bf = new BitmapFont(Gdx.files.internal("font/Mecha.fnt"), false);
			this.s = s;
			this.c = c;
		}
		
		@Override
		public void draw() {
			ren.sr.begin(ShapeType.FilledRectangle);
			ren.sr.setColor(floatColor(255, 255, 255));
			ren.sr.filledRect(x, y, w, h);
			ren.sr.setColor(c);
			ren.sr.filledRect(x + 2, y + 2, w - 4, h - 4);
			ren.sr.end();
			
			ren.sb.setColor(floatColor(255, 255, 255));
			ren.sb.begin();
			TextBounds b = bf.getBounds(s);
			bf.draw(ren.sb, s, x + (w / 2 - b.width / 2), y + (h / 2 + b.height / 2));
			ren.sb.end();
		}

		@Override
		public abstract void run();
	}
}
