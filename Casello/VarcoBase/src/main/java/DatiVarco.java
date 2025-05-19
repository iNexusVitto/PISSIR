import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.databind.ObjectMapper;

public class DatiVarco {

	private final String DEFAULT_BROKER_URL = "tcp://localhost:1883";
	private final String DEFAULT_OUT_TOPIC = "caselli/initrequest";
	private final String DEFAULT_IN_TOPIC = "caselli/init";
	private String topic;
	private String UID;
	private String GUID; // Simula un GUID ovviamente non lo è davvero
	private int machine_number; // in realtà serve solo per l'emulazione
	private int type; // 1 in bilgietto, 2 in telepass, 3 out b, 4 out t

	private Publisher client_out;
	private Subscriber client_in;

	public DatiVarco(int type) throws Exception {
		if (type < 1 || type > 4)
			throw new Exception("Invalid type");
		this.type = type;

		LocalConfigHandler conf_loader = new LocalConfigHandler("config_" + machine_number + ".json");
		if (conf_loader.load_configuration()) {
			topic = conf_loader.Topic;
			UID = conf_loader.UID;
		} else
			request_server_data();

	}

	public void request_server_data() {
		GUID = RngGen.gen();

		ObjectMapper mapper = new ObjectMapper();

		CountDownLatch latch = new CountDownLatch(1);
		client_in = new Subscriber(GUID + "1", DEFAULT_BROKER_URL, (new MyMessageHandler() {

			@Override
			public void handleMessage(MyMessage message, String topic) {
				System.out.println("arrivato messaggio dal server topic:{" + topic + "}");
				if (topic.equals(DEFAULT_IN_TOPIC)) {
					System.out.println("topic match");
					// System.out.println("✅ ID ricevuto: " + new String((String)
					// message.getPayload()));

					String json = new String(message.getPayload());
					MyMessage mymessage = null;
					try {

						mymessage = mapper.readValue(json, MyMessage.class);
					} catch (Exception e) {
						System.err.println("Errore durante deserializzazione: " + e.getClass().getSimpleName() + " - "
								+ e.getMessage());
						e.printStackTrace(); // importante
					}
					latch.countDown(); // sblocca il thread principale
				} else
					System.out.println("topic mismatch");

			}

			@Override
			public void onConnectionLost(Throwable cause) {
				// TODO Auto-generated method stub

			}
		}));

		client_in.sub(DEFAULT_IN_TOPIC);

		client_out = new Publisher(GUID, DEFAULT_BROKER_URL);
		client_out.connect();
		client_out.sendMessage(DEFAULT_OUT_TOPIC, "id_request");

		// Messaggio periodico ogni 2 secondi
		ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
		scheduler.scheduleAtFixedRate(() -> {
			System.out.println("⏳ In attesa dell'assegnazione ID...");
		}, 0, 2, TimeUnit.SECONDS);

		// Attendi al massimo 30 secondi la risposta
		try {
			boolean received = latch.await(30, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		scheduler.shutdownNow();

	}
}
