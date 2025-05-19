
public interface MyMessageHandler {

    void handleMessage(MyMessage message, String topic);
    void onConnectionLost(Throwable cause);
}
