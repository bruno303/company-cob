package com.companycob.utils.thread;

public final class ThreadUtils {

	public static void threadSleep(int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException e) {
			//
		}
	}
	
	public static void threadSleep(long milis) {
		try {
			Thread.sleep(milis);
		} catch (InterruptedException e) {
			//
		}
	}
}
