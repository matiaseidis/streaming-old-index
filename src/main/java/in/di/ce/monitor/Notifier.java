package in.di.ce.monitor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Notifier {
	
	private static final String URL_BASE = "http://vivid-light-4117.herokuapp.com/"; 

	public String addUser(String nombre, String ip, int port){
		return sendRequest("user/add/"+nombre+"/"+ip+"/"+port);	
	}
	
	public String addVideo(String id, String fileName, long size){
		return sendRequest("video/add/"+id+"/"+fileName+"/"+size);
	}
	
	public String addCacho(String userId, String videoId, long from, long lenght){
		return sendRequest("cacho/add/"+userId+"/"+videoId+"/"+from+"/"+lenght);
	}
	
	public String listCachos(String videoId){
		return sendRequest("cacho/list/"+videoId);	
	}
	
	public String getGrafo(String videoId){
		return sendRequest("grafo/"+videoId);
	}
	
	private String sendRequest(String urlSuffix) {
		
		StringBuilder sb = new StringBuilder();

		try {
			URL url = new URL(URL_BASE+urlSuffix);
			URLConnection urlConnection = url.openConnection();
			BufferedReader in = new BufferedReader(
					new InputStreamReader(
							urlConnection.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null){ 
				sb.append(inputLine);
			}
			in.close();
			
		} catch ( IOException e) {
			e.printStackTrace();
		} 
		return sb.toString();
	}
	
}
