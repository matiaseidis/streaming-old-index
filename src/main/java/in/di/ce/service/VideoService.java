package in.di.ce.service;

import in.di.ce.error.VideoNoExisteException;
import in.di.ce.prevalence.BaseModel;
import in.di.ce.prevalence.transaction.AddVideo;
import in.di.ce.prevalence.transaction.RemoveVideo;
import in.di.ce.service.rta.Respuesta;
import junit.framework.Assert;
import lombok.Getter;
import lombok.Setter;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("video")
public class VideoService {
	
	public Logger logger = Logger.getLogger(getClass());

	
	@Autowired @Setter @Getter private BaseModel baseModel;
	
	@RequestMapping(value="fileName/{id}", method = RequestMethod.GET)
	public @ResponseBody Respuesta fileNameForId(ModelAndView m, @PathVariable String id) throws VideoNoExisteException{
		
		Assert.assertNotNull( id );

		return new Respuesta(baseModel.getModel().fileNameForId ( id ));
	}
	

	@RequestMapping(value="add/{id}/{fileName}/{size}", method = RequestMethod.GET)
	public @ResponseBody Respuesta add(ModelAndView m, @PathVariable String id, 
			@PathVariable String fileName, @PathVariable Long size) throws Exception{
		
		Assert.assertNotNull( id );
		Assert.assertNotNull( fileName );
		Assert.assertNotNull( size );
		
		
		
		return new Respuesta(baseModel.getPrevayler().execute(new AddVideo(id, fileName, size)));
	}
	
	@RequestMapping(value="remove/{id}", method = RequestMethod.GET)
	public @ResponseBody Respuesta remove(ModelAndView m, @PathVariable String id) throws Exception{
		
		Assert.assertNotNull( id );
		return new Respuesta(baseModel.getPrevayler().execute(new RemoveVideo( id )));
	}
	
	@RequestMapping(value="list", method = RequestMethod.GET)
	public @ResponseBody Respuesta list(ModelAndView m){
		return new Respuesta(baseModel.getModel().listVideos()) ;
	}
	

}
