import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LocalConfigHandler {

	public  String conf_file_name;
	public  String UID;
    public  String Topic;

		    
		    public LocalConfigHandler(String file_name)
		    {
		    	conf_file_name = file_name;
		    }
		    
		    public  void save_configuration() {
		    
		        // File di destinazione
		        File file = new File(conf_file_name);
		    	
		    	 // Oggetto da salvare
		    	ObjectMapper mapper = new ObjectMapper();
		        ConfigData configToSave = new ConfigData(UID, Topic);
		        
	            // 1. Scrittura su file JSON
	            try {
					mapper.writerWithDefaultPrettyPrinter().writeValue(file, configToSave);
				} catch (StreamWriteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (DatabindException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            System.out.println("✅ Config salvata in config.json");
		    }
		    
		    public boolean load_configuration() {
		      

		        // File di destinazione
		        File file = new File(conf_file_name);
		        
		        if(!file.exists())
		        	return false;

		        ObjectMapper mapper = new ObjectMapper();

		        try {
		            // 2. Lettura da file JSON
		        	ConfigData loadedConfig = mapper.readValue(file, ConfigData.class);
		            System.out.println("✅ Config letta da file");

		            // 3. Inizializzazione variabili
		            UID = loadedConfig.ID;
		            Topic = loadedConfig.Topic;

		            System.out.println("📄 ID: " + UID);
		            System.out.println("📄 Topic: " + Topic);
		            
		            

		        } catch (IOException e) {
		            e.printStackTrace();
		            return false;
		        }
		        return true;
		    }
		}