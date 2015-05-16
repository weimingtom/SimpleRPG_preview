package com.weimingtom.iteye.simplerpg.tween;

/**
 * @see http://code.google.com/p/tweener/source/browse/trunk/as3/caurina/transitions/Equations.as
 * @see com.badlogic.gdx.math.Interpolation
 * @author Administrator
 * 
 */
public abstract class SimpleTweener {
	private static double CHECK_RANGE_MIN = 0.001;

	private long currentTime, totoalTime;
	private float x, y, dx, dy, cx, cy;
	private boolean isStarted;
	private boolean enableTimeCheck = true;

	public SimpleTweener() {

	}

	public boolean isTweening() {
		return isStarted;
	}

	public void startTween(float x1, float x2, float y1, float y2, long t) {
		this.x = x1;
		this.y = y1;
		this.dx = x2 - x1;
		this.dy = y2 - y1;
		this.totoalTime = t;
		this.currentTime = 0;
		this.isStarted = true;
	}

	public boolean update() {
		if (isStarted) {
			if (enableTimeCheck) {
				if (this.currentTime > this.totoalTime) {
					this.isStarted = false;
					this.cx = this.cy = 0;
					return false;
				} else if (this.currentTime == this.totoalTime) {
					this.cx = this.x + this.dx;
					this.cy = this.y + this.dy;
					++this.currentTime;
					return true;
				} else {
					this.cx = interpolator(currentTime, x, dx, totoalTime);
					this.cy = interpolator(currentTime, y, dy, totoalTime);
					++this.currentTime;
					return true;
				}
			} else {
				this.cx = interpolator(currentTime, x, dx, totoalTime);
				this.cy = interpolator(currentTime, y, dy, totoalTime);
				if (Math.abs(this.x + this.dx - this.cx) < CHECK_RANGE_MIN
						&& Math.abs(this.y + this.dy - this.cy) < CHECK_RANGE_MIN) {
					this.isStarted = false;
					this.cx = this.x + this.dx;
					this.cy = this.y + this.dy;
					// System.out.println("SimpleTweener update is over...");
					return true;
				} else {
					++this.currentTime;
					return true;
				}
			}
		} else {
			this.cx = this.cy = 0;
			return false;
		}
	}

	public float currentX() {
		return cx;
	}

	public float currentY() {
		return cy;
	}

	public void setEnableTimeCheck(boolean enableTimeCheck) {
		this.enableTimeCheck = enableTimeCheck;
	}

	@Override
	public String toString() {
		return "currentTime = " + currentTime + ", " + "totoalTime = "
				+ totoalTime + ", " + "cx = " + cx + ", " + "cy = " + cy + ", "
				+ "x = " + (x + dx) + ", " + "y = " + (y + dy);
	}

	/*
	 * c*t/d + b
	 */
	protected abstract float interpolator(float t, float b, float c, float d);
}
