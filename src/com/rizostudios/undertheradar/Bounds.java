package com.rizostudios.undertheradar;

import com.badlogic.gdx.math.Vector2;

public class Bounds {
	public static boolean pointInRect(Vector2 point, Vector2 rPoint, float rW, float rH) {
		return point.x >= rPoint.x && point.x <= rPoint.x + rW && point.y >= rPoint.y && point.y <= rPoint.y + rH;
	}
}
