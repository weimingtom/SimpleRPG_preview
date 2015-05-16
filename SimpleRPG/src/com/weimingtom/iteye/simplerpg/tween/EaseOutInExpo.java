package com.weimingtom.iteye.simplerpg.tween;

// FIXME:easeOutInExpo
public class EaseOutInExpo extends SimpleTweener {
	private EaseInExpo easeInExpo = new EaseInExpo();
	private EaseOutExpo easeOutExpo = new EaseOutExpo();

	@Override
	protected float interpolator(float t, float b, float c, float d) {
		if (t < d / 2)
			return easeOutExpo.interpolator(t * 2, b, c / 2, d);
		return easeInExpo.interpolator((t * 2) - d, b + c / 2, c / 2, d);
	}
}
