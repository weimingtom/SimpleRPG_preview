package com.weimingtom.iteye.simplerpg.tween;

public class EaseInCirc extends SimpleTweener {
	@Override
	protected float interpolator(float t, float b, float c, float d) {
		t /= d;
		return -c * ((float) Math.sqrt(1 - t * t) - 1) + b;
	}
}
