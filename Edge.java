package GS_ClusCoeff;

public class Edge{
	
	int from;
	int to;	
	double metW;
	int comp;
	
	public Edge(){}
	public Edge(int f, int t){
		from=f;
		to=t;
	}
	
	public Edge(int f, int t, double w, int c){
		from=f;
		to=t;
		metW = w;		
		comp = c;
	}	
}
