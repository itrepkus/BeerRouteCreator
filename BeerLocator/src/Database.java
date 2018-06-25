import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
	public static void main(String[] args) {

	}

	public static void ReturnDataFromDatabase() {
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/beering?useSSL=false", "myuser",
				"password"); Statement stmt = conn.createStatement();) {
			String strSelect = "Select geo.latitude, geo.longitude, geo.brewery_id, brew.name\r\n"
					+ "From geocodes as geo inner join breweries as brew on geo.brewery_id=brew.id";
			ResultSet rset = stmt.executeQuery(strSelect);
			while (rset.next()) {
				double latitude = rset.getDouble("latitude");
				double longitude = rset.getDouble("longitude");
				int brewery_id = rset.getInt("brewery_id");
				String brewery_name = rset.getString("name");
				Brewerie BrewerieForList = new Brewerie(latitude, longitude, brewery_id, brewery_name);
				if (BrewerieForList.distanceToHome < Home.flyingDistance / 2) {
					Main.setBrewerieToList(BrewerieForList);
				}

			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

	}

}
