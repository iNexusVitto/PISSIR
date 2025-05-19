

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.eclipse.paho.client.mqttv3.*;


public class SubscribeCallback implements MqttCallback {

	
	private ObjectMapper mapper;
    private MyMessageHandler handler;

    public SubscribeCallback(MyMessageHandler handler) {
        this.mapper = new ObjectMapper();
        this.handler = handler;
    }


	
    @Override
    public void connectionLost(Throwable cause) {
        //This is called when the connection is lost. We could reconnect here.
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
    	System.out.println("messaggio arrivato, json");
    	String json = new String(message.getPayload());
    	MyMessage mymessage = null;
    	try {
    	
    	mymessage = mapper.readValue(json, MyMessage.class);
    }catch (Exception e) {
        System.err.println("Errore durante deserializzazione: " + e.getClass().getSimpleName() + " - " + e.getMessage());
        e.printStackTrace(); // importante
    }
    	System.out.println("checking");
    	if (handler != null) {
    		System.out.println("handler ok");
            handler.handleMessage(mymessage, topic);
        }
    	else
    	{
    		System.out.print("No handler");
    	}
    	
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        //no-op
    }
}
