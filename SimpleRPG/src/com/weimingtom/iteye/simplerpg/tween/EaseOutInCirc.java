package com.weimingtom.iteye.simplerpg.tween;

// FIXME: easeOutInCirc
public class EaseOutInCirc extends SimpleTweener {
	private EaseInCirc easeInCirc = new EaseInCirc();
	private EaseOutCirc easeOutCirc = new EaseOutCirc();

	@Override
	protected float interpolator(float t, float b, float c, float d) {
		if (t < d / 2)
			return easeOutCirc.interpolator(t * 2, b, c / 2, d);
		return easeInCirc.interpolator((t * 2) - d, b + c / 2, c / 2, d);
	}
}
