package in.di.ce.prevalence.transaction;

import in.di.ce.Tracking;

import java.util.Date;

import org.prevayler.TransactionWithQuery;

public class RemoveVideo implements TransactionWithQuery {

	private final String id;

	public RemoveVideo(String id) {
		this.id = id;
	}

	@Override
	public Object executeAndQuery(Object prevalentSystem, Date executionTime)
			throws Exception {
		Tracking model = (Tracking) prevalentSystem;
		return model.removeVideo(id);
	}

}
