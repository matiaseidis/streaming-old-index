package org.test;

import in.di.ce.RetrievalPlan;
import in.di.ce.Tracking;
import in.di.ce.UserCacho;
import in.di.ce.Video;
import in.di.ce.service.VideoService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;


public class RetrievalPlanTest {
	
	private String videoId = "test-video-id";
	private String fileName = "test-file-name";

	private long chunkSize = 1024*1024;
	private long lastChunkSize = 450;//bytes
	private long videoLenght = chunkSize *400 + lastChunkSize;
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
		registerChunks(userId_4, 300, 399, tracking);
		
		/*
		 * retrieve plan
		 */
		RetrievalPlan retrievalPlan = tracking.grafo(videoId, userId_1);
		
		Assert.assertNotNull(retrievalPlan);
		List<UserCacho> userCachos = retrievalPlan.getUserCachos(); 

		Assert.assertEquals(chunkSize*5, userCachos.get(0).getCacho().getLenght());
		Assert.assertEquals(chunkSize*5, userCachos.get(1).getCacho().getLenght());
		Assert.assertEquals(lastChunkSize, userCachos.get(2).getCacho().getLenght());
		
		Assert.assertTrue(retrievalPlan.getUserCachos().get(0).getUser().getId().equals(userId_1));
		Assert.assertTrue(retrievalPlan.getUserCachos().get(1).getUser().getId().equals(userId_2));
		Assert.assertTrue(retrievalPlan.getUserCachos().get(2).getUser().getId().equals(userId_3));
		Assert.assertTrue(retrievalPlan.getUserCachos().get(3).getUser().getId().equals(userId_4));
		Assert.assertTrue(retrievalPlan.getUserCachos().size() == 4);
		
		/*
		 * registro el mismo chunk que registre con el user 4, con el user 1, que es el que pide el plan.
		 * El plan deberia ignorar al user 4
		 */
		registerChunks(userId_1, 300, 399, tracking);
		/*
		 * retrieve plan
		 */
		retrievalPlan = tracking.grafo(videoId, userId_1);
		
		Assert.assertNotNull(retrievalPlan);
		Assert.assertTrue(retrievalPlan.getUserCachos().get(0).getUser().getId().equals(userId_1));
		Assert.assertTrue(retrievalPlan.getUserCachos().get(1).getUser().getId().equals(userId_2));
		Assert.assertTrue(retrievalPlan.getUserCachos().get(2).getUser().getId().equals(userId_3));
		Assert.assertTrue(retrievalPlan.getUserCachos().get(3).getUser().getId().equals(userId_1));
		Assert.assertTrue(retrievalPlan.getUserCachos().size() == 4);
	}
	
	
	private void registerChunks(String userId, int from, int to, Tracking tracking) {

		StringBuilder sb = new StringBuilder();
		for(int i = from; i<=to; i++){
			sb.append(""+i+chunkSeparator+i+chunkForRegisterSeparator);
		}
		String chunksForRegisterByUser = sb.toString();
		
		System.out.println("Chunks from "+from+" to "+to+" for user "+userId+": "+chunksForRegisterByUser);
		Assert.assertTrue(tracking.registerChunks(videoId, userId, chunkOrdinalsForExistentVideo(tracking, chunksForRegisterByUser)));
		
		/*
		 * load user cachos   
		 */
		List<Integer> chunks = tracking.getChunksFrom(videoId, userId);
		
		for(int i = from; i<=to; i++){
			Assert.assertTrue(chunks.contains(i));
		}
		Assert.assertEquals(chunks.size(), (to-from)+1);
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
		boolean registered = tracking.registerVideo(video);
		Assert.assertTrue(registered);
	}


	private List<Integer> chunkOrdinalsForExistentVideo(Tracking tracking, String chunks) {
		return new VideoService().chunkOrdinalsForExistentVideo(tracking, videoId, chunks);
	}

}
