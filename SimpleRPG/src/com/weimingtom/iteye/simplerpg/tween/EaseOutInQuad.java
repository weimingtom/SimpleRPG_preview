package com.weimingtom.iteye.simplerpg.tween;

// FIXME:easeOutInQuad
public class EaseOutInQuad extends SimpleTweener {
	private EaseInQuad easeInQuad = new EaseInQuad();
	private EaseOutQuad easeOutQuad = new EaseOutQuad();

	@Override
	protected float interpolator(float t, float b, float c, float d) {
		if (t < d / 2)
			return easeOutQuad.interpolator(t * 2, b, c / 2, d);
		return easeInQuad.interpolator((t * 2) - d, b + c / 2, c / 2, d);
	}
}
