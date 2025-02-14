/**
 * This program creates an SVG file of the network of roads that  
 * 	GearPiston should construct.
 * 
 * @author Isabella Breuhl
 * Date: 04-24-2024
 */
 
import java.io.BufferedReader;    
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class GearPiston {
	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("Usage: <C-File> <SVG-filename>");
			return;
		}
		
		String input = args[0];
		String output = args[1];
		
		try {
			BufferedReader file = new BufferedReader(new FileReader(input));
			PrintWriter svg = new PrintWriter(output);
			
			//Number of cities in the fictional land of Gaganock
			int numCities = Integer.parseInt(file.readLine());
			
			//Create the new network in Gaganock
			GaganockNetwork network = new GaganockNetwork();
			
			//Reading each line in the C-file
			for (int i = 0; i < numCities; i++) {
				String[] line = file.readLine().split(" ");
				
				int population = Integer.parseInt(line[0]);
				int x = Integer.parseInt(line[1]);
				int y = Integer.parseInt(line[2]);
				String name = line[3];
				
				//Create a city and add to the Gaganock network
				City city = new City(population, x, y, name);
				network.addCity(city);
			}
			
			//Get all potential roads to build
			network.allPotentialRoads();
			
			//Get the cost-minimized road network
			network.getMinimumSpanningTree();
			
			//Write the SVG file header
			String svgHeader = "<svg viewBox=\"0 0 3000 2" +
					   "000\" version=\"1.1" +
					   "\"     baseProfile=" +
					   "\"full\"     xmlns=" +
					   "\"http://www.w3.org/" +
					   "2000/svg\"     xmlns" +
					   ":xlink=\"http://www." +
					   "w3.org/1999/xlink\" " +
					   "    xmlns:ev=\"http:" +
					   "//www.w3.org/2001/xm" +
					   "l-events\">\r\n";
			
			svg.write(svgHeader);
			
			//Add all lines (roads) to SVG file
			for (Road road : network.getRoads()) {
				String str = "<line stroke=\"black\" x1=\"" + road.getTo().getX() + 
							 "\" y1=\"" + road.getTo().getY() + "\" x2=\"" + 
							 road.getFrom().getX() + "\" y2=\"" + road.getFrom().getY() + "\"/>\n";
				svg.write(str);
			}
			
			//Add all circles (cities) to SVG file
			for (City city : network.getCities()) {
				Double radius = (double) (city.getPopulation() * 10) / 3000000;
				String str = "<circle cx=\"" + city.getX() + 
							 "\" cy=\"" + city.getY() + 
							 "\" r=\"" + radius + 
							 "\" fill=\"red\"/>\n";
				svg.write(str);
			}
			
			//Write the SVG file footer
			String svgFooter = "</svg>";
			svg.write(svgFooter);
			
			//Close both files
			file.close();
			svg.close();
			
		} catch (IOException e) {
			System.out.println("Error reading the input file...");
		}
	}
}
