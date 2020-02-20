package dev.benjaminc.chef_simulator.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import dev.benjaminc.chef_simulator.Game;
import dev.benjaminc.util.Util;

public class TickTimer extends Thread {

	long del;
	long next;
	boolean running;
	long frame;
	int fps;
	boolean paused;
	Object pausesyncobj;
	private long startTick = 0;
	boolean invalidFrameTime = false;
	
	@SuppressWarnings("unused")
	private List<Long> vals;
	
	private Map<UUID, TickEvent> todo;
	
	/**
	 * Makes a {@link TickTimer}. You must call {@link TickTimer#start()} to start ticking
	 * @param tps
	 */
	public TickTimer(int tps) {
		super("TickTimer");
		paused = false;
		pausesyncobj = new Object();
		System.out.println("New Tick Timer");
		this.fps = tps;
		del = 1000000000 / this.fps;
		frame = -1;
		next = System.nanoTime() + del;
		running = true;
		System.out.println("Asked to start ticking tps=" + tps + ". Ticking will begin in a moment");
		vals = new ArrayList<Long>();
		todo = new HashMap<UUID, TickEvent>();
	}
	
	/**
	 * Adds a {@link TickEvent} to do on each tick
	 * @param task the {@link TickEvent} to do
	 * @param taskUUID the {@link UUID} of the TickEvent
	 */
	public void addToDo(TickEvent task, UUID taskUUID) {
		System.out.println("Adding Tick ToDo");
		todo.put(taskUUID, task);
	}
	/**
	 * Removes a TickEvent
	 * @param taskUUID the {@link UUID} of the {@link TickEvent} to remove
	 */
	public void removeToDo(UUID taskUUID) {
		todo.remove(taskUUID);
	}
	
	/**
	 * Asks the {@link TickTimer} to stop. The timer will stop on its next tick.
	 */
	public void end() {
		running = false;
		System.out.println("Stopping ticker");
	}
	
	/**
	 * Sets a flag to ask the {@link TickTimer} to pause. The timer will pause on its next tick
	 */
	public void pause() {
		paused = true;
	}
	/**
	 * Unpauses the {@link TickTimer}
	 */
	public void unpause() {
		paused = false;
		Util.resume(pausesyncobj);
	}
	/**
	 * Gets the pause state of the TickTimer
	 * @return the boolean paused state
	 */
	public boolean getPaused() {
		return paused;
	}
	
	@Override
	public void run() {
		System.out.println("Starting ticker with period " + del + "ns");
		while(running) {
			if(paused) {
				Util.pause(pausesyncobj);
				invalidFrameTime = true;
				next = System.nanoTime();
			}
			if(System.nanoTime() - next >= 0) {
//				long dif = System.currentTimeMillis() - (next - del);
				//vals.add(dif);
				
				long laststart = startTick;
				startTick = System.nanoTime();
				long ttime = startTick - laststart;
				
				if(!invalidFrameTime) {
					Game.setObservedTps(1/((double) ttime/1000000000));
				} else {
					invalidFrameTime = false;
				}
//				if(vals.size() == fps/10) {
//					long tot = 0;
//					for(Long l : vals) {
//						tot += l;
//					}
//					Double avg = (double) tot / (double) vals.size();
//					room.setFps(1000 / avg);
//					vals.clear();
//				}
				
				long startticks = System.nanoTime();
				
				for(TickEvent t : todo.values()) {
					t.tick(frame);
				}
				
				long tickstime = System.nanoTime() - startticks;
				
				next = next + del;
				frame++;
				
				if(tickstime > del) {
					System.out.println("Next next frame is also in the past");
					long behind = next;
					int dropCount = 0;
					while(next < System.nanoTime()) {
						next = next + del;
						frame++;
						dropCount++;
					}
					
					if(dropCount > 0) {
						Game.droppedFrameCount += dropCount;
						System.out.println("Can't keep up! Running " + (double)((long)(System.nanoTime()-behind)/1000)/1000 + "ms behind. Skipping " + dropCount + " ticks");
						Game.chat("Can't keep up! Running " + (double)((long)(System.nanoTime()-behind)/1000)/1000 + "ms behind. Skipping " + dropCount + " ticks");
					}
				}
				
			} else {
				long del = next - System.nanoTime();
				//del = Long.MAX_VALUE;
				if(del > 1) {
					try {
						long ms = del / 1000000;
						int ns = (int) (del % 1000000);
						Thread.sleep(ms, ns);
//						Thread.sleep(del);
					} catch (InterruptedException e) { System.out.println("Tick sleep interrupted"); }
				}
			}
		} // End loop
		System.out.println("Timer has stopped");
	}
}
