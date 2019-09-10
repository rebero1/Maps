
public interface InterfaceGraph {
	/**
	 * @param intersection intersection of the map
	 * insert inserts the intersection
	 */
	void insert(Intersection intersection );

	/**
	 * @return specified intersection according to it's ID
	 */
	Intersection getIntersection(String intersectionID);

	/**
	 * @param edge a new edge in the graph
	 * adds an edge to connect between intersections, and modifies the intersection path, add the edge to list of
	 * edges(road), and calculates the weights of the edge.
	 */
	void addEdge(Edge edge);

	/**
	 * alculates the distance for an edge.
	 */
	void calculateEdgeWeights(Edge current);

	/**
	 * @return a JPanel that shows the map
	 */
	javax.swing.JPanel drawJustMap();


	/**
	 * @return the JPanel that shows the shortest route
	 */
	javax.swing.JPanel drawShortestRoute();

	/**
	 * @param source is the starting point of the shortest route path
	 * generate the weighted, shortest path for the given starting intersection to every intersection in the map
	 * pseudo code from *Weiss' book* Fig 9.31
	 * source code
	 * @link https://www.cs.rochester.edu/u/brown/172/labs/cb_12_lab19_dijkstra.pdf
	 */
	void dijkstra(Intersection source);

	/**
	 * After running dijkstra algorithm, generates edges that represent the shortest path
	 */
	void generateShortPathEdges();

	/**
	 * @param destination is the destination intersection of the shortest path
	 * prints the path from the source @dijkstra to the destination
	 */
	void printPath( Intersection destination );

	/**
	 * @param lat1 represents the latitudinal value for intersection 1
	 * @param lon1 represents the longitudinal value  for intersection 1
	 * @param lat2 represents the latitudinal value for intersection 2
	 * @param lon2 represents the longitudinal value for intersection 2
	 * @return the distance between the two intersections
	 */
	double calculateDistance(double lat1, double lon1, double lat2, double lon2);
}
