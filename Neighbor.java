package GS_ClusCoeff;

public class Neighbor{
	
	int neighborID;
	boolean Sampled;
	boolean visited;	
	
	public Neighbor(int ID){
		neighborID = ID;
		Sampled=false;
		visited=false;	
	}
}