package com.weimingtom.iteye.simplerpg.tween;

public class EaseInOutElastic extends SimpleTweener {
	private double amplitude;
	private double period;

	public EaseInOutElastic(double period, double amplitude) {
		this.period = period;
		this.amplitude = amplitude;
	}

	@Override
	protected float interpolator(float t, float b, float c, float d) {
		if (t == 0)
			return b;
		t /= d / 2;
		if (t == 2)
			return b + c;
		double p = Double.isNaN(this.period) ? d * (0.3 * 1.5)
				: this.period;
		double s;
		double a = Double.isNaN(this.amplitude) ? 0 : this.amplitude;
		if (a == 0 || a < Math.abs(c)) {
			a = c;
			s = p / 4;
		} else {
			s = (p / (2 * Math.PI) * Math.asin(c / a));
		}
		if (t < 1) {
			t -= 1;
			return -0.5f
					* ((float) a * (float) Math.pow(2, 10 * t) * (float) Math
							.sin((t * d - s) * (2 * Math.PI) / p)) + b;
		}
		t -= 1;
		return (float) a * (float) Math.pow(2, -10 * t)
				* (float) Math.sin((t * d - s) * (2 * Math.PI) / p) * 0.5f
				+ c + b;
	}
}
