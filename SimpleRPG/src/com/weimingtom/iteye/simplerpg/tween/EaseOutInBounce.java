package com.weimingtom.iteye.simplerpg.tween;

// FIXME:easeOutInBounce
public class EaseOutInBounce extends SimpleTweener {
	private EaseInBounce easeInBounce = new EaseInBounce();
	private EaseOutBounce easeOutBounce = new EaseOutBounce();

	@Override
	protected float interpolator(float t, float b, float c, float d) {
		if (t < d / 2)
			return easeOutBounce.interpolator(t * 2, b, c / 2, d);
		return easeInBounce.interpolator((t * 2) - d, b + c / 2, c / 2, d);
	}
}
