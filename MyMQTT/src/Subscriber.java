


import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.util.ArrayList;
import java.util.Date;

public class Subscriber {

	private String broker_url;
    private ArrayList<String> topics;
    private MqttClient mqttClient;

    public Subscriber(String client_id, String broker_url, MyMessageHandler msg_handler) {
	this.broker_url = broker_url;
	topics = new ArrayList<String>();
	
	System.out.println("Subscriber is connecting to: "+broker_url);

        try {
            mqttClient = new MqttClient(broker_url, client_id);
            mqttClient.setCallback(new SubscribeCallback(msg_handler));
            mqttClient.connect();

        } catch (MqttException e) {
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("Subscriber connected");
    }

    public void sub(String topic) {
    	
    	if(topics.contains(topic))
    	{
    		 System.out.println("Subscriber is already listening on "+topic);
    		 return;
    	}
    	
        try {
        	
            mqttClient.subscribe(topic);
            System.out.println("Subscriber is now listening to "+topic);
        } catch (MqttException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }


}
