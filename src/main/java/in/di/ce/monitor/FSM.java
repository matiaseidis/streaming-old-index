package in.di.ce.monitor;

import java.io.File;

import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.apache.log4j.Logger;

public class FSM {

	private Notifier notifier;
	public Logger logger = Logger.getLogger(getClass());

	private static final String MONITORED_DIR = "/home/meidis/testFolder";
	private static final long INTERVAL = 3000;

	public void begin() throws Exception {
		notifier = new Notifier();
		File directory = new File(MONITORED_DIR);
		FileAlterationObserver observer = new FileAlterationObserver(directory);
		observer.addListener(new SharedVideosListener(notifier));

		FileAlterationMonitor monitor = new FileAlterationMonitor(INTERVAL);
		monitor.addObserver(observer);
		monitor.start();
	}

	public static void main(String[] args) {
		try {
			new FSM().begin();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

