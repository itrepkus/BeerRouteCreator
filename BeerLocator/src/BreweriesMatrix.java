
// Class to store and work with matrix of distances betweeen breweries

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BreweriesMatrix {
	int distancesArray[][];
	Brewerie brewerieIdentifiers[];
	int ammountOfBreweries = 0;
	static int length;

	public BreweriesMatrix(int size) {
		distancesArray = new int[size][size];
		brewerieIdentifiers = new Brewerie[size];
		length = size;
	}

	public void add(Brewerie b) {
		brewerieIdentifiers[ammountOfBreweries] = b;
		ammountOfBreweries++;

	}

	public void fillDistanceArray() {
		for (int i = 0; length > i; i++) {
			for (int j = i; length > j; j++) {
				distancesArray[i][j] = (int) Haversine.distance(brewerieIdentifiers[i].latitude,
						brewerieIdentifiers[i].longitude, brewerieIdentifiers[j].latitude,
						brewerieIdentifiers[j].longitude);
				distancesArray[j][i] = distancesArray[i][j];
			}
		}
	}

	public Brewerie getClosestBrewerie(Brewerie b, int n) {
		List<DistanceWithId> breweries = new ArrayList<DistanceWithId>();
		int mainBreweryIndex = getBrewerieIndex(b);
		for (int i = 0; i < distancesArray.length; i++) {
			Brewerie brewerieToAdd = brewerieIdentifiers[i];
			int distanceToBrewerie = distancesArray[mainBreweryIndex][i];
			DistanceWithId toAdd = new DistanceWithId(brewerieToAdd, distanceToBrewerie);
			breweries.add(toAdd);
		}
		Collections.sort(breweries, new Comparator<DistanceWithId>() {
			@Override
			public int compare(DistanceWithId d1, DistanceWithId d2) {
				return Integer.valueOf(d1.getDistance()).compareTo(Integer.valueOf(d2.getDistance()));
			}
		});
		return breweries.get(n).brewerie;

	}

	public int distanceBetweenBreweries(Brewerie b1, Brewerie b2) {
		return distancesArray[getBrewerieIndex(b1)][getBrewerieIndex(b2)];
	}

	public int getBrewerieIndex(Brewerie b) {

		for (int i = 0; i < brewerieIdentifiers.length; i++) {
			if (b.id == brewerieIdentifiers[i].id) {
				return i;
			}

		}
		return 0;
	}

	// Class to create groups of Breweries and distance, used for paired list
	// creation
	private class DistanceWithId {
		Brewerie brewerie;
		int distance;

		public DistanceWithId(Brewerie b, int distanceToReach) {
			brewerie = b;
			distance = distanceToReach;
		}

		public int getDistance() {
			return distance;
		}
	}

}
