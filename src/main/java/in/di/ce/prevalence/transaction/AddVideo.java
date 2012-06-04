package in.di.ce.prevalence.transaction;

import in.di.ce.Tracking;

import java.util.Date;

import org.prevayler.TransactionWithQuery;

public class AddVideo implements TransactionWithQuery {
	
	private final String id;
	private final String fileName;
	private final Long size;
	

	public AddVideo(String id, String fileName, Long size) {
		this.id = id;
		this.fileName = fileName;
		this.size = size;
	}

	@Override
	public Object executeAndQuery(Object prevalentSystem, Date executionTime)
			throws Exception {
		Tracking model = (Tracking) prevalentSystem;
		return model.addVideo(id, fileName, size);
	}

	

}
