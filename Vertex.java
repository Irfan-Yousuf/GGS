package GS_ClusCoeff;

import java.util.ArrayList;

public class Vertex{

	   int vertexID;
	   boolean Sampled;	  
	   boolean visited;
	   public ArrayList <Neighbor> NeighborList;
	   
	   public Vertex(int ID){
		   
		   vertexID = ID;
		   Sampled = false;
		   visited = false;
		   NeighborList = new ArrayList<Neighbor>();
		   
	   }
	   	  
}