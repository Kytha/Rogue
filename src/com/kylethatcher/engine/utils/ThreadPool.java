package com.kylethatcher.engine.utils;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool extends ThreadGroup {

	private boolean isAlive;
	private List<Runnable> taskQueue;
	private static IDAssigner poolID = new IDAssigner(1); 
	private int id;
	
	public ThreadPool(int numThreads) {
		super("threadPool-" + poolID.next());
		this.id = poolID.getID();
		setDaemon(true);
		taskQueue = new LinkedList<Runnable>();
		isAlive = true;
		for(int i = 0; i < numThreads; i ++) {
			new PooledThread(this).start(); 
		}
	}
	
	
	public synchronized void runTask(Runnable task) {
		if(!isAlive) throw new IllegalStateException("ThreadPool- " + id + " is DeadPool");
		if(task != null) {
			taskQueue.add(task);
			notify();
		}
	}
	
	public synchronized void close() {
		if(!isAlive) return;
		isAlive = false;
		taskQueue.clear();
		interrupt();
	}
	
	public void join() {
		synchronized(this) {
			isAlive = false;
			notifyAll();
		}
		
		Thread[] threads = new Thread[activeCount()]; 
		int count = enumerate(threads); 
		for(int i =0; i < count; i ++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	protected synchronized Runnable getTask() throws InterruptedException {
		while(taskQueue.size() == 0) {
			if(!isAlive) {
				return null;
			}
			wait();
		}
		return taskQueue.remove(0);
	}
}
