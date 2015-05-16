package com.weimingtom.iteye.simplerpg.tween;

// FIXME:easeOutInBack
public class EaseOutInBack extends SimpleTweener {
	private EaseInBack easeInBack;
	private EaseOutBack easeOutBack;

	public EaseOutInBack(double overshoot) {
		easeInBack = new EaseInBack(overshoot);
		easeOutBack = new EaseOutBack(overshoot);
	}

	@Override
	protected float interpolator(float t, float b, float c, float d) {
		if (t < d / 2)
			return easeOutBack.interpolator(t * 2, b, c / 2, d);
		return easeInBack.interpolator((t * 2) - d, b + c / 2, c / 2, d);
	}
}
