package com.rizostudios.undertheradar;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class EntityBall {
	public Vector2 pos;
	public float velY;
	public float accelY;
	public float radius;
	
	public EntityBall(Vector2 pos, float radius) {
		this.pos = pos;
		this.radius = radius;
		this.velY = 0;
		this.accelY = 0;
	}
	
	public void update(float delta) {
		ScreenGame screen = (ScreenGame) Game.screen;
		if (pos.y <= -10)
			screen.setGameOver(true);
		
		move(delta);
	}
	
	public void move(float delta) {
		float oldY = pos.y;
		pos.y += velY * delta;
		
		ScreenGame screen = (ScreenGame) Game.screen;
		for (EntityPlatform plat : screen.world.platforms) {
			boolean collide = Intersector.overlapCircleRectangle
					(new Circle(pos, radius), new Rectangle(plat.pos.x, plat.pos.y, plat.w, plat.h));
			if (pos.y - oldY < 0 && collide) {
				velY = 0;
				accelY = 0;
				pos.y = oldY;
				plat.ball = this;
			}
		}
		
		velY += accelY * delta;
		accelY += World.gravity;
	}
}
