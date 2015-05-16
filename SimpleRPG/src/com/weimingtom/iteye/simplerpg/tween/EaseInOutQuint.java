package com.weimingtom.iteye.simplerpg.tween;

public class EaseInOutQuint extends SimpleTweener {
	@Override
	protected float interpolator(float t, float b, float c, float d) {
		t /= d / 2.0f;
		if (t < 1)
			return c / 2.0f * t * t * t * t * t + b;
		t -= 2.0f;
		return c / 2.0f * (t * t * t * t * t + 2.0f) + b;
	}
}
