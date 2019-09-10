import java.awt.*;
import java.awt.geom.Line2D;
import java.util.*;

import javax.swing.JPanel;

public class AbstractGraph implements InterfaceGraph{
	public static final double radius = 3958.8;     //radius of earth in miles

	//for scaling the map
	private double MIN_LONGITUDE = 180.0;
	private double MAX_LONGITUDE = -180.0;
	private double MIN_LATITUDE = 180.0;
	private double MAX_LATITUDE = -180.0;


	public HashMap<String, Intersection> graph; //stores the graph which takes the intersection id and the intersection

	public LinkedList<Edge> edgesORroads; //stores a linked list of the edges of the graph also known as the roads of the map

	public static final double INFINITY = Double.MAX_VALUE; //initial constant for unknown distances

	//list of roads (edges) and intersections (vertices) for shortest path
	public LinkedList<Edge> shortestPathRoadList;
	public LinkedList<Intersection> shortestPathInterList;


	/**
	 *constructor
	 * */
	public AbstractGraph(){
		graph = new HashMap<>();
		edgesORroads = new LinkedList<>();
		shortestPathRoadList = new LinkedList<>();
		shortestPathInterList = new LinkedList<>();
	}


	public void insert( Intersection intersection ){
		graph.put(intersection.intersectionID, intersection);
		if(MAX_LATITUDE<intersection.latitude) MAX_LATITUDE=intersection.latitude;
		if(MIN_LATITUDE>intersection.latitude) MIN_LATITUDE=intersection.latitude;
		if(MAX_LONGITUDE<intersection.longitude) MAX_LONGITUDE=intersection.longitude;
		if(MIN_LONGITUDE>intersection.longitude) MIN_LONGITUDE=intersection.longitude;
	}


	public Intersection getIntersection(String intersectionID){
		return graph.get(intersectionID);
	}


	public void addEdge(Edge edge){

		Intersection start = graph.get(edge.start);
		Intersection end = graph.get(edge.end);

		start.addToPath(end, edge);
		//end.addToPath(start, edge);

		calculateEdgeWeights(edge);
		edgesORroads.add(edge);

	}

	/**
	 * @param lat1 represents the latitudinal value for intersection 1
	 * @param lon1 represents the longitudinal value  for intersection 1
	 * @param lat2 represents the latitudinal value for intersection 2
	 * @param lon2 represents the longitudinal value for intersection 2
	 * @return the distance between the two intersections
	 */

	public double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
		double dist1 = Math.pow(Math.sin(havAngle(lat1,lat2)),2) +(Math.cos(deg2rad(lon1))
				*Math.cos(deg2rad(lon2))*Math.pow(Math.sin(havAngle(lon1,lon2)),2)) ;
		double dist2 =  rad2deg(Math.asin(Math.sqrt(dist1)));
		double dist = 2*radius*(dist2);

