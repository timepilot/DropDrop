package com.rizostudios.undertheradar;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Renderers {
	public SpriteBatch sb;
	public ShapeRenderer sr;
	
	public Renderers() {
		sb = new SpriteBatch();
		sr = new ShapeRenderer();
	}
}
