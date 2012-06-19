package org.test;

import in.di.ce.Tracking;
import in.di.ce.UserChunks;
import in.di.ce.service.PlanService;
import in.di.ce.service.VideoService;
import in.di.ce.service.rta.Respuesta;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;


public class RetrievalPlanTest {
	
	String videoId = "test-video-id";
	String fileName = "test-file-name";
	long lenght = 1024*1024;
	String userId_1 = "test-user-id-1";
	String userId_2 = "test-user-id-1";
	String chunks = "chunk_id_0,chunk_id_1,chunk_id_2,chunk_id_3,chunk_id_4,chunk_id_5,chunk_id_6,chunk_id_7,chunk_id_8,chunk_id_9";
	
	String chunksForRegisterUser1 = "0-chunk_id_0|1-chunk_id_1|2-chunk_id_2|3-chunk_id_3|4-chunk_id_4";
	String chunksForRegisterUser2 = "5-chunk_id_5|6-chunk_id_6|7-chunk_id_7|8-chunk_id_8|9-chunk_id_9";
	
	
	@Test
	public void testRestrievalPlan(){

//		/*
//		 * servicio
//		 */
//		Tracking tracking = new Tracking();
//		VideoService videoService = new VideoService();
//		videoService.setTracking(tracking);
//		
//		/*
//		 * alta de video
//		 */
//		boolean registered = videoService.registerVideo(videoId, fileName, lenght, chunks, userId_1).getBody();
//		Assert.assertTrue(registered);
//		
//		Assert.assertTrue(videoService.registerChunks(videoId, userId_1, chunksForRegisterUser1).getBody());
//		
//		/*
//		 * register cachos user 1
//		 */
//		Respuesta<List<Integer>> chunks_1 = videoService.getChunksFrom(videoId, userId_1);
//		
//		
//		Assert.assertTrue(chunks_1.getBody().contains(0));
//		Assert.assertTrue(chunks_1.getBody().contains(1));
//		Assert.assertTrue(chunks_1.getBody().contains(2));
//		Assert.assertTrue(chunks_1.getBody().contains(3));
//		Assert.assertTrue(chunks_1.getBody().contains(4));
//		Assert.assertTrue(chunks_1.getBody().size() == 5);
//
//		Assert.assertTrue(videoService.registerChunks(videoId, userId_2, chunksForRegisterUser2).getBody());
//		/*
//		 * register cachos user 2
//		 */
//		Respuesta<List<Integer>> chunks_2 = videoService.getChunksFrom(videoId, userId_2);
//		
//		Assert.assertTrue(chunks_2.getBody().containsAll(chunks_1.getBody()));
//		Assert.assertTrue(chunks_2.getBody().contains(5));
//		Assert.assertTrue(chunks_2.getBody().contains(6));
//		Assert.assertTrue(chunks_2.getBody().contains(7));
//		Assert.assertTrue(chunks_2.getBody().contains(8));
//		Assert.assertTrue(chunks_2.getBody().contains(9));
//		Assert.assertTrue(chunks_2.getBody().size() == 10);
//		
//		Respuesta<List<Integer>> chunks = videoService.getChunksFrom(videoId, userId_1);
//		Assert.assertNotNull(chunks);
//		Assert.assertNotNull(chunks.getBody());
//		
//		/*
//		 * plan service
//		 */
//		PlanService planService = new PlanService();
//		planService.setTracking(tracking);
//		
//		/*
//		 * retrieve plan
//		 */
//		Respuesta<List<UserChunks>> retrievalPlanResponse = planService.getRetrievalPlan(videoId);
//		List<UserChunks> retrievalPlan = retrievalPlanResponse.getBody();
//		
//		Assert.assertNotNull(retrievalPlan);
//		Assert.assertNotNull(retrievalPlan.get(0).getUserId());
//		Assert.assertNotNull(retrievalPlan.get(0).getChunks().get(0));
	}

}
