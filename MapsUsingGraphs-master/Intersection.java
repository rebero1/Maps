import java.util.*;

/**
* class Intersection represents intersections of the maps ( points on the map) and vertices of the graph
*
* */
public class Intersection implements Comparable<Intersection>{
	public final double latitude, longitude;   //latitude and longitude of the intersection or x and y of vertex of the graph

	public final String intersectionID;         //the intersectionID of the intersection

	public LinkedList<Intersection> path;

	public HashMap<Intersection, Edge > neighboringEdges;       //intersection and its neighboring edges

	public boolean visited;         //keeps the boolean value for visited intersections
	public double dist;             //distance to reach the intersection
	public Intersection parent;     //parent of the intersection also represented as an intersection


	/**
	 *constructor
	 * */
	public Intersection(String intersectionID, double latitude ,double longitude){

		this.intersectionID = intersectionID;
		this.latitude = latitude;
		this.longitude = longitude;


		path = new LinkedList<>();
		neighboringEdges = new HashMap <>();


		visited = false;
		dist = Double.MAX_VALUE;
		parent = null;


	}

	/**
	 * @param intersection intersection
	 * @return a specific edge that connected this and intersection
	 */
	public Edge getEdge(Intersection intersection){
		return neighboringEdges.get(intersection);
	}

	/**
	 * @param intersection intersection
	 * @param edge edge neighboring intersection
	 * add an intersection and edge (which are neighbors with this intersection) to the neighboringEdges
	 */
	private void addEdge(Intersection intersection , Edge edge ){
		neighboringEdges.put(intersection,  edge);
	}

	/**
	* @return all neighboring intersections to this intersection
	* */
	public LinkedList<Intersection> getPath(){
		return path;
	}

	/**
	 * @param intersection intersection
	 * @param edge edge close to intersection
	 * add an intersection to the path, and also add its neighboring edges
	 */
	public void addToPath(Intersection intersection , Edge edge){
		path.add(intersection);
		addEdge(intersection ,edge);
	}


	/**
	 * @return an iterator for the neighbors of the adjacent intersections
	 */
	public ListIterator<Intersection> getAllPath(){
		return path.listIterator();
	}

	/**
	 * @return a string representation of intersection
	 */
	@Override
	public String toString(){
		return intersectionID;
	}

	/**
	 * @param intersection intersection
	 * compares the distances to reach two intersections for the priority queue
	 * @return 1 if this is greater than intersection, 0 if they are equal,
	 * and -1 if this is less than intersection
	 */
	@Override
	public int compareTo(Intersection intersection) {
		if (this.dist>intersection.dist)
			return 1;
		else if (this.dist==intersection.dist)
			return 0;
		else
			return -1;
	}


	/**
	 * Uses latitude to compute this node's X location on the screen
	 * @param inMin the minimum value of latitude on the map
	 * @param inMax The maximum value of latitude on the map
	 * @param outMin The minimum value this can map TO
	 * @param outMax the maximum value this can map TO
	 * @return computeX value
	 **/
	public int scaleLatitude(Double inMin,Double inMax, Double outMin, Double outMax){
		return computeX(inMin,inMax,outMin,outMax);
	}

	/**
	 * Uses latitude to compute this node's X location on the screen
	 * @param inMin the minimum value of latitude on the map
	 * @param inMax The maximum value of latitude on the map
	 * @param outMin The minimum value this can map TO
	 * @param outMax the maximum value this can map TO
	 * @return computeY value
	 **/
	public int scaleLongitude(Double inMin,Double inMax, Double outMin, Double outMax){
		return computeY(inMin,inMax,outMin,outMax);
	}

	/**
	 * Uses latitude to compute this node's X location on the screen
	 * @param inMin the minimum value of latitude on the map
	 * @param inMax The maximum value of latitude on the map
	 * @param outMin The minimum value this can map TO
	 * @param outMax the maximum value this can map TO
	 * @return rounded mapValue
	 **/
	private int computeX(Double inMin,Double inMax, Double outMin, Double outMax) {

		return (int) Math.round(mapValue(this.latitude, inMin, inMax, outMin, outMax) );
	}

	/**
	 * Uses longitude to compute this node's Y location on the screen
	 * @param inMin the minimum value of longitude on the map
	 * @param inMax the maximum value of longitude on the map
	 * @param outMin The minimum value this can map TO
	 * @param outMax the maximum value this can map TO
	 * @return rounded mapValue
	 **/
	private int computeY(Double inMin,Double inMax, Double outMin, Double outMax) {
		return  (int) Math.round(mapValue(this.longitude, inMin, inMax, outMin, outMax) );
	}

	/**
	 * A custom implementation of the map function from Arduino - this takes
	 * in a value and maps it from one range to another
	 * @param x The value to be mapped
	 * @param inMin The minimum value of x's starting range
	 * @param inMax The maximum value of x's starting range
	 * @param outMin The minimum value of x's ending range
	 * @param outMax The maximum value of x's ending range
	 * @return the new value of x
	 **/
	private Double mapValue(Double x, Double inMin, Double inMax, Double outMin, Double outMax) {
		return (x - inMin)*(outMax - outMin) / (inMax - inMin) + outMin;
	}
}
