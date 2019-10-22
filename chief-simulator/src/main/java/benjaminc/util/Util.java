package benjaminc.util;

import java.util.Set;

public class Util {

	public static void pause(Object sync) {
		System.out.println("Pausing a thread");
        synchronized(sync) {
            try {
                sync.wait();
            } catch (InterruptedException e) {
                // Happens if someone interrupts your thread.
            }
        }
    }

    public static void resume(Object sync) {
        synchronized(sync) {
            sync.notify();
        }
    }
    
    public static void showHeapStats(boolean usemeg) {
    	System.out.println("Showing heap stats");
    	Runtime r = Runtime.getRuntime();
    	double total = r.totalMemory();
    	double max = r.maxMemory();
    	double free = r.freeMemory();
    	
    	double div = Math.pow(2, 20);
    	String unit = "byte";
    	
    	if(usemeg) {
    		total = total / div;
    		max = max / div;
    		free = free / div;
    		unit = "MiB";
    	}
    	System.out.println(div + " Used " + total + "/" + max  + " " + unit);
    	System.out.println(free + " " + unit + " free");
    }
    public static void showThreads() {
    	System.out.println("Start thread dump");
    	Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
		Thread[] threadArray = threadSet.toArray(new Thread[threadSet.size()]);
		for(int i = 0; i < threadArray.length; i++) {
			System.out.println("[" + i + "]: " + threadArray[i]);
		}
    }
}