package com.weimingtom.iteye.simplerpg.tween;

public class EaseInElastic extends SimpleTweener {
	private double amplitude;
	private double period;

	public EaseInElastic(double period, double amplitude) {
		this.period = period;
		this.amplitude = amplitude;
	}

	@Override
	protected float interpolator(float t, float b, float c, float d) {
		if (t == 0)
			return b;
		t /= d;
		if (t == 1)
			return b + c;
		double p = Double.isNaN(this.period) ? d * 0.3 : this.period;
		double s;
		double a = Double.isNaN(this.amplitude) ? 0 : this.amplitude;
		if (a == 0 || a < Math.abs(c)) {
			a = c;
			s = p / 4;
		} else {
			s = (p / (2 * Math.PI) * Math.asin(c / a));
		}
		t -= 1;
		return -((float) a * (float) Math.pow(2, 10 * t) * (float) Math
				.sin((t * d - s) * (2 * Math.PI) / p)) + b;
	}
}
