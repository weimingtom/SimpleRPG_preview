package com.weimingtom.iteye.simplerpg.tween;

public class EaseOutExpo extends SimpleTweener {
	@Override
	protected float interpolator(float t, float b, float c, float d) {
		return (t == d) ? b + c : c * 1.001f
				* (-(float) Math.pow(2, -10 * t / d) + 1) + b;
	}
}
