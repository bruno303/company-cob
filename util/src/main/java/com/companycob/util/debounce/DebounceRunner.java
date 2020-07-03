package com.companycob.util.debounce;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class DebounceRunner {

	private final Timer timer;
	private Object lock;

	public DebounceRunner() {
		this.timer = new Timer();
		this.lock = new Object();
	}

	public void run(Runnable debounceFunction, int secondsDelay) {
		synchronized (lock) {

			timer.cancel();

			var miliseconds = secondsDelay * 1000;
			var startDate = Date.from(LocalDateTime.now().plusNanos(miliseconds).toInstant(ZoneOffset.UTC));

			timer.scheduleAtFixedRate(new TimerTask() {

				@Override
				public void run() {
					debounceFunction.run();
				}
			}, startDate, miliseconds);
			
		}
	}

}
