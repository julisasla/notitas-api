package JSON;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

public class JSONParser<Entidad> {		 
	
	ObjectMapper mapper = new ObjectMapper().registerModule(new ParameterNamesModule()).registerModule(new Jdk8Module()).registerModule(new JavaTimeModule());
	
	public Entidad jsonToObject(String json, Class<Entidad> tipoEntidad) {
		
		Entidad elemento = null;
		
		try {
			elemento = mapper.readValue(json, tipoEntidad);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return elemento;
	}
	
	public String objectToJson(Entidad entidad) {

		String json = ""; 
		
		try {
			json = mapper.writeValueAsString(entidad);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return json;
	}
	
	public String listToJson(List<Entidad> entidades) {

		String json = ""; 
		
		try {
			json = mapper.writeValueAsString(entidades);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return json;
	}
}