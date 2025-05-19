import com.fasterxml.jackson.annotation.JsonProperty;

public class MyMessage {

	@JsonProperty("senderID")
	String senderID;
	
	@JsonProperty("timestamp")
	String timestamp;
	
	@JsonProperty("payload")
	private Object payload;
	
	public MyMessage(Object message)
	{
		this.payload = message;
	}
	public MyMessage()
	{
		this.payload = null;
	}
	
	public String getSenderID()
	{
		return senderID;
	}
	
	public Object getPayload()
	{
		return payload;
	}
	
	public String getTimestamp()
	{
		return timestamp;
	}
}
