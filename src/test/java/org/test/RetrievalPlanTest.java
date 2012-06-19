package org.test;

import in.di.ce.Tracking;
import in.di.ce.UserChunks;
import in.di.ce.Video;
import in.di.ce.service.VideoService;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;


public class RetrievalPlanTest {
	
	private String videoId = "test-video-id";
	private String fileName = "test-file-name";
	private long lenght = 1024*1024;
	private String userId_1 = "test-user-id-1";
	private String userId_2 = "test-user-id-2";
	private String userId_3 = "test-user-id-3";

	String chunks = "chunk_id_0!chunk_id_1!chunk_id_2!chunk_id_3!chunk_id_4!chunk_id_5!chunk_id_6!chunk_id_7!chunk_id_8!chunk_id_9!chunk_id_10";
	
	String chunksForRegisterUser1 = "0!chunk_id_0&1!chunk_id_1&2!chunk_id_2&3!chunk_id_3&4!chunk_id_4";
	String chunksForRegisterUser2 = "5!chunk_id_5&6!chunk_id_6&7!chunk_id_7&8!chunk_id_8&9!chunk_id_9";
	String chunksForRegisterUser3 = "10!chunk_id_10";
	
	@Test
	public void testRestrievalPlan(){

		/*
		 * servicio
		 */
		Tracking tracking = new Tracking();
		
		/*
		 * alta de video
		 */
		Video video = new Video(videoId, fileName, lenght, Arrays.asList(chunks.split("!")), userId_1);
		boolean registered = tracking.registerVideo(video);
		Assert.assertTrue(registered);
	
		Assert.assertTrue(tracking.registerChunks(videoId, userId_1, chunkOrdinalsForExistentVideo(tracking, videoId, chunksForRegisterUser1)));
		
		/*
		 * register cachos user 1
		 */
		List<Integer> chunks_1 = tracking.getChunksFrom(videoId, userId_1);
		
		
		Assert.assertTrue(chunks_1.contains(0));
		Assert.assertTrue(chunks_1.contains(1));
		Assert.assertTrue(chunks_1.contains(2));
		Assert.assertTrue(chunks_1.contains(3));
		Assert.assertTrue(chunks_1.contains(4));
		Assert.assertTrue(chunks_1.size() == 5);

		Assert.assertTrue(tracking.registerChunks(videoId, userId_2, chunkOrdinalsForExistentVideo(tracking, videoId, chunksForRegisterUser2)));
		/*
		 * cachos registered by user 2
		 */
		List<Integer> chunks_2 = tracking.getChunksFrom(videoId, userId_2);
		
		Assert.assertTrue(chunks_2.contains(5));
		Assert.assertTrue(chunks_2.contains(6));
		Assert.assertTrue(chunks_2.contains(7));
		Assert.assertTrue(chunks_2.contains(8));
		Assert.assertTrue(chunks_2.contains(9));
		Assert.assertTrue(chunks_2.size() == 5);
		
		Assert.assertTrue(tracking.registerChunks(videoId, userId_3, chunkOrdinalsForExistentVideo(tracking, videoId, chunksForRegisterUser3)));
		/*
		 * cachos registered by user 3
		 */
		List<Integer> chunks_3 = tracking.getChunksFrom(videoId, userId_3);
		
		Assert.assertTrue(chunks_3.contains(10));
		Assert.assertTrue(chunks_3.size() == 1);
		
		
		/*
		 * retrieve plan
		 */
		List<UserChunks> retrievalPlan = tracking.grafo(videoId, userId_1);
		
		Assert.assertNotNull(retrievalPlan);
		Assert.assertTrue(retrievalPlan.get(0).getUserId().equals(userId_1));
		Assert.assertTrue(retrievalPlan.get(1).getUserId().equals(userId_2));
		Assert.assertTrue(retrievalPlan.get(2).getUserId().equals(userId_3));
		Assert.assertTrue(retrievalPlan.size() == 3);
		
		/*
		 * registro el mismo chunk que registre con el user 3, con el user 1, que es el que pide el plan.
		 * El plan deberia ignorar al user 3
		 */
		Assert.assertTrue(tracking.registerChunks(videoId, userId_1, chunkOrdinalsForExistentVideo(tracking, videoId, chunksForRegisterUser3)));
		/*
		 * retrieve plan
		 */
		retrievalPlan = tracking.grafo(videoId, userId_1);
		
		Assert.assertNotNull(retrievalPlan);
		Assert.assertTrue(retrievalPlan.get(0).getUserId().equals(userId_1));
		Assert.assertTrue(retrievalPlan.get(1).getUserId().equals(userId_2));
		Assert.assertTrue(retrievalPlan.get(2).getUserId().equals(userId_1));
		Assert.assertTrue(retrievalPlan.size() == 3);
	}
	
	
	private List<Integer> chunkOrdinalsForExistentVideo(Tracking tracking, String videoId, String chunks) {
		return new VideoService().chunkOrdinalsForExistentVideo(tracking, videoId, chunks);
	}

}
