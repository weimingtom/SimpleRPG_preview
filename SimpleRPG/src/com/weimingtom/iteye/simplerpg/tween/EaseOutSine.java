package com.weimingtom.iteye.simplerpg.tween;

public class EaseOutSine extends SimpleTweener {
	@Override
	protected float interpolator(float t, float b, float c, float d) {
		return c * (float) Math.sin(t / d * (Math.PI / 2)) + b;
	}
}
