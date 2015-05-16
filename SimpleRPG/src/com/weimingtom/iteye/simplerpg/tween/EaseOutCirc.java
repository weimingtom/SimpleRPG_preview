package com.weimingtom.iteye.simplerpg.tween;

public class EaseOutCirc extends SimpleTweener {
	@Override
	protected float interpolator(float t, float b, float c, float d) {
		t = t / d - 1;
		return c * (float) Math.sqrt(1 - t * t) + b;
	}
}
