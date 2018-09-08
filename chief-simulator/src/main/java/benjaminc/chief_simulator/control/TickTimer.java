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
				if(System.currentTimeMillis() > next) {
					try {
						Thread.sleep(next - System.currentTimeMillis());
					} catch (InterruptedException e) { }
				}
			}
		} // End loop
	}
}
