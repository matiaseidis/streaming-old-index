package in.di.ce.service;

import in.di.ce.Tracking;
import in.di.ce.UserChunks;
import in.di.ce.error.VideoNoExisteException;
import in.di.ce.prevalence.BaseModel;
import in.di.ce.service.rta.Respuesta;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("plan")
public class PlanService {


	private static final Log log = LogFactory.getLog(PlanService.class); 

	@Autowired @Setter @Getter private Tracking tracking;

	@Autowired @Setter @Getter private BaseModel baseModel;

	@RequestMapping(value="{videoId}", method = RequestMethod.GET)
	public Respuesta<List<UserChunks>> getRetrievalPlan(@PathVariable String videoId){
		log.info("returning grafo for video: " + videoId);
		return new Respuesta<List<UserChunks>>(tracking.grafo(videoId));
	}

}
