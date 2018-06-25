
import java.util.LinkedList;
import java.util.Queue;

public class Router {
	static Queue<travelNode> travelData = new LinkedList<travelNode>();
	static int distanceLeft;
	static boolean[] visitedBreweriesCheck = new boolean[Main.breweriesList.size()];
	static BreweriesMatrix distanceMatrix = new BreweriesMatrix(Main.breweriesList.size());
	static Brewerie home = new Brewerie(Home.getLatitude(), Home.getLongitude());
	static boolean isReachable = true;

	public static void createRoute() {
		distanceLeft = Home.getFlyingDistance();
		travelNode homeNode = new travelNode(home, 0);
		travelData.add(homeNode);
		initializeDistanceMatrix();
		Brewerie tempBrewerie = findClosestToHome();
		createTravelQueue(tempBrewerie, distanceLeft);
		travelNode finishNode = new travelNode(home, tempBrewerie.getDistanceToHome());
		travelData.add(finishNode);
		decreaseDistance(tempBrewerie.getDistanceToHome());

	}

	private static void decreaseDistance(int distance) {
		distanceLeft = distanceLeft - distance;
	}

	public static void initializeDistanceMatrix() {
		for (Brewerie b : Main.breweriesList) {
			distanceMatrix.add(b);
		}
		distanceMatrix.fillDistanceArray();
	}

	public static void createTravelQueue(Brewerie b, int distanceLeft) {
		while (isReachable) {
			for (int i = 0; i < Main.breweriesList.size(); i++) {
				Brewerie BrewerieToAdd = distanceMatrix.getClosestBrewerie(b, i);
				int distanceBetween = distanceMatrix.distanceBetweenBreweries(BrewerieToAdd, b);
				if (canBeAdded(BrewerieToAdd, distanceBetween)) {
					addVisitedBrewerie(BrewerieToAdd, distanceBetween);
					i = 0;
					b = BrewerieToAdd;
				}

			}
			isReachable = false;
		}

	}

	public static void addVisitedBrewerie(Brewerie b, int distance) {
		travelNode temp = new travelNode(b, distance);
		travelData.add(temp);
		distanceLeft = distanceLeft - distance;
		visitedBreweriesCheck[distanceMatrix.getBrewerieIndex(b)] = true;
		home.addBeerList(b.beerList);
	}

	public static boolean canBeAdded(Brewerie b, int distance) {
		int distanceAfter = distanceLeft - distance;
		int indexOfBrewerie = distanceMatrix.getBrewerieIndex(b);
		if (distanceAfter > b.distanceToHome & !visitedBreweriesCheck[indexOfBrewerie]) {
			return true;
		} else {
			return false;
		}
	}

	public static void printRoute() {
		int breweriesVisited = travelData.size();
		System.out.println("Breweries visited: " + breweriesVisited);
		while (!travelData.isEmpty()) {
			travelNode loopNode;
			Brewerie loopBrewerie;
			loopNode = travelData.remove();
			loopBrewerie = loopNode.brewerie;
			System.out.println("[" + loopBrewerie.id + "] " + loopBrewerie.name + ": " + loopBrewerie.latitude + ", "
					+ loopBrewerie.longitude + " distance " + loopNode.distance + "km");
		}
		System.out.println("\n\n\n" + "Distance traveled: " + (Home.getFlyingDistance() - distanceLeft) + "\n\n\n");
		System.out.println("Beers taken: " + home.getBeerAmmount());
		home.printBeerList();

	}

	public static void printBrewerieIndexes() {
		for (int i = 0; i < distanceMatrix.brewerieIdentifiers.length; i++) {
			System.out.println(distanceMatrix.brewerieIdentifiers[i].id);
		}

	}

	private static Brewerie findClosestToHome() {
		int lowestDistance = Integer.MAX_VALUE;
		Brewerie closestBrewerie = null;
		for (int i = 0; i < Main.breweriesList.size(); i++) {
			if (lowestDistance > Main.breweriesList.get(i).distanceToHome) {
				lowestDistance = Main.breweriesList.get(i).distanceToHome;
				closestBrewerie = Main.breweriesList.get(i);
			}

		}
		return closestBrewerie;

	}

	private static class travelNode {
		Brewerie brewerie;
		int distance;

		public travelNode(Brewerie b, int travelDistance) {
			brewerie = b;
			distance = travelDistance;
		}
	}
}
