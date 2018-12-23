package benjaminc.chief_simulator.control;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;

import benjaminc.chief_simulator.graphics.Room;

public class TickTimer extends Thread {

	int del;
	long next;
	Room room;
	boolean running;
	long frame;
	int fps;
	
	private List<Long> vals;
	
	public TickTimer(int fps, Room r) {
		super("TickTimer");
		this.fps = fps;
		del = 1000 / this.fps;
		room = r;
		frame = -1;
		next = System.currentTimeMillis() + del;
		running = true;
		System.out.println("Ticking");
		vals = new ArrayList<Long>();
		super.start();
	}
	
	public void end() {
		running = false;
	}
	
	@Override
	public void run() {
		while(running) {
			if(next <= System.currentTimeMillis()) {
				long dif = System.currentTimeMillis() - (next - del);
				vals.add(dif);
				if(vals.size() == fps) {
					long tot = 0;
					for(Long l : vals) {
						tot += l;
					}
					Double avg = (double) tot / (double) vals.size();
					room.setFps(1000 / avg);
					vals.clear();
				}
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
				if(del > 1) {
					try {
						Thread.sleep(del);
					} catch (InterruptedException e) { }
				}
			}
		} // End loop
	}
}
