package com.rizostudios.undertheradar;

import com.badlogic.gdx.math.Vector2;

public class EntityPlatform {
	public Vector2 pos;
	public float velY;
	public float w, h;
	public boolean selected;
	
	public EntityBall ball;
	
	public EntityPlatform(Vector2 pos, float w, float h, float yVel) {
		this.pos = pos;
		this.w = w;
		this.h = h;
		this.velY = yVel;
	}
	
	public void update(float delta) {
		if (selected) {
			pos.x += InputMouse.mouseDelta.x;
			if (ball != null)
				ball.pos.x += InputMouse.mouseDelta.x;
		}
		if (pos.y <= Game.height + 10) {
			pos.y += velY * delta;
			if (ball != null)
				ball.pos.y = pos.y + h + ball.radius;
		} else {
			ScreenGame screen = (ScreenGame) Game.screen;
			screen.setGameOver(true);
		}
			
	}
}