		return dist;
	}

	/**
	 * converts degrees to radians
	 * @param deg is the angle to be changed from degrees to radians
	 * @return the radian angle
	 */
	private static double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

	/**
	 * converts radians to degrees
	 * @param rad is the angle to be changed from radians to degrees
	 * @return the degree angle
	 */
	private static double rad2deg(double rad) {
		return (rad * 180.0 / Math.PI);
	}

	/**
	 * @havAAngle returns the half of the values
	 * @param d1 represents the start intersection value
	 * @param d2 represents the final intersection value
	 * @return half of the either the latitude or longitudinal values in radians
	 * */
	private static double havAngle(double d1, double d2){
		return (deg2rad(d2-d1)/2);
	}



	public void calculateEdgeWeights(Edge edge){
		Intersection intersection1 = getIntersection(edge.start);
		double latitude1 = intersection1.latitude;
		double longitude1 = intersection1.longitude;

		Intersection intersection2 = getIntersection(edge.end);
		double latitude2 = intersection2.latitude;
		double longitude2 =  intersection2.longitude;

		edge.distance = calculateDistance(latitude1, longitude1, latitude2, longitude2);
	}



	public JPanel drawJustMap(){
		return new JustTheMap();
	}

	/**
	 * JustTheMap class is a JPanel that draws a map of the place
	 * without the shortest distances
	 */
	private class JustTheMap extends JPanel{

		public void paintComponent(Graphics graphics){

			super.paintComponent(graphics);
			ListIterator<Edge> edgeListIterator = edgesORroads.listIterator();

			Edge currEdge;
			int latitude1, longitude1, latitude2, longitude2;
			Intersection intersection1, intersection2;

			while(edgeListIterator.hasNext()){
				currEdge = edgeListIterator.next();


				//find the starting intersection and ending intersection of the edge that is being iterated
				intersection1 = getIntersection(currEdge.end);
				intersection2 = getIntersection(currEdge.start);


				//find the latitudes and longitudes of intersection 1 and intersection 2
				latitude1 =  intersection1.scaleLatitude(MIN_LATITUDE,MAX_LATITUDE,getHeight()-50.0,50.0);
				longitude1 =  intersection1.scaleLongitude(MIN_LONGITUDE,MAX_LONGITUDE,50.0,getWidth()-50.0);

				latitude2 =  intersection2.scaleLatitude(MIN_LATITUDE,MAX_LATITUDE,getHeight()-50.0,50.0);
				longitude2 =  intersection2.scaleLongitude(MIN_LONGITUDE,MAX_LONGITUDE,50.0,getWidth()-50.0);


				graphics.drawLine(longitude1,latitude1, longitude2, latitude2);
			}
		}
	}



	public JPanel drawShortestRoute(){
		return new ShortestRoutePanel();
	}

	/**
	 * ShortestPathView class is a JPanel that constructs the map with black line and colors the
	 * shortest path as red line
	 */
	private class ShortestRoutePanel extends JPanel {
		public ShortestRoutePanel(){
			super();
		}
		public void paintComponent(Graphics graphics){
			super.paintComponent(graphics);
			ListIterator<Edge> edgeListIterator = edgesORroads.listIterator();

			Edge currEdge;
			int latitude1, longitude1, latitude2, longitude2;
			Intersection intersection1, intersection2;

			while( edgeListIterator.hasNext() ){
				currEdge = edgeListIterator.next();

				//find the starting intersection and ending intersection of the edge that is being iterated
				intersection1 = getIntersection(currEdge.start);
				intersection2 = getIntersection(currEdge.end);

				//find the latitudes and longitudes of intersection 1 and intersection 2
				latitude1 =  intersection1.scaleLatitude(MIN_LATITUDE,MAX_LATITUDE,getHeight()-50.0,50.0);
				longitude1 =  intersection1.scaleLongitude(MIN_LONGITUDE,MAX_LONGITUDE,50.0,getWidth()-50.0);

				latitude2 =  intersection2.scaleLatitude(MIN_LATITUDE,MAX_LATITUDE,getHeight()-50.0,50.0);
				longitude2 =  intersection2.scaleLongitude(MIN_LONGITUDE,MAX_LONGITUDE,50.0,getWidth()-50.0);

				//System.out.println(latitude1+"lat1"+longitude1+"lon1"+latitude2+"lat2"+longitude2+"lon2");

				//check if the shortest path contains the current edge in the @edgeListIterator
				//use blue if the edge is in the shortest part, and black if otherwise

				Graphics2D g2 = (Graphics2D) graphics;
				if(shortestPathRoadList.contains(currEdge)) {
					g2.setStroke(new BasicStroke(3));
					g2.setColor(Color.RED);
				}
				else {
					g2.setStroke(new BasicStroke(1));
					g2.setColor(Color.BLACK);
				}

				g2.draw(new Line2D.Float( longitude1,latitude1, longitude2, latitude2));



			}
		}
	}


	public void dijkstra(Intersection source){
		Intersection startIntersection = graph.get(source.intersectionID);

		PriorityQueue<Intersection> queueIntersection = new PriorityQueue<>();
		LinkedList<Intersection> intersectionsList = new LinkedList<>();

		intersectionsList.addAll(graph.values());
		int notVisited = 0;

		for(Intersection currIntersection : intersectionsList){ //initialize all vertices to a queue
			if(currIntersection.intersectionID.equals(startIntersection.intersectionID))
				currIntersection.dist = 0.0;
			else
				currIntersection.dist = INFINITY;

			currIntersection.visited = false;
			notVisited++;
			currIntersection.parent = null;

			queueIntersection.add(currIntersection);
		}

		while( notVisited > 0 ){ //algorithm runs until all notVisited intersections are visited
			Intersection currentVertex = graph.get(queueIntersection.poll().intersectionID);
			currentVertex.visited = true;
			notVisited--;

			LinkedList<Intersection> adjacentIntersections = currentVertex.getPath();
			for(Intersection intersection: adjacentIntersections){
				if(!intersection.visited){
					double edgeCost = currentVertex.getEdge(intersection).distance;

					if( currentVertex.dist + edgeCost <  intersection.dist ){
						intersection.dist = currentVertex.dist + edgeCost;
						intersection.parent = currentVertex;

						queueIntersection.remove(intersection);
						queueIntersection.add(intersection);
					}
				}
			}
		}

	}


	public void printPath(Intersection destination){

		if(destination.parent!=null){

			printPath(destination.parent);
			System.out.print(" to ");
		}

		System.out.print(destination);

		shortestPathInterList.add(destination);

	}


	public void generateShortPathEdges(){

		Intersection start = shortestPathInterList.poll();
		Intersection current = shortestPathInterList.poll();


		if( start!=null && current!=null){

			Edge edge = start.getEdge(current);

			shortestPathRoadList.add(edge);

			while(!shortestPathInterList.isEmpty()){
				Intersection next = shortestPathInterList.poll();
				edge = current.getEdge(next);
				shortestPathRoadList.add(edge);
				current = next;
			}
		}
	}
}
