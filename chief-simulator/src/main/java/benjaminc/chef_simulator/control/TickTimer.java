package benjaminc.chef_simulator.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import benjaminc.chef_simulator.rooms.Room;

public class TickTimer extends Thread {

	long del;
	long next;
	Room room;
	boolean running;
	long frame;
	int fps;
	
	@SuppressWarnings("unused")
	private List<Long> vals;
	
	private Map<UUID, TickEvent> todo;
	
	public TickTimer(int tps, Room r) {
		super("TickTimer");
		System.out.println("New Tick Timer");
		this.fps = tps;
		del = 1000000000 / this.fps;
		room = r;
		frame = -1;
		next = System.nanoTime() + del;
		running = true;
		System.out.println("Asked to start ticking tps=" + tps + ". Ticking will begin in a moment");
		vals = new ArrayList<Long>();
		todo = new HashMap<UUID, TickEvent>();
		//super.start();
		addToDo(new TickEvent() { @Override public void tick(long frame) {
			if(room != null) { room.tick(frame); } else { System.out.println("[ERROR] Room is NULL!"); } }
			}, UUID.randomUUID());
	}
	
	public void addToDo(TickEvent task, UUID taskUUID) {
		System.out.println("Adding Tick ToDo");
		todo.put(taskUUID, task);
	}
	public void removeToDo(UUID taskUUID) {
		todo.remove(taskUUID);
	}
	
	public void end() {
		running = false;
		System.out.println("Stopping ticker");
	}
	
	private long startTick = 0;
	
	@Override
	public void run() {
		System.out.println("Starting ticker with period " + del + "ns");
		while(running) {
			if(System.nanoTime() - next >= 0) {
//				long dif = System.currentTimeMillis() - (next - del);
				//vals.add(dif);
				
				long laststart = startTick;
				startTick = System.nanoTime();
				long ttime = startTick - laststart;
				
				room.setTps(1/((double) ttime/1000000000));
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
					long behind = next;
					int dropCount = 0;
					while(next < System.nanoTime()) {
						next = next + del;
						frame++;
						dropCount++;
					}
					
					if(dropCount > 0) {
						room.dropedFrame(dropCount);
						System.out.println("Can't keep up! Running " + (double)((long)(System.nanoTime()-behind)/1000)/1000 + "ms behind. Skipping " + dropCount + " ticks");
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
