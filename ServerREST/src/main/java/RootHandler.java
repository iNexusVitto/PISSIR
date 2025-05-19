import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpPrincipal;
import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.*;
import java.util.*;
import java.net.URI;

public class RootHandler implements HttpHandler {
  private int port;
  
  @Override
  public void handle(HttpExchange he) throws IOException {
	String response = "<h1>Server start success if you see this message</h1>" + "<h1>Port: " + port + "</h1>";
	he.sendResponseHeaders(200, response.length());
	OutputStream os = he.getResponseBody();
	os.write(response.getBytes());
	os.close();
  }
  
  RootHandler(int p) {
	port = p;
  }
}
