package benjaminc.util;

import java.text.DecimalFormat;
import java.util.Set;

public class Util {

	public static void pause(Object sync) {
//		System.out.println("Pausing thread " + Thread.currentThread().getName());
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
    	String unit = "byte";

    	if(usemeg) {
    		double div = 1048576;
    		total = total / div;
    		max = max / div;
    		free = free / div;
    		unit = "MiB";
    	}
    	
    	DecimalFormat df = new DecimalFormat("#.###");
    	df.setGroupingSize(3);
    	df.setGroupingUsed(true);
    	System.out.println("Used " + df.format(total) + "/" + df.format(max)  + " " + unit);
    	System.out.println(df.format(free) + " " + unit + " free");
    }
    public static void showThreads() {
    	System.out.println("Start thread dump");
    	Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
		Thread[] threadArray = threadSet.toArray(new Thread[threadSet.size()]);
		for(int i = 0; i < threadArray.length; i++) {
			System.out.println("[" + i + "]: " + threadArray[i]);
		}
    }
    public static void printAllStackTraces() {
    	System.out.println("Start stack trace dump -------------------");
    	System.out.println("Current thread: " + Thread.currentThread().getName());
    	for(Thread t : Thread.getAllStackTraces().keySet()) {
			StackTraceElement[] elems = t.getStackTrace();
			System.out.println("Thread " + t.getName());
			for(StackTraceElement e : elems) {
				System.out.println("\t" + e);
			}
		}
    	System.out.println("End stack trace dump ---------------------");
    }
}