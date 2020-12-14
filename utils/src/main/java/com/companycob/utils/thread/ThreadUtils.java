package com.companycob.utils.thread;

public final class ThreadUtils {

	private ThreadUtils() { }

	public static void threadSleep(int seconds) {
		ThreadUtils.threadSleep(seconds * 1000L);
	}
	
	public static void threadSleep(long milis) {
		try {
			Thread.sleep(milis);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
}
