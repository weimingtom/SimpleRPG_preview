package com.weimingtom.iteye.simplerpg.tween;

public class EaseInOutBack extends SimpleTweener {
	private double overshoot;

	public EaseInOutBack(double overshoot) {
		this.overshoot = overshoot;
	}

	@Override
	protected float interpolator(float t, float b, float c, float d) {
		float s = Double.isNaN(overshoot) ? 1.70158f : (float) overshoot;
		t /= d / 2;
		if (t < 1) {
			overshoot *= (1.525);
			return c / 2 * (t * t * ((s + 1) * t - s)) + b;
		}
		t -= 2;
		s *= (1.525);
		return c / 2 * (t * t * ((s + 1) * t + s) + 2) + b;
	}
}
