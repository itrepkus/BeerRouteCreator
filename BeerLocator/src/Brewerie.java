import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

// Class for breweries that goes into list

public class Brewerie {
	double latitude;
	double longitude;
	int id;
	String name;
	int distanceToHome;
	List<Beer> beerList = new ArrayList<Beer>();

	public Brewerie(double latitudeSet, double longitudeSet, int idSet, String nameSet) {
		latitude = latitudeSet;
		longitude = longitudeSet;
		id = idSet;
		name = nameSet;
		distanceToHome = (int) Haversine.distance(latitude, longitude, Home.getLatitude(), Home.getLongitude());

		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/beering?useSSL=false", "myuser",
				"password"); Statement stmt = conn.createStatement();

		) {
			String strSelect = ("Select name from beers where brewery_id=" + id);
			ResultSet rset = stmt.executeQuery(strSelect);
			while (rset.next()) {
				String name = rset.getString("name");
				Beer beerToList = new Beer(name);
				beerList.add(beerToList);

			}
		} catch (SQLException ex) {

			ex.printStackTrace();

		}

	}

	public Brewerie(double latitudeSet, double longitudeSet) {
		latitude = latitudeSet;
		longitude = longitudeSet;
		name = "Home";
		distanceToHome = (int) Haversine.distance(latitude, longitude, Home.getLatitude(), Home.getLongitude());
	}

	public int getDistanceToHome() {
		return distanceToHome;
	}

	public int getBeerAmmount() {
		return beerList.size();
	}

	public void addBeerList(List<Beer> list) {
		for (Beer b : list) {
			beerList.add(b);
		}
	}

	public void printBeerList() {
		for (Beer b : beerList) {
			System.out.println(b.name);
		}
	}

	private class Beer {
		String name;

		private Beer(String n) {
			name = n;
		}
	}
}