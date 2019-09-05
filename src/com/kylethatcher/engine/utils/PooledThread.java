package com.kylethatcher.engine.utils;

public class PooledThread extends Thread{

	private static IDAssigner poolID = new IDAssigner(1); 
	private ThreadPool pool;
	
	public PooledThread(ThreadPool pool) {
		super(pool, "Pooled Thread - " + poolID.next());
		this.pool = pool;
	}
	
	public void run() {
		while(!isInterrupted()) {
			Runnable task = null;
			try {
				task = pool.getTask();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(task == null) {
				return;
			}
			try {
				task.run();
			} catch(Throwable t) {
				pool.uncaughtException(this, t);
			}
		}
	}
}
