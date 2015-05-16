package com.weimingtom.iteye.simplerpg.tween;

// FIXME:easeOutInQuint
public class EaseOutInQuint extends SimpleTweener {
	private EaseInQuint easeInQuint = new EaseInQuint();
	private EaseOutQuint easeOutQuint = new EaseOutQuint();

	@Override
	protected float interpolator(float t, float b, float c, float d) {
		if (t < d / 2)
			return easeOutQuint.interpolator(t * 2, b, c / 2, d);
		return easeInQuint.interpolator((t * 2) - d, b + c / 2, c / 2, d);
	}
}
