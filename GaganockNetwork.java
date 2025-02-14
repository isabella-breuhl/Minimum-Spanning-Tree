/**
 * This program creates a network for a road construction optimization in Gaganock. 
 * 
 * @author Isabella Breuhl
 * Date: 04-24-2024
 */

import java.util.ArrayList;   
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GaganockNetwork {
	private Map<City, List<Road>> connections;
	private List<Road> roads;
	
	public GaganockNetwork() {
		connections = new HashMap<>();
		roads = new ArrayList<>();
	}
	
	public List<Road> getRoads() {
		return roads;
	}
	
	public Set<City> getCities() {
		return connections.keySet();
	}
	
	public void addCity(City city) throws IllegalArgumentException {
		if (city == null) {
			throw new IllegalArgumentException("Cities cannot be null!");
		} else if (connections.containsKey(city)) {
			return;
		}
		
		connections.put(city, new ArrayList<>());
	}
	
	public Road addRoad(City from, City to, double distance) {
		if (!connections.containsKey(from) || !connections.containsKey(to)) {
			return null;
		}
		
		Road road = new Road(from, to, distance);
		connections.get(from).add(road);
		return road;
	}
	
	public void allPotentialRoads() {
		for (City from : connections.keySet()) {
			int x1 = from.getX();
			int y1 = from.getY();
			for (City to : connections.keySet()) {
				int x2 = to.getX();
				int y2 = to.getY();
				
				//Calculate straight shot distance between cities
				double distance = Math.sqrt((double) Math.pow((x2-x1), 2) + Math.pow((y2-y1), 2));
				distance = distance / (from.getPopulation() + to.getPopulation());
			
				//Create potential road and add it to the map
				Road road = new Road(from, to, Math.abs(distance));
				connections.get(from).add(road);
			}
		}
	}
	
	
	public void getMinimumSpanningTree() {
		Map<City, Double> DIST = new HashMap<>();
		Map<City, Boolean> KNOWN = new HashMap<>();
		Map<City, City> FROM = new HashMap<>();
		
		//Setting maps to starting values
		int first = 1;
		for (City city : connections.keySet()) {
			if (first == 1) {
				DIST.put(city, 0.0);
			} else {
				DIST.put(city, Double.POSITIVE_INFINITY);
			}
			
			KNOWN.put(city, false);
			FROM.put(city, null);
			first++;
		}
		
		//Prim's Algorithm
		while (true) {
			City curr = closestUnknownCity(DIST, KNOWN);
			
			if (curr == null) {
				break;
			}
			
			KNOWN.put(curr, true);
		
			for (Road road : connections.get(curr)) {
				City to = road.getTo();
				if (!KNOWN.get(to)) {
					double newDistance = road.getDistance();
					double currentDistance = DIST.get(to);
					
					if (newDistance < currentDistance) {
						DIST.put(to, newDistance);
						FROM.put(to, curr);
					}
				}
			}
		}
		
		//Add all roads to arrayList 'Roads'
		for (City city : FROM.keySet()) {
			if (FROM.get(city) != null) {
				Road road = new Road(city, FROM.get(city), DIST.get(city));
				roads.add(road);
			}
		}
	}
	
	public City closestUnknownCity(Map<City, Double> DIST, Map<City, Boolean> KNOWN) {
		if (DIST.isEmpty()) {
			return null;
		}
		
		City smallest = null;
		
		for (City city : DIST.keySet()) {
			if (smallest == null && !KNOWN.get(city)) { 
				smallest = city; 
			} else if (smallest != null && DIST.get(city) < DIST.get(smallest) && !KNOWN.get(city)) {
				smallest = city;
			} 
		}
		
		return smallest;
	}
}
