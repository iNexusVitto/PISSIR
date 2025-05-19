import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

public class RESTManager {

	private int port = 3000;
	private HttpServer server;
	
	public RESTManager()
	{
		try {
			server=HttpServer.create(new InetSocketAddress(port),0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		server.createContext("/", new RootHandler(port));
		server.setExecutor(null);
	}
	
	public void start()
	{
		server.start();
	}
	
}
