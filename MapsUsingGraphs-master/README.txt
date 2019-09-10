Names: Ndabubaha Rebero Elysee Prince  netId:rndabuba
      Naijuka Roland                   NetId:rnaijuka

The program displays the map of a given data
in this case we are given ur.txt, monroe.txt, and nys.txt

The program reads input file from the terminal in a format of:
a) java StreetMap ur.txt -- show -- directions HOYT MOREY  //Showing both map
                                                           //and the directions

b) java StreetMap ur.txt -- show //Just showing the map
c) java StreetMap ur.txt -- directions HOYT MOREY  //We still show the map

NOTE: The program shows the map in all cases
1. The obstacles we overcame was implementing Dijkstra's algorithm
so efficiently so that it can run in O(|V+E|) where V--number of vertices(intersections),
and E--number of edges(roads). Thus we implemented Dijkstra's algorithm from Weiss book Fig 9.31

2. The second obstacle to overcome was scaling the latitudes and longitudes on the map,
in an effective way so that the maps are identical to the ones of the respective (individual) places
Thus we implemented the map() scale function in Arduino to help with scaling the graph
In addition to that, we implemented latitude as Y and longitude as X so that we can get the exact shape of the map

The programs files include:-
                            1. InterfaceGraph
                            2. AbstractGraph
                            3. Intersection
                            4. Edge
                            5. MapOfPlace
                            6. StreetMap
InterfaceGraph is an nterface which contains the interface functions for the graph
NOTE:
    Read InterfaceGraph to understand what each function that will be implemented does
AbstractGraph implements InterfaceGraph. This is where all the graph functions are implemented
NOTE:
    This is not an abstract class, ignore the naming
Intersection implements the vertices of the graph, which is the same as the intersections of the map
Edge implements the edges of the graph, also referred to as the Roads of the map
MapOfPlace reads the file data and draws the frame
StreetMap contains the main for running the program
NOTE:
    It can only run if the args.length passed is greater than 3


Distribution of work:
Naijuka:

    Worked on the implementation of Dijkstra's algorithm.
This was used though out the code. This included design of Nodes and Graph

Rebero

    worked on implementation of the map sketching and optmizing the time taken to draw on the graph.
With the use of scaling of latitude and longitude.

To draw a map of nys, it takes **less than 30s**
To draw a map of monroe, it takes **less than 4s**
To draw a map of ur, it takes **less than 1s**
The program runs with Dijkstra's algorithm of O(|V+E|) which means that worst case scenario is
when we have a dense graph with V^2-V edges/roads which implies that the expected run time would be O(V^2)


Extra credits:
        We scaled the map to fit the map, and resemble the original maps
        We implemented the program to efficiently run in less than 35 for nys which is the densely populated graph

