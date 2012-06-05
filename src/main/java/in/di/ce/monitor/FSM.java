package in.di.ce.monitor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public class FSM {
	
	private Notifier notifier = new Notifier();
	
	private static final String MONITORED_DIR = "/home/meidis/testFolder";
	private static final long INTERVAL = 3000;
	
	private static String userId = "EXAMPLE_USER";
	
	public static void main(String[] args) {
		try {
			new FSM().begin();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void begin() throws Exception {
	File directory = new File(MONITORED_DIR);
	FileAlterationObserver observer = new FileAlterationObserver(directory);
	observer.addListener(new FileAlterationListener() {

	    // here you have to implement a couple of methods and especially this one:
	    public void onDirectoryDelete(File directory) {
	        // do your processing here
	    }

		@Override
		public void onStart(FileAlterationObserver observer) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onDirectoryCreate(File directory) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onDirectoryChange(File directory) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onFileCreate(File file) {
			
			File dest = null;
			
			if(file.getName().contains(" ")) {
				dest = new File(org.apache.commons.lang.StringUtils.replace(file.getAbsolutePath(), " ", "_"));
				if ( !file.renameTo(dest) ){
					throw new RuntimeException("no puedo renombrar");
				}
				file= dest;
			}
			
			String videoId = md5(file);
			
			if( file.getName().endsWith(".part")) {
				String[] meta = file.getName().split(".");
				
				Assert.isTrue(meta.length == 3);
				
				String[] splittedMeta = meta[1].split("-");
				
				Assert.isTrue(splittedMeta.length == 2);

				notifier.addCacho(userId, videoId, Long.valueOf(splittedMeta[0]), Long.valueOf(splittedMeta[1]));
			} else {
				notifier.addVideo(videoId, file.getName(), file.length());
			}
			System.err.println();
			System.out.println("cachos:");
			System.out.println(notifier.listCachos(videoId));
			System.out.println("videos:");
			System.out.println(notifier.listVideos(videoId));
			
		}

		private String md5(File file) {
			
			byte[] bytes = null;
			try {
				bytes = IOUtils.toByteArray(new FileInputStream(file));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			//new byte[(int) file.length()]
			
			MessageDigest md = null;
			try {
				md = MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			md.reset();
			md.update(bytes);
			byte[] thedigest = md.digest();
			
			BigInteger bigInt = new BigInteger(1,thedigest);
			String hashtext = bigInt.toString(16);
			// Now we need to zero pad it if you actually want the full 32 chars.
			while(hashtext.length() < 32 ){
			  hashtext = "0"+hashtext;
			}
			System.out.println(hashtext);
			return hashtext;
		}

		@Override
		public void onFileChange(File file) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onFileDelete(File file) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onStop(FileAlterationObserver observer) {
			// TODO Auto-generated method stub
			
		}
	});

	FileAlterationMonitor monitor = new FileAlterationMonitor(INTERVAL);
	monitor.addObserver(observer);
	monitor.start();
	}
}
