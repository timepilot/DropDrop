package com.rizostudios.undertheradar;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public class World {
	Renderers ren;
	private Random rand = new Random();
	
	EntityBall ball;
	static Color bC = Screen.floatColor(200, 0, 0);
	static int bR = 10;
	
	public List<EntityPlatform> platforms = new ArrayList<EntityPlatform>();
	static Color pC = Screen.floatColor(100, 100, 100);
	static int pH = 10;
	static int pW = 100;
	float startingPV;
	float pV;
	
	private List<EntityPlatform> disposal = new ArrayList<EntityPlatform>();
	
	private Vector2 spawnPoint = new Vector2(Game.width / 2, Game.height - 50);
	
	public static float gravity = -50F;
	
	public World(Renderers ren, int difficulty) {
		ball = new EntityBall(spawnPoint.cpy().add(0, pH + bR * 2), bR);
		switch (difficulty) {
		case 0:
			pW = 100;
			startingPV = 60;
			break;
		case 1:
			pW = 75;
			startingPV = 90;
			break;
		case 2:
			pW = 50;
			startingPV = 120;
			break;
		}
		
		pV = startingPV;
		
		EntityPlatform plat = new EntityPlatform(spawnPoint.cpy().add(-pW / 2, 0), pW, pH, 0);
		plat.ball = ball;
		platforms.add(plat);
		
		this.ren = ren;
	}
	
	public void update(float delta) {
		for(EntityPlatform e : disposal)
			platforms.remove(e);
		disposal.clear();

		ball.update(delta);
		for (EntityPlatform p : platforms) {
			p.update(delta);
		}
		
		if (platforms.size() < 2)
			platforms.add(new EntityPlatform(new Vector2(rand.nextInt(Game.width - pW), -10), pW, 10, pV));
		
		pV += 0.001F;
	}
	
	public void render() {
		ren.sr.begin(ShapeType.FilledRectangle);
		for (EntityPlatform e : platforms) {
			ren.sr.setColor(World.pC);
			ren.sr.filledRect(e.pos.x, e.pos.y, e.w, e.h);
		}
		ren.sr.end();
		
		ren.sr.begin(ShapeType.FilledCircle);
		ren.sr.setColor(World.bC);
		ren.sr.filledCircle(ball.pos.x, ball.pos.y, ball.radius, 20);
		ren.sr.end();
	}
	
	public void kill(EntityPlatform e) {
		disposal.add(e);
	}
}
