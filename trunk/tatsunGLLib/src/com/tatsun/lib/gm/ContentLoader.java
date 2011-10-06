package com.tatsun.lib.gm;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.util.Log;

public class ContentLoader {
	public static final int QUEUE_SIZE = 32;
	private static final String TAG = "ContentLoader";
	private ScheduledExecutorService executor;
	private volatile boolean isActive = false;
	private final ArrayBlockingQueue<WorkSet> queue = new ArrayBlockingQueue<WorkSet>(QUEUE_SIZE);
	private Runnable task = new Runnable() {	
		public void run() {
			while(!Thread.currentThread().isInterrupted() && isActive) {
				try {
					WorkSet workSet = queue.take();
					try {
						workSet.future.status = Future.Status.LOADING;
						workSet.work.run();
						workSet.future.status = Future.Status.OK;
					} catch(RuntimeException e) {
						workSet.future.status = Future.Status.ERROR;
						Log.w(TAG, "surface executer RuntimeException. ", e);
					} catch(Exception e) {
						workSet.future.status = Future.Status.ERROR;
						Log.w(TAG, "surface executer Exception. ", e);
					}
					workSet.future.isFinished = true;
				} catch(InterruptedException e) {
					Log.w(TAG, "surface executer InterruptedException. ", e);
				} catch(Exception e) {
					Log.w(TAG, "surface executer Exception. ", e);
				}
			}
			isActive = false;
		}
	};
	
	public ContentLoader() {
		isActive = false;
	}
	
	public Future offer(Runnable work) {
		Future future = new Future();
		WorkSet workSet = new WorkSet();
		workSet.future = future;
		workSet.work = work;
		
		boolean offer = queue.offer(workSet);
		if(offer)
			return future;
		return null;
	}
	
	/**
	 * onSurfaceChanged
	 */
	public void onSurfaceChanged() {
		if(isActive) {
			Log.i(TAG, "is started");
			return;
		}
		isActive = true;
		queue.clear();

		executor = Executors.newSingleThreadScheduledExecutor();
		executor.schedule(task, 0, TimeUnit.MILLISECONDS);
	}
	
	/**
	 * onPause
	 */
	public void onPause() {
		isActive = false;
		
		if(executor != null) {
			executor.shutdown();
			queue.clear();
			executor = null;
		}
	}

	/**
	 * 
	 * @author tatsuhiro_koyama
	 *
	 */
	public static final class Future {
		public static enum Status {
			NONE,
			LOADING,
			OK,
			ERROR,
		}
		public volatile boolean isFinished = false;
		public volatile Status status = Status.NONE;
	}
	
	/**
	 * 
	 * @author tatsuhiro_koyama
	 *
	 */
	private static final class WorkSet {
		public Future future;
		public Runnable work;
	}
}
