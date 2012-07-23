package org.test;

import in.di.ce.RetrievalPlan;
import in.di.ce.Tracking;
import in.di.ce.UserCacho;
import in.di.ce.Video;
import in.di.ce.service.VideoService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;


public class RetrievalPlanTest {
	
	private String videoId = "test-video-id";
	private String fileName = "test-file-name";

	private long chunkSize = 1024*1024;
	private long lastChunkSize = chunkSize/2;//bytes
	private long videoLenght = chunkSize * 400 + lastChunkSize;
	private String chunkSeparator = "!";
	private String chunkForRegisterSeparator = "&";
	
	private String userId_1 = "test-user-id-1";
	private String userId_2 = "test-user-id-2";
	private String userId_3 = "test-user-id-3";
	private String userId_4 = "test-user-id-4";

	String videoChunks;
	
	@Test
	public void testRestrievalPlan(){

		Tracking tracking = new Tracking();
		registerNewVideo(tracking);
		registerChunks(userId_1, 0, 99, tracking);
		registerChunks(userId_2, 100, 199, tracking);
		registerChunks(userId_3, 200, 299, tracking);
		registerChunks(userId_4, 300, 400, tracking);
		
		/*
		 * load user cachos   
		 */
		Set<Integer> chunks = tracking.getChunksFrom(videoId, userId_1);
		Assert.assertEquals(userId_1, chunks.size(), 100);
		
		chunks = tracking.getChunksFrom(videoId, userId_2);
		Assert.assertEquals(userId_2, chunks.size(), 100);
		
		chunks = tracking.getChunksFrom(videoId, userId_3);
		Assert.assertEquals(userId_3, chunks.size(), 100);
		
		chunks = tracking.getChunksFrom(videoId, userId_4);
		Assert.assertEquals(userId_4, chunks.size(), 101);
		
		/*
		 * retrieve plan
		 */
		RetrievalPlan retrievalPlan = tracking.plan(videoId, userId_1);
		
		Assert.assertNotNull(retrievalPlan);
		List<UserCacho> userCachos = retrievalPlan.getUserCachos(); 

		Assert.assertTrue(retrievalPlan.getUserCachos().get(0).getUser().getId().equals(userId_1));
		Assert.assertTrue(retrievalPlan.getUserCachos().get(1).getUser().getId().equals(userId_2));
		Assert.assertTrue(retrievalPlan.getUserCachos().get(2).getUser().getId().equals(userId_3));
		Assert.assertTrue(retrievalPlan.getUserCachos().get(3).getUser().getId().equals(userId_4));
		Assert.assertTrue(retrievalPlan.getUserCachos().size() == 4);

		Assert.assertEquals(chunkSize*100, userCachos.get(0).getCacho().getLenght());
		Assert.assertEquals(chunkSize*100, userCachos.get(1).getCacho().getLenght());
		Assert.assertEquals(chunkSize*100, userCachos.get(2).getCacho().getLenght());
		Assert.assertEquals(chunkSize*100+lastChunkSize, userCachos.get(3).getCacho().getLenght());
		
		/*
		 * registro el mismo chunk que registre con el user 4, con el user 1, que es el que pide el plan.
		 * El plan deberia ignorar al user 4 hasta el ultimo chunk
		 */
		registerChunks(userId_1, 300, 399, tracking);
		
		chunks = tracking.getChunksFrom(videoId, userId_1);
		Assert.assertEquals(userId_1, chunks.size(), 200);
		/*
		 * retrieve plan
		 */
		retrievalPlan = tracking.plan(videoId, userId_1);
		
		Assert.assertNotNull(retrievalPlan);
		Assert.assertEquals(5, retrievalPlan.getUserCachos().size());
		Assert.assertTrue(retrievalPlan.getUserCachos().get(0).getUser().getId().equals(userId_1));
		Assert.assertTrue(retrievalPlan.getUserCachos().get(1).getUser().getId().equals(userId_2));
		Assert.assertTrue(retrievalPlan.getUserCachos().get(2).getUser().getId().equals(userId_3));
		Assert.assertTrue(retrievalPlan.getUserCachos().get(3).getUser().getId().equals(userId_1));
		Assert.assertTrue(retrievalPlan.getUserCachos().get(4).getUser().getId().equals(userId_4));
		
		
		/*
		 * registro el mismo chunk que registre con el user 4, con el user 1, que es el que pide el plan.
		 * El plan deberia ignorar al user 4 por completo
		 */
		registerChunks(userId_1, 400, 400, tracking);
		
		chunks = tracking.getChunksFrom(videoId, userId_1);
		Assert.assertEquals(userId_1, chunks.size(), 201);
		/*
		 * retrieve plan
		 */
		retrievalPlan = tracking.plan(videoId, userId_1);
		
		Assert.assertNotNull(retrievalPlan);
		Assert.assertEquals(4, retrievalPlan.getUserCachos().size());
		Assert.assertTrue(retrievalPlan.getUserCachos().get(0).getUser().getId().equals(userId_1));
		Assert.assertTrue(retrievalPlan.getUserCachos().get(1).getUser().getId().equals(userId_2));
		Assert.assertTrue(retrievalPlan.getUserCachos().get(2).getUser().getId().equals(userId_3));
		Assert.assertTrue(retrievalPlan.getUserCachos().get(3).getUser().getId().equals(userId_1));
		
		/*
		 * unregister
		 */
		unregisterChunks(userId_4, 300, 400, tracking);

		/*
		 * load user cachos   
		 */
		chunks = tracking.getChunksFrom(videoId, userId_4);
		Assert.assertEquals(userId_4, chunks.size(), 0);
	}
	
	
	private void registerChunks(String userId, int from, int to, Tracking tracking) {

		StringBuilder sb = new StringBuilder();
		for(int i = from; i<=to; i++){
			sb.append(""+i+chunkSeparator+i+chunkForRegisterSeparator);
		}
		String chunksForRegisterByUser = sb.toString();
		
		System.out.println("Chunks from "+from+" to "+to+" for user "+userId+": "+chunksForRegisterByUser);
		Assert.assertTrue(tracking.registerChunks(videoId, userId, chunkOrdinalsForExistentVideo(tracking, chunksForRegisterByUser)));
	}
	
	private void unregisterChunks(String userId, int from, int to, Tracking tracking) {

		StringBuilder sb = new StringBuilder();
		for(int i = from; i<=to; i++){
			sb.append(""+i+chunkSeparator+i+chunkForRegisterSeparator);
		}
		String chunksForUnregisterByUser = sb.toString();
		
		System.out.println("Chunks from "+from+" to "+to+" for user "+userId+": "+chunksForUnregisterByUser);
		Assert.assertTrue(tracking.unregisterChunks(videoId, userId, chunkOrdinalsForExistentVideo(tracking, chunksForUnregisterByUser)));
	}


	private void registerNewVideo(Tracking tracking) {
		
		List<String> chunks = new ArrayList<String>();
		for(int i = 0; i<401; i++) {
			chunks.add(Integer.toString(i));
		}
		
		/*
		 * alta de video
		 */
		Video video = new Video(videoId, fileName, videoLenght, chunks, userId_1);
		boolean registered = tracking.registerVideo(video, userId_1, chunks);
		Assert.assertTrue(registered);
	}


	private List<Integer> chunkOrdinalsForExistentVideo(Tracking tracking, String chunks) {
		List<Integer> result = new ArrayList<Integer>(
				new VideoService().chunkOrdinalsForExistentVideo(tracking, videoId, chunks).keySet()
				);
		
		return result;
	}

}
