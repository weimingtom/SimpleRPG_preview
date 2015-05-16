package com.weimingtom.iteye.simplerpg.tween;

public class EaseInOutQuad extends SimpleTweener {
	@Override
	protected float interpolator(float t, float b, float c, float d) {
		t /= (d / 2.0f);
		if (t < 1) {
			return c / 2.0f * t * t + b;
		}
		--t;
		return -c / 2.0f * (t * (t - 2.0f) - 1.0f) + b;
	}
}