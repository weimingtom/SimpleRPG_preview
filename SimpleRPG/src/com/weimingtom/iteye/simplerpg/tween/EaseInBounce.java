package com.weimingtom.iteye.simplerpg.tween;

// FIXME:easeInBounce
public class EaseInBounce extends SimpleTweener {
	private EaseOutBounce easeOutBounce = new EaseOutBounce();

	@Override
	protected float interpolator(float t, float b, float c, float d) {
		return c - easeOutBounce.interpolator(d - t, 0, c, d) + b;
	}
}
