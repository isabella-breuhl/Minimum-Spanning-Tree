/**
 * This class create a city.
 * 
 * @author Isabella Breuhl
 * Date: 04-24-2024
 */

public class City {
	private int population;
	private int x;
	private int y;
	private String name;
	
	public City(int population, int x, int y, String name) {
		this.population = population;
		this.x = x;
		this.y = y;
		this.name = name;
	}
	
	public int getPopulation() {
		return population;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public String getName() {
		return name;
	}
}
