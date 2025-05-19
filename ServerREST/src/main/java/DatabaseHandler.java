import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseHandler {

	private Connection conn = null;
	private String db_path;
	
	public DatabaseHandler(String db_path)
	{
		this.db_path = db_path;
	}
	
	public void connect() {

		try {
			// 1. Connessione al DB (verrà creato se non esiste)
			String url = "jdbc:sqlite:"+db_path;
			conn = DriverManager.getConnection(url);

			// 2. Crea una tabella
			/*
			 * Statement stmt = conn.createStatement(); String sqlCreate =
			 * "CREATE TABLE IF NOT EXISTS veicoli (" + "targa TEXT PRIMARY KEY," +
			 * "orario_entrata TEXT" + ")"; stmt.execute(sqlCreate);
			 * 
			 * // 3. Inserisci un record String sqlInsert =
			 * "INSERT INTO veicoli(targa, orario_entrata) VALUES ('AB123CD', '2025-05-18T14:00')"
			 * ; stmt.execute(sqlInsert);
			 * 
			 * // 4. Leggi i dati ResultSet rs = stmt.executeQuery("SELECT * FROM veicoli");
			 * while (rs.next()) { System.out.println("Targa: " + rs.getString("targa") +
			 * ", Orario: " + rs.getString("orario_entrata")); }
			 * 
			 * 
			 * stmt.close();
			 * 
			 */

		} catch (Exception e) {
			System.out.println("Errore: " + e.getMessage());
		}
	}

	public void disconnect() {
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
