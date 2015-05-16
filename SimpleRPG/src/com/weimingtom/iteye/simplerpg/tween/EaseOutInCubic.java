package com.weimingtom.iteye.simplerpg.tween;

// FIXME: easeOutInCubic
public class EaseOutInCubic extends SimpleTweener {
	private EaseInCubic easeInCubic = new EaseInCubic();
	private EaseOutCubic easeOutCubic = new EaseOutCubic();

	@Override
	protected float interpolator(float t, float b, float c, float d) {
		if (t < d / 2)
			return easeOutCubic.interpolator(t * 2, b, c / 2, d);
		return easeInCubic.interpolator((t * 2) - d, b + c / 2, c / 2, d);
	}
}

