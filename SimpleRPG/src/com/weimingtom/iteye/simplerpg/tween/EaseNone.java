package com.weimingtom.iteye.simplerpg.tween;

public class EaseNone extends SimpleTweener {
	@Override
	protected float interpolator(float t, float b, float c, float d) {
		return c * t / d + b;
	}
}
