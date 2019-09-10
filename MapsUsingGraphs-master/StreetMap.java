
import java.io.File;

public class StreetMap {
	public static void main(String[] args) {
		if (args.length<2){
			System.exit(0);
		}


		String mapFileName = new File("").getAbsoluteFile()+File.separator+args[0]; //location of the file

		String startIntersection = ""; //string representation of the starting intersection id

		String endIntersection = ""; //string representation of the ending intersection id

		boolean showWindow=false, getDirections=false;


		for(int i=1; i<args.length; i++) {
			if(args[i].contains("show")) {
				showWindow = true;
			} else if (args[i].contains("directions")) {
				getDirections = true;
				startIntersection = args[i+1];
				endIntersection = args[i+2];
			}
		}


		new MapOfPlace( mapFileName ,showWindow,getDirections, startIntersection, endIntersection);


	}
}