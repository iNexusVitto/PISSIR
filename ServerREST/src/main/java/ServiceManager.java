
public class ServiceManager {

	private DatabaseHandler db;
	private Publisher pb;
	int connected_devices;
	
	public ServiceManager()
	{
		db = new DatabaseHandler("databse.db");
		connected_devices = 0;
	}
	
	public void setPublisher(Publisher pb)
	{
		this.pb = pb;
	}
	public void send_id(String id)
	{
		System.out.print("sending id to "+id);
		connected_devices++;
		pb.sendMessage(MQTTManager.DEFAULT_RESPONSE_TOPIC, new ConfigData(""+connected_devices,"caselli/in"));
	}
}
