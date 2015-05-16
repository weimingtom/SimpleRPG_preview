package com.weimingtom.iteye.simplerpg.tween;

// FIXME:easeInOutBounce
public class EaseInOutBounce extends SimpleTweener {
	private EaseInBounce easeInBounce = new EaseInBounce();
	private EaseOutBounce easeOutBounce = new EaseOutBounce();

	@Override
	protected float interpolator(float t, float b, float c, float d) {
		if (t < d / 2)
			return easeInBounce.interpolator(t * 2, 0, c, d) * 0.5f + b;
		else
			return easeOutBounce.interpolator(t * 2 - d, 0, c, d) * 0.5f
					+ c * 0.5f + b;
	}
}
