package com.weimingtom.iteye.simplerpg.tween;

// FIXME:easeOutInQuart
public class EaseOutInQuart extends SimpleTweener {
	private EaseInQuart easeInQuart = new EaseInQuart();
	private EaseOutQuart easeOutQuart = new EaseOutQuart();

	@Override
	protected float interpolator(float t, float b, float c, float d) {
		if (t < d / 2)
			return easeOutQuart.interpolator(t * 2, b, c / 2, d);
		return easeInQuart.interpolator((t * 2) - d, b + c / 2, c / 2, d);
	}
}
