package com.weimingtom.iteye.simplerpg.tween;

public class EaseOutBounce extends SimpleTweener {
	@Override
	protected float interpolator(float t, float b, float c, float d) {
		t /= d;
		if (t < (1.0 / 2.75)) {
			return c * (7.5625f * t * t) + b;
		} else if (t < (2.0 / 2.75)) {
			t -= (1.5 / 2.75);
			return c * (7.5625f * t * t + 0.75f) + b;
		} else if (t < (2.5 / 2.75)) {
			t -= (2.25 / 2.75);
			return c * (7.5625f * t * t + 0.9375f) + b;
		} else {
			t -= (2.625 / 2.75);
			return c * (7.5625f * t * t + 0.984375f) + b;
		}
	}
}
