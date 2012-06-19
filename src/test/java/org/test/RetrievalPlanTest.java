package org.test;

import in.di.ce.Tracking;
import in.di.ce.UserChunks;
import in.di.ce.Video;
import in.di.ce.service.VideoService;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;


public class RetrievalPlanTest {
	
	String videoId = "test-video-id";
	String fileName = "test-file-name";
	long lenght = 1024*1024;
	String userId_1 = "test-user-id-1";
	String userId_2 = "test-user-id-1";
	String chunks = "chunk_id_0,chunk_id_1,chunk_id_2,chunk_id_3,chunk_id_4,chunk_id_5,chunk_id_6,chunk_id_7,chunk_id_8,chunk_id_9";
	
	String chunksForRegisterUser1 = "0!1!2!3!4";
	String chunksForRegisterUser2 = "5!6!7!8!9";
	
	@Test
	@Ignore
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
		 * register cachos user 2
		 */
		List<Integer> chunks_2 = tracking.getChunksFrom(videoId, userId_2);
		
		Assert.assertTrue(chunks_2.containsAll(chunks_1));
		Assert.assertTrue(chunks_2.contains(5));
		Assert.assertTrue(chunks_2.contains(6));
		Assert.assertTrue(chunks_2.contains(7));
		Assert.assertTrue(chunks_2.contains(8));
		Assert.assertTrue(chunks_2.contains(9));
		Assert.assertTrue(chunks_2.size() == 10);
		
		List<Integer> chunks = tracking.getChunksFrom(videoId, userId_1);
		Assert.assertNotNull(chunks);

		/*
		 * retrieve plan
		 */
		List<UserChunks> retrievalPlan = tracking.grafo(videoId, userId_1);
		
		Assert.assertNotNull(retrievalPlan);
		Assert.assertNotNull(retrievalPlan.get(0).getUserId());
		Assert.assertNotNull(retrievalPlan.get(0).getChunks().get(0));
	}
	
	
	private List<Integer> chunkOrdinalsForExistentVideo(Tracking tracking, String videoId, String chunks) {
		return new VideoService().chunkOrdinalsForExistentVideo(tracking, videoId, chunks);
	}

}
