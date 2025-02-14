/**
 * This class creates a road connecting 2 cities.
 * 
 * @author Isabella Breuhl
 * Date: 04-24-2024
 */

public class Road {

	private City from;
	private City to;
	private double distance;
	
	public Road(City from, City to, double distance) {
		this.from = from;
		this.to = to;
		this.distance = distance;
	}
	
	public City getFrom() {
		return from;
	}
	
	public City getTo() {
		return to;
	}
	
	public double getDistance() {
		return distance;
	}
}
