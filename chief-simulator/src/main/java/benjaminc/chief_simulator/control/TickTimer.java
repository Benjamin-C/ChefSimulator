package benjaminc.chief_simulator.control;

import benjaminc.chief_simulator.graphics.Room;

public class TickTimer extends Thread {

	int del;
	long next;
	Room room;
	boolean running;
	long frame;
	
	public TickTimer(int fps, Room r) {
		super("TickTimer");
		del = 1000 / fps;
		room = r;
		frame = -1;
		next = System.currentTimeMillis() + del;
		super.start();
		running = true;
	}
	
	public void end() {
		running = false;
	}
	
	@Override
	public void run() {
		while(running) {
			if(next <= System.currentTimeMillis()) {
				next = next + del;
				frame++;
				if(room != null) {
					room.tick(frame);
				} else {
					System.out.println("[ERROR] Room is NULL!");
				}
			} else {
				long del = next - System.currentTimeMillis();
				//del = Long.MAX_VALUE;
				System.out.println(del);
				if(del > 1) {
					try {
						Thread.sleep(del);
					} catch (InterruptedException e) { }
				}
			}
		} // End loop
	}
}
