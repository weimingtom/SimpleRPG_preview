package com.weimingtom.iteye.simplerpg.tween;

public class EaseGeometric extends SimpleTweener {
	private double ratio;

	public EaseGeometric(double ratio, boolean enableTimeCheck) {
		this.ratio = ratio;
		setEnableTimeCheck(enableTimeCheck);
	}

	@Override
	protected float interpolator(float t, float b, float c, float d) {
		double r = Double.isNaN(ratio) ? 0.5 : ratio;
		return b + c * (float) (r * (1.0 - Math.pow((1 - r), t)) / r);
	}
}
