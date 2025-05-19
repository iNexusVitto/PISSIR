
public class MessageHandler implements MyMessageHandler{

	private ServiceManager sm;
	
	MessageHandler(ServiceManager sm)
	{
		this.sm = sm;
	}
	
	@Override
	public void handleMessage(MyMessage message, String topic) {
		 System.out.println("Message arrived. Topic: " + topic + " clientID "+message.getSenderID() +" time " + message.getTimestamp() );
		if(((String)message.getPayload()).equals("id_request"))
		{
			sm.send_id(message.getSenderID());
		}
				
				
	}

	@Override
	public void onConnectionLost(Throwable cause) {
		// TODO Auto-generated method stub
		
	}
	

	
}
