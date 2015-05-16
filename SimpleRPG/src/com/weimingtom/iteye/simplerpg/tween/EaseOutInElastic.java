package com.weimingtom.iteye.simplerpg.tween;

// FIXME: easeOutInElastic
public class EaseOutInElastic extends SimpleTweener {
	private EaseInElastic easeInElastic;
	private EaseOutElastic easeOutElastic;

	public EaseOutInElastic(double period, double amplitude) {
		easeInElastic = new EaseInElastic(period, amplitude);
		easeOutElastic = new EaseOutElastic(period, amplitude);
	}

	@Override
	protected float interpolator(float t, float b, float c, float d) {
		if (t < d / 2)
			return easeOutElastic.interpolator(t * 2, b, c / 2, d);
		return easeInElastic.interpolator((t * 2) - d, b + c / 2, c / 2, d);
	}
}
