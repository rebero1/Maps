import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class MapOfPlace {
	//Name of Map
	String mapName;

	//JPanel
	JPanel panel;

	//List of intersections and edges
	public LinkedList<Intersection> intersections;
	public LinkedList<Edge> edges;

	//List of intersection and road data from file
	public LinkedList<String> intersectionData;
	public LinkedList<String> roadData;

	//data from file
	public LinkedList<String> fileData;

	//number of vertices in graph
	public int numberOfIntersections;


	public AbstractGraph graph;


	/**
	 *constructor
	 * @param fileName the name of the file
	 * @param showWindow boolean for if we should show the window
	 * @param getDirections boolean for if we should get directions
	 * @param startIntersection name of the starting intersection
	 * @param endIntersection name of the destination
	 * */
	public MapOfPlace( String fileName ,boolean showWindow, boolean getDirections, String startIntersection, String endIntersection){

		String line; //takes in the each line of the file

		//Initialize variables
		numberOfIntersections = 0;
		intersections = new LinkedList<>();
		edges = new LinkedList<>();
		roadData = new LinkedList<>();
		intersectionData = new LinkedList<>();
		fileData = new LinkedList<>();
		graph = new AbstractGraph();

		try{

			FileReader fr = new FileReader(fileName);
			BufferedReader br = new BufferedReader(fr);


			while((line=br.readLine()) != null ){ //for each line in the file

				if(line.startsWith("i")){ //add an intersection
					numberOfIntersections++;
					addToGraph(true, line ); //add to graph
				}

				else{//add an edge (also a road)
					addToGraph(false, line ); //add to graph
				}

			}//end of reading file

			br.close();

			//calculate shortest path here
			if (getDirections) {
				System.out.println("Shortest path from " + startIntersection + " to " + endIntersection);
				graph.dijkstra(graph.getIntersection(startIntersection));

				System.out.println("Shortest Path as intersections: " );
				graph.printPath(graph.getIntersection(endIntersection));

				System.out.println("\nShortest path as roads: ");
				graph.generateShortPathEdges();
			}



			//Name of map
			char path2 = fileName.charAt(fileName.length()-5);

			if (path2=='r')
				mapName="ur";
			else if (path2=='e')
				mapName="monroe";
			else
				mapName="nys";


			mapName=("Map of "+mapName).toUpperCase();

			panel = showMap(showWindow,getDirections);

			//Draw frame and draw the graph
			JFrame frame = new JFrame();

			//center the frame title
			frame.addComponentListener(new ComponentAdapter() {
				@Override
				public void componentResized(ComponentEvent e) {
					titleAlign(frame);
				}

			});
			frame.setTitle(mapName);

			frame.add(panel);
			frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
			frame.setVisible(true);

			frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);

		}

		catch(FileNotFoundException ex) {
			System.out.println("Unable to open file " + fileName);
		}

		catch(IOException ex) {
			System.out.println("Error reading file "+ fileName);
		}
	}


	/**
	 * @param isIntersection boolean is true if the line is an intersection
	 * @param currentLine the line of the file currently being read
	 * puts file content into the graph appropriately, i.e. either an intersection or road/edge
	 */
	private void addToGraph( boolean isIntersection , String currentLine ){

		String[] arrayData = currentLine.split("[\t ]");
		if (isIntersection) {
			String intersectionID = arrayData[1];
			double latitude = Double.parseDouble(arrayData[2]);
			double longitude = Double.parseDouble(arrayData[3]);

			graph.insert(new Intersection(intersectionID,latitude,longitude));
		}
		else{
			String RoadID = arrayData[1];
			String intersection1 = arrayData[2];
			String intersection2 = arrayData[3];
graph.addEdge(new Edge(RoadID,intersection2,intersection1));
			graph.addEdge(new Edge(RoadID,intersection1,intersection2));

		}

	}


	/**
	 * @param showWindow showWindow calls the map to be drawn if it is true
	 * @param getDirections get directions for the shortest path
	* */
	private JPanel showMap(boolean showWindow, boolean getDirections){
		if (showWindow&&!getDirections){
			return graph.drawJustMap();
		}

		System.out.println(graph.shortestPathRoadList);
		return graph.drawShortestRoute();

	}

	/**
	 * @param frame frame that we draw on
	 *              changes the position of the title of the frame
	* */
	private void titleAlign(JFrame frame) {

		Font font = frame.getFont();

		String currentTitle = frame.getTitle().trim();
		FontMetrics fm = frame.getFontMetrics(font);
		int frameWidth = frame.getWidth();
		int titleWidth = fm.stringWidth(currentTitle);
		int spaceWidth = fm.stringWidth(" ");
		int centerPos = (frameWidth / 2) - (titleWidth / 2);
		int spaceCount = centerPos / spaceWidth;
		String pad = "";
		pad = String.format("%" + (spaceCount - 14) + "s", pad);
		frame.setTitle(pad + currentTitle);


	}
}
