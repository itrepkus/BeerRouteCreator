
import java.util.ArrayList;
import java.util.List;

public class Main {

	static List<Brewerie> breweriesList = new ArrayList<Brewerie>();

	public static void main(String[] args) {

		long startTime = System.nanoTime();

		Database.ReturnDataFromDatabase();
		if (breweriesList.isEmpty()) {
			System.out.println("There are no reachable breweries");
		} else {
			Router.createRoute();
			Router.printRoute();
		}
		long endTime = System.nanoTime();
		long totalTime = endTime - startTime;
		System.out.println("\n\n\n" + totalTime / 1000000 + " ms");
	}

	public static void setBrewerieToList(Brewerie e) {
		breweriesList.add(e);
	}

}