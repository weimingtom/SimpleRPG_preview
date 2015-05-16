package com.weimingtom.iteye.simplerpg.tween;

public class EaseOutQuint extends SimpleTweener {
	@Override
	protected float interpolator(float t, float b, float c, float d) {
		t = t / d - 1.0f;
		return c * (t * t * t * t * t + 1.0f) + b;
	}
}
