package com.weimingtom.iteye.simplerpg.tween;

public class EaseInOutCirc extends SimpleTweener {
	@Override
	protected float interpolator(float t, float b, float c, float d) {
		t /= d / 2;
		if (t < 1)
			return -c / 2 * ((float) Math.sqrt(1 - t * t) - 1) + b;
		t -= 2;
		return c / 2 * ((float) Math.sqrt(1 - t * t) + 1) + b;
	}
}
