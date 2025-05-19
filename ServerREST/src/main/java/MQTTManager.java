
public class MQTTManager {

	private Subscriber subscriber;
	private Publisher publisher;
	public  String BROKER_URL = "tcp://localhost:1883";
	public static final String DEFAULT_REQUEST_TOPIC = "caselli/initrequest";
	public static final String DEFAULT_RESPONSE_TOPIC = "caselli/init";
	
	public MQTTManager(ServiceManager sm)
	{
		subscriber = new Subscriber("CentralServerSub",BROKER_URL, new MessageHandler(sm));
		subscriber.sub(DEFAULT_REQUEST_TOPIC);
		
		publisher = new Publisher("CentralServerPub", BROKER_URL);
		publisher.connect();
	}
	
	public Publisher getPub()
	{
		return publisher;
	}
}
