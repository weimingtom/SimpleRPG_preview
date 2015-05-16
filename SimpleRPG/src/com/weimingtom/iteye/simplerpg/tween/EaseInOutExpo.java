package com.weimingtom.iteye.simplerpg.tween;

public class EaseInOutExpo extends SimpleTweener {
	@Override
	protected float interpolator(float t, float b, float c, float d) {
		if (t == 0)
			return b;
		if (t == d)
			return b + c;
		t /= d / 2;
		if (t < 1)
			return c / 2 * (float) Math.pow(2, 10 * (t - 1)) + b - c
					* 0.0005f;
		--t;
		return c / 2 * 1.0005f * (-(float) Math.pow(2, -10 * t) + 2f) + b;
	}
}
