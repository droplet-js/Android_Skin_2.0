package com.v7lin.android.env.graphics.drawable;

import java.util.concurrent.atomic.AtomicInteger;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;

/**
 * 
 * 
 * @author v7lin E-mail:v7lin@qq.com
 */
public class CompatAnimationDrawable extends AnimationDrawable {

	private AtomicInteger stat = new AtomicInteger(0);
	private int repeatInterval = 0;

	public CompatAnimationDrawable(int animateTimer, int repeatInterval) {
		super();
		if (animateTimer > 0) {
			stat.set(animateTimer);
			super.setOneShot(true);
		} else {
			stat.set(Integer.MAX_VALUE);
			super.setOneShot(true);
		}
		this.repeatInterval = repeatInterval;
	}

	@Deprecated
	@Override
	public void setOneShot(boolean oneShot) {
		super.setOneShot(oneShot);
	}

	@Override
	public int getIntrinsicWidth() {
		int width = super.getIntrinsicWidth();
		if (width == -1 && getCurrent() == null && getNumberOfFrames() > 0) {
			Drawable drawable = getFrame(0);
			width = drawable != null ? drawable.getIntrinsicWidth() : -1;
		}
		return width;
	}

	@Override
	public int getIntrinsicHeight() {
		int height = super.getIntrinsicHeight();
		if (height == -1 && getCurrent() == null && getNumberOfFrames() > 0) {
			Drawable drawable = getFrame(0);
			height = drawable != null ? drawable.getIntrinsicHeight() : -1;
		}
		return height;
	}

	@Override
	public int getMinimumWidth() {
		int width = super.getMinimumWidth();
		if (width == -1 && getCurrent() == null && getNumberOfFrames() > 0) {
			Drawable drawable = getFrame(0);
			width = drawable != null ? drawable.getMinimumWidth() : -1;
		}
		return width;
	}

	@Override
	public int getMinimumHeight() {
		int height = super.getMinimumHeight();
		if (height == -1 && getCurrent() == null && getNumberOfFrames() > 0) {
			Drawable drawable = getFrame(0);
			height = drawable != null ? drawable.getMinimumHeight() : -1;
		}
		return height;
	}

	@Override
	public void scheduleSelf(Runnable what, long when) {
		super.scheduleSelf(new RunableWrapper(what), when);
	}

	class RunableWrapper implements Runnable {

		private Runnable wrapped;

		public RunableWrapper(Runnable wrapped) {
			super();
			this.wrapped = wrapped;
		}

		@Override
		public void run() {
			if (wrapped != null) {
				wrapped.run();
			}
			if (isOneShot()) {
				if (getCurrent() != null && getCurrent().equals(getFrame(getNumberOfFrames() - 1))) {
					long lastDuration = getDuration(getNumberOfFrames() - 1);
					if (stat.decrementAndGet() > 0 && isRunning()) {
						scheduleSelf(wrapped, SystemClock.uptimeMillis() + lastDuration + repeatInterval);
					}
				}
			}
		}
	}
}
