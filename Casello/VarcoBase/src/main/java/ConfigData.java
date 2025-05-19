
public class ConfigData {
    // Classe che rappresenta il JSON
        public String ID;
        public String Topic;

        // Costruttori vuoto e pieno (necessari per Jackson)
        public ConfigData() {}

        public ConfigData(String ID, String Topic) {
            this.ID = ID;
            this.Topic = Topic;
        }
    
}
