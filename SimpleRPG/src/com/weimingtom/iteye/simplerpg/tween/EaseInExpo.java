package com.weimingtom.iteye.simplerpg.tween;

public class EaseInExpo extends SimpleTweener {
	@Override
	protected float interpolator(float t, float b, float c, float d) {
		return (t == 0) ? b : c * (float) Math.pow(2, 10 * (t / d - 1)) + b
				- c * 0.001f;
	}
}
