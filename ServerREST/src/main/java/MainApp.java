import java.io.IOException;
import java.net.InetSocketAddress;

public class MainApp {

	public static void main(String[] args) throws IOException {
		
		ServiceManager sm = new ServiceManager();
		RESTManager rs = new RESTManager();
		MQTTManager mqttm = new MQTTManager(sm);
		rs.start();
		sm.setPublisher(mqttm.getPub());
	}
}