import java.time.Instant;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Publisher {
    private MqttClient client;
    private String broker_url;
    private String client_id;
    private ObjectMapper mapper;

    public Publisher(String client_id, String broker_url) {

    	this.broker_url = broker_url;
    	this.client_id = client_id;
    	mapper = new ObjectMapper();
    	
        //We have to generate a unique Client id.
        try {

            client = new MqttClient(broker_url, client_id);
 

        } catch (MqttException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    public void setID(String client_id)
    {
    	this.client_id = client_id;
    }
    
    public void setBrokerURL(String broker_url)
    {
    	this.broker_url = broker_url;
    }

    public void connect() {

    	if (client.isConnected()) {
    	    try {
				client.disconnect();
			} catch (MqttException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
        try {
            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(false);
            options.setWill(client.getTopic("caselli/status"), "I'm gone :(".getBytes(), 0, false);

            System.out.println("Publisher is connecting to: "+broker_url);
            client.connect(options);

        } catch (MqttException e) {
            e.printStackTrace();
            System.exit(1);
        } 
        System.out.println("Publisher connected");
    }
    
    public void sendMessage(String topic, Object message)
    {
    	MyMessage mymsg = new MyMessage(message);
    	mymsg.senderID = client_id;
    	
    	mymsg.timestamp = Instant.now().toString();

        // Serializza in JSON
        String json = "";
		try {
			json = mapper.writeValueAsString(mymsg);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		    	
        final MqttTopic tp = client.getTopic(topic);
	
        
        try {
        	tp.publish(new MqttMessage(json.getBytes()));
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


    }
    
}
