package com.weimingtom.iteye.simplerpg.tween;

public class EaseInOutSine extends SimpleTweener {
	@Override
	protected float interpolator(float t, float b, float c, float d) {
		return -c / 2 * ((float) Math.cos(Math.PI * t / d) - 1) + b;
	}
}
