/**
 * class Edge represents a ROAD
 *
 */
public class Edge implements Comparable<Edge>{
	public final String start, end; //two intersections of the edge/road

	public final String RoadID;//Road ID

	public double distance;//distance of the road

	/**
	 *constructor
	* */
	public Edge( String RoadID, String start, String end){

		this.RoadID = RoadID;
		this.start = start;
		this.end = end;

		distance = 0;       //always zero because it has not been calculated
	}

	/**
	* @param edge edge
	 * compares the distances(cost) to reach two edges for the priority queue
	 * @return 1 if this is greater than edge, 0 if they are equal,
	 * and -1 if this is less than edge
	* */
	@Override
	public int compareTo(Edge edge) {

		if (this.distance>edge.distance)
			return 1;
		else if (this.distance==edge.distance)
			return 0;
		else
			return -1;

	}

	/**
	* edge represented by the String
	* */
	@Override
	public String toString(){
		return this.start + " to " + this.end;
	}
}