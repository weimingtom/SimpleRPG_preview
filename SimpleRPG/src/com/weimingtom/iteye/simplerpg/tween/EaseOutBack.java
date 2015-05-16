package com.weimingtom.iteye.simplerpg.tween;

public class EaseOutBack extends SimpleTweener {
	private double overshoot;

	public EaseOutBack(double overshoot) {
		this.overshoot = overshoot;
	}

	@Override
	protected float interpolator(float t, float b, float c, float d) {
		float s = Double.isNaN(overshoot) ? 1.70158f : (float) overshoot;
		t = t / d - 1;
		return c * (t * t * ((s + 1) * t + s) + 1) + b;
	}
}

