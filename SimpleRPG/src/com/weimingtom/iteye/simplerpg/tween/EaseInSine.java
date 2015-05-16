package com.weimingtom.iteye.simplerpg.tween;

public class EaseInSine extends SimpleTweener {
	@Override
	protected float interpolator(float t, float b, float c, float d) {
		return -c * (float) Math.cos(t / d * (Math.PI / 2)) + c + b;
	}
}
