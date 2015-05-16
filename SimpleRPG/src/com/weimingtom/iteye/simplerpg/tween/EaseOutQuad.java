package com.weimingtom.iteye.simplerpg.tween;

public class EaseOutQuad extends SimpleTweener {
	@Override
	protected float interpolator(float t, float b, float c, float d) {
		t /= d;
		return -c * t * (t - 2) + b;
	}
}
