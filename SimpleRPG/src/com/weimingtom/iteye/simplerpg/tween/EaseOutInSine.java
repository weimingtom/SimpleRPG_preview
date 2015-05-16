package com.weimingtom.iteye.simplerpg.tween;

// FIXME:easeOutInSine
public class EaseOutInSine extends SimpleTweener {
	private EaseInSine easeInSine = new EaseInSine();
	private EaseOutSine easeOutSine = new EaseOutSine();

	@Override
	protected float interpolator(float t, float b, float c, float d) {
		if (t < d / 2)
			return easeOutSine.interpolator(t * 2, b, c / 2, d);
		return easeInSine.interpolator((t * 2) - d, b + c / 2, c / 2, d);
	}
}
